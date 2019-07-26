package com.zypcy.framework.fast.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一数据返回实体
 *
 * @author zhuyu
 */
@ApiModel(value = "ResponseModel", description = "统一数据返回实体")
@Data
@Accessors(chain = true)
public class ResponseModel<T> {

    /**
     * 操作返回码
     **/
    @ApiModelProperty(value = "操作返回码", example = "0000")
    private String resultCode;

    /**
     * 操作返回码文字释义
     **/
    @ApiModelProperty(value = "操作返回码文字释义", example = "操作成功")
    private String resultMessage;

    /**
     * 操作返回结果
     */
    @ApiModelProperty(value = "操作返回结果")
    private T resultObj;

    //构造函数私有化
    private ResponseModel() {
    }


    public static ResponseModel failInstance() {
        return new ResponseModel()
                .setResultCode(ResultEnum.FAIL.getResultCode())
                .setResultMessage(ResultEnum.FAIL.getResultMessage());
    }

    public static ResponseModel successInstance() {
        return new ResponseModel()
                .setResultCode(ResultEnum.SUCCESS.getResultCode())
                .setResultMessage(ResultEnum.SUCCESS.getResultMessage());
    }

    public static class Builder<T> {
        private String code;
        private String message;
        private T obj;

        public static Builder builder() {
            return new Builder();
        }

        public final <T> ResponseModel<T> build() {
            return new ResponseModel<T>()
                    .setResultCode(code)
                    .setResultMessage(message)
                    .setResultObj((T) obj);
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder obj(T obj) {
            this.obj = obj;
            return this;
        }
    }

}
