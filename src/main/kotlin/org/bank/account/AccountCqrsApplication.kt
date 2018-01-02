/*
package org.bank.account

import org.axonframework.commandhandling.AsynchronousCommandBus
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.spring.config.EnableAxon
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean

@EnableAxon
@SpringBootApplication
class AccountCqrsApplication {

    @Bean
    fun  eventStorageEngine(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }

    @Bean
    fun commandBus(): CommandBus {
        return AsynchronousCommandBus()
    }
}

fun main(args: Array<String>) {
   val context: ConfigurableApplicationContext  = SpringApplication.run(AccountCqrsApplication::class.java, *args)
    val commandBus = context.getBean(CommandBus::class.java)

    commandBus.dispatch(asCommandMessage<Any>(CreateAccountCommand("1234", 1000)))
    commandBus.dispatch(asCommandMessage<Any>(WithdrawMoneyCommand("1234", 100)))
    commandBus.dispatch(asCommandMessage<Any>(WithdrawMoneyCommand("1234", 300)))
}*/
