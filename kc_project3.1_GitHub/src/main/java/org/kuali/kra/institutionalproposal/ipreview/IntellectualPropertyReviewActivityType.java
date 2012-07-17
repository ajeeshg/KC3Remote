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
package org.kuali.kra.institutionalproposal.ipreview;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;
import java.util.LinkedHashMap;

public class IntellectualPropertyReviewActivityType extends KraPersistableBusinessObjectBase { 
    
    private static final long serialVersionUID = 1L;

    private String intellectualPropertyReviewActivityTypeCode; 
    private String description; 
    
    
    public IntellectualPropertyReviewActivityType() { 

    } 
    
    public String getIntellectualPropertyReviewActivityTypeCode() {
        return intellectualPropertyReviewActivityTypeCode;
    }

    public void setIntellectualPropertyReviewActivityTypeCode(String intellectualPropertyReviewActivityTypeCode) {
        this.intellectualPropertyReviewActivityTypeCode = intellectualPropertyReviewActivityTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
        hashMap.put("intellectualPropertyReviewActivityTypeCode", this.getIntellectualPropertyReviewActivityTypeCode());
        hashMap.put("description", this.getDescription());
        return hashMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime
                * result
                + ((intellectualPropertyReviewActivityTypeCode == null) ? 0 : intellectualPropertyReviewActivityTypeCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IntellectualPropertyReviewActivityType other = (IntellectualPropertyReviewActivityType) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (intellectualPropertyReviewActivityTypeCode == null) {
            if (other.intellectualPropertyReviewActivityTypeCode != null) {
                return false;
            }
        } else if (!intellectualPropertyReviewActivityTypeCode.equals(other.intellectualPropertyReviewActivityTypeCode)) {
            return false;
        }
        return true;
    }
    
}
