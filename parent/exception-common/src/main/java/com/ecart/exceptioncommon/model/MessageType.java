package com.ecart.exceptioncommon.model;

public enum MessageType {

    NO_RECORD_EXIST("1004", "Kayıt Bulunamadı"),
    GENERAL_EXCEPTION("9999", "Genel Bir Hata Var"),
    TOKEN_IS_EXPIRED("0808", "Token Süresi Bitmiştir"),
    USERNAME_NOT_FOUND("1006", "Username Bulunamadı"),
    USENAME_OR_PASSWORD_INVALID("1007", "Kullanıcı adı veya şifre hatalı"),
    USERNAME_OR_PASSWORD_INVALID("1007", "Kullanıcı adı veya şifre hatalı");


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
