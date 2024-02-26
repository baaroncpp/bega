package com.bwongo.user_mgt.api;

import com.bwongo.user_mgt.models.dto.request.RoleRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserGroupRequestDto;
import com.bwongo.user_mgt.models.dto.response.GroupAuthorityResponseDto;
import com.bwongo.user_mgt.models.dto.response.PermissionResponseDto;
import com.bwongo.user_mgt.models.dto.response.RoleResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserGroupResponseDto;
import com.bwongo.user_mgt.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bwongo.tenant_mgt.utils.TenantMsgConstants.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 * @LocalTime 11:17 AM
 **/
@Tag(name = "Roles", description = "Manage user access permissions in Bega")
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RoleAndPermissionApi {

    private final RoleService roleService;

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "roles", produces = APPLICATION_JSON)
    public List<RoleResponseDto> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "role", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public RoleResponseDto addRole(@RequestBody RoleRequestDto role){
        return roleService.addRole(role);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "permission/{id}", produces = APPLICATION_JSON)
    public PermissionResponseDto getPermissionById(@PathVariable("id") Long id){
        return roleService.getPermissionById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "permissions/{roleName}", produces = APPLICATION_JSON)
    public List<PermissionResponseDto> getPermissionByRoleName(@PathVariable("roleName") String roleName){
        return roleService.getAllPermissionsByRoleName(roleName);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/activate/{id}", produces = APPLICATION_JSON)
    public PermissionResponseDto activatePermission(@PathVariable("id") Long id){
        return roleService.activatePermission(id);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/de-activate/{id}", produces = APPLICATION_JSON)
    public PermissionResponseDto deactivatePermission(@PathVariable("id") Long id){
        return roleService.deactivatePermission(id);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "assignable/permissions", produces = APPLICATION_JSON)
    public List<PermissionResponseDto> getAllAssignablePermissions(){
        return roleService.getAllPermissionsAssignable();
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "group/authorities", produces = APPLICATION_JSON)
    public List<GroupAuthorityResponseDto> getAllGroupAuthorities(){
        return roleService.getAllGroups();
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "group/authorities/{userGroupId}", produces = APPLICATION_JSON)
    public List<GroupAuthorityResponseDto> getUserGroupAuthorities(@PathVariable("userGroupId") Long userGroupId){
        return roleService.getUserGroupAuthorities(userGroupId);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/assign-to-group", produces = APPLICATION_JSON)
    public GroupAuthorityResponseDto assignPermissionToUserGroup(@RequestParam("permissionName") String permissionName,
                                                                 @RequestParam("userGroupId") Long userGroupId){
        return roleService.assignPermissionToUserGroup(permissionName, userGroupId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/un-assign-to-group", produces = APPLICATION_JSON)
    public boolean unAssignPermissionFromUserGroup(@RequestParam("permissionName") String permissionName,
                                                   @RequestParam("userGroupId") Long userGroupId){
        return roleService.unAssignPermissionFromUserGroup(permissionName, userGroupId);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "groups", produces = APPLICATION_JSON)
    public List<UserGroupResponseDto> getAllUserGroups(){
        return roleService.getAllUserGroups();
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "group")
    public UserGroupResponseDto addUserGroup(@RequestBody UserGroupRequestDto userGroupDto){
        return roleService.addUserGroup(userGroupDto);
    }
}
