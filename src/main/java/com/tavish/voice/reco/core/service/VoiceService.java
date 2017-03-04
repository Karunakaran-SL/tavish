package com.tavish.voice.reco.core.service;

import com.tavish.voice.reco.core.controller.Voice;

import java.io.InputStream;

/**
 * Created by khjg232 on 04/03/2017.
 */
public interface VoiceService {
    InputStream handleCommand(InputStream inputStream, Voice voice);
}
