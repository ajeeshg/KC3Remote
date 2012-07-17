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
package org.kuali.kra.award.notesandattachments.notes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * This class...
 */
public class AwardNotepadBean implements Serializable {

 /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2409602626444019766L;

    private AwardForm parent;
    
    private AwardNotepad newAwardNotepad;
    
    /**
     * Constructs a AwardNotepadBean
     * @param parent
     */
    public AwardNotepadBean() {
        super();
    }
    /**
     * Constructs a CostShareFormHelper
     * @param parent
     */
    public AwardNotepadBean(AwardForm parent) {
        this.parent = parent;
    }
    
    /**
     * Initialize subform
     */
    public void init() {
        newAwardNotepad = new AwardNotepad(); 
    }


    /**
     * Gets the newAwardNotepad attribute. 
     * @return Returns the newAwardNotepad.
     */
    public AwardNotepad getNewAwardNotepad() {
        return newAwardNotepad;
    }

    /**
     * Sets the newAwardNotepad attribute value.
     * @param newAwardNotepad The newAwardNotepad to set.
     */
    public void setNewAwardNotepad(AwardNotepad newAwardNotepad) {
        this.newAwardNotepad = newAwardNotepad;
    }

    /**
     * This method...
     * @return
     */
    public AwardDocument getAwardDocument() {
        return parent.getAwardDocument();
    }
    
    /**
     * This method...
     * @return
     */
    public Object getData() {
        return getNewAwardNotepad();
    }
    
    /**
     * This method is called when adding a new AwardCostShare
     * @param formHelper
     * @return
     * @throws Exception
     */
    public boolean addNote(AwardNotepadBean awardNotepadBean) throws Exception {
        AwardNotepad note = awardNotepadBean.getNewAwardNotepad();
               
        if(StringUtils.isBlank(note.getComments())){
            note.setComments("&nbsp");
        }
        
        Calendar cl = Calendar.getInstance();
        note.setCreateTimestamp(new Timestamp(cl.getTime().getTime()));
        note.setCreateUser(GlobalVariables.getUserSession().getPrincipalName());
        awardNotepadBean.getAwardDocument().getAward().add(note);
        awardNotepadBean.init();
        return true;
    }
}
