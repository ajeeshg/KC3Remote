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
package org.kuali.kra.budget.rates;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

public class RateClass extends KraPersistableBusinessObjectBase {
	private String rateClassCode;
	private String description;
	private String rateClassType;
	private String rateClassTypeDescription;
	private RateClassType rateClassTypeT;
	private String icrTypeCode;
	
	

	public String getIcrTypeCode() {
        return icrTypeCode;
    }

    public void setIcrTypeCode(String icrTypeCode) {
        this.icrTypeCode = icrTypeCode;
    }

    public String getRateClassCode() {
		return rateClassCode;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRateClassType() {
		return rateClassType;
	}

	public void setRateClassType(String rateClassType) {
		this.rateClassType = rateClassType;
	}


	@Override 
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap hashMap = new LinkedHashMap();
		hashMap.put("rateClassCode", getRateClassCode());
		hashMap.put("description", getDescription());
		hashMap.put("rateClassType", getRateClassType());
		hashMap.put("icrTypeCode", getIcrTypeCode());
		return hashMap;
	}

    public String getRateClassTypeDescription() {
        return rateClassTypeDescription;
    }

    public void setRateClassTypeDescription(String rateClassTypeDescription) {
        this.rateClassTypeDescription = rateClassTypeDescription;
    }

    public RateClassType getRateClassTypeT() {
        return rateClassTypeT;
    }

    public void setRateClassTypeT(RateClassType rateClassTypeT) {
        this.rateClassTypeT = rateClassTypeT;
    }
}
