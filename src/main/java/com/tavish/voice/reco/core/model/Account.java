package com.tavish.voice.reco.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommandEntry> commandEntries;
}
