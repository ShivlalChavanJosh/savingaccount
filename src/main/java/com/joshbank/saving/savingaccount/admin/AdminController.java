package com.joshbank.saving.savingaccount.admin;


import com.joshbank.saving.savingaccount.user.User;
import com.joshbank.saving.savingaccount.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
  public Admin login(@RequestBody Admin admin){
      return adminService.login(admin.getUsername(),admin.getPassword());
  }

  @PostMapping("/customers")
  public User createUser(@Valid @RequestBody User customer){
    return adminService.createCustomer(customer);
  }

  @DeleteMapping(path = "/customer/{customerId}")
  public void deleteuser(@PathVariable UUID customerId){
    adminService.deleteUser(customerId);
  }

  @GetMapping("/customers")
  public List<User> listUsers(){
    return adminService.listUsers();
  }

}
