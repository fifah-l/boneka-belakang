package com.TokoBoneka.TokoBoneka.model;

import javax.persistence.*;

@Entity
@Table(name = "produk")
public class Produk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_boneka")
    private String namaBoneka;

    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "harga")
    private double harga;

    @Column(name = "stok")
    private int stok;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    public Produk(Admin admin, String deskripsi, double harga, Long id, String namaBoneka, int stok) {
        this.admin = admin;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.id = id;
        this.namaBoneka = namaBoneka;
        this.stok = stok;
        this.image = image;
    }

    public Produk() {

    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaBoneka() {
        return namaBoneka;
    }

    public void setNamaBoneka(String namaBoneka) {
        this.namaBoneka = namaBoneka;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

}