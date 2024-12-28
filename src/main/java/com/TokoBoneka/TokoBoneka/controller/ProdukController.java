package com.TokoBoneka.TokoBoneka.controller;

import com.TokoBoneka.TokoBoneka.DTO.ProdukDTO;
import com.TokoBoneka.TokoBoneka.model.Produk;
import com.TokoBoneka.TokoBoneka.service.ProdukService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProdukController {

    private final ProdukService produkService;

    public ProdukController(ProdukService produkService) {
        this.produkService = produkService;
    }

    @GetMapping("/produk/all")
    public ResponseEntity<List<Produk>> getAllProduk() {
        List<Produk> produkList = produkService.getAllProduk();
        return ResponseEntity.ok(produkList);
    }

    @GetMapping("/produk/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Produk>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Produk> produkList = produkService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(produkList);
    }

    @GetMapping("/produk/getById/{id}")
    public ResponseEntity<Produk> getProdukById(@PathVariable Long id) {
        Optional<Produk> produk = produkService.getProdukById(id);
        return produk.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/produk/tambah/{idAdmin}")
    public ResponseEntity<ProdukDTO> tambahProduk(
            @PathVariable Long idAdmin,
            @RequestBody ProdukDTO produkDTO) {
        ProdukDTO savedProduk = produkService.tambahProdukDTO(idAdmin, produkDTO);
        return ResponseEntity.ok(savedProduk);
    }

    @PutMapping("/produk/editById/{id}")
    public ResponseEntity<ProdukDTO> editProduk(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestBody ProdukDTO dataprodukDTO) throws IOException {
        ProdukDTO updatedProduk = produkService.editProdukDTO(id, idAdmin, dataprodukDTO);
        return ResponseEntity.ok(updatedProduk);
    }

    @DeleteMapping("/produk/delete/{id}")
    public ResponseEntity<Void> deleteProduk(@PathVariable Long id) throws IOException {
        System.out.println("ID yang diterima untuk penghapusan: " + id); // Debug log
        produkService.deleteProduk(id);
        return ResponseEntity.noContent().build();
    }

}