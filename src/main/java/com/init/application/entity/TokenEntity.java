package com.init.application.entity;

import com.init.application.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class TokenEntity extends BaseEntity {
    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "expired_access")
    private Date expiredAccess;

    @Column(name = "expired_refresh")
    private Date expiredRefresh;

    @Column(name = "revoked_refresh")
    private Date revokedRefresh;
}
