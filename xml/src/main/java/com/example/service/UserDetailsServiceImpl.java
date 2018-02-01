package com.example.service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.korisnici.Korisnik;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private Korisnici2Service korisnici2Service;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			String korisnikStr = korisnici2Service.pronadjiKorisnickoIme(username);
			if (korisnikStr == null) {
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			} else {
				Korisnik korisnik = korisnici2Service.unmarshalling(korisnikStr);

				List<GrantedAuthority> grantedAuthority = new ArrayList<>();
				grantedAuthority.add(new SimpleGrantedAuthority(korisnik.getUloga()));
				return new org.springframework.security.core.userdetails.User(korisnik.getKorisnickoIme(),
						korisnik.getLozinka(), grantedAuthority);
			}
		} catch (IOException | JAXBException e1) {
			logger.info(e1.getMessage());
			return null;
		}

	}

}
