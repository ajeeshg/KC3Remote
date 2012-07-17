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
package org.kuali.kra.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.util.AuditCluster;
import org.kuali.rice.kns.util.AuditError;
import org.kuali.rice.kns.util.ExceptionUtils;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * This class provides error reporting capabilities.
 * <p>
 * This logic was taken from
 * {@link ResearchDocumentRuleBase ResearchDocumentRuleBase} so that classes don't have to
 * subclass ResearchDocumentRuleBase to report an error using these convenient methods.
 * </p>
 */
public class ErrorReporter {

    private static final Log LOG = LogFactory.getLog(ErrorReporter.class);
    
    /**
     * Wrapper around global errorMap.put call, to allow better logging.
     * 
     * @param propertyName
     * @param errorKey
     * @param errorParams
     */
    public void reportError(String propertyName, String errorKey, String... errorParams) {
        GlobalVariables.getErrorMap().putError(propertyName, errorKey, errorParams);
        if (LOG.isDebugEnabled()) {
            LOG.debug("rule failure at " + ExceptionUtils.describeStackLevels(1, 2));
        }
    }
    
    /**
     * Adds an audit error to the
     * {@link GlobalVariables#getAuditErrorMap() GlobalVariables#getAuditErrorMap()}.
     * 
     * @param error the error to add.
     * @param errorKey the error map key
     * @param clusterLabel the cluster label
     * @param clusterCategory the cluster category
     * @throws IllegalArgumentException if error, errorKey, clusterLabel, or clusterCategory are null or
     * if errorKey, clusterLabel, or clusterCategory are whitespace
     */
    public void reportAuditError(AuditError error, String errorKey, String clusterLabel, String clusterCategory) {
        if (error == null || StringUtils.isBlank(errorKey)
            || StringUtils.isBlank(clusterLabel) || StringUtils.isBlank(clusterCategory)) {
            throw new IllegalArgumentException(new StringBuilder("null argument error: ")
                .append(error)
                .append(" errorkey: ")
                .append(errorKey)
                .append(" clusterLabel: ")
                .append(clusterLabel)
                .append(" clusterCategory: ")
                .append(clusterCategory).toString());
        }
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("rule failure at " + ExceptionUtils.describeStackLevels(1, 2));
        }
        
        @SuppressWarnings("unchecked")
        final Map<String, AuditCluster> errorMap = GlobalVariables.getAuditErrorMap();
        
        AuditCluster cluster = errorMap.get(errorKey);
        
        if (cluster == null) {
            cluster = new AuditCluster(clusterLabel, new ArrayList<AuditError>(), clusterCategory);
            errorMap.put(errorKey, cluster);
        }
        
        @SuppressWarnings("unchecked")
        final Collection<AuditError> errors = cluster.getAuditErrorList();
        errors.add(error);
    }
    
    public void reportSoftError(String propertyName, String errorKey, String... errorParams) {
        addSoftError(propertyName, errorKey, errorParams);
        if (LOG.isDebugEnabled()) {
            LOG.debug("rule failure at " + ExceptionUtils.describeStackLevels(1, 2));
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Collection<SoftError>> getSoftErrors() {
        UserSession session = GlobalVariables.getUserSession();
        Object o = session.retrieveObject(KeyConstants.SOFT_ERRORS_KEY);
        Map<String, Collection<SoftError>> softErrors =(Map<String, Collection<SoftError>>) o;
        if(softErrors == null) {
            softErrors = initializeSoftErrorMap();
        }
        return softErrors;
    }
    
    /**
     * This method adds a soft error to the collection of soft errors
     * @param errorKey
     * @param errorParams
     */
    private void addSoftError(String propertyName, String errorKey, String[] errorParams) {
        Map<String, Collection<SoftError>> softErrorMap = getSoftErrors();
        Collection<SoftError> errorsForProperty = softErrorMap.get(propertyName);
        if(errorsForProperty == null) {
            errorsForProperty = new HashSet<SoftError>();
        }
        errorsForProperty.add(new SoftError(errorKey, errorParams));
        softErrorMap.put(propertyName, errorsForProperty);
    }

    private Map<String, Collection<SoftError>> initializeSoftErrorMap() {
        Map<String, Collection<SoftError>> softErrorMap = Collections.synchronizedMap(new HashMap<String, Collection<SoftError>>() {
            private static final long serialVersionUID = 709850431504932842L;

            @Override
            public Collection<SoftError> get(Object key) {
                return super.remove(key);
            }
            
        });
        GlobalVariables.getUserSession().addObject(KeyConstants.SOFT_ERRORS_KEY, softErrorMap);
        return softErrorMap;
    }
    
    /**
     * Wrapper around global errorMap.put call, to allow better logging.
     * 
     * @param propertyName
     * @param errorKey
     * @param errorParams
     */
    public void reportWarning(String propertyName, String errorKey, String... errorParams) {
        GlobalVariables.getMessageMap().putWarning(propertyName, errorKey, errorParams);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("rule warning at ", ExceptionUtils.describeStackLevels(1, 2)));
        }
    }

   
    /**
     * Does the property have any errors in the message map?
     * @param propertyName
     * @return
     */
    public boolean propertyHasErrorReported(String propertyName) {
        boolean result = false;
        if( GlobalVariables.getMessageMap().getErrorMessagesForProperty(propertyName) != null) {
            result = GlobalVariables.getMessageMap().getErrorMessagesForProperty(propertyName).size() > 0;
        }
        return result;
    }
    
    /**
     * Removed the errors in the message map for the property.
     * @param propertyName
     */
    public void removeErrors(String propertyName) {
        if(GlobalVariables.getMessageMap().getErrorMessagesForProperty(propertyName)!=null) {
            GlobalVariables.getMessageMap().getErrorMessagesForProperty(propertyName).clear();
        }
    }
    
}
