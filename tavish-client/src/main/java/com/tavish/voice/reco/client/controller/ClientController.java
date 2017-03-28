package com.tavish.voice.reco.client.controller;

import com.tavish.voice.reco.client.service.AePlayWave;
import com.tavish.voice.reco.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;

/**
 * Created by khjg232 on 09/03/2017.
 */
@RestController("/api/voice")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String invoke(HttpServletResponse response, @RequestParam("command") String commnad){
        ResponseEntity<byte[]>  responseEntity = clientService.invoke(commnad);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            response.setStatus(200);
            //response.setHeader("Content-Disposition", "attachment; filename=\"" +
            //"testr.wav" + "\"");
            //IOUtils.write(responseEntity.getBody(), response.getOutputStream());
            ByteArrayInputStream bis = new ByteArrayInputStream(responseEntity.getBody());
            AudioFormat audioFormat = new AudioFormat(22050, 16, 2, true, false);
            AudioInputStream audioInputStream2 = new AudioInputStream(bis, audioFormat,
                    102400);
            new AePlayWave(audioInputStream2).start();
        }
        return "Suucess";
    }
}
