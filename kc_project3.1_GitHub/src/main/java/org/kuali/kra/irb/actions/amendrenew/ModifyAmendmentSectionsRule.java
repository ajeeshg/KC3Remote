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
package org.kuali.kra.irb.actions.amendrenew;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.rule.BusinessRuleInterface;
import org.kuali.kra.rules.ResearchDocumentRuleBase;

/**
 * Business rule for modifying  amendment sections.  The user is required to enter a summary.
 * For protocol amendments they must select at least one module which will be modified in the amendment.
 */
@SuppressWarnings("unchecked")
public class ModifyAmendmentSectionsRule extends ResearchDocumentRuleBase implements BusinessRuleInterface<ModifyAmendmentSectionsEvent> {

    public boolean processRules(ModifyAmendmentSectionsEvent event) {
        
        boolean valid = true;
        
        if (StringUtils.isBlank(event.getAmendmentBean().getSummary())) {
            valid = false;
            reportError(event.getPropertyName(), KeyConstants.ERROR_PROTOCOL_SUMMARY_IS_REQUIRED);
        }
        
        if (event.isAmendment() && !event.getAmendmentBean().isSomeSelected()) {
            valid = false;
            reportError(event.getPropertyName(), KeyConstants.ERROR_PROTOCOL_SELECT_MODULE);
        }
        
        return valid;
    }
}
