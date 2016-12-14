package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.*;
import com.fiveonechain.digitasset.exception.AssetNotFoundException;
import com.fiveonechain.digitasset.exception.AssetStatusTransferException;
import com.fiveonechain.digitasset.exception.ImageUrlNotFoundException;
import com.fiveonechain.digitasset.service.*;
import com.fiveonechain.digitasset.util.DateUtil;
import com.fiveonechain.digitasset.util.ResultUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yuanshichao on 2016/11/16.
 */

@RestController
public class AssetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    private static final int MAX_CYCLE_IN_MONTH = 60;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESC_LENGTH = 200;
    private static final int MAX_CERT_SIZE = 3;
    private static final int MAX_PHOTO_SIZE = 6;

    private final List<AssetStatus> maturityStatusList = Lists.newArrayList(AssetStatus.MATURITY, AssetStatus.CLEAR);

    @Autowired
    private AssetService assetService;

    @Autowired
    private ImageUrlService imageUrlService;

    @Autowired
    private UserService userService;

    @Autowired
    private GuaranteeCorpService guarService;

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登记实物资产
     *
     */
    @RequestMapping(value = "assets", method = RequestMethod.POST)
    public Result submitAsset(
            @AuthenticationPrincipal UserContext userContext,
            @RequestParam("name") String name,
            @RequestParam("desc") String description,
            @RequestParam("cert") String certificate,
            @RequestParam("value") int value,
            @RequestParam("cycle") int cycle,
            @RequestParam(value = "photos", required = false, defaultValue = "") String photos,
            @RequestParam(value = "guarId", required = false) Integer guarId) {

        name = name.trim();
        if (StringUtils.isEmpty(name) || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("名称异常");
        }

        description = description.trim();
        if (StringUtils.isEmpty(description) || description.length() > MAX_DESC_LENGTH) {
            throw new IllegalArgumentException("描述异常");
        }

        if (cycle <= 0 || cycle > MAX_CYCLE_IN_MONTH) {
            throw new IllegalArgumentException("发行周期异常");
        }

        List<Integer> certIds = parsePictureList(certificate);
        if (certIds.isEmpty() || certIds.size() > MAX_CERT_SIZE) {
            throw new IllegalArgumentException("产权证明数量异常");
        }

        List<Integer> photoIds = parsePictureList(photos);
        if (photoIds.size() > MAX_PHOTO_SIZE) {
            throw new IllegalArgumentException("照片数量异常");
        }

        if (value <= 0) {
            throw new IllegalArgumentException("资产估值异常");
        }


        boolean needGuar = false;
        if (guarId != null) {
            needGuar = true;
        }
        if (needGuar && !userService.isUserValid(guarId, UserRoleEnum.CORP)) {
            throw new IllegalArgumentException("担保公司不存在");
        }

        int userId = userContext.getUserId();
        if (!imageUrlService.isImageIdsValid(userId, certIds, ImageTypeEnum.ASSET_CERT)) {
            throw new IllegalArgumentException("产权证明异常");
        }
        if (!imageUrlService.isImageIdsValid(userId, photoIds, ImageTypeEnum.ASSET_PHOTO)) {
            throw new IllegalArgumentException("照片异常");
        }

        int assetId = assetService.nextAssetId();

        Asset asset = new Asset();
        asset.setAssetId(assetId);
        asset.setUserId(userId);
        asset.setName(name);
        asset.setDescription(description);
        asset.setCertificate(certificate);
        asset.setPhotos(photos);
        asset.setCycle(cycle);
        asset.setValue(value);
        asset.setEvalValue(value);
        if (needGuar) {
            asset.setGuarId(guarId);
        }
        assetService.createAsset(asset, needGuar);
        return ResultUtil.success();
    }

    private List<Integer> parsePictureList(String str) {
        List<Integer> idList = new LinkedList<>();
        try {
            String[] tokens = str.split(",");
            for (String token : tokens) {
                int id = Integer.parseInt(token);
                idList.add(id);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return idList;
    }


    /**
     * 获取实物资产详情
     *
     * @param userContext
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}", method = RequestMethod.GET)
    public Result getAssetDetail(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

        Optional<Asset> assetOpt = assetService.getAssetOptional(assetId);
        if (!assetOpt.isPresent()) {
            return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
        }

        AssetDetail assetDetail = new AssetDetail();

        Asset asset = assetOpt.get();
        boolean isGuaranteed = assetService.isAssetGuaranteed(asset);
        if (isGuaranteed) {
            Optional<GuaranteeCorp> guarOpt = guarService.getGuarOptional(asset.getGuarId());
            if (!guarOpt.isPresent()) {
                LOGGER.error("{} guarId {} NOT FOUND", ErrorInfo.SERVER_ERROR, asset.getGuarId());
                return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            }
            assetDetail.setGuaranteed(true);
            assetDetail.setGuarId(asset.getGuarId());
            assetDetail.setGuarName(guarOpt.get().getCorpName());
            assetDetail.setValue(asset.getEvalValue());
        } else {
            assetDetail.setGuaranteed(false);
            assetDetail.setValue(asset.getValue());
        }
        assetDetail.setName(asset.getName());
        assetDetail.setDesc(asset.getDescription());

        try {
            List<String> certList = imageUrlService.getUrlListByImageIds(parsePictureList(asset.getCertificate()));
            List<String> photoList = imageUrlService.getUrlListByImageIds(parsePictureList(asset.getPhotos()));

            assetDetail.setCertList(certList);
            assetDetail.setPhotoList(photoList);
        } catch (ImageUrlNotFoundException e) {
            LOGGER.error("{} {}", ErrorInfo.SERVER_ERROR, e.getMessage());
            return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
        }

        return ResultUtil.success(assetDetail);
    }

    @RequestMapping(value = "assets/waitevaluate", method = RequestMethod.GET)
    public Result getWaitEvaluateAssetList(
            @AuthenticationPrincipal UserContext host) {

        if (!host.hasRole(UserRoleEnum.CORP)) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        List<Asset> assetList = assetService.getAssetListByGuarAndStatus(host.getUserId(), AssetStatus.WAIT_EVALUATE);

        List<WaitEvaluateAssetItem> assetItemList = new LinkedList<>();
        for (Asset asset : assetList) {
            WaitEvaluateAssetItem assetItem = new WaitEvaluateAssetItem();
            assetItem.setAssetId(asset.getAssetId());
            assetItem.setAssetName(asset.getName());
            assetItem.setCycle(asset.getCycle());
            assetItem.setDesc(asset.getDescription());
            assetItem.setIssuerId(asset.getUserId());

            Optional<UserInfo> userInfoOpt = userInfoService.getUserInfoOptional(asset.getUserId());
            if (!userInfoOpt.isPresent()) {
                LOGGER.error("{} issuerId {} NOT FOUND", ErrorInfo.SERVER_ERROR, asset.getUserId());
                continue;
            }
            assetItem.setIssuerName(userInfoOpt.get().getRealName());
            assetItemList.add(assetItem);
        }

        return ResultUtil.success(assetItemList);
    }

    @RequestMapping(value = "assets/evaluated", method = RequestMethod.GET)
    public Result getEvaluatedAssetList(
            @AuthenticationPrincipal UserContext host) {

        if (!host.hasRole(UserRoleEnum.CORP)) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        List<Asset> assetList = assetService.getAssetListByGuarExcludeStatus(host.getUserId(), AssetStatus.WAIT_EVALUATE);
        List<EvaluatedAssetItem> assetItemList = new LinkedList<>();
        for (Asset asset : assetList) {
            AssetStatus status = AssetStatus.fromValue(asset.getStatus());

            EvaluatedAssetItem assetItem = new EvaluatedAssetItem();
            assetItem.setAssetId(asset.getAssetId());
            assetItem.setAssetName(asset.getName());
            assetItem.setDesc(asset.getDescription());
            assetItem.setStatus(CodeMessagePair.of(status.getCode(), status.getMessage()));
            assetItem.setUpdateTime(asset.getUpdateTime());

            List<AssetOperation> operationList = assetService.getAvailableOperation(status, UserRoleEnum.CORP);
            assetItem.setOperations(mapCodeMessagePair(operationList));

            assetItemList.add(assetItem);
        }

        return ResultUtil.success(assetItemList);
    }

    private List<CodeMessagePair> mapCodeMessagePair(List<AssetOperation> operationList) {
        return operationList.stream()
                .map(m -> CodeMessagePair.of(m.getCode(), m.getMessage()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "assets/maturity", method = RequestMethod.GET)
    public Result getMaturityAssetList(
            @AuthenticationPrincipal UserContext host) {

        if ((!host.hasRole(UserRoleEnum.CORP))
                && (!host.hasRole(UserRoleEnum.USER_PUBLISHER))) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        List<Asset> assetList;
        UserRoleEnum role;
        if (host.hasRole(UserRoleEnum.CORP)) {
            assetList = assetService.getAssetListByGuarAndStatusList(host.getUserId(), maturityStatusList);
            role = UserRoleEnum.CORP;
        } else {
            assetList = assetService.getNoGuarAssetListByIssuerAndStatusList(host.getUserId(), maturityStatusList);
            role = UserRoleEnum.USER_PUBLISHER;
        }

        List<MaturityAssetItem> assetItemList = new LinkedList<>();
        for (Asset asset : assetList) {
            MaturityAssetItem assetItem = new MaturityAssetItem();
            assetItem.setAssetId(asset.getAssetId());
            assetItem.setAssetName(asset.getName());
            assetItem.setIssuerId(asset.getUserId());

            Optional<UserInfo> userInfoOpt = userInfoService.getUserInfoOptional(asset.getUserId());
            if (!userInfoOpt.isPresent()) {
                LOGGER.error("{} issuerId {} NOT FOUND", ErrorInfo.SERVER_ERROR, asset.getUserId());
                continue;
            }

            assetItem.setIssuerName(userInfoOpt.get().getRealName());
            assetItem.setEndTime(asset.getEndTime());
            AssetStatus status = AssetStatus.fromValue(asset.getStatus());
            assetItem.setStatus(CodeMessagePair.of(status.getCode(), status.getMessage()));
            List<AssetOperation> operationList = assetService.getAvailableOperation(status, role);
            assetItem.setOperations(mapCodeMessagePair(operationList));

            assetItemList.add(assetItem);
        }

        return ResultUtil.success(assetItemList);
    }

    @RequestMapping(value = "assets/delivery", method = RequestMethod.GET)
    public Result getDeliveryAssetList(
            @AuthenticationPrincipal UserContext host) {

        if (!host.hasRole(UserRoleEnum.CORP)) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        List<Asset> assetList = assetService.getAssetListByGuarAndStatus(host.getUserId(), AssetStatus.APPLY_DELIVERY);

        List<DeliveryAssetItem> assetItemList = new LinkedList<>();
        for (Asset asset : assetList) {
            DeliveryAssetItem assetItem = new DeliveryAssetItem();

            assetItem.setAssetId(asset.getAssetId());
            assetItem.setAssetName(asset.getName());
            AssetStatus status = AssetStatus.fromValue(asset.getStatus());
            assetItem.setStatus(CodeMessagePair.of(status.getCode(), status.getMessage()));
            assetItem.setIssuerId(asset.getUserId());

            Optional<UserInfo> userInfoOpt = userInfoService.getUserInfoOptional(asset.getUserId());
            if (!userInfoOpt.isPresent()) {
                LOGGER.error("{} issuerId {} NOT FOUND", ErrorInfo.SERVER_ERROR, asset.getUserId());
                continue;
            }

            assetItem.setIssuerName(userInfoOpt.get().getRealName());

            Optional<UserAsset> userAssetOpt = userAssetService.getWhollyOwnerOfAsset(asset.getAssetId(), asset.getEvalValue());
            if (!userAssetOpt.isPresent()) {
                LOGGER.error("{} wholly-owner AssetId {} NOT FOUND", ErrorInfo.SERVER_ERROR, asset.getAssetId());
                continue;
            }

            assetItem.setProposerId(userAssetOpt.get().getUserId());

            userInfoOpt = userInfoService.getUserInfoOptional(assetItem.getProposerId());
            if (!userInfoOpt.isPresent()) {
                LOGGER.error("{} wholly-owner UserId {} NOT FOUND", ErrorInfo.SERVER_ERROR, assetItem.getProposerId());
                continue;
            }

            assetItem.setProposerName(userInfoOpt.get().getRealName());

            List<AssetOperation> operationList = assetService.getAvailableOperation(status, UserRoleEnum.CORP);
            assetItem.setOperations(mapCodeMessagePair(operationList));

            assetItemList.add(assetItem);
        }

        return ResultUtil.success(assetItemList);
    }


    @RequestMapping(value = "assets/trade", method = RequestMethod.GET)
    public Result getAvailAssetList(
            @AuthenticationPrincipal UserContext host) {

        List<Asset> assetList = assetService.getAssetListByStatus(AssetStatus.ISSUE);
        assetList = filterMaturityStatusAsset(assetList);

        List<AssetItem> assetItemList = new LinkedList<>();
        for (Asset asset : assetList) {

            AssetItem item = new AssetItem();
            item.setAssetId(asset.getAssetId());
            item.setAssetName(asset.getName());
            item.setEndTime(asset.getEndTime());

            boolean isGuar = assetService.isAssetGuaranteed(asset);
            item.setGuaranteed(isGuar);
            if (isGuar) {
                Optional<GuaranteeCorp> guarOpt = guarService.getGuarOptional(asset.getGuarId());
                if (!guarOpt.isPresent()) {
                    LOGGER.error("{} guarId {} NOT FOUND", ErrorInfo.SERVER_ERROR, asset.getGuarId());
                    continue;
                }
                GuaranteeCorp guar = guarOpt.get();
                item.setGuarId(asset.getGuarId());
                item.setGuraName(guar.getCorpName());
                item.setValue(asset.getEvalValue());
                item.setExpEarnings(asset.getExpEarnings());
            } else {
                item.setValue(asset.getValue());
                item.setExpEarnings(asset.getExpEarnings());
            }

            int availShare = userAssetService.sumTradeBalanceByAsset(asset.getAssetId());
            item.setAvailShare(availShare);
            item.setPercent(availShare*100/asset.getEvalValue());

            assetItemList.add(item);
        }

        return ResultUtil.success(assetItemList);
    }

    private List<Asset> filterMaturityStatusAsset(List<Asset> assetList) {
        List<Asset> filteredAssetList = new LinkedList<>();
        for (Asset asset : assetList) {
            if (assetService.checkAssetMaturity(asset)) {
                assetService.updateAssetStatusAsync(asset.getAssetId(), AssetStatus.MATURITY);
            } else {
                filteredAssetList.add(asset);
            }
        }
        return filteredAssetList;
    }

    /**
     * 评估资产
     *
     * @param host ROLE_GUAR
     * @param result
     * @param conclusion
     * @param value
     * @param fee
     * @param earnings
     */
    @RequestMapping(value = "assets/{assetId}/evaluate", method = RequestMethod.POST)
    public Result evaluateAsset(
            @AuthenticationPrincipal UserContext host,
            @PathVariable("assetId") int assetId,
            @RequestParam("result") boolean result,
            @RequestParam(value = "conclusion", required = false) String conclusion,
            @RequestParam(value = "value", required = false) Integer value,
            @RequestParam(value = "fee", required = false) Integer fee,
            @RequestParam(value = "earnings", required = false) Integer earnings) {

        if (!host.hasRole(UserRoleEnum.CORP)) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        Optional<Asset> assetOpt = assetService.getAssetOptional(assetId);
        if (!assetOpt.isPresent()) {
            return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
        }
        Asset asset = assetOpt.get();

        if (!assetService.isAssetGuaranteed(asset, host.getUserId())) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        if (result) {
            assetService.updateAssetStatusStateMachine(host, assetId, AssetStatus.REJECT_EVALUATE);
        } else {
            asset.setEvalConclusion(conclusion);
            asset.setEvalValue(value);
            asset.setFee(fee);
            asset.setExpEarnings(earnings);

            assetService.updateAssetEvalInfo(asset);
            assetService.updateAssetStatusStateMachine(host, assetId, AssetStatus.PASS_EVALUATE);
        }

        return ResultUtil.success();
    }

    /**
     * 发行资产
     *
     * @param host
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/issue", method = RequestMethod.POST)
    public Result issueAsset(
            @AuthenticationPrincipal UserContext host,
            @PathVariable("assetId") int assetId,
            @RequestParam("payOrder") String order) {

        if (!host.hasRole(UserRoleEnum.USER_PUBLISHER)) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        Optional<Asset> assetOpt = assetService.getAssetOptional(assetId);
        if (!assetOpt.isPresent()) {
            return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
        }
        Asset asset = assetOpt.get();
        if (asset.getUserId() != assetId) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        // TODO check pay order

        LocalDateTime now = LocalDateTime.now();
        Date startTime = DateUtil.toDate(now);
        Date endTime = DateUtil.toDate(now.plusMonths(asset.getCycle()));

        asset.setStartTime(startTime);
        asset.setEndTime(endTime);

        assetService.updateAssetStatusStateMachine(host, assetId, AssetStatus.ISSUE);
        return ResultUtil.success();
    }

    /**
     * 冻结资产
     *
     * @param userContext ROLE_GUAR
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/freeze", method = RequestMethod.POST)
    public Result freezeAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

        // TODO check assetId

        assetService.updateAssetStatusStateMachine(userContext, assetId, AssetStatus.FROZEN);
        return ResultUtil.success();
    }

    /**
     * 解冻资产
     *
     * @param userContext ROLE_GUAR
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/unfreeze", method = RequestMethod.POST)
    public void unfreezeAsset(
        @AuthenticationPrincipal UserContext userContext,
        @PathVariable("assetId") int assetId) {

    }

    @ExceptionHandler(AssetStatusTransferException.class)
    @ResponseBody
    public Result handleAssetStatusTransferException() {
        return ResultUtil.buildErrorResult(ErrorInfo.ASSET_STATUS_TRANSFER_ERROR);
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseBody
    public Result handleAssetNotFoundException() {
        return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
    }
}
