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
package org.kuali.kra.irb.actions.print;

import java.io.Serializable;

/**
 * 
 * This class is a bean for questionnaire print options.  It is used 
 * Questionnaire print option in protocol print.
 */
public class QuestionnairePrintOption implements Serializable {

    private static final long serialVersionUID = -2388949517675336400L;
    private Long questionnaireRefId;
    private Integer questionnaireId; 
    private String label;
    private String questionnaireName;
    private String itemKey;
    private String subItemKey;
    private String subItemCode;
    private boolean selected;

    public Long getQuestionnaireRefId() {
        return questionnaireRefId;
    }

    public void setQuestionnaireRefId(Long questionnaireRefId) {
        this.questionnaireRefId = questionnaireRefId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getSubItemKey() {
        return subItemKey;
    }

    public void setSubItemKey(String subItemKey) {
        this.subItemKey = subItemKey;
    }

    public String getSubItemCode() {
        return subItemCode;
    }

    public void setSubItemCode(String subItemCode) {
        this.subItemCode = subItemCode;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

}
