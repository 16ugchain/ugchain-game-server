var asset = new Vue({
    el: "#asset",
    data: {
        assetData: {
            "assetName": "",
            "assetCycle": "",
            "assetDescription": "",
            "assetAppraisement": "",
            "assetGuarantee": "",
            "guaranteeCompany": ""
        },
        userInfo: {
            proveImgId: [],
            assetImgId: []
        },
        api: {
            upload: "/image/upload",
            createAsset: "/assets"
        }
    }
});


$("#file-1").fileinput({
    language: 'zh', //设置语言
    uploadUrl: asset.api.upload, // you must set a valid URL here else you will get an error
    allowedFileExtensions: ['jpg', 'png', 'gif'],
    overwriteInitial: false,
    showUpload: true, //是否显示上传按钮
    // maxFileSize: 1000,
    maxFileSize: 10000,
    maxFileCount: 5,
    minFilesNum: 1,
    initialCaption: "请上传产权证明",
    browseClass: "btn btn-success", //按钮样式
    slugCallback: function (filename) {
        console.log(filename);
        return filename.replace('(', '_').replace(']', '_');
    }
});
$("#file-1").on("fileuploaded", function (event, data, previewId, index) {
    var da = data.response;
    asset.userInfo.proveImgId.push(da["data"]);
    console.log(asset.userInfo.proveImgId);
});
$("#file-2").fileinput({
    language: 'zh', //设置语言
    uploadUrl: asset.api.upload, // you must set a valid URL here else you will get an error
    allowedFileExtensions: ['jpg', 'png', 'gif'],
    showUpload: true, //是否显示上传按钮
    overwriteInitial: true,
    // maxFileSize: 1000,
    maxFileSize: 10000,
    maxFileCount: 10,
    minFileCount: 6,
    initialCaption: "请上传实物图片",
    browseClass: "btn btn-success", //按钮样式
    slugCallback: function (filename) {
        return filename.replace('(', '_').replace(']', '_');
    }
});
$("#file-2").on("fileuploaded", function (event, data, previewId, index) {
    var da = data.response;
    asset.userInfo.assetImgId.push(da["data"]);
    console.log(asset.userInfo.assetImgId);
});


$('form').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        // 资产名称
        asset_name: {
            message: '资产名称验证失败',
            validators: {
                notEmpty: {
                    message: '资产名称不能为空'
                }
            }
        },
        // 发行周期
        asset_cycle: {
            message: '发行周期失败',
            validators: {
                notEmpty: {
                    message: '发行周期不能为空'
                },
                regexp: {
                    regexp: /[1-6]|[1-9]+$/,
                    message: '发行周期不能超过60个月'
                }
            }
        },
        // 资产描述
        asset_description: {
            message: '资产名称验证失败',
            validators: {
                notEmpty: {
                    message: '资产描述不能为空'
                }
            }
        },
        // 资产估值
        asset_appraisement: {
            message: '资产名称验证失败',
            validators: {
                notEmpty: {
                    message: '资产估值不能为空'
                }
            }
        },
        // 担保公司
        guarantee_company: {
            message: '资产名称验证失败',
            validators: {
                notEmpty: {
                    message: '资产名称不能为空'
                }
            }
        },
    }
}).on('success.form.bv', function (e) {
    // 全部验证通过，提交校验
    e.preventDefault();
    var certStr = asset.userInfo.proveImgId.join(",");
    var photosStr = asset.userInfo.assetImgId.join(",");
    console.log(certStr);
    console.log(photosStr);
    $.post(asset.api.createAsset, {
        name: asset.assetData.assetName,
        desc: asset.assetData.assetDescription,
        cert: certStr,
        value: asset.assetData.assetAppraisement,
        cycle: asset.assetData.assetCycle,
        photos: photosStr,
        guarId: asset.assetData.guaranteeCompany
    }, function (data) {
        console.log(data);
        if (data.meta.code === 200) {
            console.log(data.meta.message);
            window.location.href="/physical-assets.html";
        }
    })
    // post 请求
    // $.post()
});
