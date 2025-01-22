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

    @Column(name = "foto_url")
    private String fotoUrl;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    public Produk() {
        }

    public Produk(Long id, Admin admin, String namaBoneka, String deskripsi, double harga, int stok, String fotoUrl) {
            this.id = id;
            this.admin = admin;
            this.namaBoneka = namaBoneka;
            this.deskripsi = deskripsi;
            this.harga = harga;
            this.stok = stok;
            this.fotoUrl = fotoUrl;
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

        public String getDeskripsi() {
            return  deskripsi = deskripsi;
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

        public int getStok() {
            return  stok;
        }

        public void setStok(int stok) {
            this.stok = stok;
        }

        public String getFotoUrl() {
            return fotoUrl;
        }

        public void setFotoUrl(String fotoUrl) {
            this.fotoUrl = fotoUrl;
        }

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }

}