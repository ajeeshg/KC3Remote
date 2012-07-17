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
package org.kuali.kra.proposaldevelopment.rules;

import static org.kuali.kra.infrastructure.KeyConstants.ERROR_ATTACHMENT_NOT_AUTHORIZED;
import static org.kuali.kra.infrastructure.KeyConstants.ERROR_ATTACHMENT_STATUS_NOT_SELECTED;
import static org.kuali.kra.infrastructure.KeyConstants.ERROR_ATTACHMENT_TYPE_NOT_SELECTED;
import static org.kuali.kra.infrastructure.KeyConstants.ERROR_NARRATIVE_STATUS_INVALID;
import static org.kuali.kra.infrastructure.KeyConstants.ERROR_NARRATIVE_TYPE_DESCRITPION_REQUIRED;
import static org.kuali.kra.infrastructure.KeyConstants.ERROR_NARRATIVE_TYPE_DUPLICATE;
import static org.kuali.kra.infrastructure.KraServiceLocator.getService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.bo.KcPerson;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.infrastructure.NarrativeRight;
import org.kuali.kra.infrastructure.PermissionConstants;
import org.kuali.kra.proposaldevelopment.bo.Narrative;
import org.kuali.kra.proposaldevelopment.bo.NarrativeType;
import org.kuali.kra.proposaldevelopment.bo.NarrativeUserRights;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.proposaldevelopment.rule.AddNarrativeRule;
import org.kuali.kra.proposaldevelopment.rule.NewNarrativeUserRightsRule;
import org.kuali.kra.proposaldevelopment.rule.SaveNarrativesRule;
import org.kuali.kra.proposaldevelopment.rule.event.AddNarrativeEvent;
import org.kuali.kra.proposaldevelopment.rule.event.SaveNarrativesEvent;
import org.kuali.kra.rules.ResearchDocumentRuleBase;
import org.kuali.kra.service.KcAttachmentService;
import org.kuali.kra.service.KcPersonService;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.rice.kns.service.ParameterService;
import org.kuali.rice.kns.util.ErrorMap;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * Implementation of business rules required for the Proposal attachment page of the 
 * <code>{@link org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument}</code>.
 *
 * @see org.kuali.rice.kns.rules.BusinessRule
 * @author kualidev@oncourse.iu.edu
 * @version 1.0
 */
public class ProposalDevelopmentNarrativeRule extends ResearchDocumentRuleBase implements AddNarrativeRule,SaveNarrativesRule, NewNarrativeUserRightsRule { 
    private static final String NARRATIVE_TYPE_ALLOWMULTIPLE_NO = "N";
    private static final String DOCUMENT_NARRATIVES = "document.narratives";
    private static final String PROPOSAL = "Proposal";
    private static final String NARRATIVE_TYPE_CODE = "narrativeTypeCode";
    private static final String MODULE_STATUS_CODE_COMPLETED = "C";
    
    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(ProposalDevelopmentNarrativeRule.class);
    
    private transient KcPersonService kcPersonService;
    private transient PersonService<Person> personService;
    private transient ParameterService parameterService;
    private transient KcAttachmentService  kcAttachmentService;
    
