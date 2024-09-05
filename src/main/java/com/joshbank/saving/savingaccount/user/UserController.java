package com.joshbank.saving.savingaccount.user;


import com.joshbank.saving.savingaccount.admin.Admin;
import com.joshbank.saving.savingaccount.dto.DepositPostRequest;
import com.joshbank.saving.savingaccount.dto.WithdrawAmountRequest;
import com.joshbank.saving.savingaccount.transaction.Transaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin")
    public ResponseEntity<Admin> regiseterAdmin(@Valid @RequestBody Admin admin){
        return userService.registerAdmin(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        return  userService.login(user.getUsername(),user.getPassword());
    }

//    @GetMapping("/dashboard")
//    public ResponseEntity<User> dashboard(@RequestParam UUID userId){
//        return userService.updateProfile(userId,userService.login(userId.toString(),userId.toString()));
   // }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable UUID userId, @RequestBody User user){

        return userService.updateProfile(userId,user);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositPostRequest request){
        return userService.deposit(request.getUserId(),request.getAmount());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawAmount(@RequestBody WithdrawAmountRequest request){
        return userService.withdraw(request.getUserId(),request.getAmount());
    }

    @GetMapping(path = "/transactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable UUID userId){
        return userService.getTransactionHistory(userId);
    }


}
