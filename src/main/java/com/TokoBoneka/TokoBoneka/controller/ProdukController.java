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

    @PutMapping(value = "/produk/editById/{id}")
    public ResponseEntity<ProdukDTO> editAnggota(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestPart(value = "anggota") ProdukDTO anggotaDTO) throws IOException {

        ProdukDTO updatedAnggota = produkService.editProdukDTO(id, idAdmin, anggotaDTO);
        return ResponseEntity.ok(updatedAnggota);
    }

    @DeleteMapping("/produk/delete/{id}")
    public ResponseEntity<Void> deleteProduk(@PathVariable Long id) throws IOException {
        produkService.deleteProduk(id);
        return ResponseEntity.noContent().build();
    }
}