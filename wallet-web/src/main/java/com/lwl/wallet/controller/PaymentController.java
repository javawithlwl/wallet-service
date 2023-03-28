package com.lwl.wallet.controller;

import com.lwl.wallet.domain.Payments;
import com.lwl.wallet.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentsService paymentsService;

    @GetMapping("/{mobile}")
    ResponseEntity<List<Payments>> getPayments(@PathVariable("mobile") String mobile){
        List<Payments> paymentsList = paymentsService.selectPayments(mobile);
        return ResponseEntity.ok(paymentsList);
    }

    @PostMapping("/addpayment")
    ResponseEntity<String> addPayments(@RequestBody Payments payments){
        long payments1 = paymentsService.insertPayment(payments);
        return ResponseEntity.ok("Payments were done with id: "+payments1);
    }
}
