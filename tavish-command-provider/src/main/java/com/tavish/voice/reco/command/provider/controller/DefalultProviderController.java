package com.tavish.voice.reco.command.provider.controller;

import com.tavish.voice.reco.sdk.CommandRequest;
import com.tavish.voice.reco.sdk.CommandResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by khjg232 on 02/04/2017.
 */
@RestController
public class DefalultProviderController {
    @RequestMapping(value = "/api/provider/default", method = RequestMethod.POST)
    public CommandResponse processCommand(HttpServletResponse response, HttpServletRequest request,
                                          @RequestBody CommandRequest command){
       return new CommandResponse();
    }
}
