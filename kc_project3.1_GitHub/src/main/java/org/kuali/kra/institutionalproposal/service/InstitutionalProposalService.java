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
package org.kuali.kra.institutionalproposal.service;

import java.util.List;
import java.util.Set;

import org.kuali.kra.budget.core.Budget;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;

/**
 * This interface defines public services made available by the Institutional Proposal module.
 */
public interface InstitutionalProposalService {
    
    /**
     * Creates a new Institutional Proposal with data copied from the given development proposal and budget.
     * 
     * @param developmentProposal DevelopmentProposal
     * @param budget Budget
     * @return String The new proposal number
     */
    String createInstitutionalProposal(DevelopmentProposal developmentProposal, Budget budget);
    
    /**
     * Creates a new version of the Institutional Proposal corresponding to the given proposal number, 
     * with data copied from the given development proposal and budget.
     * 
     * @param proposalNumber String
     * @param developmentProposal DevelopmentProposal
     * @param budget Budget
     * @return String The new version number
     */
    String createInstitutionalProposalVersion(String proposalNumber, DevelopmentProposal developmentProposal, Budget budget);
    
    /**
     * Return an Institutional Proposal, if one exists.
     * 
     * @param proposalId String
     * @return InstitutionalProposal, or null if none is found.
     */
    InstitutionalProposal getInstitutionalProposal(String proposalId);
    
    /**
     * Return the PENDING version of an Institutional Proposal, if one exists.
     * Note, PENDING here refers to the Version Status, NOT the Proposal Status of the Institutional Proposal.
     * 
     * @param proposalNumber String
     * @return InstitutionalProposal, or null if a PENDING version is not found.
     * @see org.kuali.kra.bo.versioning.VersionStatus
     */
    InstitutionalProposal getPendingInstitutionalProposalVersion(String proposalNumber);
    
    /**
     * Return the ACTIVE version of an Institutional Proposal, if one exists.
     * Note, ACTIVE here refers to the Version Status, NOT the Proposal Status of the Institutional Proposal.
     * 
     * @param proposalNumber String
     * @return InstitutionalProposal, or null if a ACTIVE version is not found.
     * @see org.kuali.kra.bo.versioning.VersionStatus
     */
    InstitutionalProposal getActiveInstitutionalProposalVersion(String proposalNumber);
    
    /**
     * Designate one or more Institutional Proposals as Funded by an Award.
     * 
     * If the given Proposal has a Proposal Status of Pending, a new Final version of the Proposal
     * will be created with a Proposal Status of Funded.
     * 
     * If the current Active version is already Funded, it will be left alone.
     * 
     * @param proposalNumbers The proposals to update.
     * @return List<InstitutionalProposal> The new Funded versions.
     */
    List<InstitutionalProposal> fundInstitutionalProposals(Set<String> proposalNumbers);
    
    /**
     * Designate the given Proposals as no longer funded by the given Award.
     * 
     * If the given Award was the only funding Award for a Proposal, a new Final version of the Proposal
     * will be created with a Proposal Status of Pending.
     * 
     * If the Proposal has other funding Awards, it will be left alone.  It will also be left alone
     * if it is funded by the active version of the given award number (this is a functional requirement).
     * 
     * @param proposalNumbers The proposals to update.
     * @param awardNumber The Award that is de-funding the proposal.
     * @param awardSequence The sequence number of the Award.
     * @return List<InstitutionalProposal> The new Pending versions.
     */
    List<InstitutionalProposal> defundInstitutionalProposals(Set<String> proposalNumbers, String awardNumber, Integer awardSequence);
}