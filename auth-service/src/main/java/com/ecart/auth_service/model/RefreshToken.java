package com.ecart.auth_service.model;

import com.ecart.postgresqlcommon.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends CommonEntity {

    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;

    @Column(name = "expired_date", nullable = false)
    private Date expiredDate;

    @ManyToOne(optional = false)
    private User user;
}
