package com.joshbank.saving.savingaccount.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByUsername(String username);

    //Optional<User> save(User user);

   // Optional<User> findByUserEmail(String userEmail);



}
