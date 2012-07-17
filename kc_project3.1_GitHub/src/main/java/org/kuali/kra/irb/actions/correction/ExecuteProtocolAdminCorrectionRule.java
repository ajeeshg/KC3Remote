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

import org.kuali.kra.irb.ProtocolDocument;
import org.kuali.rice.kns.rule.BusinessRule;

/**
 * 
 * This interface describes the public methods needed for validating an assignment and agenda.
 */
public interface ExecuteProtocolAdminCorrectionRule extends BusinessRule {

    /**
     * Process the business validation when a protocol is assigned to a committee/schedule.
     * 
     * @param document the protocol document
     * @param actionBean contains the committee/schedule to assign to
     * @return true if valid; otherwise false
     */
    boolean processAdminCorrectionRule(ProtocolDocument document, AdminCorrectionBean actionBean);
}
