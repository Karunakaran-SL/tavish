package com.tavish.voice.reco.core.service.impl;

import com.google.cloud.speech.spi.v1beta1.SpeechClient;
import com.google.cloud.speech.v1beta1.*;
import com.google.protobuf.ByteString;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class Google {
    public String convert2Text(InputStream inputStream){
        try {
            SpeechClient speech = SpeechClient.create();

            byte[] data = IOUtils.toByteArray(inputStream);
            ByteString audioBytes = ByteString.copyFrom(data);

            // Configure request with local raw PCM audio
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRate(16000)
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Use blocking call to get audio transcript
            SyncRecognizeResponse response = speech.syncRecognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result: results) {
                List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
                for (SpeechRecognitionAlternative alternative: alternatives) {
                    System.out.printf("Transcription: %s%n", alternative.getTranscript());
                }
            }
            speech.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello";
    }
}
