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
package org.kuali.kra.budget.distributionincome;

import java.util.LinkedHashMap;

import org.kuali.kra.budget.parameters.BudgetPeriod;
import org.kuali.kra.proposaldevelopment.hierarchy.HierarchyMaintainable;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 * 
 */
public class BudgetProjectIncome extends BudgetDistributionAndIncomeComponent implements HierarchyMaintainable {
    private static final long serialVersionUID = 8999969227018875501L;

    public static final String DOCUMENT_COMPONENT_ID_KEY = "BUDGET_PROJECT_INCOME_KEY";
    
    private Long budgetPeriodId;
    private BudgetPeriod budgetPeriod;
    private Integer budgetPeriodNumber;
    private String description;
    private KualiDecimal projectIncome;

    private String hierarchyProposalNumber;
    private boolean hiddenInHierarchy;

    /**
     * 
     * This method...
     * @return
     */
    public Integer getBudgetPeriodNumber() {
        return budgetPeriodNumber;
    }

    /**
     * 
     * This method...
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * This method...
     * @return
     */
    public KualiDecimal getProjectIncome() {
        return projectIncome;
    }

    /**
     * 
     * This method...
     * @param budgetPeriodNumber
     */
    public void setBudgetPeriodNumber(Integer budgetPeriodNumber) {
        this.budgetPeriodNumber = (budgetPeriodNumber != null && budgetPeriodNumber.intValue() > 0) ? budgetPeriodNumber : null;
    }

    /**
     * 
     * This method...
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * This method...
     * @param income
     */
    public void setProjectIncome(KualiDecimal income) {
        this.projectIncome = income;
    }

    /**
     * 
     * @see org.kuali.kra.budget.distributionincome.BudgetDistributionAndIncomeComponent#toStringMapper()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap<String, Object> hashMap = super.toStringMapper();
        hashMap.put("budgetPeriodNumber", getBudgetPeriodNumber());
        hashMap.put("description", getDescription());
        hashMap.put("income", getProjectIncome());
        return hashMap;
    }

    /**
     * 
     * @see org.kuali.kra.budget.distributionincome.BudgetDistributionAndIncomeComponent#getDocumentComponentIdKey()
     */
    @Override
    public String getDocumentComponentIdKey() {
        return DOCUMENT_COMPONENT_ID_KEY;
    }

    public Long getBudgetPeriodId() {
        return budgetPeriodId;
    }

    public void setBudgetPeriodId(Long budgetPeriodId) {
        this.budgetPeriodId = budgetPeriodId;
    }

    public void setBudgetPeriod(BudgetPeriod budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    public BudgetPeriod getBudgetPeriod() {
        return budgetPeriod;
    }

    public String getHierarchyProposalNumber() {
        return hierarchyProposalNumber;
    }

    public void setHierarchyProposalNumber(String hierarchyProposalNumber) {
        this.hierarchyProposalNumber = hierarchyProposalNumber;
    }

    public boolean isHiddenInHierarchy() {
        return hiddenInHierarchy;
    }

    public void setHiddenInHierarchy(boolean hiddenInHierarchy) {
        this.hiddenInHierarchy = hiddenInHierarchy;
    }
}
