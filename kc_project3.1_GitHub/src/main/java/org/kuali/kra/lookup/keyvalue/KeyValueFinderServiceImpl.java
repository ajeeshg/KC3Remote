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
package org.kuali.kra.lookup.keyvalue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.core.util.KeyLabelPair;

/**
 * This class...
 */
public class KeyValueFinderServiceImpl implements KeyValueFinderService {
    private BusinessObjectService businessObjectService;
    private static final Log LOG = LogFactory.getLog(KeyValueFinderServiceImpl.class);
    /**
     * @see org.kuali.kra.lookup.keyvalue.KeyValueFinderService#getKeyValuesFor(java.lang.Class)
     */
    public List<KeyLabelPair> getKeyValues(Class keyValClass,String codePropName,String valPropName) {
        Collection keyVals = businessObjectService.findAll(keyValClass);
        List<KeyLabelPair> keyValueList = new ArrayList<KeyLabelPair>(keyVals.size());
        keyValueList.add(new KeyLabelPair("", "select"));
        for (Iterator iterator = keyVals.iterator(); iterator.hasNext();) {
            Object keyValObj = iterator.next();
            Method getCodeMeth;
            try {
                getCodeMeth = keyValObj.getClass().getMethod("get"+StringUtils.capitalize(codePropName), null);
                Method getValMeth = keyValObj.getClass().getMethod("get"+StringUtils.capitalize(valPropName), null);
                Object code = getCodeMeth.invoke(keyValObj, null);
                Object value = getValMeth.invoke(keyValObj, null);
                if(code!=null && value!=null){
                    keyValueList.add(new KeyLabelPair(code.toString(), value.toString()));
                }
            }
            catch (SecurityException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }
            catch (NoSuchMethodException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }
            catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }
            catch (IllegalAccessException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }
            catch (InvocationTargetException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }
        }
        return keyValueList;
    }

    /**
     * 
     * @see org.kuali.kra.lookup.keyvalue.KeyValueFinderService#getKeyValues(java.lang.Class, java.lang.String, java.lang.String, java.util.Map)
     */
    public List<KeyLabelPair> getKeyValues(Class keyValClass, String codePropName, String valPropName, Map queryMap) {
        
        Collection keyVals = businessObjectService.findMatching(keyValClass,queryMap);
        List<KeyLabelPair> keyValueList = new ArrayList<KeyLabelPair>(keyVals.size());
        keyValueList.add(new KeyLabelPair("", "select:"));
        for (Iterator iterator = keyVals.iterator(); iterator.hasNext();) {
            Object keyValObj = iterator.next();
            Method getCodeMeth;
            try {
                getCodeMeth = keyValObj.getClass().getMethod("get"+StringUtils.capitalize(codePropName), null);
                Method getValMeth = keyValObj.getClass().getMethod("get"+StringUtils.capitalize(valPropName), null);
                String code = (String)getCodeMeth.invoke(keyValObj, null);
                String value = (String)getValMeth.invoke(keyValObj, null);
                keyValueList.add(new KeyLabelPair(code, value));
            }catch (SecurityException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }catch (NoSuchMethodException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }catch (IllegalArgumentException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }catch (IllegalAccessException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }catch (InvocationTargetException e) {
                LOG.debug(e.getMessage(), e);
                LOG.error(e.getMessage());
            }
        }
        return keyValueList;
    }

    /**
     * Gets the businessObjectService attribute.
     * @return Returns the businessObjectService.
     */
    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /**
     * Sets the businessObjectService attribute value.
     * @param businessObjectService The businessObjectService to set.
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

}
