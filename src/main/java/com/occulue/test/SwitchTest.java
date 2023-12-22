/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.test;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.util.Assert.state;

import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.api.*;
import com.occulue.subscriber.*;

/**
 * Test Switch class.
 *
 * @author your_name_here
 */
public class SwitchTest{

    // ------------------------------------
	// default constructor
    // ------------------------------------
	public SwitchTest() {
		subscriber = new SwitchSubscriber();
	}

	// test methods
	@Test
	/*
	 * Initiate SwitchTest.
	 */
	public void startTest() throws Throwable {
		try {
			LOGGER.info("**********************************************************");
			LOGGER.info("Beginning test on Switch...");
			LOGGER.info("**********************************************************\n");
			
			// ---------------------------------------------
			// jumpstart process
			// ---------------------------------------------
			jumpStart();
			
		} catch (Throwable e) {
			throw e;
		} finally {
		}
	}

	/** 
	 * jumpstart the process by instantiating2 Switch
	 */
	protected void jumpStart() throws Throwable {
		LOGGER.info( "\n======== create instances to get the ball rolling  ======== ");

		switchId = SwitchBusinessDelegate.getSwitchInstance()
		.createSwitch( generateNewCommand() )
		.get();

		// ---------------------------------------------
		// set up query subscriptions after the 1st create
		// ---------------------------------------------
		testingStep = "create";
		setUpQuerySubscriptions();

		SwitchBusinessDelegate.getSwitchInstance()
				.createSwitch( generateNewCommand() )
				.get();

	}

	/** 
	 * Set up query subscriptions
	 */
	protected void setUpQuerySubscriptions() throws Throwable {
		LOGGER.info( "\n======== Setting Up Query Subscriptions ======== ");
			
		try {            
			subscriber.switchSubscribe().updates().subscribe(
					  successValue -> {
						  LOGGER.info(successValue.toString());
						  try {
							  LOGGER.info("GetAll update received for Switch : " + successValue.getSwitchId());
							  if (successValue.getSwitchId().equals(switchId)) {
								  if (testingStep.equals("create")) {
									  testingStep = "update";
									  update();
								  } else if (testingStep.equals("delete")) {
									  testingStep = "complete";
									  state( getAll().size() == sizeOfSwitchList - 1 , "value not deleted from list");
									  LOGGER.info("**********************************************************");
									  LOGGER.info("Switch test completed successfully...");
									  LOGGER.info("**********************************************************\n");
								  }
							  }
						  } catch( Throwable exc ) {
							  LOGGER.warning( exc.getMessage() );
						  }
					  },
					  error -> LOGGER.warning(error.getMessage()),
					  () -> LOGGER.info("Subscription on switch consumed")
					);
			subscriber.switchSubscribe( switchId ).updates().subscribe(
					  successValue -> {
						  LOGGER.info(successValue.toString());
						  try {
							  LOGGER.info("GetOne update received for Switch : " + successValue.getSwitchId() + " in step " + testingStep);
							  testingStep = "delete";
							  sizeOfSwitchList = getAll().size();
							  delete();
						  } catch( Throwable exc ) {
							  LOGGER.warning( exc.getMessage() );
						  }
					  },
					  error -> LOGGER.warning(error.getMessage()),
					  () -> LOGGER.info("Subscription on switch for switchId consumed")

					);
			

			}
			catch (Exception e) {
				LOGGER.warning( e.getMessage() );
				throw e;
			}
		}
		
		/** 
	 * read a Switch. 
	 */
	protected Switch read() throws Throwable {
		LOGGER.info( "\n======== READ ======== ");
		LOGGER.info( "-- Reading a previously created Switch" );

		Switch entity = null;
		StringBuilder msg = new StringBuilder( "-- Failed to read Switch with primary key" );
		msg.append( switchId );
		
		SwitchFetchOneSummary fetchOneSummary = new SwitchFetchOneSummary( switchId );

		try {
			entity = SwitchBusinessDelegate.getSwitchInstance().getSwitch( fetchOneSummary );

			assertNotNull( entity,msg.toString() );

			LOGGER.info( "-- Successfully found Switch " + entity.toString() );
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : " + e );

			throw e;
		}
		
		return entity;
	}

