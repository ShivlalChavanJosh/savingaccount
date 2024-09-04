package com.joshbank.saving.savingaccount.admin;


import com.joshbank.saving.savingaccount.user.User;
import com.joshbank.saving.savingaccount.user.UserRepository;
import com.joshbank.saving.savingaccount.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private EmailUtils emailUtils;

    private PasswordEncoder passwordEncoder;

    public AdminService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Admin login(String username, String password){
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return admin;
    }

    public User createCustomer(User customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        User user = userRepository.save(customer);
        emailUtils.sendUserMail(user);
        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID customerId) {
        userRepository.deleteById(customerId);
    }
}
