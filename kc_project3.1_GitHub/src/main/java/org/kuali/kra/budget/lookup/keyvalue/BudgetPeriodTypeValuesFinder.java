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
package org.kuali.kra.budget.lookup.keyvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.kra.budget.parameters.BudgetPeriodType;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.lookup.keyvalue.KeyValueFinderService;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.KeyValuesService;
import org.kuali.rice.core.util.KeyLabelPair;

public class BudgetPeriodTypeValuesFinder extends KeyValuesBase {
    
    public List<KeyLabelPair> getKeyValues() {
        KeyValuesService keyValuesService = (KeyValuesService) KraServiceLocator.getService("keyValuesService");
        Collection budgetPeriodTypes = keyValuesService.findAll(BudgetPeriodType.class);
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        
        for (Iterator iter = budgetPeriodTypes.iterator(); iter.hasNext();) {
            BudgetPeriodType budgetPeriodType = (BudgetPeriodType) iter.next();
            keyValues.add(new KeyLabelPair(budgetPeriodType.getBudgetPeriodTypeCode().toString(), budgetPeriodType.getDescription()));                            
        }
                
        return keyValues;
    }

}
