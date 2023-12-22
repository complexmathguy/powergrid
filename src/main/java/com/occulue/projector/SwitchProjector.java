/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.projector;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.repository.*;

/**
 * Projector for Switch as outlined for the CQRS pattern.  All event handling and query handling related to Switch are invoked here
 * and dispersed as an event to be handled elsewhere.
 * 
 * Commands are handled by SwitchAggregate
 * 
 * @author your_name_here
 *
 */
//@ProcessingGroup("switch")
@Component("switch-projector")
public class SwitchProjector extends SwitchEntityProjector {
		
	// core constructor
	public SwitchProjector(SwitchRepository repository, QueryUpdateEmitter queryUpdateEmitter ) {
        super(repository);
        this.queryUpdateEmitter = queryUpdateEmitter;
    }	

	/*
     * @param	event CreateSwitchEvent
     */
    @EventHandler( payloadType=CreateSwitchEvent.class )
    public void handle( CreateSwitchEvent event) {
	    LOGGER.info("handling CreateSwitchEvent - " + event );
	    Switch entity = new Switch();
        entity.setSwitchId( event.getSwitchId() );
        entity.setOpen( event.getOpen() );
	    
    	// ------------------------------------------
    	// persist a new one
    	// ------------------------------------------ 
	    create(entity);
        
        // ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllSwitch( entity );
    }

    /*
     * @param	event UpdateSwitchEvent
     */
    @EventHandler( payloadType=UpdateSwitchEvent.class )
    public void handle( UpdateSwitchEvent event) {
    	LOGGER.info("handling UpdateSwitchEvent - " + event );
    	
	    Switch entity = new Switch();
        entity.setSwitchId( event.getSwitchId() );
        entity.setOpen( event.getOpen() );
 
    	// ------------------------------------------
    	// save with an existing instance
    	// ------------------------------------------ 
		update(entity);

    	// ------------------------------------------
    	// emit to subscribers that find one
    	// ------------------------------------------    	
        emitFindSwitch( entity );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllSwitch( entity );        
    }
    
    /*
     * @param	event DeleteSwitchEvent
     */
    @EventHandler( payloadType=DeleteSwitchEvent.class )
    public void handle( DeleteSwitchEvent event) {
    	LOGGER.info("handling DeleteSwitchEvent - " + event );
    	
    	// ------------------------------------------
    	// delete delegation
    	// ------------------------------------------
    	Switch entity = delete( event.getSwitchId() );

    	// ------------------------------------------
    	// emit to subscribers that find all
    	// ------------------------------------------    	
        emitFindAllSwitch( entity );

    }    




    /**
     * Method to retrieve the Switch via an SwitchPrimaryKey.
     * @param 	id Long
     * @return 	Switch
     * @exception ProcessingException - Thrown if processing any related problems
     * @exception IllegalArgumentException 
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public Switch handle( FindSwitchQuery query ) 
    throws ProcessingException, IllegalArgumentException {
    	return find( query.getFilter().getSwitchId() );
    }
    
    /**
     * Method to retrieve a collection of all Switchs
     *
     * @param	query	FindAllSwitchQuery 
     * @return 	List<Switch> 
     * @exception ProcessingException Thrown if any problems
     */
    @SuppressWarnings("unused")
    @QueryHandler
    public List<Switch> handle( FindAllSwitchQuery query ) throws ProcessingException {
    	return findAll( query );
    }


	/**
	 * emit to subscription queries of type FindSwitch, 
	 * but only if the id matches
	 * 
	 * @param		entity	Switch
	 */
	protected void emitFindSwitch( Switch entity ) {
		LOGGER.info("handling emitFindSwitch" );
		
	    queryUpdateEmitter.emit(FindSwitchQuery.class,
	                            query -> query.getFilter().getSwitchId().equals(entity.getSwitchId()),
	                            entity);
	}
	
	/**
	 * unconditionally emit to subscription queries of type FindAllSwitch
	 * 
	 * @param		entity	Switch
	 */
	protected void emitFindAllSwitch( Switch entity ) {
		LOGGER.info("handling emitFindAllSwitch" );
		
	    queryUpdateEmitter.emit(FindAllSwitchQuery.class,
	                            query -> true,
	                            entity);
	}


	//--------------------------------------------------
    // attributes
    // --------------------------------------------------
	@Autowired
	private final QueryUpdateEmitter queryUpdateEmitter;
    private static final Logger LOGGER 	= Logger.getLogger(SwitchProjector.class.getName());

}
