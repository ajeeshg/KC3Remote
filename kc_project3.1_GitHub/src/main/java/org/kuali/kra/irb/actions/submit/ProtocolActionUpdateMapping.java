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
package org.kuali.kra.irb.actions.submit;

import org.kuali.kra.drools.brms.FactBean;
import org.kuali.kra.irb.Protocol;
import org.kuali.kra.irb.actions.ProtocolAction;

/*
 * This is the post condition attributes for a protocol action
 */
public class ProtocolActionUpdateMapping implements FactBean {
    
    String actionTypeCode;
    
    String submissionTypeCode;
    
    String protocolStatusCode;
    
    String protocolNumberARCondition;

    Protocol protocol;
    
    ProtocolSubmission protocolSubmission;
    
    ProtocolAction protocolAction;
    
    public ProtocolActionUpdateMapping(String actionTypeCode, String submissionTypeCode, String protocolStatusCode, String specialCondition) {
        super();
        this.actionTypeCode=actionTypeCode;
        this.submissionTypeCode = submissionTypeCode;
        this.protocolStatusCode = protocolStatusCode;
        this.protocolNumberARCondition = specialCondition;
    }
    
    public ProtocolSubmission getProtocolSubmission() {
        return protocolSubmission;
    }

    public void setProtocolSubmission(ProtocolSubmission protocolSubmission) {
        this.protocolSubmission = protocolSubmission;
    }
    
    public String getActionTypeCode() {
        return actionTypeCode;
    }
    
    public void setActionTypeCode(String actionTypeCode) {
        this.actionTypeCode = actionTypeCode;
    }
    
    public Protocol getProtocol() {
        return protocol;
    }
    
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    
    public String getSubmissionTypeCode() {
        return submissionTypeCode;
    }
    
    public void setSubmissionTypeCode(String submissionTypeCode) {
        this.submissionTypeCode = submissionTypeCode;
    }

    public String getProtocolStatusCode() {
        return protocolStatusCode;
    }

    public void setProtocolStatusCode(String protocolStatusCode) {
        this.protocolStatusCode = protocolStatusCode;
    }
    
    public String getProtocolNumberARCondition() {
        return protocolNumberARCondition;
    }

    public void setProtocolNumberARCondition(String protocolNumberARCondition) {
        this.protocolNumberARCondition = protocolNumberARCondition;
    }

    public ProtocolAction getProtocolAction() {
        return protocolAction;
    }

    public void setProtocolAction(ProtocolAction protocolAction) {
        this.protocolAction = protocolAction;
    }
}
