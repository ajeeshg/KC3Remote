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
package org.kuali.kra.bo;

import java.util.LinkedHashMap;

/**
 * Defines the type of the Special Review.
 */
public class SpecialReviewType extends KraSortablePersistableBusinessObjectBase {
    
    /**
     * The Human Subjects Special Review type.
     */
    public static final String HUMAN_SUBJECTS = "1";

    private static final long serialVersionUID = -7939863013575475658L;
    
    private String specialReviewTypeCode;
    private String description;

    public String getSpecialReviewTypeCode() {
        return specialReviewTypeCode;
    }

    public void setSpecialReviewTypeCode(String specialReviewTypeCode) {
        this.specialReviewTypeCode = specialReviewTypeCode;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected LinkedHashMap<?, ?> toStringMapper() {
        LinkedHashMap<String, Object> propMap = new LinkedHashMap<String, Object>();
        propMap.put("specialReviewTypeCode",getSpecialReviewTypeCode());
        propMap.put("description", getDescription());
        propMap.put("updateTimestamp", getUpdateTimestamp());
        propMap.put("updateUser", getUpdateUser());
        return propMap;
    }

}