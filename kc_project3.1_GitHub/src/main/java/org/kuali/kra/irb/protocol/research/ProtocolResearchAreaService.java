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
package org.kuali.kra.irb.protocol.research;

import java.util.Collection;

import org.kuali.kra.bo.ResearchArea;
import org.kuali.kra.irb.Protocol;


public interface ProtocolResearchAreaService {

    /**
     * When a multi-lookup returns for a set of Research Areas, we must add them to the Protocol Document.
     * Note that we don't add duplicate research areas.
     * NOTE: This should be moved to a service since it is business logic.
     * @param protocolDocument the Protocol Document
     * @param selectedBOs the selected BOs (Research Areas)
     */
    public abstract void addProtocolResearchArea(Protocol protocol, Collection<ResearchArea> selectedBOs);
    
    /**
     * Checks to see if the list of research areas in the given protocol is empty.
     * @param protocol The protocol with the list of research areas
     * @return True if there are no research areas in this protocol, false otherwise
     */
    boolean isEmptyProtocolResearchAreas(Protocol protocol);
    
}
