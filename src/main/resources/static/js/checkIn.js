var checkIn = new Vue({
        el: "body",
        data: {
            userInfo: {
                imageId: ""
            },
            api: {
                upload: "/image/upload"
            }
        }

    }
);

$("#file-1").fileinput({
    language: 'zh', //设置语言
    uploadAsync : true,
    uploadUrl: checkIn.api.upload, // you must set a valid URL here else you will get an error
    allowedFileExtensions: ['jpg', 'png', 'gif'],
    overwriteInitial: false,
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
function saveImg(){
    $("#asset_prove").ajaxSubmit(function(message) {
        console.log(message);
        checkIn.userInfo.imageId = message.data;
    });
    return false;
}
$('#file-1').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    console.log(response);//打印出返回的json
});
$("#file-2").fileinput({
    language: 'zh', //设置语言
    uploadUrl: '#', // you must set a valid URL here else you will get an error
    allowedFileExtensions: ['jpg', 'png', 'gif'],
    overwriteInitial: true,
    // maxFileSize: 1000,
    maxFileSize: 10000,
    maxFileCount: 10,
    minFileCount: 6,
    initialCaption: "请上实物图片",
    browseClass: "btn btn-success", //按钮样式
    slugCallback: function (filename) {
        return filename.replace('(', '_').replace(']', '_');
    }
});
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
        }
    }
})
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

    // assetData.assetName   资产名称
    // assetData.assetCycle     发行周期
    // assetData.assetDescription   资产描述
    // assetData.assetAppraisement    资产估值
    // assetData.assetGuarantee      延压担保
    // assetData.guaranteeCompany     担保公司

    // post 请求
    // $.post()
});