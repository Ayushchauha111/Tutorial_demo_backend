package com.rakuten.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rakuten.training.model.Nuser;

public interface SignupRepo extends JpaRepository<Nuser, Long> {
	
	Nuser findByemailid(String EmailId);
	Nuser findByemailidAndPasswordAndRole(String EmailId,String PassWord,String Role);

}
