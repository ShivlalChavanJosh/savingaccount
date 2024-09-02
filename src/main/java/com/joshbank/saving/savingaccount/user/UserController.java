package com.joshbank.saving.savingaccount.user;


import com.joshbank.saving.savingaccount.admin.Admin;
import com.joshbank.saving.savingaccount.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/add")
//    private void registerUser(@RequestBody User user){
//        userService.addUser(user);
//    }

    @PostMapping("/admin")
    public Admin regiseterAdmin(@RequestBody Admin admin){
        return userService.registerAdmin(admin);
    }


    @PostMapping("/login")
    public User login(@RequestBody User user){
        return  userService.login(user.getUsername(),user.getPassword());
    }

    @GetMapping("/dashboard")
    public User dashboard(@RequestParam UUID userId){
        return userService.updateProfile(userId,userService.login(userId.toString(),userId.toString()));
    }

    @PutMapping("/profile")
    public User updateProfile(@RequestParam UUID userId,@RequestBody User user){
        return userService.updateProfile(userId,user);
    }

    @PostMapping("/deposit")
    public User deposit(@RequestParam UUID userid, BigDecimal amount){
        return userService.deposit(userid,amount);
    }

    @PostMapping("/withdraw")
    public User withdrawAmount(@RequestParam UUID userId,BigDecimal amount){
        return userService.withdraw(userId,amount);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactionHistory(@RequestParam UUID userId){
        return userService.getTransactionHistory(userId);
    }


}
