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
package org.kuali.kra.dao;

import java.util.Collection;

import org.kuali.rice.kns.dao.LookupDao;

/**
 * This class is Custom lookup service Interface for kc development.
 */
public interface KraLookupDao extends LookupDao {

    /**
     * This method returns collection with wildcard in lookup string.
     * @param example
     * @param criteria
     * @param unbounded
     * @return
     */
    @SuppressWarnings("unchecked")
    Collection findCollectionUsingWildCard(Class businessObjectClass, String field, String wildCard, boolean unbounded);

}
