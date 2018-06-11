package com.example.project2018.server.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.project2018.server.controller.AuthController;
import com.example.project2018.server.model.users.Permission;
import com.example.project2018.server.model.users.Role;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.repository.UserRepository;
import com.example.project2018.server.security.JwtUser;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
    @Autowired
    private UserRepository userRepository;
    
    
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
        	logger.info("No user found with username: {}", username);
            throw new UsernameNotFoundException(String.format("No user found with username: '%s'.", username));
        } else {
            return getUserDetails(user);
        }
    }
    private JwtUser getUserDetails(User user) {
		
		return new JwtUser(user.getId(), user.getUsername(),user.getFirstname(), user.getLastname(), user.getEmail(),user.getPassword(), getAuthorities(user.getRoles()), user.getEnabled());
	}
	private final Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	Set<String> set = new HashSet<>();
    	for (Role role : roles) {
			set.add(role.getName());
				for (Permission permission : role.getPermissions()) {
					set.add(permission.getName());
				}
		}
    	authorities.addAll(set.stream()
    			.map(SimpleGrantedAuthority::new)
    			.collect(Collectors.toList()));
    	
    	authorities.get(0).getAuthority();
        return authorities;
    }
}
