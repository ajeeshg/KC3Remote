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
package org.kuali.kra.timeandmoney.service;

import java.util.List;
import java.util.Map;

import org.kuali.kra.timeandmoney.AwardVersionHistory;
import org.kuali.kra.timeandmoney.TimeAndMoneyDocumentHistory;
import org.kuali.rice.kew.exception.WorkflowException;

public interface TimeAndMoneyHistoryService {
    
    void  getTimeAndMoneyHistory(String awardNumber, Map<Object, Object> timeAndMoneyHistory, List<Integer> columnSpan) throws WorkflowException;

    void buildTimeAndMoneyHistoryObjects(String awardNumber, List<AwardVersionHistory> awardVersionHistoryCollection) throws WorkflowException;
}
