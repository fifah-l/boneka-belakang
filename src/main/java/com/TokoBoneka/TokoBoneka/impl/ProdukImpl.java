package com.TokoBoneka.TokoBoneka.impl;

import com.TokoBoneka.TokoBoneka.DTO.ProdukDTO;
import com.TokoBoneka.TokoBoneka.exception.NotFoundException;
import com.TokoBoneka.TokoBoneka.model.Produk;
import com.TokoBoneka.TokoBoneka.model.Admin;
import com.TokoBoneka.TokoBoneka.repository.ProdukRepository;
import com.TokoBoneka.TokoBoneka.repository.AdminRepository;
import com.TokoBoneka.TokoBoneka.service.ProdukService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdukImpl implements ProdukService {

    private final ProdukRepository produkRepository;
    private final AdminRepository adminRepository;

    public ProdukImpl(ProdukRepository produkRepository, AdminRepository adminRepository) {
        this.produkRepository = produkRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Produk> getAllProduk() {
        return produkRepository.findAll();
    }

//    @Override
//    public List<Produk> getAllByUser(Long idUser) {
//        return List.of();
//    }

    @Override
    public List<Produk> getAllByAdmin(Long idAdmin) {
        return produkRepository.findByAdminId(idAdmin);
    }

    @Override
    public Optional<Produk> getProdukById(Long id) {
        return produkRepository.findById(id); // Menggunakan bawaan JpaRepository
    }

    @Override
    public ProdukDTO tambahProdukDTO(Long idAdmin, @NotNull ProdukDTO produkDTO) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin dengan ID " + idAdmin + " tidak ditemukan"));

        Produk produk = new Produk();
        produk.setAdmin(admin);
        produk.setNamaBoneka(produkDTO.getNamaBoneka());
        produk.setDeskripsi(produkDTO.getDeskripsi());
        produk.setHarga(produkDTO.getHarga());
        produk.setStok(produkDTO.getStok());
        produk.setImage(produkDTO.getImage()); // Menyimpan URL gambar

        Produk savedProduk = produkRepository.save(produk);

        ProdukDTO result = new ProdukDTO();
        result.setId(savedProduk.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaBoneka(savedProduk.getNamaBoneka());
        result.setDeskripsi(savedProduk.getDeskripsi());
        result.setHarga(savedProduk.getHarga());
        result.setStok(savedProduk.getStok());
        result.setImage(savedProduk.getImage()); // Menyimpan URL gambar

        return result;
    }

    @Override
    public ProdukDTO editProdukDTO(Long id, Long idAdmin, @NotNull ProdukDTO produkDTO) throws IOException {
        Produk existingProduk = produkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produk tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin dengan ID " + idAdmin + " tidak ditemukan"));

        existingProduk.setAdmin(admin);
        existingProduk.setNamaBoneka(produkDTO.getNamaBoneka());
        existingProduk.setDeskripsi(produkDTO.getDeskripsi());
        existingProduk.setHarga(produkDTO.getHarga());
        existingProduk.setStok(produkDTO.getStok());
        existingProduk.setImage(produkDTO.getImage()); // Memperbarui URL gambar

        Produk updatedProduk = produkRepository.save(existingProduk);

        ProdukDTO result = new ProdukDTO();
        result.setId(updatedProduk.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaBoneka(updatedProduk.getNamaBoneka());
        result.setDeskripsi(updatedProduk.getDeskripsi());
        result.setHarga(updatedProduk.getHarga());
        result.setStok(updatedProduk.getStok());
        result.setImage(updatedProduk.getImage()); // Menyimpan URL gambar

        return result;
    }


    @Override
    public void deleteProduk(Long id) throws IOException {
        if (!produkRepository.existsById(id)) {
            throw new IllegalArgumentException("Produk dengan ID " + id + " tidak ditemukan.");
        }
        produkRepository.deleteById(id);
    }

}