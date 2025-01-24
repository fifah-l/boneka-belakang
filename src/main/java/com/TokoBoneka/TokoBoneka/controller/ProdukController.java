package com.TokoBoneka.TokoBoneka.controller;

import com.TokoBoneka.TokoBoneka.DTO.ProdukDTO;
import com.TokoBoneka.TokoBoneka.model.Produk;
import com.TokoBoneka.TokoBoneka.service.ProdukService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
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
        if (produkList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Handle case when no data found
        }
        return ResponseEntity.ok(produkList);
    }

    @GetMapping("/produk/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Produk>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Produk> produkList = produkService.getAllByAdmin(idAdmin);
        if (produkList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Handle case when no data found for user
        }
        return ResponseEntity.ok(produkList);
    }

    @GetMapping("/produk/{id}")
    public ResponseEntity<Produk> getProdukById(@PathVariable Long id) {
        Optional<Produk> produk = produkService.getProdukById(id);
        return produk.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/produk/tambah/{idAdmin}")
    public ResponseEntity<ProdukDTO> tambahProduk(
            @PathVariable Long idAdmin,
            @RequestParam("produk") String produkJson,
            @RequestParam("file") MultipartFile file) throws IOException {
        // Convert the produk JSON string to ProdukDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProdukDTO produkDTO = objectMapper.readValue(produkJson, ProdukDTO.class);
        // Upload the photo and get the photo URL from ProdukImpl
        String fotoUrl = produkService.uploadFoto(file);  // Call the uploadFoto from the service implementation
        // Set the photo URL in the DTO
        produkDTO.setFotoUrl(fotoUrl);
        // Save the produk with the photo URL
        ProdukDTO savedProduk = produkService.tambahProdukDTO(idAdmin, produkDTO);
        // Log to ensure the fotoUrl is set correctly
        System.out.println("Saved Produk: " + savedProduk);
        return ResponseEntity.ok(savedProduk);
    }

    @PutMapping("/produk/edit/{id}/{idAdmin}")
    public ResponseEntity<ProdukDTO> editProduk(
            @PathVariable Long id,

        @PathVariable Long idAdmin,
        @RequestParam("produk") String produkJson,
        @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

            // Edit the other produk fields without photo
            ProdukDTO updatedProduk = produkService.editProdukDTO(id, idAdmin, produkJson, file);
            return ResponseEntity.ok(updatedProduk);
        }

    @DeleteMapping("/produk/delete/{id}")
    public ResponseEntity<Void> deleteProduk(@PathVariable Long id) throws IOException {
        produkService.deleteProduk(id);
        return ResponseEntity.noContent().build();
    }
}