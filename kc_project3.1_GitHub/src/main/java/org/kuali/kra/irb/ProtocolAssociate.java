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
package org.kuali.kra.irb;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.SequenceAssociate;
import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

/**
 * 
 * This class is to maintain repetitive coeus legacy code, protocolNumber & sequenceNumber, for protocol Bos.
 * 
 * Much of this class is duplicated with ProtocolAssociate but there is no
 * way around that due to the limitations in rice, ojb, etc.
 */
public abstract class ProtocolAssociate extends KraPersistableBusinessObjectBase implements SequenceAssociate<Protocol> {
    private static final long serialVersionUID = -8385115657304261423L;
    
    private String protocolNumber;

    private Integer sequenceNumber = Integer.valueOf(0);

    private Long protocolId;
    private Protocol protocol;

    /**
     * Constructs a ProtocolAssociate setting protocol related fields.
     * 
     * @param protocol the Protocol
     */
    public ProtocolAssociate(Protocol protocol) {
        this.setProtocol(protocol);
    }
    
    /**
     * Constructs a ProtocolAssociate.
     * 
     * Assures that the sequence number is always set. Note that 
     * when protocol implements versioning,  the KC versioning API 
     * will need to manage sequenceNumber.
     * 
     */
    public ProtocolAssociate() {
        super();
    }
    
    /**
     * Gets the protocolId attribute. 
     * @return Returns the protocolId.
     */
    public Long getProtocolId() {
        return this.protocolId;
    }

    /**
     * Sets the protocolId attribute value.
     * @param protocolId The protocolId to set.
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }
    
    /**
     * Gets the protocol attribute. 
     * @return Returns the protocol.
     */
    public Protocol getProtocol() {
        return this.protocol;
    }

    /**
     * Sets the protocol attribute value.
     * @param protocol The protocol to set.
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
        this.initProtocolInfo(protocol);
    }
    
    /**
     * Sets the protocol id and protocolNumber from the passed in protocol.
     * @param aProtocol the Protocol
     */
    private void initProtocolInfo(Protocol aProtocol) {       
        this.setProtocolId(aProtocol != null ? aProtocol.getProtocolId() : null);
        this.setProtocolNumber(aProtocol != null ? aProtocol.getProtocolNumber() : null);
        this.setSequenceNumber(aProtocol != null ? aProtocol.getSequenceNumber() : null);
    }
    
    /**
     * Gets the sequence number.
     * @return sequence number.
     */
    public Integer getSequenceNumber() {
        return this.sequenceNumber;
    }

    /**
     * Sets the sequence number.
     * @param sequenceNumber sequence number.
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    /**
     * Gets the protocol number.
     * @return protocol number.
     */
    public String getProtocolNumber() {
        if ((StringUtils.isBlank(protocolNumber) || "0".equals(protocolNumber)) && protocol != null && StringUtils.isNotBlank(protocol.getProtocolNumber())) {
            setProtocolNumber(protocol.getProtocolNumber());
        }
        return this.protocolNumber;
    }

    /**
     * Sets the protocol number.
     * @param protocolNumber protocol number.
     */
    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    /** {@inheritDoc} */
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("protocolId", this.protocolId);
        map.put("protocolNumber", this.protocolNumber);
        map.put("sequenceNumber", this.sequenceNumber);
        return map;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.protocolNumber == null) ? 0 : this.protocolNumber.hashCode());
        result = prime * result + ((this.sequenceNumber == null) ? 0 : this.sequenceNumber.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProtocolAssociate other = (ProtocolAssociate) obj;
        if (this.protocolNumber == null) {
            if (other.protocolNumber != null) {
                return false;
            }
        } else if (!this.protocolNumber.equals(other.protocolNumber)) {
            return false;
        }
        if (this.sequenceNumber == null) {
            if (other.sequenceNumber != null) {
                return false;
            }
        } else if (!this.sequenceNumber.equals(other.sequenceNumber)) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    public Protocol getSequenceOwner() {
        return this.getProtocol();
    }

    /** {@inheritDoc} */
    public void setSequenceOwner(Protocol newlyVersionedOwner) {
        this.setProtocol(newlyVersionedOwner);   
    }
    
    /**
     * This inits the object to an unpersisted state by calling {@link #resetPersistenceState()}
     * Also, sets the protocol, protocol id, sequence number, and protocol number.
     * @param aProtocol the protocol to init the object with.
     */
    public final void init(Protocol aProtocol) {
        this.resetPersistenceState();
        this.setProtocol(aProtocol);
        this.postInitHook(aProtocol);
        this.setSequenceNumber(0);
    }
    
    /**
     * This method is designed to allow subclasses to perform additional initialization not performed by the final init method.
     * This method is called by {@link #init(Protocol)} after all other initialization is performed.
     * @param aProtocol the protocol initialization is requested with.
     */
    public void postInitHook(Protocol aProtocol) {
        //no-op
    }
}
