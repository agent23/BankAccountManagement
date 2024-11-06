package com.bank.demo.bankaccountmanagement.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Account")
@Table(name = "t_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountId;
    private String accountHolderName;
    private String accountType;
    private String currentBalance;
    private String availableBalance;
    private String accountStatus;
    private String accountCreationDate;
}
