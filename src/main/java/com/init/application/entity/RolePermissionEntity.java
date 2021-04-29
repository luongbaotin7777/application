package com.init.application.entity;

import com.init.application.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role_permissions")
public class RolePermissionEntity extends BaseEntity {
    @Column(name = "PERMISSION_ID")
    @Type(type = "uuid-char")
    private UUID permissionId;

    @Column(name = "ROLE_ID")
    @Type(type = "uuid-char")
    private UUID roleId;
}
