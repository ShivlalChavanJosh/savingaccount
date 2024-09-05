package com.joshbank.saving.savingaccount.admin;


import com.joshbank.saving.savingaccount.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  AdminService adminService;

  @PostMapping("/login")
  public ResponseEntity<Admin> login(@RequestBody Admin admin){
      return adminService.login(admin.getUsername(),admin.getPassword());
  }

  @PostMapping("/customers")
  public ResponseEntity<User> createUser(@Valid @RequestBody User customer){
    return adminService.createCustomer(customer);
  }

  @DeleteMapping(path = "/customer/{customerId}")
  public void deleteuser(@PathVariable UUID customerId){
    adminService.deleteUser(customerId);
  }

  @GetMapping("/customers")
  public ResponseEntity<List<User>> listUsers(){
    return adminService.listUsers();
  }

}
