package com.tavish.voice.reco.core.controller;

import com.tavish.voice.reco.core.model.VoiceResponse;
import com.tavish.voice.reco.core.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@RestController
public class VoiceController {

    @Autowired
    VoiceService voiceService;

    @RequestMapping(value = "/api/voice",method = RequestMethod.POST, produces = MediaType
            .APPLICATION_OCTET_STREAM_VALUE)
    public void processCommand(HttpServletResponse response, HttpServletRequest request){
        VoiceResponse voiceResponse = new VoiceResponse();
        try {
            final Part file = request.getPart("file");
            voiceService.handleCommand(file.getInputStream(),new Voice(), response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "test.wav" +
                    "\"");
            //return ResponseEntity.status(HttpStatus.OK).body(new InputStreamResource
            // (inputStream));
            // Copy the stream to the response's output stream.
            //IOUtils.copy(inputStream, response.getOutputStream());
            //response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/api/direct", method = RequestMethod.POST, produces = MediaType
            .APPLICATION_OCTET_STREAM_VALUE)
    public void processDirectCommand(HttpServletResponse response, HttpServletRequest request,
                                     @RequestParam(value = "command") String command){
        VoiceResponse voiceResponse = new VoiceResponse();
        try {
            voiceService.handleDirectCommand(command,new Voice(), response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "test.wav" +
                    "\"");
            //return ResponseEntity.status(HttpStatus.OK).body(new InputStreamResource
            // (inputStream));
            // Copy the stream to the response's output stream.
            //IOUtils.copy(inputStream, response.getOutputStream());
            //response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
