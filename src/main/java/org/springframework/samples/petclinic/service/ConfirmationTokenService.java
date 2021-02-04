package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ConfirmationToken;
import org.springframework.samples.petclinic.repository.ConfirmationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmationTokenService {
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Transactional
	public Collection<ConfirmationToken> findAll(){
		return confirmationTokenRepository.findAll();
	}
	
	public Optional<ConfirmationToken> findById(int id){
		return confirmationTokenRepository.findById(id);
	}
	
	public void save(ConfirmationToken confirmationToken) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		confirmationToken.setPass(encoder.encode(confirmationToken.getPass()));
		confirmationTokenRepository.save(confirmationToken);
	}

	public Optional<ConfirmationToken> findByEmail(String email) {
		return confirmationTokenRepository.findByEmail(email);
	}

	public Optional<ConfirmationToken> findByToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}

}
