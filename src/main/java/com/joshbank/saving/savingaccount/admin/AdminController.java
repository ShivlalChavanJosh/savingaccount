package com.joshbank.saving.savingaccount.admin;


import com.joshbank.saving.savingaccount.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  AdminService adminService;

  @PostMapping("/login")
  public Admin login(@RequestBody Admin admin){
      return adminService.login(admin.getUsername(),admin.getPassword());
  }

  @PostMapping("/customers")
  public User createUser(@RequestBody User customer){
    return adminService.createCustomer(customer);
  }

  @DeleteMapping("/customer/{customerId}")
  public void deleteuser(@PathVariable UUID customerId){
    adminService.deleteUser(customerId);
  }

  @GetMapping("/customers")
  public List<User> listUsers(){
    return adminService.listUsers();
  }

}
