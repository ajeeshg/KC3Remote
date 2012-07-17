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
package org.kuali.kra.irb.noteattachment;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.kra.SkipVersioning;
import org.kuali.kra.irb.Protocol;
import org.kuali.kra.irb.personnel.ProtocolPerson;

/**
 * This class represents the Protocol Attachment Personnel.
 */
public class ProtocolAttachmentPersonnel extends ProtocolAttachmentBase {

    private static final long serialVersionUID = -7115904344245464654L;
    private static final String GROUP_CODE = "2";
    
    private Integer personId;
    @SkipVersioning
    private ProtocolPerson person;
    
    private String typeCode;
    private ProtocolAttachmentType type;
    private String description;
    
     
    /**
     * empty ctor to satisfy JavaBean convention.
     */
    public ProtocolAttachmentPersonnel() {
        super();
    }
    
    /**
     * Convenience ctor to add the protocol as an owner.
     * 
     * <p>
     * This ctor does not validate any of the properties.
     * </p>
     * 
     * @param protocol the protocol.
     */
    public ProtocolAttachmentPersonnel(final Protocol protocol) {
        super(protocol);
    }

    /**
     * Gets the Protocol Attachment Personnel Person.
     * @return the Protocol Attachment Personnel Person
     */
    public ProtocolPerson getPerson() {
        if (person == null && personId != null) {
            this.refreshReferenceObject("person");
        }
        return this.person;
    }

    /**
     * Sets the Protocol Attachment Personnel Person.
     * @param person the Protocol Attachment Personnel Person
     */
    public void setPerson(ProtocolPerson person) {
        this.person = person;
    }

    /**
     * Gets the person Id. 
     * @return the person Id.
     */
    public Integer getPersonId() {
        return this.personId;
    }

    /**
     * Sets the person Id.
     * @param personId the person Id.
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /** {@inheritDoc} */
    public ProtocolAttachmentType getType() {
        return this.type;
    }

    /** {@inheritDoc} */
    public void setType(ProtocolAttachmentType type) {
        this.type = type;
    }

    /** {@inheritDoc} */
    public String getTypeCode() {
        return this.typeCode;
    }

    /** {@inheritDoc} */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
    /** {@inheritDoc} */
    public String getGroupCode() {
        return GROUP_CODE;
    }
    
    /** {@inheritDoc} */
    public String getDescription() {
        return this.description;
    }

    /** {@inheritDoc} */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /** {@inheritDoc} */
    @Override
    public String getAttachmentDescription() {
        return "Personnel Attachment";
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean supportsVersioning() {
        return false;
    }

       
    /** 
     * {@inheritDoc}
     * also nulling the person id because when saving after versioning, the person id is reverting to the wrong BO.
     */
    @Override
    public void resetPersistenceState() {
        super.resetPersistenceState();
        this.setPersonId(null);
    }
    
    /** {@inheritDoc} */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        final LinkedHashMap<String, Object> hashMap = super.toStringMapper();
        hashMap.put(PropertyName.PERSON_ID.getPropertyName(), this.getPersonId());
        hashMap.put(TypedAttachment.PropertyName.TYPE_CODE.getPropertyName(), this.getTypeCode());
        hashMap.put(TypedAttachment.PropertyName.DOCUMENT_ID.getPropertyName(), this.getDocumentId());
        hashMap.put(TypedAttachment.PropertyName.GROUP_CODE.getPropertyName(), this.getGroupCode());
        hashMap.put(TypedAttachment.PropertyName.DESCRIPTION.getPropertyName(), this.getDescription());
        return hashMap;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
//        result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
        result = prime * result + ((personId == null) ? 0 : personId.hashCode());
        result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProtocolAttachmentPersonnel other = (ProtocolAttachmentPersonnel) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
//        if (documentId == null) {
//            if (other.documentId != null) {
//                return false;
//            }
//        } else if (!documentId.equals(other.documentId)) {
//            return false;
//        }
        if (personId == null) {
            if (other.personId != null) {
                return false;
            }
        } else if (!personId.equals(other.personId)) {
            return false;
        }
        if (typeCode == null) {
            if (other.typeCode != null) {
                return false;
            }
        } else if (!typeCode.equals(other.typeCode)) {
            return false;
        }
        return true;
    }

    /**
     * Contains all the property names in this class.
     */
    public static enum PropertyName {
        PERSON_ID("personId");
        
        private final String name;
        
        /**
         * Sets the enum properties.
         * @param name the name.
         */
        PropertyName(final String name) {
            this.name = name;
        }
        
        /**
         * Gets the property name.
         * @return the the property name.
         */
        public String getPropertyName() {
            return this.name;
        }
        
        /**
         * Gets the {@link #getPropertyName() propertyName()}.
         * @return {@link #getPropertyName() propertyName()}
         */
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    @Override
    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        if (updateTimestamp == null || getUpdateTimestamp() == null) {
            super.setUpdateTimestamp(updateTimestamp);
        }
    }

    @Override
    public void setUpdateUser(String updateUser) {
        if (updateUser == null || getUpdateUser() == null ) {
            super.setUpdateUser(updateUser);
        }
    }

    public void init(ProtocolPerson protocolPerson) {
        setPerson(protocolPerson);
        setPersonId(protocolPerson.getProtocolPersonId());
        setId(null);
        setProtocolNumber(protocolPerson.getProtocol().getProtocolNumber());
    }

}
