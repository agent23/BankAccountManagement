package com.bank.demo.bankaccountmanagement.service.impl;

import com.bank.demo.bankaccountmanagement.repository.BankAccountRepository;
import com.bank.demo.bankaccountmanagement.service.BankAccountService;
import com.bank.demo.bankaccountmanagement.service.WithdrawalEventPublisher;
import com.bank.demo.bankaccountmanagement.dto.WithdrawalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;
    private final WithdrawalEventPublisher withdrawalEventPublisher;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, WithdrawalEventPublisher withdrawalEventPublisher) {
        this.bankAccountRepository = bankAccountRepository;
        this.withdrawalEventPublisher = withdrawalEventPublisher;
    }

    @Override
    @Transactional
    public WithdrawalResult withdraw(Long accountId, BigDecimal amount) {
        logger.info("[BankAccountServiceImpl] Begin withdrawal process on account {} and amount of {}", accountId, amount);
        BigDecimal currentBalance = bankAccountRepository.getCurrentBalance(accountId);
        if (currentBalance != null && currentBalance.compareTo(amount) >= 0) {
            bankAccountRepository.updateBalance(accountId, amount);
            withdrawalEventPublisher.publishSuccessfulEvent(amount, accountId);
            return new WithdrawalResult(true);
        } else {
            withdrawalEventPublisher.publishFailedEvent(amount, accountId);
            return new WithdrawalResult(false);
        }
    }
}
