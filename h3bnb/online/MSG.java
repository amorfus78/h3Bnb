package com.example.h3bnb.online;

public class MSG {

    private Integer success;
    private String message;
    private String nomutilisateur;

    public MSG() {
    }
    public MSG(Integer success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
