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
package org.kuali.kra.kim.service.impl;

import org.kuali.kra.kim.service.KcGroupService;
import org.kuali.rice.kim.service.IdentityManagementService;

public class KcGroupServiceImpl implements KcGroupService {
    private IdentityManagementService identityManagementService;

    public void setIdentityManagementService(IdentityManagementService identityManagementService) {
        this.identityManagementService = identityManagementService;
    }

    public boolean isMemberOfGroup(String principalId, String groupId) {
        return identityManagementService.isMemberOfGroup(principalId, groupId);
    }
}
