/*******************************************************************************
  Turnstone Biologics Confidential
  
  2018 Turnstone Biologics
  All Rights Reserved.
  
  This file is subject to the terms and conditions defined in
  file 'license.txt', which is part of this source code package.
   
  Contributors :
        Turnstone Biologics - General Release
 ******************************************************************************/
package com.occulue.subscriber;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.axonframework.messaging.responsetypes.ResponseTypes;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Component;


import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;

/**
 * Subscriber for Switch related events.  .
 * 
 * @author your_name_here
 *
 */
@Component("switch-subscriber")
public class SwitchSubscriber extends BaseSubscriber {

	public SwitchSubscriber() {
		queryGateway = applicationContext.getBean(QueryGateway.class);
	}
	
    public SubscriptionQueryResult<List<Switch>, Switch> switchSubscribe() {
        return queryGateway
                .subscriptionQuery(new FindAllSwitchQuery(), 
                		ResponseTypes.multipleInstancesOf(Switch.class),
                		ResponseTypes.instanceOf(Switch.class));
    }

    public SubscriptionQueryResult<Switch, Switch> switchSubscribe(@DestinationVariable UUID switchId) {
        return queryGateway
                .subscriptionQuery(new FindSwitchQuery(new LoadSwitchFilter(switchId)), 
                		ResponseTypes.instanceOf(Switch.class),
                		ResponseTypes.instanceOf(Switch.class));
    }




    // -------------------------------------------------
    // attributes
    // -------------------------------------------------
	@Autowired
    private final QueryGateway queryGateway;
}

