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
package org.kuali.kra.proposaldevelopment.rules;

import static org.kuali.kra.infrastructure.Constants.AUDIT_ERRORS;

import java.util.ArrayList;
import java.util.List;

import org.apache.batik.util.gui.MemoryMonitor.Usage;
import org.kuali.kra.bo.CoeusModule;
import org.kuali.kra.bo.CoeusSubModule;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.proposaldevelopment.questionnaire.ProposalDevelopmentModuleQuestionnaireBean;
import org.kuali.kra.proposaldevelopment.service.ProposalDevelopmentS2sQuestionnaireService;
import org.kuali.kra.questionnaire.QuestionnaireUsage;
import org.kuali.kra.questionnaire.answer.AnswerHeader;
import org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService;
import org.kuali.kra.rules.ResearchDocumentRuleBase;
import org.kuali.kra.s2s.bo.S2sOppForms;
import org.kuali.kra.s2s.bo.S2sOpportunity;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rule.DocumentAuditRule;
import org.kuali.rice.kns.util.AuditCluster;
import org.kuali.rice.kns.util.AuditError;
import org.kuali.rice.kns.util.GlobalVariables;

public class ProposalDevelopmentQuestionnaireAuditRule extends ResearchDocumentRuleBase implements DocumentAuditRule {

    private static final String PROPOSAL_QUESTIONNAIRE_KEY="questionnaireHelper.answerHeaders[%s].answers[0].answer";
    private static final String PROPOSAL_QUESTIONNAIRE_PANEL_KEY="%s%s%s";
   
    private transient QuestionnaireAnswerService questionnaireAnswerService;
    
    public boolean processRunAuditBusinessRules(Document document) {
        
        boolean valid = true;
        ProposalDevelopmentDocument proposalDevelopmentDocument = (ProposalDevelopmentDocument)document;
        
        List<AnswerHeader> headers = getQuestionnaireAnswerService().getQuestionnaireAnswer(new ProposalDevelopmentModuleQuestionnaireBean(proposalDevelopmentDocument.getDevelopmentProposal()));  
        List<QuestionnaireUsage> usages = getQuestionnaireAnswerService().getPublishedQuestionnaire(CoeusModule.PROPOSAL_DEVELOPMENT_MODULE_CODE, CoeusSubModule.ZERO_SUBMODULE, true);
        for (int i=0;i<headers.size();i++) {
            AnswerHeader header = headers.get(i);
            if (!header.getCompleted()) {
                valid = false;
                getProposalS2sAuditErrorsByGroup("questionnaireHelper",usages.get(0).getQuestionnaireLabel(),i).add(
                        new AuditError(String.format(PROPOSAL_QUESTIONNAIRE_KEY, i, "complete"), KeyConstants.ERROR_QUESTIONNAIRE_NOT_COMPLETE,
                                Constants.QUESTIONS_PAGE+"."+usages.get(0).getQuestionnaireLabel(), new String[] {usages.get(0).getQuestionnaireLabel()}));
            }
        }
        
        return valid;
    }
 
    
    
    private synchronized QuestionnaireAnswerService getQuestionnaireAnswerService() {
        if (questionnaireAnswerService == null) {
            questionnaireAnswerService = KraServiceLocator.getService(QuestionnaireAnswerService.class);
        }
        return questionnaireAnswerService;
    }
    
    
    /**
     * This method should only be called if an audit error is intending to be added because it will actually add a <code>{@link List<AuditError>}</code>
     * to the auditErrorMap.
     * 
     * @return List of AuditError instances
     */
    @SuppressWarnings("unchecked")
    private List<AuditError> getProposalS2sAuditErrorsByGroup(String formProperty, String usageLabel, Integer answerHeaderIndex) {
        List<AuditError> auditErrors = new ArrayList<AuditError>();
        String key = String.format( PROPOSAL_QUESTIONNAIRE_PANEL_KEY, formProperty, usageLabel, answerHeaderIndex );
        
        if (!GlobalVariables.getAuditErrorMap().containsKey(key)) {
           GlobalVariables.getAuditErrorMap().put(key, new AuditCluster(usageLabel, auditErrors, AUDIT_ERRORS));
        }
        else {
            auditErrors = ((AuditCluster)GlobalVariables.getAuditErrorMap().get(key)).getAuditErrorList();
        }
        
        return auditErrors;
    }

    
    

}
