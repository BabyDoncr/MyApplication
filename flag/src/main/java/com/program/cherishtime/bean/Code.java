package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class Code {
    /**
     * msg : 验证码发送成功
     * code : 1
     * time : 1595843064785
     * status : 200
     * token : a4a68ed4f5c09a8c963df70377257eef
     */

    private String msg;
    private int code;
    private long time;
    private int status;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NotNull
    @Override
    public String toString() {
        return "Code{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", time=" + time +
                ", status=" + status +
                ", token='" + token + '\'' +
                '}';
    }
}
