package com.lwl.wallet.controller;

import com.lwl.wallet.domain.Transactions;
import com.lwl.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{mobile}")
    ResponseEntity<List<Transactions>> selectTransacions(@PathVariable("mobile") String mobile){
        List<Transactions> transactionsList = transactionService.selectTransaction(mobile);
        return ResponseEntity.ok(transactionsList);
    }

    @RequestMapping("/addTransaction")
    ResponseEntity<String> insertTransactions(@RequestBody Transactions transactions){
        long transactions1 = transactionService.insertTransaction(transactions);
        return ResponseEntity.ok("Transactions have done with id's : " + transactions1);
    }

}
