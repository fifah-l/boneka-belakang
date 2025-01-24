package com.TokoBoneka.TokoBoneka.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.TokoBoneka.TokoBoneka.DTO.ProdukDTO;
import com.TokoBoneka.TokoBoneka.exception.NotFoundException;
import com.TokoBoneka.TokoBoneka.model.Admin;
import com.TokoBoneka.TokoBoneka.model.Produk;
import com.TokoBoneka.TokoBoneka.repository.AdminRepository;
import com.TokoBoneka.TokoBoneka.repository.ProdukRepository;
import com.TokoBoneka.TokoBoneka.service.ProdukService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdukImpl implements ProdukService {
    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";
    private final ProdukRepository produkRepository;
    private final AdminRepository adminRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ProdukImpl(ProdukRepository produkRepository, AdminRepository adminRepository) {
        this.produkRepository = produkRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Produk> getAllProduk() {
        return List.of();
    }

    @Override
    public List<Produk> getAllMobil() {
        return produkRepository.findAll();
    }

    @Override
    public List<Produk> getAllByAdmin(Long idAdmin) {
        return produkRepository.findByAdminId(idAdmin);
    }

    @Override
    public Optional<Produk> getProdukById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Produk> getMobilById(Long id) {
        return produkRepository.findById(id);
    }

    @Override
    public ProdukDTO tambahProdukDTO(Long idAdmin, ProdukDTO produkDTO) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        Produk produk = new Produk();
        produk.setAdmin(admin);
        produk.setNamaBoneka(produkDTO.getNamaBoneka());
        produk.setDeskripsi(produkDTO.getDeskripsi());
        produk.setHarga(produkDTO.getHarga());
        produk.setStok(produkDTO.getStok());
        produk.setFotoUrl(produkDTO.getFotoUrl());

        Produk savedProduk = produkRepository.save(produk);

        ProdukDTO result = new ProdukDTO();
        result.setId(savedProduk.getId());
        result.setNamaBoneka(savedProduk.getNamaBoneka());
        result.setDeskripsi(savedProduk.getDeskripsi());
        result.setHarga(savedProduk.getHarga());
        result.setStok(savedProduk.getStok());
        result.setFotoUrl(savedProduk.getFotoUrl());

        return result;
    }

    @Override
    public ProdukDTO editProdukDTO(Long id, Long idAdmin, ProdukDTO produkDTO) throws IOException {
        return null;
    }

    @Override
    public ProdukDTO editProdukDTO(Long id, Long idAdmin, String produkJson, MultipartFile file) throws IOException {
        // Parse produkJson menjadi ProdukDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProdukDTO produkDTO = objectMapper.readValue(produkJson, ProdukDTO.class);

        // Cari produk berdasarkan ID
        Produk existingProduk = produkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produk tidak ditemukan"));

        // Cari admin berdasarkan ID
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin dengan ID " + idAdmin + " tidak ditemukan"));

        // Update data produk
        existingProduk.setAdmin(admin);
        existingProduk.setNamaBoneka(produkDTO.getNamaBoneka());
        existingProduk.setDeskripsi(produkDTO.getDeskripsi());
        existingProduk.setHarga(produkDTO.getHarga());
        existingProduk.setStok(produkDTO.getStok());

        // Jika file foto diunggah, proses file tersebut
        if (file != null && !file.isEmpty()) {
            String fotoUrl = uploadFoto(file);
            existingProduk.setFotoUrl(fotoUrl);
        }

        // Simpan perubahan ke database
        Produk updatedProduk = produkRepository.save(existingProduk);

        // Konversi kembali ke ProdukDTO untuk respon
        ProdukDTO result = new ProdukDTO();
        result.setId(updatedProduk.getId());
        result.setNamaBoneka(updatedProduk.getNamaBoneka());
        result.setDeskripsi(updatedProduk.getDeskripsi());
        result.setHarga(updatedProduk.getHarga());
        result.setStok(updatedProduk.getStok());
        result.setFotoUrl(updatedProduk.getFotoUrl());

        return result;
    }

    @Override
    public String uploadFoto(MultipartFile file) throws IOException {
        String uploadUrl = BASE_URL + "/uploadFoto";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return extractFileUrlFromResponse(response.getBody());
        } else {
            throw new IOException("Failed to upload file: " + response.getStatusCode());
        }
    }

    @Override
    public String editUploadFoto(Long id, MultipartFile file) throws IOException {
        String editUrl = BASE_URL + "/editUploadFoto";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("fileId", id.toString());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(editUrl, HttpMethod.PUT, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return extractFileUrlFromResponse(response.getBody());
        } else {
            throw new IOException("Failed to update file: " + response.getStatusCode());
        }
    }
    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        JsonNode dataNode = jsonResponse.path("data");
        return dataNode.path("url_file").asText();
    }
    @Override
    public void deleteProduk(Long id) throws IOException {
        produkRepository.deleteById(id);
    }
}