    /**
     * This method is used to validate narratives and institute proposal attachments before adding.
     * It checks whether the narratives are duplicated for those of which have allowMultiple flag set as false.
     * @see org.kuali.kra.proposaldevelopment.rule.AddNarrativeRule#processAddNarrativeBusinessRules(org.kuali.kra.proposaldevelopment.rule.event.AddNarrativeEvent)
     */
    public boolean processAddNarrativeBusinessRules(AddNarrativeEvent narrativeEvent) {
        ProposalDevelopmentDocument document = (ProposalDevelopmentDocument)narrativeEvent.getDocument();
        Narrative narrative = narrativeEvent.getNarrative();
        boolean rulePassed = true;
        populateNarrativeType(narrative);
        ErrorMap map = GlobalVariables.getErrorMap();

        if(narrative.getNarrativeType()==null)
            rulePassed = false;
        
        if(!StringUtils.isBlank(narrative.getModuleStatusCode()) 
                && narrative.getModuleStatusCode().equalsIgnoreCase(MODULE_STATUS_CODE_COMPLETED)
                && StringUtils.isBlank(narrative.getFileName())) {
            LOG.debug(ERROR_NARRATIVE_STATUS_INVALID);
            reportError("newNarrative.moduleStatusCode", ERROR_NARRATIVE_STATUS_INVALID);
            rulePassed = false;
        }
        
        if (StringUtils.isBlank(narrative.getFileName())) {
            rulePassed = false;
            reportError("newNarrative.narrativeFile", KeyConstants.ERROR_REQUIRED_FOR_FILE_NAME, "File Name");
        }
        
        String attachmentFileName = narrative.getFileName();
        KcAttachmentService attachmentService = getKcAttachmentService();
      
        // Checking attachment file name for invalid characters.
        if (attachmentService.hasInvalidCharacters(attachmentFileName)) {
            String parameter = getParameterService().
                               getParameterValue(ProposalDevelopmentDocument.class, Constants.INVALID_FILE_NAME_CHECK_PARAMETER);
           
            if (Constants.INVALID_FILE_NAME_ERROR_CODE.equals(parameter)) {
                rulePassed &= false;
                reportError("newNarrative.narrativeFile", KeyConstants.INVALID_FILE_NAME,
                        attachmentFileName, attachmentService.getInvalidCharacters());
            } else {
                rulePassed &= true;
                reportWarning("newNarrative.narrativeFile", KeyConstants.INVALID_FILE_NAME,
                        attachmentFileName, attachmentService.getInvalidCharacters());
            }
        }
        
        map.addToErrorPath("newNarrative");
        getService(DictionaryValidationService.class).validateBusinessObject(narrative,false);
        map.removeFromErrorPath("newNarrative");
        int size = map.keySet().size();
        rulePassed &= size<=0;
        rulePassed &= checkNarrative(document.getDevelopmentProposal().getNarratives(), narrative);
        
        return rulePassed;
    }
    /**
     * This method is used to validate narratives and institute proposal attachments before saving.
     * It checks whether the narratives are duplicated for those of which have allowMultiple flag set as false.
     * @see org.kuali.kra.proposaldevelopment.rule.SaveNarrativesRule#processSaveNarrativesBusinessRules(org.kuali.kra.proposaldevelopment.rule.event.SaveNarrativesEvent)
     */
    public boolean processSaveNarrativesBusinessRules(SaveNarrativesEvent saveNarrativesEvent) {
        
        boolean rulePassed = checkUserRights(saveNarrativesEvent);
        
        List<Narrative> narrativeList = saveNarrativesEvent.getNarratives();
        int size = narrativeList.size();
       
        for (int i = 0; i < size; i++) {
            Narrative narrative = narrativeList.get(0);
            
            narrativeList.remove(narrative);  
            //--size;
            rulePassed &= checkNarrative(narrativeList,narrative);
        }
        
        Narrative narrative = saveNarrativesEvent.getNarrative();
        populateNarrativeType(narrative);
        if(!StringUtils.isBlank(narrative.getModuleStatusCode()) 
                && narrative.getModuleStatusCode().equalsIgnoreCase(MODULE_STATUS_CODE_COMPLETED)
                && StringUtils.isBlank(narrative.getFileName())) {
            LOG.debug(ERROR_NARRATIVE_STATUS_INVALID);
            reportError("newNarrative.moduleStatusCode", ERROR_NARRATIVE_STATUS_INVALID);
            rulePassed = false;
        }
        
        return rulePassed;
    }
    
    /**
     * Check to see if the user modified a narrative and verify that the
     * user has the necessary permission to make that modification.
     * @param saveNarrativesEvent
     * @return
     */
    private boolean checkUserRights(SaveNarrativesEvent saveNarrativesEvent) {
        boolean isValid = true;
        String userId = GlobalVariables.getUserSession().getPrincipalId();
        
        List<Narrative> narratives = saveNarrativesEvent.getNarratives();
        List<Narrative> originalNarratives = saveNarrativesEvent.getOriginalNarratives();
        for (Narrative origNarrative : originalNarratives) {
            NarrativeUserRights userRights = getUserRights(userId, origNarrative);
            if (userRights != null && ((StringUtils.equals(userRights.getAccessType(), NarrativeRight.VIEW_NARRATIVE_RIGHT.getAccessType())) ||
                (StringUtils.equals(userRights.getAccessType(), NarrativeRight.NO_NARRATIVE_RIGHT.getAccessType())))) {
                
                Narrative narrative = findNarrative(narratives, origNarrative);
                if (!origNarrative.equals(narrative)) {
                    isValid = false;
                    reportError("newNarrative.narrativeTypeCode", ERROR_ATTACHMENT_NOT_AUTHORIZED, origNarrative.getNarrativeType().getDescription());
                }
            }
        }
        return isValid;
    }
    
