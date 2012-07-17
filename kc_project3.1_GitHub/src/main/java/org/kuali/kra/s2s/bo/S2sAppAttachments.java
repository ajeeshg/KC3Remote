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
package org.kuali.kra.s2s.bo;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

public class S2sAppAttachments extends KraPersistableBusinessObjectBase {
    private Long s2sAppAttachmentId;
    private String contentId;
	private String proposalNumber;
	private String contentType;
	private String hashCode;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getProposalNumber() {
		return proposalNumber;
	}

	public void setProposalNumber(String proposalNumber) {
		this.proposalNumber = proposalNumber;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}


	@Override 
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap hashMap = new LinkedHashMap();
		hashMap.put("contentId", getContentId());
		hashMap.put("proposalNumber", getProposalNumber());
		hashMap.put("contentType", getContentType());
		hashMap.put("hashCode", getHashCode());
		return hashMap;
	}

    /**
     * Gets the s2sAppAttachmentId attribute. 
     * @return Returns the s2sAppAttachmentId.
     */
    public Long getS2sAppAttachmentId() {
        return s2sAppAttachmentId;
    }

    /**
     * Sets the s2sAppAttachmentId attribute value.
     * @param appAttachmentId The s2sAppAttachmentId to set.
     */
    public void setS2sAppAttachmentId(Long appAttachmentId) {
        s2sAppAttachmentId = appAttachmentId;
    }
}
