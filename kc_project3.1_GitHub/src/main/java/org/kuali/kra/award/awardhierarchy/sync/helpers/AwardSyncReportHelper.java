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
package org.kuali.kra.award.awardhierarchy.sync.helpers;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.awardhierarchy.sync.AwardSyncChange;
import org.kuali.kra.award.awardhierarchy.sync.AwardSyncType;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.award.paymentreports.awardreports.AwardReportTerm;
import org.kuali.rice.kns.bo.PersistableBusinessObject;

/**
 * Award Hierarchy Sync Helper for Reports.
 */
public class AwardSyncReportHelper extends AwardSyncHelperBase {
    
    @SuppressWarnings("unchecked")
    @Override
    public void applySyncChange(Award award, AwardSyncChange change) 
        throws NoSuchFieldException, IntrospectionException, IllegalAccessException, InvocationTargetException, 
        ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Collection reports = award.getAwardReportTermItems();
        AwardReportTerm report = (AwardReportTerm) getAwardSyncUtilityService().findMatchingBo(reports, change.getXmlExport().getKeys());
        if (StringUtils.equals(change.getSyncType(), AwardSyncType.ADD_SYNC.getSyncValue())) {
            if (report != null) {
                this.setValuesOnSyncable(report, change.getXmlExport().getValues(), change);
            } else {
                report = new AwardReportTerm();
                setValuesOnSyncable(report, change.getXmlExport().getKeys(), change);
                setValuesOnSyncable(report, change.getXmlExport().getValues(), change);
                award.add(report);
            }
        } else {
            if (report != null) {
                reports.remove(report);
            }
        }
    }
    
    @Override
    protected String getObjectDesc(PersistableBusinessObject syncableObject, String attrName) {
        return "Report";
    }
    
    @Override
    protected String getDataDesc(PersistableBusinessObject syncableObject, String attrName) {
        AwardReportTerm term = (AwardReportTerm) syncableObject;
        String result = term.getReportClass().getDescription()+ DELIMITER
            + term.getReport().getDescription();
        if (term.getFrequency() != null) {
            result += DELIMITER + term.getFrequency().getDescription();
        }
        if (term.getFrequencyBase() != null) {
            result += DELIMITER + term.getFrequencyBase().getDescription();
        }
        if (term.getDistribution() != null) {
            result += DELIMITER + term.getDistribution().getDescription();
        }
        return result;
    }     
}
