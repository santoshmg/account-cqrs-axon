package org.bank.account;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

public class Application {

    public static void main(String[] args) {
        Configuration configuration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Account.class)
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .configureCommandBus(c -> new AsynchronousCommandBus())
                .buildConfiguration();

        configuration.start();
        configuration.commandBus().dispatch(asCommandMessage(new  CreateAccountCommand("1234", 1000)));
        configuration.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", 100)));
        configuration.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", 20000)));
    }
}
