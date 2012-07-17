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
package org.kuali.kra.committee.bo;

import java.util.LinkedHashMap;

/**
 * 
 * This class is for associating common Committee properties to the extended Committee children BOs
 */
public abstract class CommitteeAssociate extends CommitteeSequenceAssociate {

    private static final long serialVersionUID = -6350020738083606018L;

    private Long committeeIdFk;
    
    public Long getCommitteeIdFk() {
        return committeeIdFk;
    }

    public void setCommitteeIdFk(Long committeeIdFk) {
        this.committeeIdFk = committeeIdFk;
    }

    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("committeeIdFk", this.getCommitteeIdFk());
        return map;
    }

}
