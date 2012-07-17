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
package org.kuali.kra.institutionalproposal.exception;

import org.kuali.rice.kns.exception.KualiException;

public class InstitutionalProposalCreationException extends KualiException {

    private static final long serialVersionUID = 1033037690690398384L;

    public InstitutionalProposalCreationException(String message) {
        super(message);
    }
    
    public InstitutionalProposalCreationException(String message, Throwable t) {
        super(message, t);
    }

}
