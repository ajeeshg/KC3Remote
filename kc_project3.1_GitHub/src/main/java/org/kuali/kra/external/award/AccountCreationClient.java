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
package org.kuali.kra.external.award;

import javax.xml.datatype.DatatypeConfigurationException;

import org.kuali.kra.award.home.Award;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.service.DocumentService;

public interface AccountCreationClient {

    /**
     * This method creates and award account.
     * @param award
     * @throws DatatypeConfigurationException
     * @throws WorkflowException
     */
    void createAwardAccount(Award award)throws DatatypeConfigurationException, WorkflowException;
    
    void setDocumentService(DocumentService documentService);
}
