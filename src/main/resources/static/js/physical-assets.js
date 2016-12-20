var data = [];
var roleNum = window.parent.roleNum;
// 表格嵌入数据
$.ajax({
    url : "/assets/assetsList",
    type : "get",
    async: false,
    success: function(result){
        console.log(result);
//					var da = JSON.parse(result);
        for (var i = 0; i < result.data.length; i++) {
            data[i] = new Array();
            // 点击资产名称跳连接
            if(roleNum==1){
                data[i].name = "<a href=" + "/assetsDetail/publish/"+result.data[i].assetId+">" + result.data[i].name + "";
            }else if(roleNum==2){
                data[i].name = "<a href=" + "/assetsDetail/assign/"+result.data[i].assetId+">" + result.data[i].name + "";
            }


            data[i].valuation = ""+result.data[i]["value"]+"";
            if(result.data[i]["startTime"]==null){
                data[i].start_time = ""
            }else {
                data[i].start_time = ""+result.data[i]["startTime"]+"";
            }
            if(result.data[i]["endTime"]==null){
                data[i].last_time = ""
            }else{
                data[i].last_time = ""+result.data[i]["endTime"]+"";
            }
            for(var j=0;j<result.data[i]["operation"].length;j++){
                data[i].operation = new Array();
                data[i].operation.push("<a href=\"javascript:void(0);\" onclick=\"issueAssets("+result.data[i].assetId+","+result.data[i]["operation"][j]["code"]+")\">"+result.data[i]["operation"][j]["message"]+"</a>");
            }
//						data[i].can_trade = ""+result.data[i]["tradeShare"]+"";
            if(result.data[i]["guaranteed"]==true){
                data[i].guarantee = ""+result.data[i]["guarName"]+"";
            }else{
                data[i].guarantee = "无";
            }
            data[i].status = ""+result.data[i]["statusStr"]+"";
        }
    }
})
$("#table2").bootstrapTable({
    data:data
});
// 插入登记按钮
$(".fixed-table-toolbar").append('<a  class="btn btn-success" href="check-in.html">+ 登记资产</a>');

function issueAssets(assetId,operationCode) {
    switch (operationCode){
        case 3:issue(assetId);break;
        case 4:applyDelivery(assetId);break;
    }

}
function issue(assetId) {
    $.ajax({
        url : "/assets/"+assetId+"/issue",
        type : "post",
        success: function(result){
            console.log(result);
            if(result.meta.code==200){
                $("#modalAlert",parent.document).click();
                $(".modal-body",parent.document).html("发行成功");
                location.reload();
            }else{
                $("#modalAlert",parent.document).click();
                $(".modal-body",parent.document).html(result.meta.message);
                location.reload();
            }
        }
    })
}
function applyDelivery(assetId) {
    $.ajax({
        url : "/assets/"+assetId+"/applydelivery",
        type : "post",
        success: function(result){
            console.log(result);
            if(result.meta.code==200){
                $("#modalAlert",parent.document).click();
                $(".modal-body",parent.document).html("申请交割成功");
                location.reload();
            }else{
                $("#modalAlert",parent.document).click();
                $(".modal-body",parent.document).html(result.meta.message);
                location.reload();
            }
        }
    })
}