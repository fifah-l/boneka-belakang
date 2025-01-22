package com.TokoBoneka.TokoBoneka.service;



import com.TokoBoneka.TokoBoneka.DTO.ProdukDTO;
import com.TokoBoneka.TokoBoneka.model.Produk;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProdukService {
    List<Produk> getAllProduk();

    List<Produk> getAllByAdmin(Long idAdmin);

    Optional<Produk> getProdukById(Long id);

    ProdukDTO tambahProdukDTO(Long idAdmin, ProdukDTO produkDTO);

    ProdukDTO editProdukDTO(Long id, Long idAdmin, ProdukDTO produkDTO) throws IOException;

    String editUploadFoto(Long id, MultipartFile file) throws IOException;

    String uploadFoto(MultipartFile file) throws IOException;

    void deleteProduk(Long id) throws IOException;
}