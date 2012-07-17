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
package org.kuali.kra.budget.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;
import org.kuali.kra.budget.rates.ValidCeRateType;

public class CostElement extends KraPersistableBusinessObjectBase implements Comparable {
	private String costElement;
	private String budgetCategoryCode;
	private String description;
	private Boolean onOffCampusFlag;
	private String budgetCategoryTypeCode;
    private Boolean activeFlag;
	private List<ValidCeRateType> validCeRateTypes;
	private BudgetCategory budgetCategory;
	private String financialObjectCode;
	
	public String getFinancialObjectCode() {
        return financialObjectCode;
    }
	
    public void setFinancialObjectCode(String financialObjectCode) {
        this.financialObjectCode = financialObjectCode;
    }
    
    public CostElement(){
	    validCeRateTypes = new ArrayList<ValidCeRateType>();
	}
	public String getCostElement() {
		return costElement;
	}

	public void setCostElement(String costElement) {
		this.costElement = costElement;
	}

	public String getBudgetCategoryCode() {
		return budgetCategoryCode;
	}

	public void setBudgetCategoryCode(String budgetCategoryCode) {
		this.budgetCategoryCode = budgetCategoryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getOnOffCampusFlag() {
		return onOffCampusFlag;
	}

	public void setOnOffCampusFlag(Boolean onOffCampusFlag) {
		this.onOffCampusFlag = onOffCampusFlag;
	}

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }


	@Override 
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap hashMap = new LinkedHashMap();
		hashMap.put("costElement", getCostElement());
		hashMap.put("budgetCategoryCode", getBudgetCategoryCode());
		hashMap.put("financialObjectCode", getFinancialObjectCode());
		hashMap.put("description", getDescription());
		hashMap.put("onOffCampusFlag", getOnOffCampusFlag());
        hashMap.put("activeFlag", getActiveFlag());
		return hashMap;
	}

    public List<ValidCeRateType> getValidCeRateTypes() {
        return validCeRateTypes;
    }

    public void setValidCeRateTypes(List<ValidCeRateType> validCeRateTypes) {
        this.validCeRateTypes = validCeRateTypes;
    }

    /**
     * This is for totals page to sort it by CostElement
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        return compareTo((CostElement) o);
    }
    
    public int compareTo(CostElement costElement) {
        return this.costElement.compareTo(costElement.costElement);
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public String getBudgetCategoryTypeCode() {
        return budgetCategoryTypeCode;
    }

    public void setBudgetCategoryTypeCode(String budgetCategoryTypeCode) {
        this.budgetCategoryTypeCode = budgetCategoryTypeCode;
    }

}
