package com.tavish.voice.reco.core.service.impl;

import com.tavish.voice.reco.core.model.Account;
import com.tavish.voice.reco.core.repository.AccountRepository;
import com.tavish.voice.reco.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account createOrUpdateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(long id) {
        return accountRepository.getOne(id);
    }

    @Override
    public boolean deleteAccount(long id){
        accountRepository.delete(id);
        return true;
    }
}
