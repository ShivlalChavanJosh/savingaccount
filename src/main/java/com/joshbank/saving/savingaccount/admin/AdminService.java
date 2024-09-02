package com.joshbank.saving.savingaccount.admin;


import com.joshbank.saving.savingaccount.user.User;
import com.joshbank.saving.savingaccount.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    public Admin login(String username, String password){
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return admin;
    }

    public User createCustomer(User customer) {
        return userRepository.save(customer);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID customerId) {
        userRepository.deleteById(customerId);
    }
}
