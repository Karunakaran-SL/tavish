package com.tavish.voice.reco.core.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.tavish.voice.reco.core.controller.Voice;
import com.tavish.voice.reco.core.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by khjg232 on 04/03/2017.
 */
@Service
public class VoiceServiceImpl implements VoiceService{
    @Autowired
    Google google;
    public InputStream handleCommand(InputStream inputStream, Voice voice){
        google.convert2Text(inputStream);
        return inputStream;
    }
}