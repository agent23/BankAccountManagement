package com.bank.demo.bankaccountmanagement.controller;

import com.bank.demo.bankaccountmanagement.service.BankAccountService;
import com.bank.demo.bankaccountmanagement.dto.WithdrawalResult;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bank")
public class BankAccountController {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    private final BankAccountService bankAccountService;
    private final MeterRegistry meterRegistry;

    public BankAccountController(BankAccountService bankAccountService, MeterRegistry meterRegistry) {
        this.bankAccountService = bankAccountService;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        meterRegistry.counter("bank.withdrawal.requests").increment();

        try {
            WithdrawalResult result = bankAccountService.withdraw(accountId, amount);
            if (result.isSuccessful()) {
                meterRegistry.counter("bank.withdrawal.successful").increment();
                meterRegistry.summary("bank.withdrawal.amount").record(amount.doubleValue());
                logger.info("Withdrawal successful for account {} in the amount of {}", accountId, amount);
                return ResponseEntity.status(HttpStatus.OK).body("Withdrawal successful");
            } else {
                meterRegistry.counter("bank.withdrawal.failed").increment();
                logger.warn("Insufficient funds for withdrawal from account {}", accountId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds for withdrawal");
            }
        } catch (Exception e) {
            meterRegistry.counter("bank.withdrawal.failed").increment();
            logger.error("Error processing withdrawal for account {}: {}", accountId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Withdrawal failed due to an unexpected error");
        }
    }



}
