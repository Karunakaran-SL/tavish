package com.tavish.voice.reco.core.service;

import com.tavish.voice.reco.core.controller.Voice;

import java.io.InputStream;
import java.io.OutputStream;

public interface VoiceService {
    void handleCommand(InputStream inputStream, Voice voice, OutputStream outputStream);
    void handleDirectCommand(String command, Voice voice, OutputStream outputStream);
}
