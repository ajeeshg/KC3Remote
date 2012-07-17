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
package org.kuali.kra.irb.actions.correction;

import org.kuali.kra.irb.actions.ActionHelper;
import org.kuali.kra.irb.actions.ProtocolEditableBean;

public class AdminCorrectionBean extends ProtocolEditableBean {

    private static final long serialVersionUID = 3247703113947298472L;
    
    private String comments;
    private boolean applyCorrection;
    
    /**
     * Constructs a AdminCorrectionBean.
     * @param actionHelper Reference back to the action helper for this bean
     */
    public AdminCorrectionBean(ActionHelper actionHelper) {
        super(actionHelper);
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public boolean isApplyCorrection() {
        return applyCorrection;
    }
    
    public void setApplyCorrection(boolean applyCorrection) {
        this.applyCorrection = applyCorrection;
    }
    
    public boolean isAmendmentRenewalOutstanding() {
        return !(getGeneralInfoEnabled() &&  
            getFundingSourceEnabled() && 
            getProtocolReferencesEnabled() && 
            getProtocolOrganizationsEnabled() && 
            getSubjectsEnabled() && 
            getAddModifyAttachmentsEnabled() && 
            getAreasOfResearchEnabled() && 
            getSpecialReviewEnabled() && 
            getProtocolPersonnelEnabled() && 
            getOthersEnabled() &&
            getProtocolPermissionsEnabled() &&
            getQuestionnaireEnabled());
    }

}
