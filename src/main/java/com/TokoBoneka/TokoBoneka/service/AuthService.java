package com.TokoBoneka.TokoBoneka.service;

import com.TokoBoneka.TokoBoneka.detail.AdminDetail;
import com.TokoBoneka.TokoBoneka.model.LoginRequest;
import com.TokoBoneka.TokoBoneka.model.Admin;
import com.TokoBoneka.TokoBoneka.repository.AdminRepository;
import com.TokoBoneka.TokoBoneka.securityNew.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminDetail loadAdminByAdminname(String adminname) {
        Optional<Admin> adminOptional = adminRepository.findByAdminname(adminname);
        if (adminOptional.isPresent()) {
            return AdminDetail.buildAdmin(adminOptional.get());
        }
        throw new IllegalArgumentException("Admin not found with adminname: " + adminname);
    }

    public Map<String, Object> authenticate(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        AdminDetail adminDetails = loadAdminByAdminname(email);

        if (!passwordEncoder.matches(loginRequest.getPassword(), adminDetails.getPassword())) {
            throw new BadCredentialsException("Email atau password yang Anda masukkan salah");
        }

        String token = jwtTokenUtil.generateToken(adminDetails);

        Map<String, Object> adminProduk = new HashMap<>();
        adminProduk.put("id", adminDetails.getId());
        adminProduk.put("email", adminDetails.getEmail());
        adminProduk.put("role", adminDetails.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("adminProduk", adminProduk);
        response.put("token", token);

        return response;
    }
}