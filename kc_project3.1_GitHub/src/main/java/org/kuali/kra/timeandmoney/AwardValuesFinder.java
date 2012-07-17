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
package org.kuali.kra.timeandmoney;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.kuali.kra.award.awardhierarchy.AwardHierarchy;
import org.kuali.kra.award.awardhierarchy.AwardHierarchyService;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.service.AwardHierarchyUIService;
import org.kuali.kra.timeandmoney.document.TimeAndMoneyDocument;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.core.util.KeyLabelPair;

import java.util.Arrays;

public class AwardValuesFinder extends KeyValuesBase{
    
    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        keyValues.add(new KeyLabelPair("", "select:"));
        keyValues.add(new KeyLabelPair(Constants.AWARD_HIERARCHY_DEFAULT_PARENT_OF_ROOT, "External"));
        TimeAndMoneyForm timeAndMoneyForm = (TimeAndMoneyForm) GlobalVariables.getKualiForm();        
        TimeAndMoneyDocument document = timeAndMoneyForm.getTimeAndMoneyDocument();
        
        document.setAwardHierarchyItems(((TimeAndMoneyDocument)GlobalVariables.getUserSession().retrieveObject(
                GlobalVariables.getUserSession().getKualiSessionId() + Constants.TIME_AND_MONEY_DOCUMENT_STRING_FOR_SESSION)).getAwardHierarchyItems());    
        
        if(document.getAwardHierarchyItems()!=null && document.getAwardHierarchyItems().size()!=0){
            Object[] keyset = document.getAwardHierarchyItems().keySet().toArray();
            Arrays.sort(keyset);
            for(Object awardNumber : keyset) {
                keyValues.add(new KeyLabelPair((String) awardNumber, document.getAwardHierarchyItems().get(awardNumber).getAwardNumber()));
            }
        }
        
        return keyValues;
    }
    
    public AwardHierarchyService getAwardHierarchyService(){        
        return (AwardHierarchyService) KraServiceLocator.getService(AwardHierarchyService.class);
    }
    
    public AwardHierarchyUIService getAwardHierarchyUIService(){        
        return (AwardHierarchyUIService) KraServiceLocator.getService(AwardHierarchyUIService.class);
    }

}
