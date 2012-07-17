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
package org.kuali.kra.award.paymentreports.awardreports;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.rules.ResearchDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * The AwardPaymentScheduleRuleImpl.
 */
public class AwardReportTermRuleImpl extends ResearchDocumentRuleBase 
                                            implements AwardReportTermRule {
    
    private static final String AWARD_REPORT_TERM_REPORT_CODE_PROPERTY = "reportCode";
    private static final String AWARD_REPORT_TERM_FREQUENCY_CODE_PROPERTY = "frequencyCode";
    private static final String AWARD_REPORT_TERM_FREQUENCY_BASE_CODE_PROPERTY = "frequencyBaseCode";
    private static final String AWARD_REPORT_TERM_DISTRIBUTION_PROPERTY = "ospDistributionCode";
    private static final String AWARD_REPORT_TERM_DUE_DATE_PROPERTY = "dueDate";
    
    private static final String REPORT_CODE_ERROR_PARM = "Type (Type)";
    private static final String FREQUENCY_CODE_ERROR_PARM = "Frequency (Frequency)";
    private static final String FREQUENCY_BASE_CODE_ERROR_PARM = "Frequency Base (Frequency Base)";
    private static final String DISTRIBUTION_ERROR_PARM = "OSP File Copy  (OSP File Copy )";
    private static final String DUE_DATE_ERROR_PARM = "Due Date (Due Date)";
    private static final String EMPTY_CODE = "-1";

    /**
     * 
     * @see org.kuali.kra.award.paymentreports.awardreports.AwardReportTermRule#processAwardReportTermBusinessRules(
     *          org.kuali.kra.award.paymentreports.awardreports.AwardReportTermRuleEvent)
     */
    public boolean processAwardReportTermBusinessRules(AwardReportTermRuleEvent event) {
        boolean validFields = true;
        //document.awardList[0].awardReportTermItems[0].
        String fieldStarter = "document.awardList[0].awardReportTermItems[";
        String fieldEnder = "].";
        int counter = 0;
        for (AwardReportTerm awardReportTermItem : event.getAward().getAwardReportTermItems()) {
            validFields = validateRequiredFields(awardReportTermItem, fieldStarter + counter + fieldEnder) && validFields;
            counter++;
        }
        return processCommonValidations(event) && validFields;        
    }
    /**
     * 
     * This method processes new AwardPaymentSchedule rules
     * 
     * @param event
     * @return
     */
    public boolean processAddAwardReportTermBusinessRules(AddAwardReportTermRuleEvent event) {
        return validateRequiredFields(event.getAwardReportTermItemForValidation(), "") && processCommonValidations(event);        
    }
    
    private boolean processCommonValidations(AwardReportTermRuleEvent event) {
        GenericAwardReportTerm awardReportTermItem = event.getAwardReportTermItemForValidation();        
        List<? extends GenericAwardReportTerm> items = 
            (List<? extends GenericAwardReportTerm>) event.getAward().getAwardReportTermItems();
        return isUnique(items, awardReportTermItem);
    }
    
    /**
     * 
     * An award report term item is unique if no other matching items are in the collection
     * To know if this is a new add or an edit of an existing equipment item, we check 
     * the identifier for nullity. If null, this is an add; otherwise, it's an update
     * If an update, then we expect to find one match in the collection (itself). If an add, 
     * we expect to find no matches in the collection.
     * @param awardReportTermItems
     * @param awardReportTermItem
     * @return
     */
    protected boolean isUnique(List<? extends GenericAwardReportTerm> awardReportTermItems, GenericAwardReportTerm awardReportTermItem) {
        boolean duplicateFound = false;
        for (GenericAwardReportTerm listItem : awardReportTermItems) {
            duplicateFound = awardReportTermItem != listItem && listItem.equalsInitialFields(awardReportTermItem);
            if (duplicateFound) {
                break;
            }
        }
        
        if (duplicateFound) {
            if (!GlobalVariables.getErrorMap().containsMessageKey(KeyConstants.ERROR_AWARD_REPORT_TERM_ITEM_NOT_UNIQUE)) {
                reportError("awardReportTerm", KeyConstants.ERROR_AWARD_REPORT_TERM_ITEM_NOT_UNIQUE);
            }
        }
        return !duplicateFound;
    }
    
    /**
     * 
     * This method validates that all the required fields have values.  It is protected so that it can be unit tested easily.
     * @param awardReportTermItem
     * @param fieldPrePend
     * @return
     */
    protected boolean validateRequiredFields(GenericAwardReportTerm awardReportTermItem, String fieldPrePend) {
        boolean retVal = true;
        if (StringUtils.isBlank(awardReportTermItem.getReportCode())) {
            retVal = false;
            reportError(fieldPrePend + AWARD_REPORT_TERM_REPORT_CODE_PROPERTY, KeyConstants.ERROR_REQUIRED, REPORT_CODE_ERROR_PARM);
        }
        if (StringUtils.isBlank(awardReportTermItem.getFrequencyCode())) {
            retVal = false;
            reportError(fieldPrePend + AWARD_REPORT_TERM_FREQUENCY_CODE_PROPERTY, KeyConstants.ERROR_REQUIRED, FREQUENCY_CODE_ERROR_PARM);
        }
        if (StringUtils.isBlank(awardReportTermItem.getFrequencyBaseCode())) {
            retVal = false;
            reportError(fieldPrePend + AWARD_REPORT_TERM_FREQUENCY_BASE_CODE_PROPERTY, KeyConstants.ERROR_REQUIRED, FREQUENCY_BASE_CODE_ERROR_PARM);
        }
        if (StringUtils.isBlank(awardReportTermItem.getOspDistributionCode())) {
            reportError(fieldPrePend + AWARD_REPORT_TERM_DISTRIBUTION_PROPERTY, KeyConstants.ERROR_REQUIRED, DISTRIBUTION_ERROR_PARM);
            retVal = false;
        }
        return retVal;
    }
    
    /**
     * 
     * This method is used to validate new AwardTemplateReportTerms on the Sponsor Template maint doc
     * using the same rules Award uses.
     * @param awardReportTerm
     * @param existingItems
     * @return
     */
    public boolean processAwardReportTermBusinessRules(GenericAwardReportTerm awardReportTerm,
            List<? extends GenericAwardReportTerm> existingItems) {
        return validateRequiredFields(awardReportTerm, "") && isUnique(existingItems, awardReportTerm);
    }

}
