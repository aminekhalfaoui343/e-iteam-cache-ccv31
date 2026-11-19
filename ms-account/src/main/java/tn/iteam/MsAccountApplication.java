package tn.iteam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tn.iteam.entities.Account;
import tn.iteam.enums.AccountType;
import tn.iteam.repositories.AccountRepository; // Assurez-vous du bon package

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EnableFeignClients
@SpringBootApplication
public class MsAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAccountApplication.class, args);
        System.out.println("Application Started !!!!");
    }

    @Bean
    public CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        return args -> {

            List<Account> accountList = List.of(
                    Account.builder()
                            .accountId("ACC-001")
                            .currency("DTN")
                            .balance(15000.0)
                            .createdAt(LocalDate.now())
                            .type(AccountType.CURRENT_ACCOUNT)
                            .customerId(1L)
                            .build(),

                    Account.builder()
                            .accountId("ACC-002")
                            .currency("DTN")
                            .balance(25000.0)
                            .createdAt(LocalDate.now())
                            .type(AccountType.SAVING_ACCOUNT)
                            .customerId(2L)
                            .build()
            );

            accountRepository.saveAll(accountList);
        };
    }
}