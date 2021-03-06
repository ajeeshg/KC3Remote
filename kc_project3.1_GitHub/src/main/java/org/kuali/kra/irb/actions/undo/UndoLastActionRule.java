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
package org.kuali.kra.irb.actions.undo;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.irb.ProtocolDocument;
import org.kuali.kra.rules.ResearchDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * Validate the assignment of a protocol to a agenda.
 */
public class UndoLastActionRule extends ResearchDocumentRuleBase implements ExecuteUndoLastActionRule {
   
    public boolean processUndoLastActionRule(ProtocolDocument document, UndoLastActionBean actionBean) {
        boolean valid = true;
        if (StringUtils.isBlank(actionBean.getComments())) {
            valid = false;
            GlobalVariables.getErrorMap().putError(Constants.PROTOCOL_UNDO_LASTACTION_PROPERTY_KEY + ".comments", 
                                                   KeyConstants.ERROR_PROTOCOL_UNDO_LASTACTION_COMMENTS_REQUIRED);  
        }
        return valid;
    }
}
