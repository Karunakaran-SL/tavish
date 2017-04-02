package com.tavish.voice.reco.sdk;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommandRequest implements Serializable{
    private String command;
    private boolean conversation;
    private String path;
    private String serialNo;
    private String group;
}