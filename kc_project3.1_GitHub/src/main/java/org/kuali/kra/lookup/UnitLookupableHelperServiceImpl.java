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
package org.kuali.kra.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.bo.Unit;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;

/**
 * Unit lookup that accounts for the extra parameter {@code campusCode} and filters the search results if it is defined.
 */
public class UnitLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    private static final long serialVersionUID = -3661085880649722426L;
    
    private static final String CAMPUS_CODE_FIELD = "campusCode";
    private static final String CAMPUS_LOOKUPABLE_CLASS_NAME = "org.kuali.rice.kns.bo.CampusImpl";

    @Override
    public List<Row> getRows() {
        List<Row> rows = super.getRows();
        
        boolean multiCampusEnabled = getParameterService().getIndicatorParameter(
            Constants.KC_GENERIC_PARAMETER_NAMESPACE, Constants.KC_ALL_PARAMETER_DETAIL_TYPE_CODE, Constants.PARAMETER_MULTI_CAMPUS_ENABLED);
        
        for (Row row : rows) {
            for (Field field : row.getFields()) {
                if (field.getPropertyName().equals(CAMPUS_CODE_FIELD)) {
                    field.setFieldConversions(CAMPUS_CODE_FIELD + Constants.COLON + field.getPropertyName());
                    field.setLookupParameters(field.getPropertyName() + Constants.COLON + CAMPUS_CODE_FIELD);
                    field.setInquiryParameters(field.getPropertyName() + Constants.COLON + CAMPUS_CODE_FIELD);
                    field.setQuickFinderClassNameImpl(CAMPUS_LOOKUPABLE_CLASS_NAME);
                    field.setFieldDirectInquiryEnabled(true);
                    if (multiCampusEnabled) {
                        if (StringUtils.isBlank(field.getDefaultValue())) {
                            String campusCode = (String) GlobalVariables.getUserSession().retrieveObject(Constants.USER_CAMPUS_CODE_KEY);
                            field.setDefaultValue(campusCode);
                            field.setPropertyValue(field.getDefaultValue());
                        }
                    }
                }
            }
        }
        
        return rows;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String campusCode = fieldValues.remove(CAMPUS_CODE_FIELD);
        List<? extends BusinessObject> searchResults = super.getSearchResults(fieldValues);
        
        List<Unit> filteredSearchResults = new ArrayList<Unit>();
        for (BusinessObject searchResult : searchResults) {
            Unit unit = (Unit) searchResult;
            if (StringUtils.startsWith(unit.getUnitNumber(), campusCode)) {
                filteredSearchResults.add(unit);
            }
        }

        return filteredSearchResults;
    }

}