	/** 
	 * updating a Switch.
	 */
	protected void update() throws Throwable {
		LOGGER.info( "\n======== UPDATE ======== ");
		LOGGER.info( "-- Attempting to update a Switch." );

		StringBuilder msg = new StringBuilder( "Failed to update a Switch : " );        
		Switch entity = read();
		UpdateSwitchCommand command = generateUpdateCommand();
		command.setSwitchId(entity.getSwitchId());

		try {            
			assertNotNull( entity, msg.toString() );

			LOGGER.info( "-- Now updating the created Switch." );

			// for use later on...
			switchId = entity.getSwitchId();

			SwitchBusinessDelegate proxy = SwitchBusinessDelegate.getSwitchInstance();  

			proxy.updateSwitch( command ).get();

			LOGGER.info( "-- Successfully saved Switch - " + entity.toString() );
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : primarykey = " + switchId + " : command -" +  command + " : " + e );

			throw e;
		}
	}

	/** 
	 * delete a Switch.
	 */
	protected void delete() throws Throwable {
		LOGGER.info( "\n======== DELETE ======== ");
		LOGGER.info( "-- Deleting a previously created Switch." );

		Switch entity = null;
		
		try{
		    entity = read(); 
			LOGGER.info( "-- Successfully read Switch with id " + switchId );            
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( "-- Failed to read Switch with id " + switchId );

			throw e;
		}

		try{
			SwitchBusinessDelegate.getSwitchInstance().delete( new DeleteSwitchCommand( entity.getSwitchId() ) ).get();
			LOGGER.info( "-- Successfully deleted Switch with id " + switchId );            
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( "-- Failed to delete Switch with id " + switchId );

			throw e;
		}
	}

	/**
	 * get all Switchs.
	 */
	protected List<Switch> getAll() throws Throwable {    
		LOGGER.info( "======== GETALL ======== ");
		LOGGER.info( "-- Retrieving Collection of Switchs:" );

		StringBuilder msg = new StringBuilder( "-- Failed to get all Switch : " );        
		List<Switch> collection  = new ArrayList<>();

		try {
			// call the static get method on the SwitchBusinessDelegate
			collection = SwitchBusinessDelegate.getSwitchInstance().getAllSwitch();
			assertNotNull( collection, "An Empty collection of Switch was incorrectly returned.");
			
			// Now print out the values
			Switch entity = null;            
			Iterator<Switch> iter = collection.iterator();
			int index = 1;

			while( iter.hasNext() ) {
				// Retrieve the entity   
				entity = iter.next();

				assertNotNull( entity,"-- null entity in Collection." );
				assertNotNull( entity.getSwitchId(), "-- entity in Collection has a null primary key" );        

				LOGGER.info( " - " + String.valueOf(index) + ". " + entity.toString() );
				index++;
			}
		}
		catch ( Throwable e ) {
			LOGGER.warning( unexpectedErrorMsg );
			LOGGER.warning( msg.toString() + " : " + e );

			throw e;
		}
		
		return collection;			
	}

	/**
	 * Assigns a common log handler for each test class in the suite 
	 * in the event log output needs to go elsewhere
	 * 
	 * @param		handler	Handler
	 * @return		SwitchTest
	 */
	protected SwitchTest setHandler(Handler handler) {
		if ( handler != null )
			LOGGER.addHandler(handler); 
		return this;
	}

	/**
	 * Returns a new populated Switch
	 * 
	 * @return CreateSwitchCommand alias
	 */
	protected CreateSwitchCommand generateNewCommand() {
        CreateSwitchCommand command = new CreateSwitchCommand( null,  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16) );
		
		return( command );
	}

		/**
		 * Returns a new populated Switch
		 * 
		 * @return UpdateSwitchCommand alias
		 */
	protected UpdateSwitchCommand generateUpdateCommand() {
	        UpdateSwitchCommand command = new UpdateSwitchCommand( null,  org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16) );
			
			return( command );
		}
	//-----------------------------------------------------
	// attributes 
	//-----------------------------------------------------
	protected UUID switchId = null;
	protected SwitchSubscriber subscriber = null;
	private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
	private final Logger LOGGER = Logger.getLogger(SwitchTest.class.getName());
	private String testingStep = "";
	private Integer sizeOfSwitchList = 0;
}
