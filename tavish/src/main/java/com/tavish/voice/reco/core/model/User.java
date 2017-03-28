package com.tavish.voice.reco.core.model;

import com.tavish.voice.reco.core.model.type.AuthType;
import com.tavish.voice.reco.core.model.type.Role;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private AuthType authType;
    private Role role;
}
