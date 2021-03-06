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
package org.kuali.kra.budget.lookup.keyvalue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.kra.award.budget.AwardBudgetService;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.budget.document.BudgetDocument;
import org.kuali.kra.budget.parameters.BudgetPeriod;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.lookup.keyvalue.KeyValueFinderService;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.service.DocumentService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.MultipleValueLookupForm;
import org.kuali.rice.core.util.KeyLabelPair;

public class BudgetExpensePeriodValuesFinder extends BudgetPeriodValuesFinder {
    protected Log LOG = LogFactory.getLog(BudgetExpensePeriodValuesFinder.class);
    
    protected KeyValueFinderService keyValueFinderService= (KeyValueFinderService)KraServiceLocator.getService("keyValueFinderService");
    protected AwardBudgetService awardBudgetService = KraServiceLocator.getService(AwardBudgetService.class);
    protected DocumentService documentService = KraServiceLocator.getService(DocumentService.class);
    
    /**
     * Constructs the list of Budget Periods.  Each entry
     * in the list is a &lt;key, value&gt; pair, where the "key" is the unique
     * status code and the "value" is the textual description that is viewed
     * by a user.  The list is obtained from the BudgetDocument if any are defined there. 
     * Otherwise, it is obtained from a lookup of the BUDGET_PERIOD database table
     * via the "KeyValueFinderService".
     * 
     * @return the list of &lt;key, value&gt; pairs of abstract types.  The first entry
     * is always &lt;"", "select:"&gt;.
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> keyLabelPairs = null;
        
        KualiForm form = GlobalVariables.getKualiForm();
        if (form instanceof KualiDocumentFormBase) {
            Document doc = ((KualiDocumentFormBase) form).getDocument();
            if(doc instanceof BudgetDocument) {
                List<BudgetPeriod> budgetPeriods = ((BudgetDocument)doc).getBudget().getBudgetPeriods();
                if (budgetPeriods.size() > 0) {
                    keyLabelPairs = buildKeyLabelPairs(budgetPeriods);
                }
            }
        } else if (form instanceof MultipleValueLookupForm) {
            try {
                BudgetDocument doc = (BudgetDocument) getDocumentService().getByDocumentHeaderId(((MultipleValueLookupForm) form).getDocNum());
                List<BudgetPeriod> budgetPeriods = getAwardBudgetService().findBudgetPeriodsFromLinkedProposal(((AwardDocument) doc.getParentDocument()).getAward().getAwardNumber());
                if (budgetPeriods.size() > 0) {
                    keyLabelPairs = buildKeyLabelPairsForPeriodSearch(budgetPeriods);
                }
            }
            catch (WorkflowException e) {
                LOG.error("Unable to load document for budget period values finder.", e);
            }
        }
        
        if (keyLabelPairs != null) {
            return keyLabelPairs;
        } else {
            return new ArrayList<KeyLabelPair>();
        }
    }
    
    private List<KeyLabelPair> buildKeyLabelPairs(List<BudgetPeriod> budgetPeriods) {
        List<KeyLabelPair> keyLabelPairs = new ArrayList<KeyLabelPair>();
        for (BudgetPeriod budgetPeriod : budgetPeriods) {
            if (budgetPeriod.getBudgetPeriod() != null) {
                keyLabelPairs.add(new KeyLabelPair(budgetPeriod.getBudgetPeriod(), budgetPeriod.getLabel()));
            }
        }
        return keyLabelPairs;
    }
    
    private List<KeyLabelPair> buildKeyLabelPairsForPeriodSearch(List<BudgetPeriod> budgetPeriods) {
        List<KeyLabelPair> keyLabelPairs = new ArrayList<KeyLabelPair>();
        List<Integer> uniqueKeys = new ArrayList<Integer>();
        for (BudgetPeriod budgetPeriod : budgetPeriods) {
            if (budgetPeriod.getBudgetPeriod() != null && !uniqueKeys.contains(budgetPeriod.getBudgetPeriod())) {
                uniqueKeys.add(budgetPeriod.getBudgetPeriod());
                keyLabelPairs.add(new KeyLabelPair(budgetPeriod.getBudgetPeriod(), budgetPeriod.getBudgetPeriod().toString()));
            }
        }
        return keyLabelPairs;
    }    

    protected KeyValueFinderService getKeyValueFinderService() {
        return keyValueFinderService;
    }

    public void setKeyValueFinderService(KeyValueFinderService keyValueFinderService) {
        this.keyValueFinderService = keyValueFinderService;
    }

    protected AwardBudgetService getAwardBudgetService() {
        return awardBudgetService;
    }

    public void setAwardBudgetService(AwardBudgetService awardBudgetService) {
        this.awardBudgetService = awardBudgetService;
    }

    protected DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }
}
