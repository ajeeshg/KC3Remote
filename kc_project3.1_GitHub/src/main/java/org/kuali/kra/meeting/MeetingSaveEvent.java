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
package org.kuali.kra.meeting;

import org.kuali.kra.committee.document.CommitteeDocument;
import org.kuali.kra.rule.BusinessRuleInterface;
import org.kuali.rice.kns.document.Document;

/**
 * 
 * This class for the event to save committee schedule.
 */
public class MeetingSaveEvent extends MeetingEventBase<MeetingSaveRule> {
    
    private static final String MSG = "Save meeting data ";
    
    public MeetingSaveEvent(String errorPathPrefix, CommitteeDocument document, MeetingHelper meetingHelper, ErrorType type) {
        super(MSG + getDocumentId(document), errorPathPrefix, document, meetingHelper, type);
    }
    
    public MeetingSaveEvent(String errorPathPrefix, Document document, MeetingHelper meetingHelper, ErrorType type) {
        this(errorPathPrefix, (CommitteeDocument)document, meetingHelper, type);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public BusinessRuleInterface getRule() {
        return new MeetingSaveRule();
    }

}
