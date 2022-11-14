package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

//@SuppressWarnings("unused")
public class Msg {

    private String msg;
    private int code;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NotNull
    @Override
    public String toString() {
        return "Msg{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", status=" + status +
                '}';
    }
}
