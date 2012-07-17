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
package org.kuali.kra.award.paymentreports.awardreports.reporting;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.kra.award.AwardAssociate;
import org.kuali.kra.bo.KcPerson;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.service.KcPersonService;

public class AwardReporting extends AwardAssociate { 
    
    private static final long serialVersionUID = 1L;

    private Integer awardReportingId; 
    private Long awardReportTermId;
    private Date dueDate; 
    private Integer overdueCounter; 
    private Integer reportStatusCode; 
    private Date activityDate; 
    private String comments; 
    private String personId;
    private transient KcPersonService kcPersonService;
    
    //private ReportStatus reportStatus;
      
    public AwardReporting() { 

    }
    
    /**
     * Gets the awardReportTermId attribute. 
     * @return Returns the awardReportTermId.
     */
    public Long getAwardReportTermId() {
        return awardReportTermId;
    }

    /**
     * Sets the awardReportTermId attribute value.
     * @param awardReportTermId The awardReportTermId to set.
     */
    public void setAwardReportTermId(Long awardReportTermId) {
        this.awardReportTermId = awardReportTermId;
    }

    /**
     * Gets the person attribute. 
     * @return Returns the person.
     */
    public KcPerson getPerson() {
        return this.getKcPersonService().getKcPersonByPersonId(personId);
    }
    
    /**
     * Gets the KC Person Service.
     * @return KC Person Service.
     */
    public KcPersonService getKcPersonService() {
        if (this.kcPersonService == null) {
            this.kcPersonService = KraServiceLocator.getService(KcPersonService.class);
        }
        
        return this.kcPersonService;
    }
    
    public Integer getAwardReportingId() {
        return awardReportingId;
    }

    public void setAwardReportingId(Integer awardReportingId) {
        this.awardReportingId = awardReportingId;
    }
    
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getOverdueCounter() {
        return overdueCounter;
    }

    public void setOverdueCounter(Integer overdueCounter) {
        this.overdueCounter = overdueCounter;
    }

    public Integer getReportStatusCode() {
        return reportStatusCode;
    }

    public void setReportStatusCode(Integer reportStatusCode) {
        this.reportStatusCode = reportStatusCode;
    }

    public Date getActivityDate() {
        return activityDate;
    }
    
    /**
     * @see org.kuali.kra.Sequenceable#resetPersistenceState()
     */
    public void resetPersistenceState() {
        this.awardReportingId = null;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /** {@inheritDoc} */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
        hashMap.put("awardReportingId", this.getAwardReportingId());        
        hashMap.put("dueDate", this.getDueDate());
        hashMap.put("overdueCounter", this.getOverdueCounter());
        hashMap.put("reportStatusCode", this.getReportStatusCode());
        hashMap.put("activityDate", this.getActivityDate());
        hashMap.put("comments", this.getComments());
        hashMap.put("personId", this.getPersonId());        
        return hashMap;
    }
    
}
