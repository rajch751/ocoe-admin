package com.uob.auth.ocoeadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uob.auth.ocoeadmin.jwt.entity.TblTokenTime;
import com.uob.auth.ocoeadmin.jwt.model.TokenTimeDTO;

@Repository
public interface TblTokenTimeRepository extends CrudRepository<TblTokenTime, Long> {
	
	
	
	@Query("select new com.uob.auth.ocoeadmin.jwt.model.TokenTimeDTO(tokenName, tokenDuration) from TblTokenTime")
	List<TokenTimeDTO> listAllTokenName();
	
	List<TblTokenTime> findAll();

}
