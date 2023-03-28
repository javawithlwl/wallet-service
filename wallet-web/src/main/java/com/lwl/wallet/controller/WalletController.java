package com.lwl.wallet.controller;

import com.lwl.wallet.domain.Wallet;
import com.lwl.wallet.dto.LoadAmountDto;
import com.lwl.wallet.dto.TransferAmountDto;
import com.lwl.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;


    @PutMapping("/load-money")
    ResponseEntity<Float> loadMoney(@RequestBody LoadAmountDto loadAmountDto){
        float addBalance = walletService.addBalance(loadAmountDto.getMobile(),loadAmountDto.getSource(),loadAmountDto.getAmount());
        return ResponseEntity.ok(addBalance);
    }

    @GetMapping("/balance/{mobile}")
    ResponseEntity<Float> getBalance(@PathVariable("mobile") String mobile){
        float getBalance = walletService.getBalance(mobile);
        return ResponseEntity.ok(getBalance);
    }

    @GetMapping("/{mobile}")
    ResponseEntity<Wallet> getWallet(@PathVariable("mobile") String mobile){
        Wallet wallet = walletService.getWallet(mobile);
        return ResponseEntity.ok(wallet);
    }


    @PutMapping("/transfer-amount")
    ResponseEntity<Float> transferAmount(@RequestBody TransferAmountDto transferAmountDto){
        float transferAmount = walletService.transferAmount(transferAmountDto.getFromMobile(),transferAmountDto.getToMobile(), transferAmountDto.getAmount());
        return ResponseEntity.ok(transferAmount);
    }

    @DeleteMapping("/{mobile}")
    ResponseEntity<String> deleteWallet(@PathVariable("mobile") String mobile){
        boolean deleteWallet = walletService.deleteWallet(mobile);
        return ResponseEntity.ok(deleteWallet? "Wallet Deleted":"something Went wrong");
    }

    @PostMapping("/{mobile}")
    ResponseEntity<Long> createWallet(@PathVariable("mobile") String mobile){
        long createWallet = walletService.addWallet(mobile);
        return ResponseEntity.ok(createWallet);
    }

    @GetMapping("/mobilelist")
    ResponseEntity<List<String>> getActiveMobileList(){
        List<String> getMobileList = walletService.getActiveMobile();
        return ResponseEntity.ok(getMobileList);
    }

}
