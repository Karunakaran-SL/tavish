package com.tavish.voice.reco.core.service.impl;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.BufferedDoubleDataSource;
import marytts.util.data.audio.DDSAudioInputStream;
import marytts.util.data.audio.MaryAudioUtils;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class DefaultCommandHandler {
    public void handleCommand(String command, OutputStream outputStream){
        // init mary
        LocalMaryInterface mary = null;
        try {
            mary = new LocalMaryInterface();
        } catch (MaryConfigurationException e) {
            System.err.println("Could not initialize MaryTTS interface: " + e.getMessage());
            e.printStackTrace();
        }

        // synthesize
        AudioInputStream audio = null;
        try {
            audio = mary.generateAudio(command);
        } catch (SynthesisException e) {
            System.err.println("Synthesis failed: " + e.getMessage());
            //System.exit(1);
        }

        //return audio;
        //String outputFileName = "C:\\dev\\test_"+ UUID.randomUUID()+".wav";
        //InputStream inputStream = null;
        // write to output
        double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);
        try {
            DDSAudioInputStream outputAudio = new DDSAudioInputStream(new BufferedDoubleDataSource(samples), audio.getFormat());

            AudioSystem.write(outputAudio, AudioFileFormat.Type.WAVE, outputStream);
            //MaryAudioUtils.writeWavFile(samples, outputFileName, );
            //System.out.println("Output written to " + outputFileName);
            //inputStream = new FileInputStream(outputFileName);
        } catch (IOException e) {
            System.err.println("Could not write to file: "  + e.getMessage());
            //System.exit(1);
        }
        //return inputStream;
    }
}
