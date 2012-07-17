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
package org.kuali.kra.institutionalproposal;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.CommentType;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;

public class ProposalComment extends InstitutionalProposalAssociate { 
    
    private static final long serialVersionUID = 1L;

    private Long proposalCommentsId; 
    private Long proposalId; 
    private String commentTypeCode; 
    private String comments; 
    
    private CommentType commentType; 
    private InstitutionalProposal proposal; 
    
    public ProposalComment() { 

    } 
    
    public Long getProposalCommentsId() {
        return proposalCommentsId;
    }

    public void setProposalCommentsId(Long proposalCommentsId) {
        this.proposalCommentsId = proposalCommentsId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getCommentTypeCode() {
        return commentTypeCode;
    }

    public void setCommentTypeCode(String commentTypeCode) {
        this.commentTypeCode = commentTypeCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public InstitutionalProposal getProposal() {
        return proposal;
    }

    public void setProposal(InstitutionalProposal proposal) {
        this.proposal = proposal;
    }

    /** {@inheritDoc} */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
        hashMap.put("proposalCommentsId", this.getProposalCommentsId());
        hashMap.put("proposalId", this.getProposalId());
        hashMap.put("commentCode", this.getCommentTypeCode());
        hashMap.put("comments", this.getComments());
        return hashMap;
    }
    
}
