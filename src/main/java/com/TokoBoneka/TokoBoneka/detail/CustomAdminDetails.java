package com.TokoBoneka.TokoBoneka.detail;

import com.TokoBoneka.TokoBoneka.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetails")
public class CustomAdminDetails implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public CustomAdminDetails(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByAdminname(username)
                .map(AdminDetail::buildAdmin)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));
    }
}
