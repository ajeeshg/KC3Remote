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
package org.kuali.kra.irb.correspondence;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

public class CorrespondentType extends KraPersistableBusinessObjectBase { 
    
    private static final long serialVersionUID = 1L;

    private String correspondentTypeCode; 
    private String description; 
    private String qualifier; 
    
   
    public CorrespondentType() { 

    } 
    
    public String getCorrespondentTypeCode() {
        return correspondentTypeCode;
    }

    public void setCorrespondentTypeCode(String correspondentTypeCode) {
        this.correspondentTypeCode = correspondentTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    /** {@inheritDoc} */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
        hashMap.put("correspondentTypeCode", this.getCorrespondentTypeCode());
        hashMap.put("description", this.getDescription());
        hashMap.put("qualifier", this.getQualifier());
        return hashMap;
    }
    
}
