package org.bank.account;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.EnableAxon;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@EnableAxon
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context   = SpringApplication.run(AccountApplication.class, args);
        CommandBus commandBus = context.getBean(CommandBus.class);

        commandBus.dispatch(asCommandMessage(new CreateAccountCommand("1234", 1000)), new CommandCallback<Object, Object>() {
            @Override
            public void onSuccess(CommandMessage<?> commandMessage, Object o) {
                commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", 100)));
                commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("1234", 300)));
            }

            @Override
            public void onFailure(CommandMessage<?> commandMessage, Throwable throwable) {

            }
        });
    }

    @Bean
    public  EventStorageEngine eventStorageEngine(){
        return new InMemoryEventStorageEngine();
    }

//    @Bean
//    public  CommandBus commandBus(){
//        return new AsynchronousCommandBus();
//    }

    @Bean
    public Repository<Account> jpaAccountRepository(EventBus eventBus) {
        return new GenericJpaRepository<>(entityManagerProvider(), Account.class, eventBus);
    }

    @Bean
    public EntityManagerProvider entityManagerProvider(){
        return new ContainerManagedEntityManagerProvider();
    }
    @Bean
    public EventBus eventBus(){
        return new SimpleEventBus();
    }

    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager tx){
        return new SpringTransactionManager(tx);
    }

}
