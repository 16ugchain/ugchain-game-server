package com.fiveonechain.digitasset.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.CerConfig;
import com.fiveonechain.digitasset.config.JwtConfig;
import com.fiveonechain.digitasset.config.QiNiuConfig;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.ImageUploadException;
import com.fiveonechain.digitasset.exception.JsonSerializableException;
import com.fiveonechain.digitasset.service.*;
import com.fiveonechain.digitasset.util.HttpClientUtil;
import com.fiveonechain.digitasset.util.RandomCharUtil;
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
    private UserInfoService iUserInfoService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private ImageUrlService imageUrlService;
    @Autowired
    private GuaranteeCorpService iGuaranteeCorpService;
    @Autowired
    private QiNiuConfig qiNiuConfig;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private CerConfig cerConfig;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private RedisService redisService;

    Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();


    StringBuilderHolder stringBuilderHolder = new StringBuilderHolder(0);

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
        if ((!value.equals(verification)) || value == null) {
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

    @RequestMapping(value = "/bindCreditCard", method = RequestMethod.POST)
    public Result bindCreditCard(@RequestParam("creditCardId") String creditCardId,
                                 @RequestParam("creditCardOwner") String creditCardOwner,
                                 @RequestParam("creditCardBank") String creditCardBank,
                                 @AuthenticationPrincipal UserContext userContext
    ) {
        UserInfo userInfo = iUserInfoService.getUserAuthByUserId(userContext.getUserId());
        if (userInfo == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        userInfo.setCreditCardId(creditCardId);
        userInfo.setCreditCardOwner(creditCardOwner);
        userInfo.setCreditCardBank(creditCardBank);
        if (!iUserInfoService.bindCreditCard(userInfo)) {
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
        if (user.getStatus() != UserStatusEnum.ACTIVE.getId()) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_STATUS_ERROR);
            return result;
        }
        Claims claims = Jwts.claims().setSubject(userName);
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(UserRoleEnum.fromValue(user.getRole()).name()));
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

    @RequestMapping(value = "/findIdentityId", method = RequestMethod.POST)
    public String findIdentityId(@RequestParam("papersNum") String identityId) {
        boolean result = iUserInfoService.isExistsSameID(identityId);
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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Result authenticate(@AuthenticationPrincipal UserContext userContext,
                               @RequestParam("real_name") String real_name,
                               @RequestParam("identity") String identity,
                               @RequestParam("identity_type") int type,
                               @RequestParam("image_id") int imageId,
                               @RequestParam(value = "email", required = false, defaultValue = "") String email,
                               @RequestParam(value = "fixed_line", required = false, defaultValue = "") String fixed_line
    ) {
        //验证identity后设置status
        boolean isExists = iUserInfoService.isExistsSameID(identity);
        if (isExists) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IDENTITY_EXISTS);
            return result;
        }
        boolean isExistsUser = iUserInfoService.isExistsUserAuth(userContext.getUserId());
        if (isExistsUser) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.AUTHENTICATION);
            return result;
        }
        List<String> imageIdStr = new ArrayList<String>();
        imageIdStr.add(String.valueOf(imageId));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userContext.getUserId());
        userInfo.setIdentity(identity);
        userInfo.setIdentityType(type);
        userInfo.setRealName(real_name);
        userInfo.setEmail(email);
        userInfo.setImageId(imageIdStr.toString());
        userInfo.setFixedLine(fixed_line);
        userInfo.setStatus(UserAuthStatusEnum.SUCCESS.getId());
        if (iUserInfoService.insertAndGetUserAuth(userInfo) != 1) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success(userInfo);
        return result;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(
            @RequestParam("imageUrl") MultipartFile image,
            @RequestParam("type") int type,
            @AuthenticationPrincipal UserContext userContext
    ) {

        ImageTypeEnum imageTypeEnum = ImageTypeEnum.fromValue(type);
        if (imageTypeEnum == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGETYPE_NOT_FOUND);
            return result;
        }
//        if (imageUrlService.isExists(userContext.getUserId(), type)) {
//            Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGE_EXISTS);
//            return result;
//        }
        User user = iUserService.getUserByUserId(userContext.getUserId());
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
            throw new ImageUploadException(user.getUserId(), type);
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
        imageUrl.setUserId(userContext.getUserId());
        imageUrl.setType(type);
        imageUrl.setUrl(urlStrBuilder.toString());
        if (imageUrlService.insertImageUrl(imageUrl) != 1) {
            ErrorInfo errorInfo = ErrorInfo.SERVER_ERROR;
            Result result = ResultUtil.buildErrorResult(errorInfo);
            return result;
        }
        Result result = ResultUtil.success(imageUrl.getImageId());
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
        User user = iUserService.getUserByUserId(userContext.getUserId());
        if (user == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }
        KeyPair keyPair = certificateService.generateKeyPair();
        Certificate certificate = certificateService.signSelfCertificate(keyPair, corp_name);
        byte[] pkcs12 = certificateService.generatePKCS12(certificate, keyPair, corp_name, cerConfig.getPassword());
        GuaranteeCorp guaranteeCorp = new GuaranteeCorp();
        guaranteeCorp.setCorpName(corp_name);
        guaranteeCorp.setJuristicPerson(juristic_person);
        guaranteeCorp.setMainBusiness(main_business);
        guaranteeCorp.setUserId(userContext.getUserId());
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
        User user = iUserService.getUserByUserId(userContext.getUserId());
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

    @ExceptionHandler(JsonSerializableException.class)
    @ResponseBody
    public Result handleJsonSerializableException() {
        return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
    }
}
