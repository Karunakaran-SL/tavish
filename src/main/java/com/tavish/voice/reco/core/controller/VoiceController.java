package com.tavish.voice.reco.core.controller;

import com.tavish.voice.reco.core.model.VoiceResponse;
import com.tavish.voice.reco.core.service.VoiceService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by khjg232 on 04/03/2017.
 */
@RestController("/api/voice")
public class VoiceController {

    @Autowired
    VoiceService voiceService;

    @RequestMapping(method = RequestMethod.POST, headers=("content-type=multipart/*"), produces =
            MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody VoiceResponse processCommand(HttpServletResponse response,
                                                      @RequestParam("file") MultipartFile file){
        VoiceResponse voiceResponse = new VoiceResponse();
        try {
            InputStream inputStream = voiceService.handleCommand(file.getInputStream(),new Voice());
            response.addHeader("Content-disposition", "attachment;filename=response.mp3");
            response.setContentType("multipart/mixed");
            // Copy the stream to the response's output stream.
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voiceResponse;
    }
}
