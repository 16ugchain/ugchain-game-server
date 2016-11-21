package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/21.
 */
public enum  AssetOrderStatusEnum {
    APPLY(1,"申请"),
    APPLY_OUT_TIME(2,"申请超时"),
    APPLY_REJECT(3,"驳回申请"),
//    APPLY_DONE(4,"同意申请"),
    OBLIGATIONS(5,"待支付"),
    OBLIGATIONS_OUT_TIME(6,"支付超时"),
    OBLIGATIONS_DONE(7,"支付完成"),
    COMPLETE_OUT_TIME(8,"交易完成超时（仲裁中）"),
    TRADE_SUCCESS(9,"交易成功"),
    TRADE_FAILED(10,"交易失败");

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    AssetOrderStatusEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static AssetOrderStatusEnum fromValue(int id){
        for(AssetOrderStatusEnum order : AssetOrderStatusEnum.values()){
            if(order.getId() == id){
                return order;
            }
        }
        return null;
    }



}
