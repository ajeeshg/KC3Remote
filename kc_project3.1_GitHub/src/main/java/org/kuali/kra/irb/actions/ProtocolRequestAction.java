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
package org.kuali.kra.irb.actions;

import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.TaskName;

/**
 * This class is an enum which set all the properties for each request action.
 * This is referenced by submit action in protocolprotocolactionaction.
 */
public enum ProtocolRequestAction {
    
    /**
     * Request to close a Protocol.
     */
    REQUEST_TO_CLOSE               (ProtocolActionType.REQUEST_TO_CLOSE,
                                    TaskName.PROTOCOL_REQUEST_CLOSE,
                                    Constants.PROTOCOL_CLOSE_REQUEST_PROPERTY_KEY, 
                                    "protocolCloseRequestBean", 
                                    "Request to Close"), 

    /**
     * Request to close enrollment to a Protocol.
     */
    REQUEST_TO_CLOSE_ENROLLMENT    (ProtocolActionType.REQUEST_TO_CLOSE_ENROLLMENT,
                                    TaskName.PROTOCOL_REQUEST_CLOSE_ENROLLMENT,
                                    Constants.PROTOCOL_CLOSE_ENROLLMENT_REQUEST_PROPERTY_KEY, 
                                    "protocolCloseEnrollmentRequestBean", 
                                    "Request to Close Enrollment"), 
                                    
    /**
     * Request for data analysis only of a Protocol.
     */
    REQUEST_FOR_DATA_ANALYSIS_ONLY (ProtocolActionType.REQUEST_FOR_DATA_ANALYSIS_ONLY, 
                                    TaskName.PROTOCOL_REQUEST_DATA_ANALYSIS,
                                    Constants.PROTOCOL_DATA_ANALYSIS_REQUEST_PROPERTY_KEY, 
                                    "protocolDataAnalysisRequestBean", 
                                    "Request for Data Analysis Only"),
    
    /**
     * Request to reopen enrollment to a Protocol.
     */
    REQUEST_TO_REOPEN_ENROLLMENT   (ProtocolActionType.REQUEST_TO_REOPEN_ENROLLMENT, 
                                    TaskName.PROTOCOL_REQUEST_REOPEN_ENROLLMENT,
                                    Constants.PROTOCOL_REOPEN_ENROLLMENT_REQUEST_PROPERTY_KEY, 
                                    "protocolReOpenEnrollmentRequestBean", 
                                    "Request to Re-open Enrollment"), 
                                  
    /**
     * Request for suspension of a Protocol.
     */
    REQUEST_FOR_SUSPENSION         (ProtocolActionType.REQUEST_FOR_SUSPENSION, 
                                    TaskName.PROTOCOL_REQUEST_SUSPENSION,
                                    Constants.PROTOCOL_SUSPEND_REQUEST_PROPERTY_KEY, 
                                    "protocolSuspendRequestBean", 
                                    "Request for Suspension"), 
                                  
    /**
     * Request for termination of a Protocol.
     */
    REQUEST_FOR_TERMINATION        (ProtocolActionType.REQUEST_FOR_TERMINATION, 
                                    TaskName.PROTOCOL_REQUEST_TERMINATE,
                                    Constants.PROTOCOL_TERMINATE_REQUEST_PROPERTY_KEY, 
                                    "protocolTerminateRequestBean", 
                                    "Request for Termination");
    
    private final String actionTypeCode;
    private final String taskName;
    private final String errorPath;
    private final String beanName;
    private final String actionName;

    
    private ProtocolRequestAction(String actionTypeCode, String taskName, String errorPath, String beanName, String actionName) {
        this.actionTypeCode = actionTypeCode;
        this.taskName = taskName;
        this.errorPath = errorPath;
        this.beanName = beanName;
        this.actionName = actionName;
    }

    public String getActionTypeCode() {
        return actionTypeCode;
    }
    
    public String getTaskName() {
        return taskName;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getActionName() {
        return actionName;
    }
    
    /**
     * Returns the value of this enumeration based on the given taskName.
     * 
     * This can return null, which isn't ideal but is necessary since TaskName is not an enum.
     * @param taskName the name of the task to perform
     * @return the ProtocolRequestAction corresponding to the taskName
     */
    public static ProtocolRequestAction valueOfTaskName(String taskName) {
        ProtocolRequestAction protocolRequestAction = null;
        
        for (ProtocolRequestAction action : values()) {
            if (action.getTaskName().equals(taskName)) {
                protocolRequestAction = action;
            }
        }
        
        return protocolRequestAction;
    }
    
    /**
     * Returns the value of this enumeration based on the given actionTypeCode.
     * 
     * This can return null, which isn't ideal but is necessary since ProtocolActionTypeCode is not an enum.
     * @param actionTypeCode the action type code
     * @return the ProtocolRequestAction corresponding to the actionTypeCode
     */
    public static ProtocolRequestAction valueOfActionTypeCode(String actionTypeCode) {
        ProtocolRequestAction protocolRequestAction = null;
        
        for (ProtocolRequestAction action : values()) {
            if (action.getActionTypeCode().equals(actionTypeCode)) {
                protocolRequestAction = action;
            }
        }
        
        return protocolRequestAction;
    }

}