package com.tavish.voice.reco.core.service.impl;

import com.tavish.voice.reco.core.model.Account;
import com.tavish.voice.reco.core.model.CommandEntry;
import com.tavish.voice.reco.core.model.CommandProvider;
import com.tavish.voice.reco.core.repository.AccountRepository;
import com.tavish.voice.reco.core.service.CommandService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Service
public class CommandServiceImpl implements CommandService {
    @Autowired
    AccountRepository accountRepository;
    public String handleCommand(String userName, String command){
        Account account = accountRepository.findByUserName(userName);
        String response = "Unable to find command mapping";
        for(CommandEntry commandEntry : account.getCommandEntries()){
            if(commandEntry.getCommand().equalsIgnoreCase(command)){
                RestTemplate restTemplate = restTemplate(commandEntry);
                for(CommandProvider commandProvider :commandEntry.getCommandProviders()){
                    try {
                        String obj = restTemplate.getForObject(commandProvider.getUrl(),String.class);
                        return obj;
                    } catch (RestClientException e) {
                        System.out.println("Error on command, Trying for next");
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    public RestTemplate restTemplate(CommandEntry commandEntry ) {
        if(commandEntry.isProxyNeeded()){
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress(commandEntry.getProxy()
                    .getHost(), commandEntry.getProxy().getPort()));
            requestFactory.setProxy(proxy);
            return new RestTemplate(requestFactory);
        }else{
            return new RestTemplate();
        }
    }

    @Data
    class IP implements Serializable{
        String origin;
    }
}
