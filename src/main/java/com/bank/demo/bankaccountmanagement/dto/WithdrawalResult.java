package com.bank.demo.bankaccountmanagement.dto;

public class WithdrawalResult {
    private final boolean successful;

    public WithdrawalResult(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
