package com.tavish.voice.reco.core.repository;

import com.tavish.voice.reco.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
