package com.tavish.voice.reco.client.service;

import com.tavish.voice.reco.client.CommandRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Created by khjg232 on 09/03/2017.
 */
@Service
public class ClientService {

    public ResponseEntity<byte[]> invoke(String command){
        ResponseEntity<byte[]> responseEntity = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
            FileInputStream fileInputStream = new FileInputStream(command);
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("file", new ByteArrayResource(IOUtils.toByteArray(fileInputStream)));
            parts.add("filename", "mytest.wav");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
            headers.setContentDispositionFormData("Content-Disposition", "attachment; " +
                    "filename=\"" + "test.wav" + "\"");
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts,
                    headers);
            responseEntity =
                    restTemplate.exchange("http://localhost:8080/api/voice",
                            HttpMethod.POST, entity, byte[].class,"test.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }


    public ResponseEntity<byte[]> invokeByCommand(String command){
        ResponseEntity<byte[]> responseEntity = null;
        try {
            CommandRequest commandRequest = new CommandRequest();
            commandRequest.setCommand(command);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
            responseEntity =
                    restTemplate.postForEntity
                            ("http://localhost:8080/api/direct", commandRequest,
                            byte[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

}
