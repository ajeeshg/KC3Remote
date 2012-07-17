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
package org.kuali.kra.service;

import java.util.List;

import org.kuali.kra.bo.KcPerson;
import org.kuali.kra.bo.RolePersons;
import org.kuali.kra.common.permissions.Permissionable;

/**
 * The Authorization Service handles access to Documents.
 *
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public interface KraAuthorizationService {

    /**
     * Get the list of usernames of people who have the given role with respect to
     * the given Permissionable.
     * @param permissionable the Permissionable
     * @param roleName the name of the Role
     * @return the list of usernames 
     */
    public List<String> getUserNames(Permissionable permissionable, String roleName);
    
    /**
     * Add a user to a role within an Award.  Standard roles for
     * Award are Aggregator and Viewer.
     * @param username the user's username
     * @param roleName the name of the Role
     * @param award the Award
     */
    public void addRole(String userId, String roleName, Permissionable permissionable);
    
    /**
     * Remove a user from a role within a Award. Standard roles for
     * Awards are Aggregator and Viewer.
     * @param username the user's username
     * @param roleName the name of the Role
     * @param award the Award
     */
    public void removeRole(String userId, String roleName, Permissionable permissionable);
    
    /**
     * Does the user have the given permission for the given Award?
     * @param username the user's username
     * @param award the Award
     * @param permissionName the name of the Permission
     * @return true if the user has permission; otherwise false
     */
    public boolean hasPermission(String userId, Permissionable permissionable, String permissionName);

    /**
     * Does the user have the given role for the given Award?
     * @param username the user's username
     * @param doc the Award
     * @param roleName the name of the Role
     * @return true if the user has the role; otherwise false
     */
    public boolean hasRole(String userId, Permissionable permissionable, String roleName);
    
    /**
     * Get the roles for this user in the Award.
     * @param username the user's username
     * @param award the Award
     * @return the list of role names this person has in the award
     */
    public List<String> getRoles(String userId, Permissionable permissionable);
    
    /**
     * Get the list of persons in a specific role for a given award.
     * @param award the Award
     * @param roleName the name of the role
     * @return the list of persons in the role for the award
     */
    public List<KcPerson> getPersonsInRole(Permissionable permissionable, String roleName);
    
    /**
     * Get the list of all of the award roles and the persons in those
     * roles for a given Award.
     * @param award the Award
     * @return the list of all roles and the people in those roles
     */
    public List<RolePersons> getAllRolePersons(Permissionable permissionable);
    
    public boolean hasRole(String userId, String namespace, String roleName);
    
    public void forceFlushRoleCaches();    
}
