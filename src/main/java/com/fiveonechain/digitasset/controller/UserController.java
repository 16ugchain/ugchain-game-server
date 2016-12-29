package com.fiveonechain.digitasset.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.JwtConfig;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserRoleEnum;
import com.fiveonechain.digitasset.domain.UserStatusEnum;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.ImageUploadException;
import com.fiveonechain.digitasset.exception.JsonSerializableException;
import com.fiveonechain.digitasset.service.RedisService;
import com.fiveonechain.digitasset.service.UserInfoService;
import com.fiveonechain.digitasset.service.UserService;
import com.fiveonechain.digitasset.util.HttpClientUtil;
import com.fiveonechain.digitasset.util.RandomCharUtil;
import com.fiveonechain.digitasset.util.ResultUtil;
import com.qiniu.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fanjl on 16/11/16.
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService iUserService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserInfoService userInfoService;

    Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();


    @RequestMapping(value = "/index")
    public Result index(@AuthenticationPrincipal UserContext userContext
    ) {
        User user = iUserService.getUserByUserId(userContext.getUserId());
        return ResultUtil.success(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result registUser(@RequestParam("user_name") String userName,
                             @RequestParam("password") String password
    ) {
        if (iUserService.isExistsUserName(userName)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NAME_EXITS);
            return result;
        }
        User user = new User();
        user.setUserName(userName);
        user.setRole(UserRoleEnum.USER_PUBLISHER.getId());
        user.setStatus(UserStatusEnum.ACTIVE.getId());
        String md5Pwd = passwordEncoder.encode(password);
        user.setPassword(md5Pwd);
        User userGet = iUserService.insertAndGetUser(user);
        if (userGet == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Claims claims = Jwts.claims().setSubject(userName);
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(UserRoleEnum.USER_PUBLISHER.name()));
        claims.put("scopes", authorities.stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("id", userGet.getUserId());
        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtConfig.getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime.plusMinutes(jwtConfig.getTokenExpirationTime()).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSigningKey())
                .compact();
        user.setToken(token);
        Result result = ResultUtil.success(user);
        return result;
    }

    @RequestMapping(value = "/findUserName", method = RequestMethod.POST)
    public String findUserName(@RequestParam("username") String userName
    ) {
//        if (iUserService.isExistsUserName(userName)) {
//            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NAME_EXITS);
//            return result;
//        }
        boolean result = iUserService.isExistsUserName(userName);
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", !result);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonSerializableException(e);
        }
        return resultString;
    }

    @RequestMapping(value = "/findMobile", method = RequestMethod.POST)
    public String findMobile(@RequestParam("telephone") String telephone
    ) {
        boolean result = iUserService.isExistsTelephone(telephone);
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", !result);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonSerializableException(e);
        }
        return resultString;

    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public String findRole(@AuthenticationPrincipal UserContext userContext
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("role", UserRoleEnum.fromString(userContext.getAuthorities().get(0).toString()).getId()+"");
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonSerializableException(e);
        }
        return resultString;

    }

    @RequestMapping(value = "/sendVerification", method = RequestMethod.POST)
    public Result sendVerification(@RequestParam("telephone") String telephone
    ) {
        String value = redisService.get(telephone);
        if (value != null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.VERIFY_FREQUENCY_ERROR);
            return result;
        }
        if (telephone.trim().length() == 0 || telephone == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.TELEPHONE_ILLEGAL);
            return result;
        }
        if (iUserService.isExistsTelephone(telephone)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.TELEPHONE_EXISTS);
            return result;
        }
        // TODO: 接入短信接口发送验证码
        String num = RandomCharUtil.getRandomNumberChar(6);
        String code = HttpClientUtil.getResult(telephone, num);
        if (Integer.valueOf(code) != 0) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        redisService.put(telephone, num);
        Result result = ResultUtil.success();
        return result;
    }

    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    public Result bindMobile(@RequestParam("telephone") String telephone,
                             @RequestParam("verification") String verification,
                             @AuthenticationPrincipal UserContext userContext
    ) {
        String value = redisService.get(telephone);
        System.out.println(value);
        if ((!value.equals(verification)) || StringUtils.isNullOrEmpty(value)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.TELEPHONE_VERIFY_FAIL);
            return result;
        }
        User user = iUserService.getUserByUserId(userContext.getUserId());
        user.setTelephone(telephone);
        if (!iUserService.updateMobile(user)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success();
        return result;
    }



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam("user_name") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("role") int role,
                        @RequestParam(value = "auto_login", defaultValue = "false", required = false) boolean auto_login) {
        if (!iUserService.isExistsUserName(userName)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }

        if (!iUserService.checkUserLogin(userName, password)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.PASSWORD_ERROR);
            return result;
        }
        User user = iUserService.getUserByUserName(userName);
        if (role == UserRoleEnum.CORP.getId() && user.getRole() != UserRoleEnum.CORP.getId()) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_ROLE_NOT_MATCH);
            return result;
        }
        if (role != UserRoleEnum.CORP.getId() && user.getRole() == UserRoleEnum.CORP.getId()) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_ROLE_NOT_MATCH);
            return result;
        }
        if (user.getStatus() != UserStatusEnum.ACTIVE.getId()) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_STATUS_ERROR);
            return result;
        }
        Claims claims = Jwts.claims().setSubject(userName);
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(UserRoleEnum.fromValue(role).name()));
        claims.put("scopes", authorities.stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("id", user.getUserId());
        LocalDateTime currentTime = LocalDateTime.now();
        if (auto_login) {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuer(jwtConfig.getTokenIssuer())
                    .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .setExpiration(Date.from(currentTime.plusMinutes(jwtConfig.getAutoLogintokenExpTime()).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSigningKey())
                    .compact();
            user.setToken(token);
        } else {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuer(jwtConfig.getTokenIssuer())
                    .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .setExpiration(Date.from(currentTime.plusMinutes(jwtConfig.getTokenExpirationTime()).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSigningKey())
                    .compact();
            user.setToken(token);
        }
        user.setRole(role);
        Result result = ResultUtil.success(user);
        return result;
    }



    @ExceptionHandler(ImageUploadException.class)
    @ResponseBody
    public Result handleUploadImageException() {
        return ResultUtil.buildErrorResult(ErrorInfo.IMAGE_UPLOAD_FAIL);
    }

    @ExceptionHandler(JsonSerializableException.class)
    @ResponseBody
    public Result handleJsonSerializableException() {
        return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
    }
}