    /**
     * Get the narrative rights for a user.
     * @param username the user's unique username
     * @param narrative the narrative to search through for the user's rights
     * @return
     */
    private NarrativeUserRights getUserRights(String userId, Narrative narrative) {

        List<NarrativeUserRights> userRightsList = narrative.getNarrativeUserRights();
        for (NarrativeUserRights userRights : userRightsList) {
            String personId = userRights.getUserId();
            KcPerson person = getKcPersonService().getKcPersonByPersonId(personId);
            if (StringUtils.equals(userId, person.getPersonId())) {
                return userRights;
            }
        }
        return null;
    }
    
    /**
     * Find the narrative that matches the original narrative.  A match occurs
     * if they have the same module number.
     * @param narratives the list of narratives
     * @param origNarrative the original narrative to compare against
     * @return the found narrative or null if not found
     */
    private Narrative findNarrative(List<Narrative> narratives, Narrative origNarrative) {
        for (Narrative narrative : narratives) {
            if (narrative.getModuleNumber().equals(origNarrative.getModuleNumber())) {
                return narrative;
            }
        }
        return null;
    }
    
    /**
     * It checks for duplicate narrative types and mandatory description for narrative type 'Other'
     * This method...
     * @param narrativeList
     * @param narrative
     * @return true if rules passed, else false
     */
    private boolean checkNarrative(List<Narrative> narrativeList, Narrative narrative) {
        String errorPath=DOCUMENT_NARRATIVES;
        boolean rulePassed = true;
        if(StringUtils.isBlank(narrative.getNarrativeTypeCode())){
            rulePassed = false;
            reportError("newNarrative.narrativeTypeCode", ERROR_ATTACHMENT_TYPE_NOT_SELECTED);
        }
        if(StringUtils.isBlank(narrative.getModuleStatusCode())){
            rulePassed = false;
            reportError("newNarrative.moduleStatusCode", ERROR_ATTACHMENT_STATUS_NOT_SELECTED);
        }
        if (rulePassed) {
            populateNarrativeType(narrative);
            String[] param = {PROPOSAL, narrative.getNarrativeType().getDescription()};
            if (narrative.getNarrativeType().getAllowMultiple().equalsIgnoreCase(NARRATIVE_TYPE_ALLOWMULTIPLE_NO)) {
                for (Narrative narr : narrativeList) {
                    if (narr!=null && StringUtils.equals(narr.getNarrativeTypeCode(),narrative.getNarrativeTypeCode())) {
                        LOG.debug(ERROR_NARRATIVE_TYPE_DUPLICATE);
                        reportError(errorPath, ERROR_NARRATIVE_TYPE_DUPLICATE, param);
                        rulePassed = false;
                    }
                }
            }else if (StringUtils.isBlank(narrative.getModuleTitle())) {
                reportError(errorPath, ERROR_NARRATIVE_TYPE_DESCRITPION_REQUIRED, param);
                rulePassed = false;
            }
        }
        return rulePassed;
    }
    private void populateNarrativeType(Narrative narrative) {
        Map<String,String> narrativeTypeMap = new HashMap<String,String>();
        narrativeTypeMap.put(NARRATIVE_TYPE_CODE, narrative.getNarrativeTypeCode());
        BusinessObjectService service = getService(BusinessObjectService.class);
        NarrativeType narrType = (NarrativeType) service.findByPrimaryKey(NarrativeType.class, narrativeTypeMap);
        if (narrType != null)
            narrative.setNarrativeType(narrType);
        
    }
    
