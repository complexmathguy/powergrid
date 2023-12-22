/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.validator;

import org.springframework.util.Assert;

import com.occulue.api.*;

public class SwitchValidator {
		
	/**
	 * default constructor
	 */
	protected SwitchValidator() {	
	}
	
	/**
	 * factory method
	 */
	static public SwitchValidator getInstance() {
		return new SwitchValidator();
	}
		
	/**
	 * handles creation validation for a Switch
	 */
	public void validate( CreateSwitchCommand switch )throws Exception {
		Assert.notNull( switch, "CreateSwitchCommand should not be null" );
//		Assert.isNull( switch.getSwitchId(), "CreateSwitchCommand identifier should be null" );
	}

	/**
	 * handles update validation for a Switch
	 */
	public void validate( UpdateSwitchCommand switch ) throws Exception {
		Assert.notNull( switch, "UpdateSwitchCommand should not be null" );
		Assert.notNull( switch.getSwitchId(), "UpdateSwitchCommand identifier should not be null" );
    }

	/**
	 * handles delete validation for a Switch
	 */
    public void validate( DeleteSwitchCommand switch ) throws Exception {
		Assert.notNull( switch, "{commandAlias} should not be null" );
		Assert.notNull( switch.getSwitchId(), "DeleteSwitchCommand identifier should not be null" );
	}
	
	/**
	 * handles fetchOne validation for a Switch
	 */
	public void validate( SwitchFetchOneSummary summary ) throws Exception {
		Assert.notNull( summary, "SwitchFetchOneSummary should not be null" );
		Assert.notNull( summary.getSwitchId(), "SwitchFetchOneSummary identifier should not be null" );
	}



}
