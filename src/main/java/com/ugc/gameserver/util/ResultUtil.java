package com.ugc.gameserver.util;


import com.ugc.gameserver.domain.result.ErrorInfo;
import com.ugc.gameserver.domain.result.Meta;
import com.ugc.gameserver.domain.result.Result;

/**
 * Created by yuanshichao on 2016/11/17.
 */
public class ResultUtil {

    public static Result success(Object data) {
        Meta meta = new Meta(200, "success");
        return new Result(meta, data);
    }

    public static Result success() {
        return success(null);
    }

    public static Result buildErrorResult(ErrorInfo info) {
        Meta meta = new Meta(info.getErrorCode(), info.getErrorMessage());
        return new Result(meta);
    }

}
