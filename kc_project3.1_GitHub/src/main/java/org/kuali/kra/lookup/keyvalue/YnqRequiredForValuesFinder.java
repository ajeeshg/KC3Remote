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
package org.kuali.kra.lookup.keyvalue;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kra.infrastructure.YnqConstants;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.core.util.KeyLabelPair;

public class YnqRequiredForValuesFinder extends KeyValuesBase {

    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        for (YnqConstants ynqConstants : YnqConstants.values()) {
            keyValues.add(new KeyLabelPair(ynqConstants.code(), ynqConstants.description()));
        }
    
        /*
        keyValues.add(new KeyLabelPair("Y", "Yes"));
        keyValues.add(new KeyLabelPair("N", "No"));
        keyValues.add(new KeyLabelPair("A", "NA"));
        keyValues.add(new KeyLabelPair("YN", "Yes,No"));
        keyValues.add(new KeyLabelPair("YNA", "Yes,No,N/A"));
        keyValues.add(new KeyLabelPair("YA", "Yes,NA"));
        keyValues.add(new KeyLabelPair("NNA", "No,NA"));
        keyValues.add(new KeyLabelPair(null, "None"));
        */
        return keyValues;
    }
}
