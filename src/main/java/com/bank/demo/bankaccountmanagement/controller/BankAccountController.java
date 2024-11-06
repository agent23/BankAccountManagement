package com.bank.demo.bankaccountmanagement.controller;

import com.bank.demo.bankaccountmanagement.service.BankAccountService;
import com.bank.demo.bankaccountmanagement.dto.WithdrawalResult;
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

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        logger.info("[BankAccountController] Begin withdrawal process on account {} and amount of {}", accountId, amount);
        try {
            WithdrawalResult result = bankAccountService.withdraw(accountId, amount);
            if (result.isSuccessful()) {
                logger.info("Withdrawal successful for account {} in the amount of {}", accountId, amount);
                return ResponseEntity.status(HttpStatus.OK).body("Withdrawal successful");
            } else {
                logger.warn("Insufficient funds for withdrawal from account {}", accountId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds for withdrawal");
            }
        } catch (Exception e) {
            logger.error("Error processing withdrawal for account {}: {}", accountId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Withdrawal failed due to an unexpected error");
        }
    }
}
