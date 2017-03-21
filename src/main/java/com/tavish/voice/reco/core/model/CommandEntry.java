package com.tavish.voice.reco.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@Entity
public class CommandEntry extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String command;
    private boolean proxyNeeded;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Proxy proxy;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommandProvider> commandProviders;
    public List<CommandProvider> getCommandProviders(){
        Collections.sort(commandProviders,new Comparator<CommandProvider>() {
            @Override
            public int compare(CommandProvider lhs, CommandProvider rhs) {
                return lhs.getPriority() > rhs.getPriority() ? -1 : (lhs.getPriority() < rhs.getPriority() )
                        ? 1
                        : 0;
            }
        });
        return commandProviders;
    }
}
