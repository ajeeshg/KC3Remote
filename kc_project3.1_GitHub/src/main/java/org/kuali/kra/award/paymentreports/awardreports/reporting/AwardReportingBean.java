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
package org.kuali.kra.award.paymentreports.awardreports.reporting;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.award.paymentreports.awardreports.AwardReportTerm;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.service.AwardScheduleGenerationService;
import org.kuali.rice.kns.service.KeyValuesService;
import org.kuali.rice.kns.service.KualiRuleService;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * This class supports the AwardForm class
 */
public class AwardReportingBean implements Serializable {    
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7425300585057908055L;
    private List<AwardReporting> newAwardReportings;
    private AwardReporting newAwardReporting;
        
    private transient KualiRuleService ruleService;
    private AwardForm form;
    
    /**
     * 
     * Constructs a AwardReportsBean.java.
     * @param form
     */
    public AwardReportingBean(AwardForm form) {
        this.form = form;
    }
    
    /**
     * 
     * Constructs a AwardReportsBean.java.
     */
    public AwardReportingBean() {
        
    }
    
    /**
     * 
     * This method retrieves the parent award report term and from there retrieves the list of child award reporting objects.
     * and deletes the one as per the index.
     * 
     * @param index
     * @param deletedItemIndex
     */
    public void deleteReportScheduleItem(int index, int deletedItemIndex) {
        List<AwardReporting> items = getAward().getAwardReportTermItems().get(index).getAwardReportings();
        if(deletedItemIndex >= 0 && deletedItemIndex < items.size()) {
            items.remove(deletedItemIndex);
        }
    }
    
    /**
     * 
     * This method gets called from the action method to generate report schedules - for the report tracking functionality.
     * 
     * @param index
     * @throws ParseException
     */
    public void generateReportSchedules(int index) throws ParseException{
        
        List<Date> dates = new ArrayList<Date>();
        
        List<AwardReportTerm> awardReportTerms = new ArrayList<AwardReportTerm>();
        awardReportTerms.add(getAward().getAwardReportTermItems().get(index));
        
        dates = getAwardScheduleGenerationService().generateSchedules(getAward(), awardReportTerms, true);
        
        getAward().getAwardReportTermItems().get(index).setAwardReportings(new ArrayList<AwardReporting>());
        
        if(dates.size() == 0){
         //   GlobalVariables.getMessageMap().putError("document.awardList[0].awardReportTermItems["+ index + "].frequencyBaseCode", KeyConstants.ERROR_SCHEDULE_GENERATION_FREQ_BASE_IS_NULL);
            newAwardReporting = new AwardReporting();
            getAward().getAwardReportTermItems().get(index).getAwardReportings().add(newAwardReporting);
        }
        
        for(Date date: dates){
            newAwardReporting = new AwardReporting();
            java.sql.Date sqldate = new java.sql.Date(date.getTime());
            newAwardReporting.setDueDate(sqldate);            
            getAward().getAwardReportTermItems().get(index).getAwardReportings().add(newAwardReporting);
        }
    }

    
    /**
     * @return
     */
    public Award getAward() {
        return form.getAwardDocument().getAward();
    }

    /**
     * 
     * @return
     */
    public AwardDocument getAwardDocument() {
        return form.getAwardDocument();
    }
    
    /**
     * 
     * This method retrieves the KualiRuleService
     * @return
     */
    protected KualiRuleService getRuleService() {
        if(ruleService == null) {
            ruleService = (KualiRuleService) KraServiceLocator.getService(KualiRuleService.class); 
        }
        return ruleService;
    }
    
    protected void setRuleService(KualiRuleService ruleService) {
        this.ruleService = ruleService;
    }
           
    /**
     * 
     * This is a wrapper method for the retrieval of KeyValuesService.
     * 
     * @return
     */
    protected KeyValuesService getKeyValuesService(){
        return KraServiceLocator.getService(KeyValuesService.class);
    }
    
    /**
    * 
    * This is a helper method to retrieve the AwardScheduleGenerationService.
    * @return
    */
    protected AwardScheduleGenerationService getAwardScheduleGenerationService(){
        return KraServiceLocator.getService(AwardScheduleGenerationService.class);
    }

    /**
     * Gets the newAwardReportings attribute. 
     * @return Returns the newAwardReportings.
     */
    public List<AwardReporting> getNewAwardReportings() {
        return newAwardReportings;
    }

    /**
     * Sets the newAwardReportings attribute value.
     * @param newAwardReportings The newAwardReportings to set.
     */
    public void setNewAwardReportings(List<AwardReporting> newAwardReportings) {
        this.newAwardReportings = newAwardReportings;
    }
    
}
