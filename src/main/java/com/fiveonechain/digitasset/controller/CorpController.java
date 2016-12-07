package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.CerConfig;
import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserStatusEnum;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.service.CertificateService;
import com.fiveonechain.digitasset.service.GuaranteeCorpService;
import com.fiveonechain.digitasset.service.UserService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.cert.Certificate;

/**
 * Created by fanjl on 2016/12/7.
 */
@RestController
@RequestMapping("/corp")
public class CorpController {
    @Autowired
    private GuaranteeCorpService iGuaranteeCorpService;
    @Autowired
    private CerConfig cerConfig;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private UserService iUserService;

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
}
