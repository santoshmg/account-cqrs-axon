package org.message;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.message.MessageCreatedEvent;
import org.message.CreateMessageCommand;
import org.message.MessagesAggregate;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class MessagesAggregateTest {
    private FixtureConfiguration<MessagesAggregate> fixture;

    @Before
    public void setUp() throws Exception {
        fixture =
                new AggregateTestFixture<MessagesAggregate>(MessagesAggregate.class);
    }

    @Test
    public void testCreateMessageCommand() throws Exception {

        String eventText = "Hello, how is your day?";
        String id = UUID.randomUUID().toString();
        fixture.given()
                .when(new CreateMessageCommand(id, eventText))
                .expectEvents(new MessageCreatedEvent(id, eventText));
    }
}