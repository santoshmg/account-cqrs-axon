package org.bank.account;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate(repository = "jpaAccountRepository")
@Entity
public class Account {

    @AggregateIdentifier
    @Id
    private String accountId;

    @Basic
    private int balance = 0;

    @Basic
    private int overDraftLimit;

    public Account() {
    }

    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getOverDraftLimit()));
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.overDraftLimit = event.getOverDraftLimit();
    }

    @CommandHandler
    public void withdrawMoneyHandler(WithdrawMoneyCommand command) throws OverDraftLimitExceededException {
        if (balance + overDraftLimit < command.getAmount()) {
            throw new OverDraftLimitExceededException();
        }
        apply(new WithdrawnMoneyEvent(command.getAccountId(), command.getAmount(), balance - command.getAmount()));

    }

    @EventHandler
    public void on(WithdrawnMoneyEvent event) {
        this.balance = event.getBalance();
    }
}
