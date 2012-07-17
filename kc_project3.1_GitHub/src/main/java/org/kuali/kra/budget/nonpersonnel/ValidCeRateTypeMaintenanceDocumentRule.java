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
package org.kuali.kra.budget.nonpersonnel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.budget.core.CostElement;
import org.kuali.kra.budget.rates.RateType;
import org.kuali.kra.budget.rates.ValidCeRateType;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.rules.KraMaintenanceDocumentRuleBase;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.GlobalVariables;

public class ValidCeRateTypeMaintenanceDocumentRule extends KraMaintenanceDocumentRuleBase {
    
    /**
     * Constructs a ValidCeRateTypeMaintenanceDocumentRule.java.
     */
    public ValidCeRateTypeMaintenanceDocumentRule() {
        super();
    }
    
    /**
     * 
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument)
     */ 
    public boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        return checkRateTypeExist(document);
    }
    
    /**
     * 
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomApproveDocumentBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument)
     */
    @Override
    public boolean processCustomApproveDocumentBusinessRules(MaintenanceDocument document) {
        return checkRateTypeExist(document);
    }

    /**
     * 
     * This method to check the rateclasscode/ratetypecode does exist in rate type table.
     * also check costelement.
     * @param maintenanceDocument
     * @return
     */
    private boolean checkRateTypeExist(MaintenanceDocument maintenanceDocument) {

        boolean valid = true;
        if (LOG.isDebugEnabled()) {
            LOG.debug("new maintainable is: " + maintenanceDocument.getNewMaintainableObject().getClass());
        }
        ValidCeRateType validCeRateType = (ValidCeRateType) maintenanceDocument.getNewMaintainableObject().getBusinessObject();

        if (StringUtils.isNotBlank(validCeRateType.getRateClassCode()) && StringUtils.isNotBlank(validCeRateType.getRateTypeCode())) {
            Map pkMap = new HashMap();
            pkMap.put("rateClassCode", validCeRateType.getRateClassCode());
            pkMap.put("rateTypeCode", validCeRateType.getRateTypeCode());
            RateType rateType = (RateType)KraServiceLocator.getService(BusinessObjectService.class).findByPrimaryKey(RateType.class, pkMap);
            if (rateType == null ) {
                GlobalVariables.getErrorMap().putError("document.newMaintainableObject.rateTypeCode", KeyConstants.ERROR_RATE_TYPE_NOT_EXIST,
                        new String[] { validCeRateType.getRateClassCode(), validCeRateType.getRateTypeCode() });
                valid = false;
            }
        }


        Map pkMap = new HashMap();
        pkMap.put("costElement", validCeRateType.getCostElement());
        valid&=checkExistenceFromTable(CostElement.class,pkMap,"costElement", "Cost Element");


        return valid;

    }


}
