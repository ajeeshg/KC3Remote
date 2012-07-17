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
package org.kuali.kra.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.bo.KcPerson;
import org.kuali.kra.bo.RolePersons;
import org.kuali.kra.common.permissions.Permissionable;
import org.kuali.kra.infrastructure.PermissionAttributes;
import org.kuali.kra.infrastructure.RoleConstants;
import org.kuali.kra.service.KcPersonService;
import org.kuali.kra.service.KraAuthorizationService;
import org.kuali.kra.service.UnitAuthorizationService;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.entity.KimPrincipal;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.rice.kim.service.RoleManagementService;

/**
 * The Kra Authorization Service Implementation.
 *
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class KraAuthorizationServiceImpl implements KraAuthorizationService { 
    
    private UnitAuthorizationService unitAuthorizationService;
    private KcPersonService kcPersonService;
    
    private RoleManagementService roleManagementService;
    private IdentityManagementService identityManagementService;
    
    /**
     * Set the Unit Authorization Service.  Injected by Spring.
     * @param unitAuthorizationService the Unit Authorization Service
     */
    public void setUnitAuthorizationService(UnitAuthorizationService unitAuthorizationService) {
        this.unitAuthorizationService = unitAuthorizationService;
    }
    
    /**
     * Set the KRA Person Service.  Injected by Spring.
     * @param personService the KRA Person Service
     */
    public void setKcPersonService(KcPersonService personService) {
        this.kcPersonService = personService;
    }
    
    public void setRoleManagementService(RoleManagementService roleManagementService) {
        this.roleManagementService = roleManagementService;
    }
    
    public void setIdentityManagementService(IdentityManagementService identityManagementService) {
        this.identityManagementService = identityManagementService;
    }

    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#getUserNames(org.kuali.kra.common.permissions.Permissionable, java.lang.String)
     */
    public List<String> getUserNames(Permissionable permissionable, String roleName) {
        List<String> userNames = new ArrayList<String>();
        Map<String, String> qualifiedRoleAttributes = new HashMap<String, String>();
        qualifiedRoleAttributes.put(permissionable.getDocumentKey(), permissionable.getDocumentNumberForPermission());
        Collection<String> users = roleManagementService.getRoleMemberPrincipalIds(permissionable.getNamespace(), roleName, new AttributeSet(qualifiedRoleAttributes));
        for(String userId: users) {
            KimPrincipal principal = identityManagementService.getPrincipal(userId);
            if(principal != null) {
                userNames.add(principal.getPrincipalName());
            }
        }
        return userNames;
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#addRole(java.lang.String, java.lang.String, org.kuali.kra.common.permissions.Permissionable)
     */
    public void addRole(String userId, String roleName, Permissionable permissionable) {
        Map<String, String> qualifiedRoleAttributes = new HashMap<String, String>();
        qualifiedRoleAttributes.put(permissionable.getDocumentKey(), permissionable.getDocumentNumberForPermission());
        roleManagementService.assignPrincipalToRole(userId, permissionable.getNamespace(), roleName, new AttributeSet(qualifiedRoleAttributes));
        forceFlushRoleCaches();
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#removeRole(java.lang.String, java.lang.String, org.kuali.kra.common.permissions.Permissionable)
     */
    public void removeRole(String userId, String roleName, Permissionable permissionable) {
        Map<String, String> qualifiedRoleAttributes = new HashMap<String, String>();
        qualifiedRoleAttributes.put(permissionable.getDocumentKey(), permissionable.getDocumentNumberForPermission());
        roleManagementService.removePrincipalFromRole(userId, permissionable.getNamespace(), roleName, new AttributeSet(qualifiedRoleAttributes));
        forceFlushRoleCaches();
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#hasPermission(java.lang.String, org.kuali.kra.common.permissions.Permissionable, java.lang.String)
     */
    public boolean hasPermission(String userId, Permissionable permissionable, String permissionName) {
        boolean userHasPermission = false;
        Map<String, String> qualifiedRoleAttributes = new HashMap<String, String>();
        qualifiedRoleAttributes.put(permissionable.getDocumentKey(), permissionable.getDocumentNumberForPermission());
        permissionable.populateAdditionalQualifiedRoleAttributes(qualifiedRoleAttributes);
        Map<String, String> permissionAttributes = PermissionAttributes.getAttributes(permissionName);
        String unitNumber = permissionable.getLeadUnitNumber();
        
        if(StringUtils.isNotEmpty(permissionable.getDocumentNumberForPermission())) {
            userHasPermission = identityManagementService.isAuthorized(userId, permissionable.getNamespace(), permissionName, new AttributeSet(permissionAttributes), new AttributeSet(qualifiedRoleAttributes));
        }
        if (!userHasPermission && StringUtils.isNotEmpty(unitNumber)) {
            userHasPermission = unitAuthorizationService.hasPermission(userId, unitNumber, permissionable.getNamespace(), permissionName);
        }
        return userHasPermission;
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#hasRole(java.lang.String, org.kuali.kra.common.permissions.Permissionable, java.lang.String)
     */
    public boolean hasRole(String userId, Permissionable permissionable, String roleName) {
        Map<String, String> qualifiedRoleAttributes = new HashMap<String, String>();
        qualifiedRoleAttributes.put(permissionable.getDocumentKey(), permissionable.getDocumentNumberForPermission());
        Role role = roleManagementService.getRoleByName(permissionable.getNamespace(), roleName);
        if(role != null) {
            return roleManagementService.principalHasRole(userId, Collections.singletonList(role.getRoleId()), new AttributeSet(qualifiedRoleAttributes));
        }
        return false;
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#getRoles(java.lang.String, org.kuali.kra.common.permissions.Permissionable)
     */
    public List<String> getRoles(String userId, Permissionable permissionable) {
        Set<String> roleNames = new HashSet<String>();
        String documentNumber = permissionable.getDocumentNumberForPermission();
        Map<String, String> qualifiedRoleAttrs = new HashMap<String, String>();
        qualifiedRoleAttrs.put(permissionable.getDocumentKey(), documentNumber);
        if (documentNumber != null) {
              //No way to get the Attribute ID - cannot use this 
//            Map<String, String> fieldValues = new HashMap<String, String>();   
//            fieldValues.put(KIMPropertyConstants.Principal.PRINCIPAL_NAME, principal.getPrincipalName());
//            fieldValues.put("attributes.attributeValue", documentNumber);
//            fieldValues.put("attributes.kimAttribute.attributeName", permissionable.getDocumentKey()); 
//            fieldValues.put("attributes.kimAttribute.namespaceCode", KraAuthorizationConstants.KC_SYSTEM_NAMESPACE_CODE); 
//            fieldValues.put("kimTypeId", "*");
//            List<Role> roles = (List<Role>) roleManagementService.getRolesSearchResults(fieldValues);
//            for(Role role : roles) {
//                roleNames.add(role.getRoleName());
//            }
            List<String> roleIds = new ArrayList<String>();
            Map<String, String> roleNameIdMap = new HashMap<String, String>();
            for(String role : permissionable.getRoleNames()) {
                String roleId = roleManagementService.getRoleIdByName(permissionable.getNamespace(), role);
                roleNameIdMap.put(roleId, role);
                roleIds.add(roleId);
            }
            List<RoleMembershipInfo> membershipInfoList = roleManagementService.getRoleMembers(roleIds, new AttributeSet(qualifiedRoleAttrs));
            for(RoleMembershipInfo memberShipInfo : membershipInfoList) {
                if(memberShipInfo.getMemberId().equalsIgnoreCase(userId)) {
                    roleNames.add(roleNameIdMap.get(memberShipInfo.getRoleId()));
                }
            }
        }
        
        return new ArrayList<String>(roleNames); 
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#getPersonsInRole(org.kuali.kra.common.permissions.Permissionable, java.lang.String)
     */
    public List<KcPerson> getPersonsInRole(Permissionable permissionable, String roleName) {
        List<KcPerson> persons = new ArrayList<KcPerson>();
        if(permissionable != null && StringUtils.isNotBlank(roleName)) { 
            Map<String, String> qualifiedRoleAttrs = new HashMap<String, String>();
            qualifiedRoleAttrs.put(permissionable.getDocumentKey(), permissionable.getDocumentNumberForPermission());
            Collection<String> users = roleManagementService.getRoleMemberPrincipalIds(permissionable.getNamespace(), roleName, new AttributeSet(qualifiedRoleAttrs));
            for(String userId : users) {
                KcPerson person = kcPersonService.getKcPersonByPersonId(userId);
                if (person != null && person.getActive()) {
                    persons.add(person);
                }
            }
        } 
        return persons;
    }
    
    /**
     * @see org.kuali.kra.award.service.KraAuthorizationService#getAllRolePersons(org.kuali.kra.common.permissions.Permissionable)
     */
    public List<RolePersons> getAllRolePersons(Permissionable permissionable) {
        List<RolePersons> rolePersonsList = new ArrayList<RolePersons>();
        
        if(permissionable != null) {
            List<String> roleNames = permissionable.getRoleNames();
            
            for (String roleName : roleNames) {
                List<String> usernames = getUserNames(permissionable, roleName);
                RolePersons rolePersons = new RolePersons();
                rolePersonsList.add(rolePersons);
     
                if(roleName.contains(RoleConstants.AGGREGATOR)) {
                    rolePersons.setAggregator(usernames);
                } else if(roleName.contains(RoleConstants.VIEWER)) {
                    rolePersons.setViewer(usernames); 
                } else if(roleName.contains(RoleConstants.NARRATIVE_WRITER)) {
                    rolePersons.setNarrativewriter(usernames);
                } else if(roleName.contains(RoleConstants.BUDGET_CREATOR)) {
                    rolePersons.setBudgetcreator(usernames);
                }
            }
        }
        
        return rolePersonsList;
    }

    
    public boolean hasRole(String userId, String namespace, String roleName) {
        Role role = roleManagementService.getRoleByName(namespace, roleName);
        if(role != null) {
            return roleManagementService.principalHasRole(userId, Collections.singletonList(role.getRoleId()), null);
        }
        return false;
    }

    /**
     * FIXME: Rice Hack - this method needs to be called because after we update kim data the cache handling is done
     * asynchronously and subsequent reads are likely to be reading from a dirty cache.
     */
    public void forceFlushRoleCaches() {
        roleManagementService.flushRoleCaches();
    }

    public RoleManagementService getRoleManagementService() {
        return roleManagementService;
    }

    public IdentityManagementService getIdentityManagementService() {
        return identityManagementService;
    }
}