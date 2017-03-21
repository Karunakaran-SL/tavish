package com.tavish.voice.reco.core.controller;

import com.tavish.voice.reco.core.model.Account;
import com.tavish.voice.reco.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/account")
public class AccountController{
    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    public Account createOrUpdateAccount(@RequestBody Account account){
        return accountService.createOrUpdateAccount(account);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account getAccount(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id){
        Account account = accountService.getAccount(Long.valueOf(id));
        return account;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public boolean deleteAccount(@RequestParam("id") long id){
        return accountService.deleteAccount(id);
    }
}
