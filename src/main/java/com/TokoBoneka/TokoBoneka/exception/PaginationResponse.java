package com.TokoBoneka.TokoBoneka.exception;

public class PaginationResponse<T> {
    private String status;

    private Integer code;

    private T produk;

    private String message;

    private Object pagination;

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

    public Object getPagination() {
        return pagination;
    }

    public void setPagination(Object pagination) {
        this.pagination = pagination;
    }
}