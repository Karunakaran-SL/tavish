package com.tavish.voice.reco.client.service;

/**
 * Created by khjg232 on 14/03/2017.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class DirectCommandRead {
    // record duration, in milliseconds
    static final long RECORD_TIME = 6000;  // 1 minute

    // path of the wav file
    File wavFile = new File("C:/Dev/RecordAudio.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    TargetDataLine line;

    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

    /**
     * Entry to run the program
     */
    public static void main(String[] args) {
        final DirectCommandRead recorder = new DirectCommandRead();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();
            try {
                ResponseEntity<byte[]> responseEntity = new ClientService().invokeByCommand(command);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    //response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    //"testr.wav" + "\"");
                    //IOUtils.write(responseEntity.getBody(), response.getOutputStream());
                    ByteArrayInputStream bis = new ByteArrayInputStream(responseEntity.getBody());
                    AudioFormat audioFormat = new AudioFormat(25050, 16, 2, true, false);
                    AudioInputStream audioInputStream2 = new AudioInputStream(bis, audioFormat,
                            102400);
                    new AePlayWave(audioInputStream2).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
