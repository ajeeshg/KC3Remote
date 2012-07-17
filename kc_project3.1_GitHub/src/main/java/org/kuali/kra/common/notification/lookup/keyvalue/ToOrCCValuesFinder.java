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
package org.kuali.kra.common.notification.lookup.keyvalue;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

/**
 * Defines the values finder for the To or CC field in Notifications.
 */
public class ToOrCCValuesFinder extends KeyValuesBase {
    
    private static final String TO_KEY = "T";
    private static final String CC_KEY = "C";
    
    private static final String TO_VALUE = "To";
    private static final String CC_VALUE = "CC";

    /**
     * {@inheritDoc}
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> toOrCCKeyValuePairList = new ArrayList<KeyLabelPair>();
        toOrCCKeyValuePairList.add(new KeyLabelPair(TO_KEY, TO_VALUE));
        toOrCCKeyValuePairList.add(new KeyLabelPair(CC_KEY, CC_VALUE));
        return toOrCCKeyValuePairList;
    }

}