package com.TokoBoneka.TokoBoneka.controller;

import com.TokoBoneka.TokoBoneka.DTO.AdminDTO;
import com.TokoBoneka.TokoBoneka.DTO.PasswordDTO;  // Import PasswordDTO
import com.TokoBoneka.TokoBoneka.exception.CommonResponse;
import com.TokoBoneka.TokoBoneka.exception.ResponseHelper;
import com.TokoBoneka.TokoBoneka.model.Admin;
import com.TokoBoneka.TokoBoneka.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin( @RequestBody AdminDTO adminDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Admin admin = new Admin();
        admin.setAdminname(adminDTO.getAdminname());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        // Jangan set role dari input, biarkan tetap default "ADMIN"

        Admin registeredAdmin = adminService.registerAdmin(admin);
        return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmin() {
        List<Admin> admins = adminService.getAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        Admin updatedAdmin = adminService.edit(id, admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @PutMapping("/edit-password/{id}")
    public ResponseEntity<CommonResponse<Admin>> putPassword(@RequestBody PasswordDTO password, @PathVariable Long id) {
        CommonResponse<Admin> response = ResponseHelper.ok(adminService.putPasswordAdmin(password, id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        Map<String, Boolean> response = adminService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}