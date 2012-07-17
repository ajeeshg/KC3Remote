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
package org.kuali.kra.rice;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.SpringBeanFactoryResourceLoader;

/**
 * A custom {@link org.kuali.rice.core.resourceloader.SpringBeanFactoryResourceLoader} which wraps a Spring BeanFactory and delegates certain
 * service lookups to the BeanFactory.
 */
public class KraResourceLoader extends SpringBeanFactoryResourceLoader {
    
    private Set<String> overridableServices = new HashSet<String>();
    
    public KraResourceLoader() {
        super(new QName("KraResourceLoader"));
    }
    
    @Override
    public Object getService(QName serviceName) {
        if (overridableServices.contains(serviceName.getLocalPart())) {
            return super.getService(serviceName);
        }
        return null;
    }

    public Set<String> getOverridableServices() {
        return overridableServices;
    }

    public void setOverridableServices(Set<String> overridableServices) {
        this.overridableServices = overridableServices;
    }
}
