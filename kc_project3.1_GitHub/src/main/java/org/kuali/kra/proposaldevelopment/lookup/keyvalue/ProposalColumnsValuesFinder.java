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
import java.util.List;
import java.util.Map;

import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;
import org.kuali.kra.service.KraPersistenceStructureService;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.BusinessObjectEntry;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.core.util.KeyLabelPair;

public class ProposalColumnsValuesFinder extends KeyValuesBase {

    private DataDictionaryService dataDictionaryService;
    
    public List<KeyLabelPair> getKeyValues() {
        BusinessObjectEntry proposalEntry = 
            getDataDictionaryService().getDataDictionary().getBusinessObjectEntry(DevelopmentProposal.class.getName());
        KraPersistenceStructureService persistenceStructureService = KraServiceLocator.getService(KraPersistenceStructureService.class);
        Map<String, String> attrToColumnMap = persistenceStructureService.getPersistableAttributesColumnMap(DevelopmentProposal.class);        
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();
        for (AttributeDefinition entry : proposalEntry.getAttributes()) {
            if (attrToColumnMap.get(entry.getName()) == null) {
                //if the data dictionary name cannot be found in the 
                //database mapping then this is not a valid entry
                continue;
            }
            KeyLabelPair keyPair = new KeyLabelPair();
            keyPair.setKey(attrToColumnMap.get(entry.getName()));
            keyPair.setLabel(entry.getShortLabel());
            keyValues.add(keyPair);
        }
        
        return keyValues;
    }
    
    private DataDictionaryService getDataDictionaryService() {
        if (dataDictionaryService == null) {
            dataDictionaryService = KraServiceLocator.getService(DataDictionaryService.class);
        }
        return dataDictionaryService;
    }
}
