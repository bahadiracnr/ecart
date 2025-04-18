package com.ecart.exceptioncommon.model;

public enum MessageType {

    NO_RECORD_EXIST("1004", "Kayıt Bulunamadı"),
    GENERAL_EXCEPTION("9999", "Genel Bir Hata Var");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
