package com.TokoBoneka.TokoBoneka.exception;

public class CommonResponse<T> {
    private String status;

    private Integer code;

    private T produk;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getProduk() {
        return produk;
    }

    public void setProduk(T produk) {
        this.produk = produk;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}