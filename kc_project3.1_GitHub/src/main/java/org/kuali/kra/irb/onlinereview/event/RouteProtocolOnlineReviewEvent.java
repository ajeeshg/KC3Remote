/*
 * Copyright 2005-2010 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.irb.onlinereview.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.kra.irb.ProtocolOnlineReviewDocument;
import org.kuali.kra.irb.onlinereview.rules.RouteProtocolOnlineReviewRule;
import org.kuali.kra.meeting.CommitteeScheduleMinute;
import org.kuali.kra.rule.event.KraDocumentEventBase;
import org.kuali.rice.kns.rule.BusinessRule;

public class RouteProtocolOnlineReviewEvent extends KraDocumentEventBase {

    
    
    private static final Log LOG = LogFactory.getLog(RouteProtocolOnlineReviewEvent.class);
    private final List<CommitteeScheduleMinute> minutes;
    private final long onlineReviewIndex;
  
    /**
     * Creates a new event.
     * @param document the document.
     * @param newProtocolNotepad the new attachment to be added.
     */
    public RouteProtocolOnlineReviewEvent(final ProtocolOnlineReviewDocument document,
        final List<CommitteeScheduleMinute> minutes, final long onlineReviewIndex ) {
        super("adding new protocol notepad", "notesAttachmentsHelper", document);
        this.onlineReviewIndex = onlineReviewIndex;
        if (document == null) {
            throw new IllegalArgumentException("the document is null");
        }
        
        if (minutes == null) {
            throw new IllegalArgumentException("the newCommitteeScheduleMinute is null");
        }
        
        this.minutes = minutes;
    }
  
    
    
    
    /** {@inheritDoc} */
    @Override
    protected void logEvent() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("save #" + this.getDocument().getDocumentNumber());
        }
    }

    public Class<RouteProtocolOnlineReviewRule> getRuleInterfaceClass() {
        return RouteProtocolOnlineReviewRule.class;
    }

    public boolean invokeRuleMethod(BusinessRule rule) {
        return this.getRuleInterfaceClass().cast(rule).processRouteProtocolOnlineReview(this);
    }

   
    public ProtocolOnlineReviewDocument getProtocolOnlineReviewDocument() {
        return (ProtocolOnlineReviewDocument)getDocument();
    }

    /**
     * Gets the onlineReviewIndex attribute. 
     * @return Returns the onlineReviewIndex.
     */
    public long getOnlineReviewIndex() {
        return onlineReviewIndex;
    }

    /**
     * Gets the minutes attribute. 
     * @return Returns the minutes.
     */
    public List<CommitteeScheduleMinute> getMinutes() {
        return minutes;
    }
    
}
