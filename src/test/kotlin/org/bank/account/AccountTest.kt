package org.account

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.bank.account.*
import org.junit.Before
import org.junit.Test

class AccountTest {

    private lateinit var fixture: FixtureConfiguration<Account>

    @Before
    fun setUp() {
        fixture = AggregateTestFixture<Account>(Account::class.java)
    }

    @Test
    fun testCreateAccountCommand() {
        val accountId = "1111"
        val overDraftLimit = 10000

        fixture.givenNoPriorActivity()
                .`when`(CreateAccountCommand(accountId, overDraftLimit))
                .expectEvents(AccountCreatedEvent(accountId, overDraftLimit))

    }

    @Test
    fun testWithdrawMoney() {

        fixture.given(AccountCreatedEvent("1111", 1000))
                .`when`(WithdrawMoneyCommand("1111", 100))
                .expectEvents(WithdrawnMoneyEvent("1111", 100, -100))
    }
    @Test
    fun testWithdrawMoneyMoreThanOverDraftLimit() {

        fixture.given(AccountCreatedEvent("1111", 1000))
                .`when`(WithdrawMoneyCommand("1111", 1002))
                .expectNoEvents()
                .expectException(OverDraftLimitExceededException::class.java)
    }

    @Test
    fun testWithdrawMoneyMoreTwice() {

        fixture.given(AccountCreatedEvent("1111", 1000),
                WithdrawnMoneyEvent("1111",999, -999))
                .`when`(WithdrawMoneyCommand("1111", 2))
                .expectNoEvents()
                .expectException(OverDraftLimitExceededException::class.java)
    }

}