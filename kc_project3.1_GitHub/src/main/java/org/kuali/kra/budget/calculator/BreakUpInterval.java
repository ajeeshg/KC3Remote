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
package org.kuali.kra.budget.calculator;

import java.util.HashMap;
import java.util.Map;

import org.kuali.kra.budget.BudgetDecimal;
import org.kuali.kra.budget.calculator.query.And;
import org.kuali.kra.budget.calculator.query.Equals;
import org.kuali.kra.budget.calculator.query.NotEquals;
import org.kuali.kra.budget.calculator.query.Or;
import org.kuali.kra.budget.core.Budget;
import org.kuali.kra.budget.nonpersonnel.BudgetRateAndBase;
import org.kuali.kra.budget.parameters.BudgetPeriod;
import org.kuali.kra.budget.rates.AbstractBudgetRate;
import org.kuali.kra.budget.rates.BudgetLaRate;
import org.kuali.kra.budget.rates.BudgetRate;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.rice.kns.service.BusinessObjectService;

/**
 * Holds all the info required for the breakup interval for which calculation 
 * has to be performed.
 *
 */
public class BreakUpInterval{
    private Boundary boundary; 
    private BudgetDecimal underRecovery; 
    private QueryList<BudgetRate> breakupIntervalRates; 
    private QueryList<BudgetLaRate> breakUpIntervalLaRates; 
    private QueryList<RateAndCost> breakupCalculatedAmounts;  
    private FormulaMaker formulaMaker; 
    private BudgetDecimal applicableAmt = BudgetDecimal.ZERO; 
    private BudgetDecimal applicableAmtCostSharing = BudgetDecimal.ZERO; 
    private BudgetRate uRRatesBean;
    private boolean laWithEBVACalculated = false;

    private Long budgetId;
    private Integer budgetPeriod;
    private Integer lineItemNumber;
    private Integer rateNumber;
    private QueryList<BudgetRateAndBase> cvRateBase ;
    /*
     * Calculate all the rates for the breakup interval
     */
    public void calculateBreakupInterval() {
        this.formulaMaker = new FormulaMaker();
        this.underRecovery = new BudgetDecimal(0);
        calculate();
    }
   
