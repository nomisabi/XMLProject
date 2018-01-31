package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.uloge.TKorisnik;
import com.example.repository.KorisnikRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private KorisnikRepository korisnikRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TKorisnik korisnik = korisnikRepository.findByUsername(username);

		if (korisnik == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			List<GrantedAuthority> grantedAuthority = new ArrayList<>();
			grantedAuthority.add(new SimpleGrantedAuthority("admin"));   //!!!!!
			return new org.springframework.security.core.userdetails.User(korisnik.getKorisnickoIme(),
					korisnik.getLozinka(), grantedAuthority);

		}

	}

}
