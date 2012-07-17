/*
 * Copyright 2005-2010 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.proposaldevelopment.lookup.keyvalue;

import java.util.List;

import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.lookup.keyvalue.KeyValueFinderService;
import org.kuali.kra.proposaldevelopment.bo.NarrativeType;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.core.util.KeyLabelPair;
/**
 * Finds the available set of supported Narrative Types.  See
 * the method <code>getKeyValues()</code> for a full description.
 * 
 * @author KRADEV team
 */

public class NarrativeTypeValuesFinder extends KeyValuesBase {
    KeyValueFinderService keyValueFinderService= (KeyValueFinderService)KraServiceLocator.getService("keyValueFinderService");
    /**
     * Constructs the list of Proposal Narrative Types. The list populates
     * from NARRATIVE_TYPE database table via the "KeyValueFinderService".
     * 
     * @return the list of &lt;key, value&gt; pairs of Narrative types.  The first entry
     * is always &lt;"", "select:"&gt;.
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinderService#getKeyValues()
     */
    public List<KeyLabelPair> getKeyValues() {
        return keyValueFinderService.getKeyValues(NarrativeType.class, "narrativeTypeCode", "description");
    }
}
