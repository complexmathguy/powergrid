package com.occulue.aggregate;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.annotation.Profile;

/**
 * Aggregate handler for Switch as outlined for the CQRS pattern, all write responsibilities 
 * related to Switch are handled here.
 * 
 * @author your_name_here
 * 
 */
@Aggregate
public class SwitchAggregate {  

	// -----------------------------------------
	// Axon requires an empty constructor
    // -----------------------------------------
    public SwitchAggregate() {
    }

	// ----------------------------------------------
	// intrinsic command handlers
	// ----------------------------------------------
    @CommandHandler
    public SwitchAggregate(CreateSwitchCommand command) throws Exception {
    	LOGGER.info( "Handling command CreateSwitchCommand" );
    	CreateSwitchEvent event = new CreateSwitchEvent(command.getSwitchId(), command.getOpen());
    	
        apply(event);
    }

    @CommandHandler
    public void handle(UpdateSwitchCommand command) throws Exception {
    	LOGGER.info( "handling command UpdateSwitchCommand" );
    	UpdateSwitchEvent event = new UpdateSwitchEvent(command.getSwitchId(), command.getOpen());        
    	
        apply(event);
    }

    @CommandHandler
    public void handle(DeleteSwitchCommand command) throws Exception {
    	LOGGER.info( "Handling command DeleteSwitchCommand" );
        apply(new DeleteSwitchEvent(command.getSwitchId()));
    }

	// ----------------------------------------------
	// association command handlers
	// ----------------------------------------------

    // single association commands

    // multiple association commands

	// ----------------------------------------------
	// intrinsic event source handlers
	// ----------------------------------------------
    @EventSourcingHandler
    void on(CreateSwitchEvent event) {	
    	LOGGER.info( "Event sourcing CreateSwitchEvent" );
    	this.switchId = event.getSwitchId();
        this.open = event.getOpen();
    }
    
    @EventSourcingHandler
    void on(UpdateSwitchEvent event) {
    	LOGGER.info( "Event sourcing classObject.getUpdateEventAlias()}" );
        this.open = event.getOpen();
    }   

	// ----------------------------------------------
	// association event source handlers
	// ----------------------------------------------


    // ------------------------------------------
    // attributes
    // ------------------------------------------
	
    @AggregateIdentifier
    private UUID switchId;
    
    private String open;

    private static final Logger LOGGER 	= Logger.getLogger(SwitchAggregate.class.getName());
}
