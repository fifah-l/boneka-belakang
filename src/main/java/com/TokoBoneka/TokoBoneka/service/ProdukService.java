package com.TokoBoneka.TokoBoneka.service;

import com.TokoBoneka.TokoBoneka.DTO.ProdukDTO;
import com.TokoBoneka.TokoBoneka.model.Produk;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProdukService {
    List<Produk> getAllProduk();

//    List<Produk> getAllByUser(Long idUser);

    List<Produk> getAllByAdmin(Long idAdmin);

    Optional<Produk> getProdukById(Long id);

    ProdukDTO tambahProdukDTO(Long idUser, ProdukDTO produkDTO);

    ProdukDTO editProdukDTO(Long id, Long idUser, ProdukDTO produkDTO) throws IOException;

    void deleteProduk(Long id) throws IOException;
}