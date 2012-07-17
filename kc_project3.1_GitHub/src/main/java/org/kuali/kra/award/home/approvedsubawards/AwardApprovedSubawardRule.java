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
package org.kuali.kra.award.home.approvedsubawards;

import org.kuali.rice.kns.rule.BusinessRule;

/**
 * 
 * This interface declares the rule method associated with <code>AwardApprovedSubaward</code> 
 * Business Object.
 */
public interface AwardApprovedSubawardRule extends BusinessRule {
    /**
     * Rule invoked upon adding a Award Report Term Recipient
     * <code>{@link org.kuali.kra.award.document.AwardDocument}</code>
     *
     * @return boolean
     */
    boolean processApprovedSubawardBusinessRules(AwardApprovedSubawardRuleEvent 
                                                    awardApprovedSubawardRuleEvent);
}
