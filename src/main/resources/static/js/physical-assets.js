var data = [];
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
            data[i].name = "<a href=" + "/assetsDetail/"+result.data[i].assetId+">" + result.data[i].name + "</a>";
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
                data[i].operation.push("<a href=\"javascript:void(0);\" onclick=\"issueAssets("+result.data[i].assetId+")\">"+result.data[i]["operation"][j]["message"]+"</a>");
            }
//						data[i].can_trade = ""+result.data[i]["tradeShare"]+"";
            data[i].guarantee = ""+result.data[i]["guarName"]+"";
            data[i].status = ""+result.data[i]["statusStr"]+"";
        }
    }
})
$("#table2").bootstrapTable({
    data:data
});
// 插入登记按钮
$(".fixed-table-toolbar").append('<a  class="btn btn-success" href="check-in.html">+ 登记资产</a>');

function issueAssets(assetId) {
    $.ajax({
        url : "/assets/"+assetId+"/issue",
        type : "post",
        success: function(result){
            console.log(result);
            if(result.meta.code==200){
                $("#modalAlert",parent.document).click();
                $(".modal-body",parent.document).html("发行成功");
            }else{
                $("#modalAlert",parent.document).click();
                $(".modal-body",parent.document).html("发行失败，请稍后再试");
            }
        }
    })
}