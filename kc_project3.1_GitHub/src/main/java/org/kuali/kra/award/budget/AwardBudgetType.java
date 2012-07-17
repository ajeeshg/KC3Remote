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
package org.kuali.kra.award.budget;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

public class AwardBudgetType extends KraPersistableBusinessObjectBase { 
    
    private static final long serialVersionUID = 1L;

    private String awardBudgetTypeCode; 
    private String description; 
    
    private AwardBudgetExt awardBudgetExt; 
    
    public AwardBudgetType() { 

    } 
    
    public String getAwardBudgetTypeCode() {
        return awardBudgetTypeCode;
    }

    public void setAwardBudgetTypeCode(String awardBudgetTypeCode) {
        this.awardBudgetTypeCode = awardBudgetTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AwardBudgetExt getAwardBudgetExt() {
        return awardBudgetExt;
    }

    public void setAwardBudgetExt(AwardBudgetExt awardBudgetExt) {
        this.awardBudgetExt = awardBudgetExt;
    }

    /** {@inheritDoc} */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
        hashMap.put("awardBudgetTypeCode", this.getAwardBudgetTypeCode());
        hashMap.put("description", this.getDescription());
        return hashMap;
    }
    
}