   private void calculate() {
       // get the calculation order from formula maker
       QueryList<ValidCalcType> validCalcTypes = formulaMaker.getValidCalcTypes();
       if (validCalcTypes != null && validCalcTypes.size() > 0) {
           int calcTypesSize = validCalcTypes.size();
           ValidCalcType tempValidCalcType;
           String rateClassType = "";
           String prevRateClassType = "";
           String eBonLARateClassCode = "0";
           String eBonLARateTypeCode = "0";
           String vAonLARateClassCode = "0";
           String vAonLARateTypeCode = "0";
           
           // get the EB on LA RateClassCode & RateTypeCode if any
           Equals eqRCType = new Equals("rateClassType", RateClassType.EMPLOYEE_BENEFITS.getRateClassType());
           Equals eqDepRCType = new Equals("dependentRateClassType", RateClassType.LA_SALARIES.getRateClassType());
           And eqRCTypeAndeqDepRCType = new And(eqRCType,eqDepRCType);
           QueryList<ValidCalcType> tempCalcTypes = validCalcTypes.filter(eqRCTypeAndeqDepRCType);
           if (tempCalcTypes.size() > 0) {
               tempValidCalcType = tempCalcTypes.get(0);
               eBonLARateClassCode = tempValidCalcType.getRateClassCode();
               eBonLARateTypeCode = tempValidCalcType.getRateTypeCode();
           }
           
           // get the VA on LA RateClassCode & RateTypeCode if any
           eqRCType = new Equals("rateClassType", RateClassType.VACATION.getRateClassType());
           eqRCTypeAndeqDepRCType = new And(eqRCType,eqDepRCType);
           tempCalcTypes = validCalcTypes.filter(eqRCTypeAndeqDepRCType);
           if (tempCalcTypes.size() > 0) {
               tempValidCalcType = tempCalcTypes.get(0);
               vAonLARateClassCode = tempValidCalcType.getRateClassCode();
               vAonLARateTypeCode = tempValidCalcType.getRateTypeCode();
           }
           
           
           /**
            * loop thru and calculate all the rates using the RateClassType order
            * of the cvValidCalcTypes QueryList
            */
           for (ValidCalcType validCalcType : validCalcTypes) {
               String rateClassCode = "0";
               String rateTypeCode = "0";
               BudgetDecimal rate = BudgetDecimal.ZERO;
               BudgetDecimal calculatedCost = BudgetDecimal.ZERO;
               BudgetDecimal calculatedCostSharing = BudgetDecimal.ZERO;
               Equals equalsRC;
               Equals equalsRT;
               And RCandRT;

               rateClassType = validCalcType.getRateClassType();
               
               //If this Rate Class Type is already calculated then skip
               if (prevRateClassType.equals("")) {
                   prevRateClassType = rateClassType;
               } else if (rateClassType.equalsIgnoreCase(prevRateClassType)) {
                   continue;
               } else {
                   prevRateClassType = rateClassType;
               }
               
               //get all the matching RateClassTypes from cvAmountDetails
               eqRCType = new Equals("rateClassType", rateClassType);
               QueryList<RateAndCost> rateAndCostList = breakupCalculatedAmounts.filter(eqRCType);
               
               /**
                *if RateClassType = 'E'(Employee Benefits) then remove all EBonLA
                *since its already calculated along with 'Y'
                */
               if (rateClassType.equals(RateClassType.EMPLOYEE_BENEFITS.getRateClassType()) &&
                                               laWithEBVACalculated) {
                   NotEquals notEqualsRC = new NotEquals("rateClassCode", eBonLARateClassCode);
                   NotEquals notEqualsRT = new NotEquals("rateTypeCode", eBonLARateTypeCode);
                   And notEqRCandnotEqRT = new And(notEqualsRC, notEqualsRT);
                   Or neRCandneRTOrneRT = new Or(notEqRCandnotEqRT, notEqualsRT);
                   rateAndCostList = rateAndCostList.filter(neRCandneRTOrneRT);
               }
               
               /**
                *if RateClassType = 'V'(Vacation) then remove all VAonLA
                *since its already calculated along with 'Y'
                */
               if (rateClassType.equals(RateClassType.VACATION.getRateClassType()) &&
                               laWithEBVACalculated) {
                   NotEquals notEqualsRC = new NotEquals("rateClassCode", vAonLARateClassCode);
                   NotEquals notEqualsRT = new NotEquals("rateTypeCode", vAonLARateTypeCode);
                   And notEqRCandnotEqRT = new And(notEqualsRC, notEqualsRT);
                   Or neRCandneRTOrneRT = new Or(notEqRCandnotEqRT, notEqualsRT);
                   rateAndCostList = rateAndCostList.filter(neRCandneRTOrneRT);
               }           
               
               //Calculate LA rate classes which get EB and vacation (rateClassType = 'Y')
               if (rateClassType.equals(RateClassType.LA_SALARIES.getRateClassType())) {
                   calculateLAWithEBandVA(rateAndCostList, eBonLARateClassCode,
                               eBonLARateTypeCode, vAonLARateClassCode,vAonLARateTypeCode);
                   laWithEBVACalculated = true;
                   continue;
                   
                   //Calculate overhead, (rateClassType = 'O')
               } else if (rateClassType.equals(RateClassType.OVERHEAD.getRateClassType())) {
                   calculateOHandUnderRecovery(rateAndCostList, eBonLARateClassCode,
                           eBonLARateTypeCode, vAonLARateClassCode,vAonLARateTypeCode);
                   continue;
               }
               
               //if no amount details then skip
               if (rateAndCostList.size() == 0) {
                   continue;
               }
               
               for (RateAndCost rateAndCost : rateAndCostList) {
                   rateClassCode = rateAndCost.getRateClassCode();
                   rateTypeCode = rateAndCost.getRateTypeCode();
                   equalsRC = new Equals("rateClassCode", rateClassCode);
                   equalsRT = new Equals("rateTypeCode", rateTypeCode);
                   RCandRT = new And(equalsRC, equalsRT);
                   QueryList tempRates;
                   //get the rate applicable for this RateClassCode & RateTypeCode
                   if (rateClassType.equals(RateClassType.LAB_ALLOCATION.getRateClassType())) {
                       tempRates = breakUpIntervalLaRates.filter(RCandRT);
                   } else {
                       tempRates = breakupIntervalRates.filter(RCandRT);
                   }
                   
                   //if rates available calculate amount
                   if (tempRates.size() > 0) {
                       AbstractBudgetRate proposalLaRate = (AbstractBudgetRate) tempRates.get(0);
                       rate = proposalLaRate.getApplicableRate();
                       //calculate cost & costSharing, set it to AmountBean
                       calculatedCost = applicableAmt.percentage(rate);
                       calculatedCostSharing = applicableAmtCostSharing.percentage(rate);
                       rateAndCost.setBaseAmount(applicableAmt);
                       rateAndCost.setBaseCostSharingAmount(applicableAmtCostSharing);
                       rateAndCost.setAppliedRate(rate);
                       rateAndCost.setCalculatedCost(calculatedCost);
                       rateAndCost.setCalculatedCostSharing(calculatedCostSharing);
                   } else {
                       rateAndCost.setCalculatedCost(BudgetDecimal.ZERO);
                       rateAndCost.setCalculatedCostSharing(BudgetDecimal.ZERO);
                       rateAndCost.setBaseAmount(BudgetDecimal.ZERO);
                       rateAndCost.setBaseCostSharingAmount(BudgetDecimal.ZERO);
                   }
               }
                
           }
          
       }
       
       BusinessObjectService boService = KraServiceLocator.getService(BusinessObjectService.class); 
       Map<String, Object> keyMap = new HashMap<String, Object>();
       
       
       /** Add Calculated amounts and fetch the data to the BudgetRateBaseBean
        *Hold the breakup intervals data into the dataholder and save to the 
        *database as and when calculate is called
        */
       QueryList<RateAndCost> cvData = getRateAndCosts();
       int rateNum = getRateNumber();
       if(cvData!= null && cvData.size() >0){
           cvRateBase = new QueryList<BudgetRateAndBase>();
           for (RateAndCost amountBean : cvData) {
               BudgetRateAndBase budgetRateBaseBean = new BudgetRateAndBase();
               Boundary boundary = getBoundary();
               boolean flag = amountBean.isApplyRateFlag();
               budgetRateBaseBean.setBudgetId(getBudgetId());
               budgetRateBaseBean.setBudgetPeriod(getBudgetPeriod());
               
               keyMap.put("budgetId", getBudgetId());
               keyMap.put("budgetPeriod", getBudgetPeriod());
               BudgetPeriod bPeriod = (BudgetPeriod) boService.findByPrimaryKey(BudgetPeriod.class, keyMap);
               if (bPeriod != null) {
                   budgetRateBaseBean.setBudgetPeriodId(bPeriod.getBudgetPeriodId());
               }
               keyMap.clear();
               
               budgetRateBaseBean.setRateNumber(++rateNum);
               budgetRateBaseBean.setLineItemNumber(getLineItemNumber());
               budgetRateBaseBean.setStartDate(new java.sql.Date(boundary.getStartDate().getTime()));;
               budgetRateBaseBean.setEndDate(new java.sql.Date(boundary.getEndDate().getTime()));
               budgetRateBaseBean.setRateClassCode(amountBean.getRateClassCode());
               budgetRateBaseBean.setRateTypeCode(amountBean.getRateTypeCode());
               budgetRateBaseBean.setOnOffCampusFlag(flag);
               budgetRateBaseBean.setAppliedRate(amountBean.getAppliedRate());
               budgetRateBaseBean.setBaseCost(amountBean.getBaseAmount());
               budgetRateBaseBean.setCalculatedCost(amountBean.getCalculatedCost());
               budgetRateBaseBean.setBaseCostSharing(amountBean.getBaseCostSharingAmount());
               budgetRateBaseBean.setCalculatedCostSharing(amountBean.getCalculatedCostSharing());
               cvRateBase.add(budgetRateBaseBean);
           }
       }
    }

/**return the vector which contains Budget Rate and Base data
    *
    */
    public QueryList getRateBase() {
        return cvRateBase;
    }
    /**
     * Calculate all the LA having EB & VA, (RateClassType = 'Y')
     * and the EB & VA rates on this LA
     * 
     * @param QueryList cvLAwithEBAmtDetails 
     */
    public void calculateLAWithEBandVA(QueryList<RateAndCost> cvLAwithEBAmtDetails, 
                                        String EBonLARateClassCode, String EBonLARateTypeCode, 
                                        String VAonLARateClassCode, String VAonLARateTypeCode) { 
        
        String rateClassCode = "0";
        String rateTypeCode = "0";
        BudgetDecimal rate = BudgetDecimal.ZERO;
        BudgetDecimal LAcalculatedCost = BudgetDecimal.ZERO;
        BudgetDecimal LAcalculatedCostSharing = BudgetDecimal.ZERO;
        BudgetDecimal EBonLAcalculatedCost = BudgetDecimal.ZERO;
        BudgetDecimal EBonLAcalculatedCostSharing = BudgetDecimal.ZERO;
        BudgetDecimal VAonLAcalculatedCost = BudgetDecimal.ZERO;
        BudgetDecimal VAonLAcalculatedCostSharing = BudgetDecimal.ZERO;
        Equals equalsRC;
        Equals equalsRT;
        And RCandRT;
        QueryList cvTempRates;
        QueryList<RateAndCost> cvEBonLAAmtDetails;
        QueryList<RateAndCost> cvVAonLAAmtDetails;
        AbstractBudgetRate proposalLaRate;
        for (RateAndCost amountBean : cvLAwithEBAmtDetails) {
            rateClassCode = amountBean.getRateClassCode();
            rateTypeCode = amountBean.getRateTypeCode();
            equalsRC = new Equals("rateClassCode", rateClassCode);
            equalsRT = new Equals("rateTypeCode", rateTypeCode);
            RCandRT = new And(equalsRC, equalsRT);

            //get the rate applicable for LA RateClassCode & RateTypeCode
            cvTempRates = breakUpIntervalLaRates.filter(RCandRT);
                    
            //if rates available calculate amount
            if (cvTempRates.size() > 0) {
                proposalLaRate = (AbstractBudgetRate)cvTempRates.get(0);
                rate = proposalLaRate.getApplicableRate();
                //calculate cost & costSharing for LA, set it to RateAndCost
                LAcalculatedCost = applicableAmt.percentage(rate);
                LAcalculatedCostSharing = applicableAmtCostSharing.percentage(rate);
                amountBean.setBaseAmount(applicableAmt);
                amountBean.setBaseCostSharingAmount(applicableAmtCostSharing);
                amountBean.setAppliedRate(rate);
                amountBean.setCalculatedCost(LAcalculatedCost);
                amountBean.setCalculatedCostSharing(LAcalculatedCostSharing);
            } else {
                amountBean.setCalculatedCost(BudgetDecimal.ZERO);
                amountBean.setCalculatedCostSharing(BudgetDecimal.ZERO);
                amountBean.setBaseAmount(BudgetDecimal.ZERO);
                amountBean.setBaseCostSharingAmount(BudgetDecimal.ZERO);
            }
            /****** Calculate EB on this LA ******/
            //get the EB on LA RateAndCost
            equalsRC = new Equals("rateClassCode", EBonLARateClassCode);
            equalsRT = new Equals("rateTypeCode", EBonLARateTypeCode);
            RCandRT = new And(equalsRC, equalsRT);
            cvEBonLAAmtDetails = breakupCalculatedAmounts.filter(RCandRT);
            if (cvEBonLAAmtDetails.size() > 0) {
                amountBean = (RateAndCost) cvEBonLAAmtDetails.get(0);
                rateClassCode = amountBean.getRateClassCode();
                rateTypeCode = amountBean.getRateTypeCode();
                equalsRC = new Equals("rateClassCode", rateClassCode);
                equalsRT = new Equals("rateTypeCode", rateTypeCode);
                RCandRT = new And(equalsRC, equalsRT);
                //get the rate applicable for EBonLA RateClassCode & RateTypeCode
                cvTempRates = breakupIntervalRates.filter(RCandRT);
                //if rates available calculate amount
                if (cvTempRates.size() > 0) {
                    proposalLaRate = (AbstractBudgetRate)cvTempRates.get(0);
                    rate = proposalLaRate.getApplicableRate();
                    //calculate cost & costSharing for EBonLA, set it to AmountBean
                    EBonLAcalculatedCost = LAcalculatedCost.percentage(rate);
                    EBonLAcalculatedCostSharing = LAcalculatedCostSharing.percentage(rate);
                    amountBean.setBaseAmount(LAcalculatedCost );
                    amountBean.setBaseCostSharingAmount(LAcalculatedCostSharing );
                    amountBean.setAppliedRate(rate);
                    amountBean.setCalculatedCost(EBonLAcalculatedCost);
                    amountBean.setCalculatedCostSharing(EBonLAcalculatedCostSharing);
                } else {
                    amountBean.setCalculatedCost(BudgetDecimal.ZERO);
                    amountBean.setCalculatedCostSharing(BudgetDecimal.ZERO);
                    amountBean.setBaseAmount(BudgetDecimal.ZERO);
                    amountBean.setBaseCostSharingAmount(BudgetDecimal.ZERO);
                    
                }
            }
            /*** Calculation of EB on LA ends here ***/
            
            /****** Calculate VA on this LA ******/
            //get the VA on LA RateAndCost
            equalsRC = new Equals("rateClassCode", VAonLARateClassCode);
            equalsRT = new Equals("rateTypeCode", VAonLARateTypeCode);
            RCandRT = new And(equalsRC, equalsRT);
            cvVAonLAAmtDetails = breakupCalculatedAmounts.filter(RCandRT);
            if (cvVAonLAAmtDetails.size() > 0) {
                amountBean = (RateAndCost) cvVAonLAAmtDetails.get(0);
                rateClassCode = amountBean.getRateClassCode();
                rateTypeCode = amountBean.getRateTypeCode();
                equalsRC = new Equals("rateClassCode", rateClassCode);
                equalsRT = new Equals("rateTypeCode", rateTypeCode);
                RCandRT = new And(equalsRC, equalsRT);

                //get the rate applicable for VAonLA RateClassCode & RateTypeCode
                cvTempRates = breakupIntervalRates.filter(RCandRT);
                    
                //if rates available calculate amount
                if (cvTempRates.size() > 0) {
                    proposalLaRate = (AbstractBudgetRate)cvTempRates.get(0);
                    rate = proposalLaRate.getApplicableRate();
                    //calculate cost & costSharing for VAonLA, set it to AmountBean
                    VAonLAcalculatedCost = LAcalculatedCost.percentage(rate);
                    VAonLAcalculatedCostSharing = LAcalculatedCostSharing.percentage(rate);
                    amountBean.setBaseAmount(LAcalculatedCost );
                    amountBean.setBaseCostSharingAmount(LAcalculatedCostSharing );
                    amountBean.setAppliedRate(rate);
                    amountBean.setCalculatedCost(VAonLAcalculatedCost);
                    amountBean.setCalculatedCostSharing(VAonLAcalculatedCostSharing);
                } else {//rates not available, so set to zero
                    amountBean.setCalculatedCost(BudgetDecimal.ZERO);
                    amountBean.setCalculatedCostSharing(BudgetDecimal.ZERO);
                    amountBean.setBaseAmount(BudgetDecimal.ZERO);
                    amountBean.setBaseCostSharingAmount(BudgetDecimal.ZERO);
                }
            }
            /*** Calculation of VA on LA ends here ***/

        } //for loop of rate class type 'Y' ends here
    }
    
