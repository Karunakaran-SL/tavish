package com.tavish.voice.reco.core.service.impl;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class IBM {

    String translated = "";
    boolean isFinal = false;
    public String convert2Text(InputStream inputStream){
        SpeechToText service = new SpeechToText();
        service.setUsernameAndPassword(System.getenv("IBM_USERNAME"),System.getenv
                ("IBM_PASSWORD"));
        translated = "";
        isFinal = false;
        RecognizeOptions options = new RecognizeOptions.Builder()
                .continuous(true)
                .interimResults(true)
                .contentType(HttpMediaType.AUDIO_WAV)
                .build();
        service.recognizeUsingWebSocket(inputStream, options, new BaseRecognizeCallback() {
                    @Override
                    public void onTranscription(SpeechResults speechResults) {
                        //System.out.println(speechResults);
                        for(Transcript transcripts : speechResults.getResults()){
                            if(transcripts.isFinal()){
                                double confidance = 0;
                                for(SpeechAlternative speechAlternatives : transcripts
                                        .getAlternatives()){
                                    if(speechAlternatives.getConfidence() >= confidance){
                                        confidance = speechAlternatives.getConfidence();
                                        translated = speechAlternatives.getTranscript();
                                    }
                                }
                                isFinal = true;
                            }
                        }
                    }
                }
            );
        do{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(!isFinal);
        return translated;
    }
}
