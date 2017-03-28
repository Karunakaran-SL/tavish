package com.tavish.voice.reco.core.service;

import com.tavish.voice.reco.core.model.Account;

public interface AccountService {
    Account createOrUpdateAccount(Account account);
    Account getAccount(long id);
    boolean deleteAccount(long id);
}

