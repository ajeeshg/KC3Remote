/*
 * Copyright 2005-2010 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.proposaldevelopment.document.authorizer;

import org.kuali.kra.authorization.Task;
import org.kuali.kra.authorization.TaskAuthorizerImpl;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.proposaldevelopment.document.authorization.ProposalTask;
import org.kuali.kra.service.KraAuthorizationService;

/**
 * A Proposal Authorizer determines if a user can perform
 * a given task on a proposal.  
 */
public abstract class ProposalAuthorizer extends TaskAuthorizerImpl {
    
    private KraAuthorizationService kraAuthorizationService;
    private boolean requiresWritableDoc = false;
    
    /**
     * @see org.kuali.kra.authorization.TaskAuthorizer#isAuthorized(java.lang.String, org.kuali.kra.authorization.Task)
     */
    public final boolean isAuthorized(String userId, Task task) {
        if (isRequiresWritableDoc() && ((ProposalTask)task).getDocument().isViewOnly()) {
            return false;
        } else {
            return isAuthorized(userId, (ProposalTask) task);
        }
    }

    /**
     * Is the user authorized to execute the given proposal task?
     * @param username the user's unique username
     * @param task the proposal task
     * @return true if the user is authorized; otherwise false
     */
    public abstract boolean isAuthorized(String userId, ProposalTask task);
    
    /**
     * Set the Kra Authorization Service.  Injected by the Spring Framework.
     * @param kraAuthorizationService the Kra Authorization Service
     */
    public final void setKraAuthorizationService(KraAuthorizationService kraAuthorizationService) {
        this.kraAuthorizationService = kraAuthorizationService;
    }
    
    /**
     * Does the given user has the permission for this proposal development document?
     * @param username the unique username of the user
     * @param doc the proposal development document
     * @param permissionName the name of the permission
     * @return true if the person has the permission; otherwise false
     */
    protected final boolean hasProposalPermission(String userId, ProposalDevelopmentDocument doc, String permissionName) {
        return kraAuthorizationService.hasPermission(userId, doc, permissionName);
    }

    public boolean isRequiresWritableDoc() {
        return requiresWritableDoc;
    }

    public void setRequiresWritableDoc(boolean requiresWritableDoc) {
        this.requiresWritableDoc = requiresWritableDoc;
    }


}