    /**
     * Calculate OH Amounts and Under recovery
     * 
     * @param QueryList cvOHAmtDetails 
     */
    private void calculateOHandUnderRecovery(QueryList<RateAndCost> cvOHAmtDetails, 
                                        String EBonLARateClassCode, String EBonLARateTypeCode, 
                                        String VAonLARateClassCode, String VAonLARateTypeCode) { 
        
        RateAndCost amountBean;
        String rateClassCode = "0";
        String rateTypeCode = "0";
        BudgetDecimal applicableRate = BudgetDecimal.ZERO;
        BudgetDecimal instituteRate = BudgetDecimal.ZERO;
        BudgetDecimal underRecoveryRate = BudgetDecimal.ZERO;
        BudgetDecimal OHcalculatedCost = BudgetDecimal.ZERO;
        BudgetDecimal OHcalculatedCostSharing = BudgetDecimal.ZERO;
        BudgetDecimal EBCalculatedCost = BudgetDecimal.ZERO;
        BudgetDecimal VACalculatedCost = BudgetDecimal.ZERO;
        BudgetDecimal EBCalculatedCostSharing = BudgetDecimal.ZERO;
        BudgetDecimal VACalculatedCostSharing = BudgetDecimal.ZERO;
        Equals equalsRCType;
        Equals equalsRC;
        Equals equalsRT;
        NotEquals notEqualsRC;
        NotEquals notEqualsRT;
        And RCandRT;
        And neRCAndneRT;
        And eqRCTypeAndneRCAndneRTOrneRT;
        Or neRCandneRTOrneRT;
        QueryList cvTempRates;
        AbstractBudgetRate budgetRate;
        //get all the EB amts. Take care to exclude EB on LA
        equalsRCType = new Equals("rateClassType", RateClassType.EMPLOYEE_BENEFITS.getRateClassType());
        notEqualsRC = new NotEquals("rateClassCode", EBonLARateClassCode);
        notEqualsRT = new NotEquals("rateTypeCode", EBonLARateTypeCode);
        neRCAndneRT = new And(notEqualsRC, notEqualsRT);
        neRCandneRTOrneRT = new Or(neRCAndneRT, notEqualsRT);
        eqRCTypeAndneRCAndneRTOrneRT = new And(equalsRCType, neRCandneRTOrneRT);
        //sum up all the EB amts
        EBCalculatedCost = new BudgetDecimal(breakupCalculatedAmounts.sum("calculatedCost", eqRCTypeAndneRCAndneRTOrneRT));
        EBCalculatedCostSharing = new BudgetDecimal(breakupCalculatedAmounts.sum("calculatedCostSharing", eqRCTypeAndneRCAndneRTOrneRT));
        //get all the VA amts. Take care to exclude VA on LA
        equalsRCType = new Equals("rateClassType", RateClassType.VACATION.getRateClassType());
        notEqualsRC = new NotEquals("rateClassCode", VAonLARateClassCode);
        notEqualsRT = new NotEquals("rateTypeCode", VAonLARateTypeCode);
        neRCAndneRT = new And(notEqualsRC, notEqualsRT);
        neRCandneRTOrneRT = new Or(neRCAndneRT, notEqualsRT);
        eqRCTypeAndneRCAndneRTOrneRT = new And(equalsRCType, neRCandneRTOrneRT);
        //sum up all the VA amts
         VACalculatedCost = new BudgetDecimal(breakupCalculatedAmounts.sum("calculatedCost", eqRCTypeAndneRCAndneRTOrneRT));
        VACalculatedCostSharing = new BudgetDecimal(breakupCalculatedAmounts.sum("calculatedCostSharing", eqRCTypeAndneRCAndneRTOrneRT));
        
        //Now calculate OH & Under-recovery amounts
        int amtDetailsSize = cvOHAmtDetails.size();
        //loop thru each amount detail bean and calculate
        for (int amtDetailsIndex = 0; amtDetailsIndex < amtDetailsSize; amtDetailsIndex++) {
            amountBean = (RateAndCost) cvOHAmtDetails.get(amtDetailsIndex);
            rateClassCode = amountBean.getRateClassCode();
            rateTypeCode = amountBean.getRateTypeCode();
            equalsRC = new Equals("rateClassCode", rateClassCode);
            equalsRT = new Equals("rateTypeCode", rateTypeCode);
            RCandRT = new And(equalsRC, equalsRT);

            //get the applicable rate & institute rate for this RateClassCode & RateTypeCode
            cvTempRates = breakupIntervalRates.filter(RCandRT);
            
            //if rates available calculate OH & Under recovery amounts
            if (cvTempRates.size() > 0) {
                budgetRate = (AbstractBudgetRate)cvTempRates.get(0);
                applicableRate = budgetRate.getApplicableRate();
                
                //If Under-recovery rate is present, then use this for Institute Rate
                if (uRRatesBean != null) {
                    instituteRate = uRRatesBean.getInstituteRate();
                } else {
                    instituteRate = budgetRate.getInstituteRate();
                }
                
                if (!amountBean.isApplyRateFlag()) {
                    underRecoveryRate = instituteRate;
                } else {
                    //calculate OH cost & costSharing, set it to AmountBean
                    OHcalculatedCost = applicableAmt.add(EBCalculatedCost).add(VACalculatedCost).percentage(applicableRate);
                    // Getting the Base Amount for the OH Calculation  = EB CalculatedCost+LI Cost+Va Caculated Cost
                    OHcalculatedCostSharing = (applicableAmtCostSharing.add(EBCalculatedCostSharing).
                                                            add(VACalculatedCostSharing).
                                                                percentage(applicableRate));
                    amountBean.setAppliedRate(applicableRate);
                    amountBean.setBaseAmount(EBCalculatedCost.add(VACalculatedCost).add(applicableAmt));
                    amountBean.setBaseCostSharingAmount(EBCalculatedCostSharing.add(VACalculatedCostSharing).add(applicableAmtCostSharing));
                    amountBean.setCalculatedCost(OHcalculatedCost);
                    amountBean.setCalculatedCostSharing(OHcalculatedCostSharing);
                    underRecoveryRate = instituteRate.subtract(applicableRate);
                }
                /**
                 *Calculate Under Recovery and add it to underRecovery attribute 
                 *of this breakup interval
                 */
                underRecovery = underRecovery.add(applicableAmt.add(EBCalculatedCost).add(VACalculatedCost).percentage(underRecoveryRate));
                underRecovery = underRecovery.add(applicableAmtCostSharing.add(EBCalculatedCostSharing).add(VACalculatedCostSharing).percentage(underRecoveryRate));
            //rates not available, so set to zero    
            } else {
                amountBean.setCalculatedCost(BudgetDecimal.ZERO);
                amountBean.setCalculatedCostSharing(BudgetDecimal.ZERO);
                amountBean.setBaseAmount(BudgetDecimal.ZERO);
                amountBean.setBaseCostSharingAmount(BudgetDecimal.ZERO);
            }
        }
    }
    
