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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.kra.bo.NoticeOfOpportunity;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.KeyValuesService;
import org.kuali.rice.core.util.KeyLabelPair;

/**
 * This class is the Values Finder for Notice of Opportunity.
 */
public class NoticeOfOpportunityValuesFinder extends KeyValuesBase {

    /**
     * Returns the key/values list (code/description) for Notice of Opportunity.
     *
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List getKeyValues() {
        KeyValuesService keyValuesService = (KeyValuesService) KraServiceLocator.getService("keyValuesService");
        Collection noticesOfOpportunity = keyValuesService.findAll(NoticeOfOpportunity.class);
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("", "select"));
        for (Iterator iter = noticesOfOpportunity.iterator(); iter.hasNext();) {
            NoticeOfOpportunity noticeOfOpportunity = (NoticeOfOpportunity) iter.next();
            keyValues.add(new KeyLabelPair(noticeOfOpportunity.getNoticeOfOpportunityCode(), noticeOfOpportunity.getDescription()));
        }
        return keyValues;
    }
}