    /**
     * @see org.kuali.kra.proposaldevelopment.rule.NewNarrativeUserRightsRule#processNewNarrativeUserRightsBusinessRules(org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument, java.util.List, int)
     */
    public boolean processNewNarrativeUserRightsBusinessRules(ProposalDevelopmentDocument document,
            List<NarrativeUserRights> newNarrativeUserRights, int narrativeIndex) {
        
        boolean isValid = true;
        
        // Must have at least one user with the right to modify narratives.
       
        if (!hasNarrativeRight(newNarrativeUserRights, NarrativeRight.MODIFY_NARRATIVE_RIGHT)) {
            isValid = false;
            this.reportError(Constants.NEW_NARRATIVE_USER_RIGHTS_PROPERTY_KEY, 
                             KeyConstants.ERROR_REQUIRE_ONE_NARRATIVE_MODIFY);
        }
        
        // The users cannot be assigned narrative rights that are
        // greater than their assigned permission.  For example, if someone
        // only has the VIEW_NARRATIVES permission, they cannot be 
        // assigned a narrative right of modify.
        
        for (NarrativeUserRights userRights : newNarrativeUserRights) {
            if (!hasPermission(userRights, document)) {
                isValid = false;
                String personId = userRights.getUserId();
                KcPerson person = getKcPersonService().getKcPersonByPersonId(personId);
                this.reportError(Constants.NEW_NARRATIVE_USER_RIGHTS_PROPERTY_KEY, 
                                 KeyConstants.ERROR_NARRATIVE_USER_RIGHT_NO_PERMISSION, person.getFullName());
            }
        }
//        ProposalAuthorizationService proposalAuthorizationService = KraServiceLocator.getService(ProposalAuthorizationService.class);
//        List<Person> aggregators=proposalAuthorizationService.getPersonsInRole(document, RoleConstants.AGGREGATOR);
//        if(aggregators.size()== 1 ){
//            for(Person person:aggregators){
//                for (NarrativeUserRights userRights : newNarrativeUserRights) {
//                    if(person.getPersonId().equals(userRights.getUserId())){
//                        if(userRights.getAccessType().equals(NarrativeRight.VIEW_NARRATIVE_RIGHT.getAccessType())){
//                            isValid = false;
//                            this.reportError(Constants.NEW_NARRATIVE_USER_RIGHTS_PROPERTY_KEY, 
//                                    KeyConstants.ERROR_ONE_AGGREGATOR_MODFIY, person.getFullName());
//                        }
//                    }
//                }
//            }
//        }
        return isValid;
    }
    
    /**
     * Do any of the users have the given narrative right?
     * @param narrativeUserRights the list of narrative user rights
     * @param narrativeRight the narrative right to look for
     * @return true if at least one user has this narrative right; otherwise false
     */
    private boolean hasNarrativeRight(List<NarrativeUserRights> narrativeUserRights, NarrativeRight narrativeRight) {
        for (NarrativeUserRights userRights : narrativeUserRights) {
            if (StringUtils.equals(userRights.getAccessType(), narrativeRight.getAccessType())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Does this person have the necessary permission for the narrative right they
     * have been assigned?  In other words, a person cannot be given the narrative
     * right of modify if they don't have the MODIFY_NARRATIVE permssion.
     * @param userRights the person's narrative right
     * @param doc the Proposal Development Document
     * @return true if the person has the necessary permission; otherwise false
     */
    private boolean hasPermission(NarrativeUserRights userRights, ProposalDevelopmentDocument doc) {
        
        String personId = userRights.getUserId();
        
        if (StringUtils.equals(userRights.getAccessType(), NarrativeRight.MODIFY_NARRATIVE_RIGHT.getAccessType())) {
            if (!hasPermission(personId, doc, PermissionConstants.MODIFY_NARRATIVE)) {
                return false;
            }
        }
        else if (StringUtils.equals(userRights.getAccessType(), NarrativeRight.VIEW_NARRATIVE_RIGHT.getAccessType())) {
            if (!hasPermission(personId, doc, PermissionConstants.VIEW_NARRATIVE)) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * This method gets the attachment service
     * @return
     */
    protected KcAttachmentService getKcAttachmentService() {
        if(this.kcAttachmentService == null) {
            this.kcAttachmentService = KraServiceLocator.getService(KcAttachmentService.class);
        }
        return this.kcAttachmentService;
    }
    /**
     * Gets the parameter service.
     * @see org.kuali.kra.rules.ResearchDocumentRuleBase#getParameterService()
     */
    protected ParameterService getParameterService() {
        if (this.parameterService == null ) {
            this.parameterService = KraServiceLocator.getService(ParameterService.class);             
        }
        return this.parameterService;
    }
    
    /**
     * Gets the KC Person Service.
     * @return KC Person Service.
     */
    protected KcPersonService getKcPersonService() {
        if (this.kcPersonService == null) {
            this.kcPersonService = KraServiceLocator.getService(KcPersonService.class);
        }
        
        return this.kcPersonService;
    }
    
    protected PersonService getPersonService() {
        if (this.personService == null) {
            this.personService = KraServiceLocator.getService(PersonService.class);
        }
        
        return this.personService;
    }    
}
