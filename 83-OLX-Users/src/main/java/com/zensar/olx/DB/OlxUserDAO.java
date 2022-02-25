package com.zensar.olx.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.bean.OlxUser;

@Repository
public interface OlxUserDAO extends JpaRepository<OlxUser,Integer> {
	
	OlxUser findByUserName(String userName);	//interface method
}
