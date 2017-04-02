package com.tavish.voice.reco.core;

import com.tavish.voice.reco.core.model.*;
import com.tavish.voice.reco.core.model.type.AuthType;
import com.tavish.voice.reco.core.model.type.Role;
import com.tavish.voice.reco.core.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TavishApplication {

	public static void main(String[] args) {
		SpringApplication.run(TavishApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(AccountRepository accountRepository) {
		List<Account> hasAccounts = accountRepository.findAll();
		if(hasAccounts ==null || hasAccounts.isEmpty()){
			updateDefaultData(accountRepository);
		}
		return event -> accountRepository.findAll();
	}

	private void updateDefaultData(AccountRepository accountRepository) {
		Account account = new Account();
		User user = new User();
		user.setAuthType(AuthType.PASSWORD);
		user.setPassword("password");
		user.setUserName("admin");
		account.setUserName("admin");
		user.setRole(Role.FULL_ACCESS);
		account.setUser(user);
		CommandEntry commandEntry = new CommandEntry();
		commandEntry.setProxyNeeded(false);
		Proxy proxy = new Proxy();
		proxy.setHost(System.getenv("PROXY_HOST"));
		proxy.setPort(Integer.valueOf(System.getenv("PROXY_PORT")));
		commandEntry.setProxy(proxy);
		commandEntry.setCommand("hello");
		CommandProvider commandProvider2 = new CommandProvider();
		commandProvider2.setAuthType(AuthType.PASSWORD);
		commandProvider2.setPriority(3);
		commandProvider2.setUrl("http://www.mocky.io/v2/58dcbdcc0f0000db17e4594d");
		CommandProvider commandProvider = new CommandProvider();
		commandProvider.setAuthType(AuthType.PASSWORD);
		commandProvider.setPriority(2);
		commandProvider.setUrl("http://httpbin.org/ip");
		List<CommandProvider> commandProviders = new ArrayList<>();
		commandProviders.add(commandProvider2);
		commandProviders.add(commandProvider);
		commandEntry.setCommandProviders(commandProviders);
		List<CommandEntry> commandEntries = new ArrayList<>();
		commandEntries.add(commandEntry);
		account.setCommandEntries(commandEntries);
		Account account1 = accountRepository.save(account);
		System.out.println("-------------------------------------->"+account1.getId());
	}
}
