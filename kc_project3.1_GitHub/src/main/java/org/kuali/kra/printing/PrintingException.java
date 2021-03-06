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
package org.kuali.kra.printing;

/**
 * This is an Exception class and is used to encapsulate all errors thrown
 * during the the execution of activities related to report printing.
 * 
 * @author
 * 
 */
public class PrintingException extends Exception {

	/**
	 * Default constructor
	 */
	public PrintingException() {
		super();
	}
	
	/**
	 * Constructor for passing exception along with message
	 * @param message relating to circumstances due to which exception occured
	 * @param t {@link Throwable} exception object with trace
	 */
	public PrintingException(String message, Throwable t) {
		super(message, t);
	}
}
