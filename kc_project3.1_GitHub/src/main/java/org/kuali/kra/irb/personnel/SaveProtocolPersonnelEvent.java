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
package org.kuali.kra.irb.personnel;

import org.kuali.kra.irb.ProtocolDocument;
import org.kuali.kra.rule.BusinessRuleInterface;
import org.kuali.kra.rule.event.KraDocumentEventBaseExtension;

/**
 * Represents the event to save a ProtocolPersonnel.
 */
public class SaveProtocolPersonnelEvent extends KraDocumentEventBaseExtension {

    /**
     * Constructs an SaveProtocolPersonnelEvent.
     * @param errorPathPrefix The error path prefix
     * @param document The document to validate
     */
    public SaveProtocolPersonnelEvent(String errorPathPrefix, ProtocolDocument document) {
        super("Saving protocol personnel on document " + getDocumentId(document), errorPathPrefix, document);
    }

    @Override
    @SuppressWarnings("unchecked")
    public BusinessRuleInterface getRule() {
        return new SaveProtocolPersonnelRule();
    }
    
}