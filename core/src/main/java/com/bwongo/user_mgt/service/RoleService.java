package com.bwongo.user_mgt.service;

import com.bwongo.base.service.AuditService;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.user_mgt.models.dto.request.RoleRequestDto;
import com.bwongo.user_mgt.models.dto.request.UserGroupRequestDto;
import com.bwongo.user_mgt.models.dto.response.GroupAuthorityResponseDto;
import com.bwongo.user_mgt.models.dto.response.PermissionResponseDto;
import com.bwongo.user_mgt.models.dto.response.RoleResponseDto;
import com.bwongo.user_mgt.models.dto.response.UserGroupResponseDto;
import com.bwongo.user_mgt.models.jpa.TGroupAuthority;
import com.bwongo.user_mgt.repository.*;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.user_mgt.util.UserMgtUtils.checkThatPermissionRoleIsAssignable;
import static com.bwongo.user_mgt.util.UserMgtUtils.createRolePermissions;
import static com.bwongo.user_mgt.util.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
@Service
@RequiredArgsConstructor
public class RoleService {
    private final TGroupAuthorityRepository groupAuthorityRepository;
    private final TUserRepository userRepository;
    private final TRoleRepository roleRepository;
    private final AuditService auditService;
    private final TUserGroupRepository userGroupRepository;
    private final TPermissionRepository permissionRepository;
    private final UserMgtDtoService userMgtDtoService;
    private static final String USER_GROUP_SUFFIX = "_GROUP";

    @Transactional
    public RoleResponseDto addRole(RoleRequestDto roleRequestDto) {

        roleRequestDto.validate();

        var existing = roleRepository.findByName(roleRequestDto.name());
        Validate.isTrue(existing.isEmpty(), ExceptionType.BAD_REQUEST, ROLE_EXISTS, roleRequestDto.name());

        var role = userMgtDtoService.dtoToTRole(roleRequestDto);
        auditService.stampLongEntity(role);

        var result = roleRepository.save(role);

        var permissions = createRolePermissions(result);
        permissions.forEach(
                auditService::stampLongEntity
        );

        permissionRepository.saveAll(permissions);

        return userMgtDtoService.roleToDto(result);
    }

    public RoleResponseDto updateRole(Long roleId, RoleRequestDto roleRequestDto) {

        roleRequestDto.validate();
        var existing = roleRepository.findById(roleId);
        Validate.isPresent(existing, ROLE_DOES_NOT_EXIST, roleId);

        var updatedRole = userMgtDtoService.dtoToTRole(roleRequestDto);
        auditService.stampLongEntity(updatedRole);

        return userMgtDtoService.roleToDto(roleRepository.save(updatedRole));
    }

    public List<RoleResponseDto> getAllRoles(){
        return roleRepository.findAll().stream()
                .map(userMgtDtoService::roleToDto)
                .collect(Collectors.toList());
    }

    public PermissionResponseDto getPermissionById(Long id) {
        var result = permissionRepository.findById(id);
        Validate.isPresent(result, PERMISSION_DOES_NOT_EXIST);
        return userMgtDtoService.permissionToDto(result.get());
    }

    public PermissionResponseDto deactivatePermission(Long id) {
        var existing = permissionRepository.findById(id);
        Validate.isPresent(existing, PERMISSION_DOES_NOT_EXIST, id);
        Validate.isTrue(existing.get().getIsAssignable(), ExceptionType.BAD_REQUEST, PERMISSION_IS_ALREADY_IN_ACTIVE, id);

        var permission = existing.get();
        permission.setIsAssignable(Boolean.FALSE);
        var result = permissionRepository.save(permission);

        return userMgtDtoService.permissionToDto(result);
    }

    public PermissionResponseDto activatePermission(Long id) {

        var existing = permissionRepository.findById(id);
        Validate.isPresent(existing, PERMISSION_DOES_NOT_EXIST, id);
        Validate.isTrue(!existing.get().getIsAssignable(), ExceptionType.BAD_REQUEST, PERMISSION_IS_ALREADY_ACTIVE, id);

        var permission = existing.get();
        permission.setIsAssignable(Boolean.TRUE);
        var result = permissionRepository.save(permission);

        return userMgtDtoService.permissionToDto(result);
    }

    public void deletePermission(Long id) {
        var existing = permissionRepository.findById(id);
        Validate.isPresent(existing, PERMISSION_DOES_NOT_EXIST, id);
    }

