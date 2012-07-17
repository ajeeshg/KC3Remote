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
package org.kuali.kra.web.servlet;

import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.rice.core.util.ClassLoaderUtils;
import org.kuali.rice.kns.util.spring.NamedOrderedListBean;
import org.kuali.rice.kns.web.servlet.KualiDWRServlet;
import org.kuali.kra.infrastructure.Constants;
import org.springframework.core.io.DefaultResourceLoader;

import uk.ltd.getahead.dwr.Configuration;

public class KraDWRServlet extends KualiDWRServlet {

    @Override
    public void configure(ServletConfig servletConfig, Configuration configuration) throws ServletException {
        super.configure(servletConfig, configuration);

        for (Object namedOrderedListBean : KraServiceLocator.getAppContext().getBeansOfType(NamedOrderedListBean.class).values()) {
            if (((NamedOrderedListBean) namedOrderedListBean).getName().equals("scriptConfigurationFilePaths")) {
                for (String scriptConfigurationFilePath : ((NamedOrderedListBean) namedOrderedListBean).getList()) {
                    if (getSpringBasedConfigPath()) {
                        DefaultResourceLoader resourceLoader = new DefaultResourceLoader(ClassLoaderUtils.getDefaultClassLoader());
                        try {
                            InputStream is = resourceLoader.getResource(scriptConfigurationFilePath).getInputStream();
                            configuration.addConfig(is);
                        }
                        catch (Exception e) {
                            throw new ServletException(e);
                        }
                    }
                    else {
                        super.readFile("target/classes/" + scriptConfigurationFilePath, configuration);
                    }
                }
            }
        }

    }
}
