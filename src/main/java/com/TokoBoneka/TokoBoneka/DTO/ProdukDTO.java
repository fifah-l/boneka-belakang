package com.TokoBoneka.TokoBoneka.DTO;

public class ProdukDTO {
    private Long id;
    private Long idAdmin;
    private String NamaBoneka;
    private String Deskripsi;
    private double Harga;
    private int stok;
    private String Image;

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setHarga(double harga) {
        Harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {this.stok = stok;}

    public String getImage() {return Image;}

    public void setImage(String image) {Image = image;}
}