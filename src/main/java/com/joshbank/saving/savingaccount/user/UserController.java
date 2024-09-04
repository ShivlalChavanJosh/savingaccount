package com.joshbank.saving.savingaccount.user;


import com.joshbank.saving.savingaccount.admin.Admin;
import com.joshbank.saving.savingaccount.dto.DepositPostRequest;
import com.joshbank.saving.savingaccount.dto.WithdrawAmountRequest;
import com.joshbank.saving.savingaccount.transaction.Transaction;
import jakarta.validation.Valid;
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

    @PostMapping("/admin")
    public Admin regiseterAdmin(@Valid @RequestBody Admin admin){
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
    public User deposit(@RequestBody DepositPostRequest request){
        return userService.deposit(request.getUserId(),request.getAmount());
    }

    @PostMapping("/withdraw")
    public User withdrawAmount(@RequestBody WithdrawAmountRequest request){
        return userService.withdraw(request.getUserId(),request.getAmount());
    }

    @GetMapping(path = "/transactions/{userId}")
    public List<Transaction> getTransactionHistory(@PathVariable UUID userId){
        return userService.getTransactionHistory(userId);
    }


}
