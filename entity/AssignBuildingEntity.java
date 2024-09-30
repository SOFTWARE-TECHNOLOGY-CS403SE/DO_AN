package com.javaweb.entity;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Table(name="assignmentbuilding")
public class AssignBuildingEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="staffid")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "buildingid")
    private BuildingEntity buildingEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BuildingEntity getBuildingEntity() {
        return buildingEntity;
    }

    public void setBuildingEntity(BuildingEntity buildingEntity) {
        this.buildingEntity = buildingEntity;
    }
}
