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
package org.kuali.kra.irb.auth;

import org.kuali.kra.infrastructure.PermissionConstants;
import org.kuali.kra.irb.Protocol;
import org.kuali.kra.irb.actions.ProtocolActionType;

/**
 * 
 * This class maintains the authorizations required to perform a modification to protocol submission.
 */
public class ModifyProtocolSubmissionAuthorizer extends ProtocolAuthorizer {

    @Override
    public boolean isAuthorized(String userId, ProtocolTask task) {
        Protocol protocol = task.getProtocol();
        return hasPermission(userId, protocol, PermissionConstants.MODIFY_PROTOCOL_SUBMISSION) 
            && canExecuteAction(task.getProtocol(), ProtocolActionType.MODIFY_PROTOCOL_SUBMISISON);
    }
}