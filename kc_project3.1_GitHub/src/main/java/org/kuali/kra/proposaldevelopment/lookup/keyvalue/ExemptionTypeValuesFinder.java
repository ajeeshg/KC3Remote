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
package org.kuali.kra.proposaldevelopment.lookup.keyvalue;

import java.util.List;

import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder;
import org.kuali.rice.kns.lookup.keyvalues.PersistableBusinessObjectValuesFinder;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.kra.bo.ExemptionType;
import org.kuali.kra.lookup.keyvalue.SortedValuesFinder;

/**
 * See {@link #getKeyValues()}.
 */
public class ExemptionTypeValuesFinder extends KeyValuesBase {

    private final KeyValuesFinder finder;
    
    /**
     * Creates the ExemptionTypeValuesFinder setting any internal dependencies to defaults
     */
    public ExemptionTypeValuesFinder() {
        PersistableBusinessObjectValuesFinder boFinder = new PersistableBusinessObjectValuesFinder();
        boFinder.setBusinessObjectClass(ExemptionType.class);
        boFinder.setKeyAttributeName("exemptionTypeCode");
        boFinder.setLabelAttributeName("description");
        this.finder = new SortedValuesFinder(boFinder);
    }
    
    /**
     * Creates the ExemptionTypeValuesFinder setting the wrapped finder.
     * @param aFinder the finder
     * @throws NullPointerException if the finder is null
     */
    ExemptionTypeValuesFinder(final KeyValuesFinder aFinder) {
        if (aFinder == null) {
            throw new NullPointerException("the finder is null");
        }
        
        this.finder = aFinder;
    }
    
    /**
     * Gets the keyvalue pair for {@link ExemptionType ExemptionType}.
     * The key is the exemptionTypeCode and the value is the description.
     * 
     * @return a list of {@link KeyLabelPair KeyLabelPair}
     */
    public List<KeyLabelPair> getKeyValues() {
        @SuppressWarnings("unchecked")
        final List<KeyLabelPair> exemptionTypes = this.finder.getKeyValues();
        return exemptionTypes;
    }
}
