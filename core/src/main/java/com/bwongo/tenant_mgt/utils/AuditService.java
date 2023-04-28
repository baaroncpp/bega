package com.bwongo.tenant_mgt.utils;

import com.bwongo.base.model.jpa.AuditEntity;
import com.bwongo.base.model.jpa.BaseEntity;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.DateTimeUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.security.models.CustomUserDetails;
import com.bwongo.user_mgt.models.jpa.TUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;


/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/24/23
 **/
@Service
public class AuditService {

    public void stampLongEntity(BaseEntity entity) {
        Date date = DateTimeUtil.getCurrentUTCTime();
        if(entity.getId() == null){
            entity.setCreatedOn(date);
        }
        entity.setCreatedOn(DateTimeUtil.getCurrentUTCTime());
    }

    public void stampAuditedEntity(AuditEntity auditEntity) {
        CustomUserDetails user = getLoggedInUser();
        Validate.notNull(user, ExceptionType.BAD_CREDENTIALS,"Only a logged in user can make this change");
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
        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            var decoded = (LinkedHashMap)((Authentication)authentication.getDetails()).getDetails();
            CustomUserDetails user = new CustomUserDetails();
            user.setUsername((String)decoded.get("username"));
            user.setId(Long.valueOf((Integer) decoded.get("id")));
            return user;
        }

        return null;
    }
}
