package org.message;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class MarkReadMessageCommand {
  
    @TargetAggregateIdentifier
    private String id;
  
    public MarkReadMessageCommand(String id) {
        this.id = id;
    }
     
    // ...
}