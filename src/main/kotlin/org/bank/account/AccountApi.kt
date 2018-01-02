package org.bank.account

import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreateAccountCommand(
        @TargetAggregateIdentifier
        val accountId: String,
        val overDraftLimit: Int)

class WithdrawMoneyCommand(
        @TargetAggregateIdentifier
        val accountId: String,
        val amount: Int)

class AccountCreatedEvent(val accountId: String, val overDraftLimit: Int)
class WithdrawnMoneyEvent(val accountId: String, val amount: Int, val balance: Int)