    public List<PermissionResponseDto> getAllPermissionsByRoleName(String roleName) {

        var existingRole = roleRepository.findByName(roleName);
        Validate.isPresent(existingRole, ROLE_NAME_DOES_NOT_EXIST, roleName);

        return permissionRepository.findAllByRole(existingRole.get()).stream()
                .map(userMgtDtoService::permissionToDto)
                .collect(Collectors.toList());
    }

    public List<PermissionResponseDto> getAllPermissionsAssignable() {
        return permissionRepository.findByIsAssignableEquals(Boolean.TRUE).stream()
                .map(userMgtDtoService::permissionToDto)
                .collect(Collectors.toList());
    }

    public boolean unAssignPermissionFromUserGroup(String permissionName, Long userGroupId) {

        var existingUserGroup = userGroupRepository.findById(userGroupId);
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userGroupId);

        var existingPermission = permissionRepository.findByName(permissionName);
        Validate.isPresent(existingPermission, PERMISSION_NAME_DOES_NOT_EXIST, permissionName);

        var existingGroupAuthority = groupAuthorityRepository.findByUserGroupAndPermission(existingUserGroup.get(), existingPermission.get());
        Validate.isTrue(existingGroupAuthority.isPresent(), ExceptionType.BAD_REQUEST, PERMISSION_NOT_ASSIGNED_TO_USER_GROUP, permissionName, existingUserGroup.get().getName());

        groupAuthorityRepository.delete(existingGroupAuthority.get());
        return Boolean.TRUE;
    }

    public GroupAuthorityResponseDto assignPermissionToUserGroup(String permissionName, Long userGroupId) {

        var existingUserGroup = userGroupRepository.findById(userGroupId);
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userGroupId);

        var existingPermission = permissionRepository.findByName(permissionName);
        Validate.isPresent(existingPermission, PERMISSION_NAME_DOES_NOT_EXIST, permissionName);
        checkThatPermissionRoleIsAssignable(existingPermission.get());

        var existingGroupAuthority = groupAuthorityRepository.findByUserGroupAndPermission(existingUserGroup.get(), existingPermission.get());
        Validate.isTrue(existingGroupAuthority.isEmpty(), ExceptionType.BAD_REQUEST, PERMISSION_ALREADY_ASSIGNED_TO_USER_GROUP, permissionName, existingUserGroup.get().getName());

        var groupAuthority = new TGroupAuthority();
        groupAuthority.setUserGroup(existingUserGroup.get());
        groupAuthority.setPermission(existingPermission.get());

        auditService.stampLongEntity(groupAuthority);

        return userMgtDtoService.groupAuthorityToDto(groupAuthorityRepository.save(groupAuthority));
    }

    public List<GroupAuthorityResponseDto> getUserGroupAuthorities(Long userGroupId) {

        var existingUserGroup = userGroupRepository.findById(userGroupId);
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userGroupId);

        return groupAuthorityRepository.findByUserGroup(existingUserGroup.get()).stream()
                .map(userMgtDtoService::groupAuthorityToDto)
                .collect(Collectors.toList());
    }

    public List<GroupAuthorityResponseDto> getAllGroups() {
        return groupAuthorityRepository.findAll().stream()
                .map(userMgtDtoService::groupAuthorityToDto)
                .collect(Collectors.toList());
    }

    public List<UserGroupResponseDto> getAllUserGroups() {
        return userGroupRepository.findAll().stream()
                .map(userMgtDtoService::mapTUserGroupToUserGroupResponseDto)
                .collect(Collectors.toList());
    }

    public UserGroupResponseDto addUserGroup(UserGroupRequestDto userGroupDto) {

        userGroupDto.validate();
        var existingUserGroup = userGroupRepository.findTUserGroupByName(userGroupDto.name() + USER_GROUP_SUFFIX);
        Validate.isTrue(existingUserGroup.isEmpty(), ExceptionType.BAD_REQUEST, USER_GROUP_ALREADY_EXISTS, userGroupDto.name()+USER_GROUP_SUFFIX);

        var userGroup = userMgtDtoService.dtoToTUserGroup(userGroupDto);
        userGroup.setName(userGroupDto.name()+USER_GROUP_SUFFIX);

        auditService.stampLongEntity(userGroup);

        return userMgtDtoService.mapTUserGroupToUserGroupResponseDto(userGroupRepository.save(userGroup));
    }
}
