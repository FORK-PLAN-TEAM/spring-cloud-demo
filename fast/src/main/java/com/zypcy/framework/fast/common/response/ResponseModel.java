package com.zypcy.framework.fast.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一数据返回实体
 *
 * @author zhuyu
 */
@ApiModel(value = "ResponseModel", description = "统一数据返回实体")
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
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResultCode(ResultEnum.FAIL.getResultCode());
        responseModel.setResultMessage(ResultEnum.FAIL.getResultMessage());
        return responseModel;
    }

    public static ResponseModel successInstance() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResultCode(ResultEnum.SUCCESS.getResultCode());
        responseModel.setResultMessage(ResultEnum.SUCCESS.getResultMessage());
        return responseModel;
    }

    public static class Builder<T> {
        private String code;
        private String message;
        private T obj;

        public static Builder builder() {
            return new Builder();
        }

        public final <T> ResponseModel<T> build() {
            ResponseModel<T> model = new ResponseModel<T>();
            model.setResultCode(code);
            model.setResultMessage(message);
            model.setResultObj((T) obj);
            return model;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }
    }


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }
}
