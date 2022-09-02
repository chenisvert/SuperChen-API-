package com.example.superchen.domain.ro;

/***
 *
 * 错误响应枚举类
 * @Author chen
 * @Date  16:38
 * @Param
 * @Return
 * @Since version-11

 */
public enum ErrorCode {


    PARAMS_ERROR(201,"参数错误"),
    LOGIN_ERROR(400,"用户名或密码错误"),
    TOKEN_ERROR(403,"token错误"),
    CODE_ERROR(403,"验证码错误"),
    SERVICE_ERROR(402,"服务未开通"),
    TIMEOUT_ERROR(100,"访问频繁，请稍后再试"),
    SERVER_ERROR(500,"服务器发生错误"),
    DATA_ERROR(501,"数据获取失败"),
    PERMISSION_ERROR(401,"权限不足"),
    SESSION_ERROR(403,"请登录");


    private int ErrCode;
    private String ErrMsg;


    ErrorCode(int ErrCode , String ErrMsg){
        this.ErrCode = ErrCode;
        this.ErrMsg = ErrMsg;
    }

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrCode(int errCode) {
        ErrCode = errCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }
}
