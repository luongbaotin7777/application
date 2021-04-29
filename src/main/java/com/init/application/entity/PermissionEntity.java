package com.init.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.init.application.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "permissions")
public class PermissionEntity extends BaseEntity {
    @Column(name = "PERMISSION_NAME")
    private String permissionName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "GROUP_PERMISSION")
    private String groupPermission;

    @ManyToMany(mappedBy = "permissionEntities", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<RoleEntity> roleEntities = new ArrayList<>();
}
