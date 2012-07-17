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
package org.kuali.kra.irb.actions.submit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.kra.irb.actions.IrbActionsKeyValuesBase;
import org.kuali.rice.core.util.KeyLabelPair;

/**
 * Assembles the Protocol Review Types to display in the drop-down menu when
 * submitting a protocol to the IRB office for review.
 */
public class ProtocolReviewTypeValuesFinder extends IrbActionsKeyValuesBase {
    
    @SuppressWarnings("unchecked")
    public List<KeyLabelPair> getKeyValues() {
        Collection<ProtocolReviewType> protocolReviewTypes = this.getKeyValuesService().findAll(ProtocolReviewType.class);
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("", "select"));
        for (ProtocolReviewType protocolReviewType : protocolReviewTypes) {
            keyValues.add(new KeyLabelPair(protocolReviewType.getReviewTypeCode(), protocolReviewType.getDescription()));
        }
        return keyValues;
    }
}
