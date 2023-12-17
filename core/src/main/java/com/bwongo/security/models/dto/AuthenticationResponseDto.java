package com.bwongo.security.models.dto;

import com.bwongo.user_mgt.models.dto.response.UserGroupResponseDto;
import com.bwongo.user_mgt.models.jpa.TUserGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private UserGroupResponseDto userGroup;
    private List<String> authorities;
}
