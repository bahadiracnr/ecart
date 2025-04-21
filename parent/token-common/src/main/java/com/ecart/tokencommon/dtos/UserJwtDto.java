package com.ecart.tokencommon.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserJwtDto {
    private String username;
}
