package com.joshbank.saving.savingaccount.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    @Query("SELECT u FROM Admin u WHERE u.username = :username")
    Optional<Admin> findByUsername(String username);


}
