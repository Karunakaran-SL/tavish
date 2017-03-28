package com.tavish.voice.reco.core.service.impl;

import com.tavish.voice.reco.core.controller.Voice;
import com.tavish.voice.reco.core.service.CommandService;
import com.tavish.voice.reco.core.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class VoiceServiceImpl implements VoiceService{
    @Autowired
    IBM ibm;

    @Autowired
    CommandService commandService;

    @Autowired
    DefaultCommandHandler defaultCommandHandler;
    public void handleCommand(InputStream inputStream, Voice voice, OutputStream outputStream){
        String converted = ibm.convert2Text(inputStream);
        System.out.println("=================>"+converted);
        String response = commandService.handleCommand("admin",converted.trim());
        defaultCommandHandler.handleCommand(response, outputStream);
    }

    public void handleDirectCommand(String command, Voice voice, OutputStream outputStream){
        String response = commandService.handleCommand("admin",command.trim());
        defaultCommandHandler.handleCommand(response, outputStream);
    }
}