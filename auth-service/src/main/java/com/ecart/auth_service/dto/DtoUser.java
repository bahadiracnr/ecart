package com.ecart.auth_service.dto;

import com.ecart.postgresqlcommon.entity.CommonEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser extends CommonEntity {
    private String username;
    private String password;
}
