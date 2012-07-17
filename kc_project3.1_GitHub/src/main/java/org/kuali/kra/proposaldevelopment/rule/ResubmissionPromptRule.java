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
package org.kuali.kra.proposaldevelopment.rule;

import org.kuali.kra.proposaldevelopment.rule.event.ResubmissionRuleEvent;
import org.kuali.rice.kns.rule.BusinessRule;

/**
 * Validates the options for Proposal Development resubmission.
 */
public interface ResubmissionPromptRule extends BusinessRule {
    
    /**
     * Validates the options for Proposal Development resubmission.
     * 
     * @param resubmissionRuleEvent the event for which this rule is run
     * @return true if one option is selected, false otherwise
     */
    boolean processResubmissionPromptBusinessRules(ResubmissionRuleEvent resubmissionRuleEvent);

}