    /** Getter for property boundary.
     * @return Value of property boundary.
     */
    public Boundary getBoundary() {
        return boundary;
    }
    
    /** Setter for property boundary.
     * @param boundary New value of property boundary.
     */
    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }
    
    /** Getter for property underRecovery.
     * @return Value of property underRecovery.
     */
    public BudgetDecimal getUnderRecovery() {
        return underRecovery;
    }
    
    /** Setter for property underRecovery.
     * @param underRecovery New value of property underRecovery.
     */
    public void setUnderRecovery(BudgetDecimal underRecovery) {
        this.underRecovery = underRecovery;
    }
    
    /** Getter for property cvPropRates.
     * @return Value of property cvPropRates.
     */
    public QueryList<BudgetRate> getBudgetProposalRates() {
        return breakupIntervalRates;
    }
    
    /** Setter for property cvPropRates.
     * @param budgetRates New value of property cvPropRates.
     */
    public void setBudgetProposalRates(QueryList<BudgetRate> budgetRates) {
        this.breakupIntervalRates = budgetRates;
    }
    
    /** Getter for property cvPropLARates.
     * @return Value of property cvPropLARates.
     */
    public QueryList<BudgetLaRate> getBudgetProposalLaRates() {
        return breakUpIntervalLaRates;
    }
    
    /** Setter for property cvPropLARates.
     * @param budgetLaRates New value of property cvPropLARates.
     */
    public void setBudgetProposalLaRates(QueryList<BudgetLaRate> budgetLaRates) {
        this.breakUpIntervalLaRates = budgetLaRates;
    }
    
    /** Getter for property cvAmountDetails.
     * @return Value of property cvAmountDetails.
     */
    public QueryList<RateAndCost> getRateAndCosts() {
        return breakupCalculatedAmounts;
    }
    
    /** Setter for property cvAmountDetails.
     * @param breakupCalculatedAmounts New value of property cvAmountDetails.
     */
    public void setRateAndCosts(QueryList<RateAndCost> cvAmountDetails) {
        this.breakupCalculatedAmounts = cvAmountDetails;
    }
    
    /** Getter for property applicableAmt.
     * @return Value of property applicableAmt.
     *
     */
    public BudgetDecimal getApplicableAmt() {
        return applicableAmt;
    }
    
    /** Setter for property applicableAmt.
     * @param applicableAmt New value of property applicableAmt.
     *
     */
    public void setApplicableAmt(BudgetDecimal applicableAmt) {
        this.applicableAmt = applicableAmt;
    }
    
    /** Getter for property applicableAmtCostSharing.
     * @return Value of property applicableAmtCostSharing.
     *
     */
    public BudgetDecimal getApplicableAmtCostSharing() {
        return applicableAmtCostSharing;
    }
    
    /** Setter for property applicableAmtCostSharing.
     * @param applicableAmtCostSharing New value of property applicableAmtCostSharing.
     *
     */
    public void setApplicableAmtCostSharing(BudgetDecimal applicableAmtCostSharing) {
        this.applicableAmtCostSharing = applicableAmtCostSharing;
    }
    
    /** Getter for property uRRatesBean.
     * @return Value of property uRRatesBean.
     *
     */
    public BudgetRate getURRatesBean() {
        return uRRatesBean;
    }
    
    /** Setter for property uRRatesBean.
     * @param uRRatesBean New value of property uRRatesBean.
     *
     */
    public void setURRatesBean(BudgetRate uRRatesBean) {
        this.uRRatesBean = uRRatesBean;
    }
    
    /**
     * Getter for property budgetPeriod.
     * @return Value of property budgetPeriod.
     */
    public int getBudgetPeriod() {
        return budgetPeriod;
    }
    
    /**
     * Setter for property budgetPeriod.
     * @param budgetPeriod New value of property budgetPeriod.
     */
    public void setBudgetPeriod(int budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }
    
    /**
     * Getter for property lineItemNumber.
     * @return Value of property lineItemNumber.
     */
    public int getLineItemNumber() {
        return lineItemNumber;
    }
    
    /**
     * Setter for property lineItemNumber.
     * @param lineItemNumber New value of property lineItemNumber.
     */
    public void setLineItemNumber(int lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
    }
    
    /**
     * Getter for property rateNumber.
     * @return Value of property rateNumber.
     */
    public int getRateNumber() {
        return rateNumber;
    }
    
    /**
     * Setter for property rateNumber.
     * @param rateNumber New value of property rateNumber.
     */
    public void setRateNumber(int rateNumber) {
        this.rateNumber = rateNumber;
    }


    /**
     * Gets the budget attribute. 
     * @return Returns the budget.
     */
    public Long getBudgetId() {
        return budgetId;
    }

    /**
     * Sets the budget attribute value.
     * @param budget id to set.
     */
    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }
 }



