
package tn.iteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.iteam.bridges.CustomerClientFeign;
import tn.iteam.entities.Account;
import tn.iteam.models.Customer;
import tn.iteam.services.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
    // Faire appel à la couche Service
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerClientFeign customerClientFeign;


    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get account by ID
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable String id) {
        Account account = accountService.getAccountById(id);
        // Connect to MS CUSTOMER
        Customer customer =    customerClientFeign.getCustomerById(account.getCustomerId());
        account.setCustomer(customer);

        return account;
    }

    // Save a new account
    @PostMapping
    public void saveCustomer(@RequestBody Account account) {
        accountService.saveAccount(account);
    }
}
