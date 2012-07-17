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
package org.kuali.kra.award.paymentreports.closeout;

import org.kuali.kra.award.home.Award;

/**
 * 
 * This class represents the AwardCloseoutService - which are related to Award Closeout panel on Payment Reports and Terms panel.
 * 
 */
public interface AwardCloseoutService {
    
    /**
     * 
     * Whenever a save occurs on Payment, Reports and Terms tab; This method gets called from the action upon save 
     * and updates the due dates for award closeout static reports.
     *
     * @param award
     */
    void updateCloseoutDueDatesBeforeSave(Award award);
}
