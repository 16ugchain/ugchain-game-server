package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.CerConfig;
import com.fiveonechain.digitasset.config.JwtConfig;
import com.fiveonechain.digitasset.config.QiNiuConfig;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.ImageUploadException;
import com.fiveonechain.digitasset.service.*;
import com.fiveonechain.digitasset.util.ResultUtil;
import com.fiveonechain.digitasset.util.StringBuilderHolder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fanjl on 16/11/16.
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserAuthService iUserAuthService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private IimageUrlService iimageUrlService;
    @Autowired
    private IGuaranteeCorpService iGuaranteeCorpService;
    @Autowired
    private QiNiuConfig qiNiuConfig;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private CerConfig cerConfig;
    @Autowired
    private CertificateService certificateService;

    Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();


    StringBuilderHolder stringBuilderHolder = new StringBuilderHolder(0);


    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public Result registUser(@RequestParam("user_name") String user_name,
                             @RequestParam("password") String password,
                             @RequestParam("role") int role
                             ) {
        if (iUserService.isExistsUserName(user_name)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NAME_EXITS);
            return result;
        }
        User user = new User();
        user.setUser_name(user_name);
        user.setRole(UserRoleEnum.fromValue(role).getId());
        user.setStatus(UserStatusEnum.ACTIVE.getId());
        String md5Pwd = passwordEncoder.encode(password);
        user.setPassword(md5Pwd);
        User userGet = iUserService.insertAndGetUser(user);
        if (userGet == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Claims claims = Jwts.claims().setSubject(user_name);
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(UserRoleEnum.fromValue(role).name()));
        claims.put("scopes", authorities.stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("id", userGet.getUser_id());
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
    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    public Result bindMobile(@RequestParam("telephone") String telephone,
                             @AuthenticationPrincipal UserContext userContext
                             ) {
        User user = iUserService.getUserByUserId((long)userContext.getUserId());
        user.setTelephone(telephone);
        if (!iUserService.updateMobile(user)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success(user);
        return result;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam("user_name") String user_name,
                        @RequestParam("password") String password,
                        @RequestParam("role") int role,
                        @RequestParam(value = "auto_login", defaultValue = "false", required = false) boolean auto_login) {
        if (!iUserService.isExistsUserName(user_name)){
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }
        if (!iUserService.checkUserLogin(user_name, password)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.PASSWORD_ERROR);
            return result;
        }
        User user = iUserService.getUserByUserName(user_name);
        if(user.getRole() != role){
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_ROLE_NOT_MATCH);
            return result;
        }
        if(user.getStatus() != UserStatusEnum.ACTIVE.getId()){
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_STATUS_ERROR);
            return result;
        }
        Claims claims = Jwts.claims().setSubject(user_name);
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(UserRoleEnum.fromValue(user.getRole()).name()));
        claims.put("scopes", authorities.stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("id", user.getUser_id());
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
        Result result = ResultUtil.success(user);
        return result;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Result authenticate(@AuthenticationPrincipal UserContext userContext,
                               @RequestParam("real_name") String real_name,
                               @RequestParam("identity") String identity,
                               @RequestParam(value = "email", required = false, defaultValue = "") String email,
                               @RequestParam(value = "fixed_line", required = false, defaultValue = "") String fixed_line
    ) {
        System.out.println(userContext);
        //验证identity后设置status
        boolean isExists = iUserAuthService.isExistsSameID(identity);
        if (isExists) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IDENTITY_EXISTS);
            return result;
        }
        boolean isExistsUser = iUserAuthService.isExistsUserAuth(userContext.getUserId());
        if (isExistsUser) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.AUTHENTICATION);
            return result;
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setUser_id(userContext.getUserId());
        userAuth.setIdentity(identity);
        userAuth.setReal_name(real_name);
        userAuth.setEmail(email);
        userAuth.setFixed_line(fixed_line);
        userAuth.setStatus(UserAuthStatusEnum.SUCCESS.getId());
        if (iUserAuthService.insertAndGetUserAuth(userAuth) != 1) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success(userAuth);
        return result;
    }

    @RequestMapping("/upload")
    public Result upload(
            @RequestParam("file") MultipartFile image,
            @RequestParam("type") int type,
            @AuthenticationPrincipal UserContext userContext
    ) {
        ImageTypeEnum imageTypeEnum = ImageTypeEnum.fromValue(type);
        if (imageTypeEnum == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGETYPE_NOT_FOUND);
            return result;
        }
        if (iimageUrlService.isExists(userContext.getUserId(), type)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGE_EXISTS);
            return result;
        }
        User user = iUserService.getUserByUserId(Long.valueOf(userContext.getUserId()));
        if (user == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }
        if (image.getSize() > qiNiuConfig.getImageMaxSize()) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGE_TOO_LARGE);
            return result;
        }
        String message = "";
        try {
            byte[] imageByte = image.getBytes();
            message += imageUploadService.uploadQiNiu(imageByte);
        } catch (IOException e) {
            throw new ImageUploadException(user.getUser_id(), type);
        }

        JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
        StringBuilder urlStrBuilder = stringBuilderHolder.resetAndGet();
        if (jsonObject.has("key")) {
            String domain = qiNiuConfig.getDownloadUrl();
            String domainStr = domain.replace("\"", "");
            urlStrBuilder.append(domainStr).append(jsonObject.get("key").toString().replace("\"", ""));
            System.out.println(urlStrBuilder.toString());
        }
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUser_id(userContext.getUserId());
        imageUrl.setType(type);
        imageUrl.setUrl(urlStrBuilder.toString());
        if (iimageUrlService.insertImageUrl(imageUrl) != 1) {
            ErrorInfo errorInfo = ErrorInfo.SERVER_ERROR;
            Result result = ResultUtil.buildErrorResult(errorInfo);
            return result;
        }
        Object data = message;
        Result result = ResultUtil.success(data);
        return result;
    }


    @RequestMapping(value = "/addCorpDetail", method = RequestMethod.POST)
    public Result addCorpDetail(@AuthenticationPrincipal UserContext userContext,
                                @RequestParam("corp_name") String corp_name,
                                @RequestParam("juristic_person") String juristic_person,
                                @RequestParam("main_business") String main_business
    ) {
        //验证identity后设置status
        boolean isExists = iGuaranteeCorpService.isExists(userContext.getUserId());
        if (isExists) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.CORP_EXISTS);
            return result;
        }
        User user = iUserService.getUserByUserId(Long.valueOf(userContext.getUserId()));
        if (user == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }
        KeyPair keyPair = certificateService.generateKeyPair();
        Certificate certificate = certificateService.signSelfCertificate(keyPair, corp_name);
        byte[] pkcs12 = certificateService.generatePKCS12(certificate, keyPair, corp_name, cerConfig.getPassword());
        GuaranteeCorp guaranteeCorp = new GuaranteeCorp();
        guaranteeCorp.setCorp_name(corp_name);
        guaranteeCorp.setJuristic_person(juristic_person);
        guaranteeCorp.setMain_business(main_business);
        guaranteeCorp.setUser_id(userContext.getUserId());
        guaranteeCorp.setStatus(UserStatusEnum.ACTIVE.getId());
        guaranteeCorp.setPkcs12(pkcs12);
        if (iGuaranteeCorpService.insertCorp(guaranteeCorp) != 1) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success(guaranteeCorp);
        return result;
    }

    @RequestMapping(value = "/getCorpDetail", method = RequestMethod.POST)
    public Result getCorpDetail(@AuthenticationPrincipal UserContext userContext
    ) {
        //验证identity后设置status
        User user = iUserService.getUserByUserId(Long.valueOf(userContext.getUserId()));
        if (user == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }
        GuaranteeCorp guaranteeCorp = iGuaranteeCorpService.findByUserId(userContext.getUserId());
        Result result = ResultUtil.success(guaranteeCorp);
        return result;
    }

    @ExceptionHandler(ImageUploadException.class)
    @ResponseBody
    public Result handleUploadImageException() {
        return ResultUtil.buildErrorResult(ErrorInfo.IMAGE_UPLOAD_FAIL);
    }

}
