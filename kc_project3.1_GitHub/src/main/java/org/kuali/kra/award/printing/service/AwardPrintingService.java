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
package org.kuali.kra.award.printing.service;

import java.util.Map;

import org.kuali.kra.award.printing.AwardPrintType;
import org.kuali.kra.bo.KraPersistableBusinessObjectBase;
import org.kuali.kra.printing.PrintingException;
import org.kuali.kra.proposaldevelopment.bo.AttachmentDataSource;

/**
 * This class provides the means for printing reports related to Award. It has
 * the capability to provide a PDF document of various reports related to Award
 * like Delta Report, Award Notice etc.
 * 
 * @author
 * 
 */
public interface AwardPrintingService {

	/**
	 * This method generates the required report and returns the PDF stream as
	 * {@link AttachmentDataSource}
	 * 
	 * @param awardDocument
	 *            Award data using which report is generated
	 * @param reportName
	 *            report to be generated
	 * @param reportParameters
	 *            {@link Map} of parameters required for report generation
	 * @return {@link AttachmentDataSource} which contains the byte array of the
	 *         generated PDF
	 * @throws PrintingException
	 *             if any errors occur during report generation
	 */
	public AttachmentDataSource printAwardReport(
			KraPersistableBusinessObjectBase awardDocument, AwardPrintType awardReportType,
			Map<String, Object> reportParameters) throws PrintingException;
}
