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

import org.kuali.kra.proposaldevelopment.rule.event.BasicProposalSiteEvent;
import org.kuali.rice.kns.rule.BusinessRule;

/**
 * Rule interface for deleting a Proposal Site.
 */
public interface DeleteProposalSiteRule extends BusinessRule {

    /**
     * Rule invoked upon deleting a proposal site from a
     * <code>{@link org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument}</code>
     *
     * @return boolean
     */
    public boolean processDeleteProposalSiteRules(BasicProposalSiteEvent deleteProposalSiteEvent);
}
