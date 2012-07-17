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
package org.kuali.kra.institutionalproposal.customdata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.bo.CustomAttribute;
import org.kuali.kra.bo.CustomAttributeDataType;
import org.kuali.kra.bo.CustomAttributeDocument;
import org.kuali.kra.bo.KcPerson;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.institutionalproposal.document.InstitutionalProposalDocument;
import org.kuali.kra.lookup.keyvalue.ArgValueLookupValuesFinder;
import org.kuali.kra.rules.ResearchDocumentRuleBase;
import org.kuali.kra.service.CustomAttributeService;
import org.kuali.kra.service.KcPersonService;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.datadictionary.validation.ValidationPattern;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.RiceKeyConstants;

/**
 * This class...
 */
public class InstitutionalProposalCustomDataRuleImpl extends ResearchDocumentRuleBase implements
        InstitutionalProposalCustomDataRule {

    private String CUSTOMATTRIBUTE_VALUES_ID = "customAttributeValues(id";
    private String RIGHT_PAREN = ")";
    private String STRING = "String";
    private String NUMBER = "Number";
    private String DATE = "Date";
    private String DOT_STAR = ".*";
    private static final String DATE_REGEX = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|2[0-9])[0-9]{2}";


    private static Map<String, String> validationClasses = new HashMap<String, String>();
    static {
        validationClasses.put("String", "org.kuali.rice.kns.datadictionary.validation.charlevel.AnyCharacterValidationPattern");
        validationClasses.put("Date", "org.kuali.rice.kns.datadictionary.validation.fieldlevel.DateValidationPattern");
        validationClasses.put("Number", "org.kuali.rice.kns.datadictionary.validation.charlevel.NumericValidationPattern");
    }
    
    /**
     * @see org.kuali.kra.institutionalproposal.customdata.InstitutionalProposalCustomDataRule#processSaveInstitutionalProposalCustomDataBusinessRules
     * (org.kuali.kra.institutionalproposal.customdata.InstitutionalProposalSaveCustomDataRuleEvent)
     */
    public boolean processSaveInstitutionalProposalCustomDataBusinessRules(InstitutionalProposalSaveCustomDataRuleEvent institutionalProposalSaveCustomDataRuleEvent) {
        InstitutionalProposalDocument institutionalProposalDocument = institutionalProposalSaveCustomDataRuleEvent.getInstitutionalProposalDocument();
        Map<String, CustomAttributeDocument> customAttributeDocuments = institutionalProposalDocument.getCustomAttributeDocuments();
        boolean valid = true;
        for (Map.Entry<String, CustomAttributeDocument> customAttributeDocumentEntry : customAttributeDocuments.entrySet()) {
            CustomAttributeDocument customAttributeDocument = customAttributeDocumentEntry.getValue();
            CustomAttribute customAttribute = customAttributeDocument.getCustomAttribute();
            //set this value from awardCustomDataList so you have all of the data in one object to be passed to helper method.
            if(institutionalProposalDocument.getInstitutionalProposal().getInstitutionalProposalCustomDataList().size() > 0) {
                int customAttributeId = customAttributeDocument.getCustomAttributeId();
                List<InstitutionalProposalCustomData> institutionalProposalCustomDataList = institutionalProposalDocument.getInstitutionalProposal().getInstitutionalProposalCustomDataList();
                for(InstitutionalProposalCustomData institutionalProposalCustomData : institutionalProposalCustomDataList){
                  if(institutionalProposalCustomData.getCustomAttributeId() == customAttributeId){  
                      customAttribute.setValue(institutionalProposalCustomData.getValue());
                      break;
                  }
                }
            }
            String errorKey = CUSTOMATTRIBUTE_VALUES_ID + customAttribute.getId() + RIGHT_PAREN;
            if (StringUtils.isNotBlank(customAttribute.getValue())) {
                valid &= validateAttributeFormat(customAttribute, errorKey);
            }
        }

        return valid;
    }
    
    /**
     * 
     * This method is to validate the format/length of custom attribute
     * @param customAttribute
     * @param errorKey
     * @return
     */
    private boolean validateAttributeFormat(CustomAttribute customAttribute, String errorKey) {

        CustomAttributeDataType customAttributeDataType = customAttribute.getCustomAttributeDataType();
        String attributeValue = customAttribute.getValue();
        if (customAttributeDataType == null && customAttribute.getDataTypeCode() != null) {
            customAttributeDataType = getCustomAttributeService().getCustomAttributeDataType(
                    customAttribute.getDataTypeCode());
        }
        if (customAttributeDataType != null) {
            Integer maxLength = customAttribute.getDataLength();
            if ((maxLength != null) && (maxLength.intValue() < attributeValue.length())) {
                GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_MAX_LENGTH, customAttribute.getLabel(), maxLength.toString());
                return false;
            }
                        
            ValidationPattern validationPattern = null;
            try {validationPattern = (ValidationPattern) Class.forName(
                    validationClasses.get(customAttributeDataType.getDescription())).newInstance();
                if (customAttributeDataType.getDescription().equalsIgnoreCase(STRING)) {
                    ((org.kuali.rice.kns.datadictionary.validation.charlevel.AnyCharacterValidationPattern) validationPattern).setAllowWhitespace(true);
                }
            } catch (Exception e) {
                //do nothing
            }
            String validFormat = getValidFormat(customAttributeDataType.getDescription());
            if (customAttributeDataType.getDescription().equalsIgnoreCase(STRING)||customAttributeDataType.getDescription().equalsIgnoreCase(NUMBER)) {
                Pattern validationExpression = validationPattern.getRegexPattern();
                //String validFormat = getValidFormat(customAttributeDataType.getDescription());
            
                if (validationExpression != null && !validationExpression.pattern().equals(DOT_STAR)) {
                    if (!validationExpression.matcher(attributeValue).matches()) {
                        GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_INVALID_FORMAT, 
                                                                customAttribute.getLabel(), attributeValue, validFormat);
                        return false;
                    }
                }
            } else if (customAttributeDataType.getDescription().equalsIgnoreCase(DATE)) {
                if(!attributeValue.matches(DATE_REGEX)) {
                    GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_INVALID_FORMAT, 
                            customAttribute.getLabel(), attributeValue, validFormat);
                     return false;
                }
            }
