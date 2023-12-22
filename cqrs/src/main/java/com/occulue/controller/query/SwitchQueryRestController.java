/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.controller.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.occulue.api.*;
import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.projector.*;

import com.occulue.controller.*;

/** 
 * Implements Spring Controller query CQRS processing for entity Switch.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Switch")
public class SwitchQueryRestController extends BaseSpringRestController {

	
    /**
     * Handles loading a Switch using a UUID
     * @param		UUID switchId
     * @return		Switch
     */    
    @GetMapping("/load")
    public Switch load( @RequestParam(required=true) UUID switchId ) {
    	Switch entity = null;

    	try {  
    		entity = SwitchBusinessDelegate.getSwitchInstance().getSwitch( new SwitchFetchOneSummary( switchId ) );
        }
        catch( Throwable exc ) {
            LOGGER.log( Level.WARNING, "failed to load Switch using Id " + switchId );
            return null;
        }

        return entity;
    }

    /**
     * Handles loading all Switch business objects
     * @return		Set<Switch>
     */
    @GetMapping("/")
    public List<Switch> loadAll() {                
    	List<Switch> switchList = null;
        
    	try {
            // load the Switch
            switchList = SwitchBusinessDelegate.getSwitchInstance().getAllSwitch();
            
            if ( switchList != null )
                LOGGER.log( Level.INFO,  "successfully loaded all Switchs" );
        }
        catch( Throwable exc ) {
            LOGGER.log( Level.WARNING,  "failed to load all Switchs ", exc );
        	return null;
        }

        return switchList;
                            
    }



//************************************************************************    
// Attributes
//************************************************************************
    protected Switch switch = null;
    private static final Logger LOGGER = Logger.getLogger(SwitchQueryRestController.class.getName());
    
}
