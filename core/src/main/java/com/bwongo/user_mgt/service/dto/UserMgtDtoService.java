package com.bwongo.user_mgt.service.dto;

import com.bwongo.user_mgt.models.dto.UserGroupResponseDto;
import com.bwongo.user_mgt.models.dto.UserResponseDto;
import com.bwongo.user_mgt.models.jpa.TUser;
import com.bwongo.user_mgt.models.jpa.TUserGroup;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/5/23
 **/
@Service
public class UserMgtDtoService {

    public UserResponseDto mapTUserToUserResponseDto(TUser user){

        if(user == null){
            return null;
        }

        return new UserResponseDto(
                user.getId(),
                user.getCreatedOn(),
                user.getModifiedOn(),
                user.getUsername(),
                user.isApproved(),
                mapTUserGroupToUserGroupResponseDto(user.getUserGroup()),
                user.getUserType(),
                user.getUserMetaId()
        );
    }

    public UserGroupResponseDto mapTUserGroupToUserGroupResponseDto(TUserGroup userGroup){

        if(userGroup == null){
            return null;
        }

        return new UserGroupResponseDto(
                userGroup.getId(),
                userGroup.getCreatedOn(),
                userGroup.getModifiedOn(),
                userGroup.getName(),
                userGroup.getNote()
        );
    }
}
