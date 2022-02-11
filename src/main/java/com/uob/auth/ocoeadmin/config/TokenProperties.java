package com.uob.auth.ocoeadmin.config;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.uob.auth.ocoeadmin.jwt.model.TokenTimeDTO;
import com.uob.auth.ocoeadmin.repository.TblTokenTimeRepository;

import lombok.Data;

import static com.uob.auth.ocoeadmin.jwt.utility.OCOEConstants.*;

@Configuration
@ConfigurationProperties(prefix = "token")
@Data
public class TokenProperties implements Serializable{
	
	private long createexpiryTime;
	private long refreshexpiryTime;
	
	
	@Autowired
	TblTokenTimeRepository tblTokenRepo;

	@PostConstruct
	public void findTokenNameDuration() {
		
		List<TokenTimeDTO> tokenData= tblTokenRepo.listAllTokenName();
		
		
		
		for(TokenTimeDTO tokenTimeDTO: tokenData) {
			
			if(tokenTimeDTO.getTokenName().equalsIgnoreCase(TOKENEXPIRYTIME)) {
				this.setCreateexpiryTime(tokenTimeDTO.getTokenDuration());
			}
			else if(tokenTimeDTO.getTokenName().equalsIgnoreCase(TOKENREFRESHTIME)){
				this.setRefreshexpiryTime(tokenTimeDTO.getTokenDuration());
			}
		 }
		
		
	}
	

}
