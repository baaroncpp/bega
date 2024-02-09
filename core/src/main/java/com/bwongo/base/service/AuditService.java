package com.bwongo.base.service;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.base.models.jpa.BaseEntity;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.security.models.CustomUserDetails;
import com.bwongo.user_mgt.models.jpa.TUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/24/23
 **/
@Service
public class AuditService {

    private static final String ONLY_LOGGED_IN_USER = "Only a logged in user can make this change";

    public void stampLongEntity(BaseEntity entity) {
        Date date = DateTimeUtil.getCurrentUTCTime();
        if(entity.getId() == null){
            entity.setCreatedOn(date);
            entity.setModifiedOn(date);
        }
        entity.setModifiedOn(date);
    }

    public void stampAuditedEntity(AuditEntity auditEntity) {
        CustomUserDetails user = getLoggedInUser();
        Validate.notNull(user, ExceptionType.BAD_CREDENTIALS, ONLY_LOGGED_IN_USER);
        Date date = DateTimeUtil.getCurrentUTCTime();
        TUser tUser = new TUser();
        tUser.setId(user.getId());

        if(auditEntity.getId() == null){
            auditEntity.setCreatedOn(date);
            auditEntity.setCreatedBy(tUser);
        }

        auditEntity.setModifiedBy(tUser);
        auditEntity.setModifiedOn(date);
    }

    public CustomUserDetails getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