//            Pattern validationExpression = validationPattern.getRegexPattern();
//            String validFormat = getValidFormat(customAttributeDataType.getDescription());
//            if (validationExpression != null && !validationExpression.pattern().equals(DOT_STAR)) {
//                if (!validationExpression.matcher(attributeValue).matches()) {
//                    GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_INVALID_FORMAT, 
//                                                                customAttribute.getLabel(), attributeValue, validFormat);
//                    return false;
//                }
//            }
            // validate BO data against the database contents 
            String lookupClass = customAttribute.getLookupClass();
            if (lookupClass != null && lookupClass.equals("org.kuali.kra.bo.KcPerson"))
            {
                if (customAttribute.getDataTypeCode().equals("1") && customAttribute.getLookupReturn().equals("userName"))
                {
                    validFormat = getValidFormat(customAttributeDataType.getDescription());
                    KcPersonService kps = getKcPersonService();
                    KcPerson customPerson = kps.getKcPersonByUserName(customAttribute.getValue());
                    if (customPerson == null)
                    {
                        GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_EXISTENCE, 
                            customAttribute.getLabel(), attributeValue, validFormat);
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
                else
                {
                    // can only validate for userName, if fullName, etc is used then a lookup
                    // is assumed that a lookup is being used, in which case validation 
                    // is not necessary
                    return true; 
                }
            }
            else if (lookupClass != null && lookupClass.equals("org.kuali.kra.bo.ArgValueLookup"))
            {
                    ArgValueLookupValuesFinder finder = new  ArgValueLookupValuesFinder();
                    finder.setArgName(customAttribute.getLookupReturn());
                    List<KeyLabelPair> kv = finder.getKeyValues();
                    Iterator<KeyLabelPair> i = kv.iterator();
                    while (i.hasNext())
                    {
                        KeyLabelPair element = (KeyLabelPair) i.next();
                        String label = element.getLabel().toLowerCase();
                        if (label.equals(attributeValue.toLowerCase()))
                        {
                            return true;
                        }      
                    }
                    validFormat = getValidFormat(customAttributeDataType.getDescription());
                    GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_EXISTENCE, 
                          customAttribute.getLabel(), attributeValue, validFormat);
                    return false;           
            }
            else if (lookupClass != null)
            {    
                Class boClass = null;
                try
                {
                    boClass = Class.forName(lookupClass);
                }
                catch (ClassNotFoundException cnfE) {/* Do nothing, checked on input */ }

                if (isInvalid(boClass, keyValue(customAttribute.getLookupReturn(), customAttribute.getValue() ) ) )         
                {
                    validFormat = getValidFormat(customAttributeDataType.getDescription());
                    GlobalVariables.getErrorMap().putError(errorKey, RiceKeyConstants.ERROR_EXISTENCE, 
                             customAttribute.getLabel(), attributeValue, validFormat);
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This method returns the valid format for the data type of the custom attribute.
     * @param dataType
     * @return
     */
    private String getValidFormat(String dataType) {
        String validFormat = Constants.DATA_TYPE_STRING;
        if(dataType.equalsIgnoreCase(NUMBER)) {
            validFormat = Constants.DATA_TYPE_NUMBER;
        }else if(dataType.equalsIgnoreCase(DATE)) {
            validFormat = Constants.DATA_TYPE_DATE;
        }
        return validFormat;
    }
    
    /**
     * 
     * This method is a helper method to retrieve CustomAttributeService.
     * @return
     */
    protected CustomAttributeService getCustomAttributeService() {
        return KraServiceLocator.getService(CustomAttributeService.class);
    }

    /**
     * 
     * This method is a helper method to retrieve KcPersonService.
     * @return
     */
    protected KcPersonService getKcPersonService() {
        return KraServiceLocator.getService(KcPersonService.class);    
    }

}
