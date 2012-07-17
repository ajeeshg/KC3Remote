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
package org.kuali.kra.budget.calculator;

/**
 * This class represents different possible enumerations of RateClassType
 */
public enum RateClassType {
    INFLATION("I"),
    OVERHEAD("O"),
    EMPLOYEE_BENEFITS("E"),
    LAB_ALLOCATION("L"),
    LA_SALARIES("Y"),
    VACATION("V"),
    OTHER("X");
    private final String rateClassType;
    RateClassType(String rateClassType){
        this.rateClassType = rateClassType;
    }
    public String getRateClassType(){
        return rateClassType;
    }
}
