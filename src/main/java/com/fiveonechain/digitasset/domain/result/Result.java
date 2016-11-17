package com.fiveonechain.digitasset.domain.result;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by yuanshichao on 2016/11/17.
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private final Meta meta;
    private final Object data;

    public Result(Meta meta, Object data) {
        this.meta = meta;
        this.data = data;
    }

    public Result(Meta meta) {
        this(meta, null);
    }

    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }
}

