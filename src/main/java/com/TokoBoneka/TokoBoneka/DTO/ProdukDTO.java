package com.TokoBoneka.TokoBoneka.DTO;

public class ProdukDTO {
    private Long id;
    private Long idAdmin;
    private String NamaBoneka;
    private String Deskripsi;
    private double Harga;
    private int Stok;
    private String fotoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNamaBoneka() {
        return NamaBoneka;
    }

    public void setNamaBoneka(String NamaBoneka) {
        this.NamaBoneka = NamaBoneka;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String Deskripsi) {
        this.Deskripsi = Deskripsi;
    }

    public double getHarga() {
        return Harga;
    }

    public void setHarga(double Harga) {
        this.Harga = Harga;
    }

    public int getStok() {
        return Stok;
    }

    public void setStok(int Stok) {
        this.Stok = Stok;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}