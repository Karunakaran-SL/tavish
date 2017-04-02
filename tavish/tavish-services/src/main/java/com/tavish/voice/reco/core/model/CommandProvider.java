package com.tavish.voice.reco.core.model;

import com.tavish.voice.reco.core.model.type.AuthType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class CommandProvider extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //private List<User> allowedUsers;
    private String url;
    //private Map<String,String> queryParam;
    private AuthType authType;
    private int priority;
    //TODO add more for Oauth2
}
