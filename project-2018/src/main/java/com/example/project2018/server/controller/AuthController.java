package com.example.project2018.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.server.dto.AuthDTO;
import com.example.project2018.server.dto.PasswordRequest;
import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.exception.AuthenticationException;
import com.example.project2018.server.model.users.Permission;
import com.example.project2018.server.model.users.Role;
import com.example.project2018.server.model.users.User;
import com.example.project2018.server.security.JwtAuthenticationRequest;
import com.example.project2018.server.security.JwtAuthenticationResponse;

import com.example.project2018.server.security.JwtTokenUtil;
import com.example.project2018.server.security.JwtUser;
import com.example.project2018.server.security.service.JwtUserDetailsService;
import com.example.project2018.server.service.UserService;





@RestController
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> logInUser(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

    	Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
       
        // Reload password post-security so we can generate the token
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        logger.info("{} logged in.", userDetails.getUsername());
        
        List<String> roles = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
        List<String> permissions = new ArrayList<String>();
        
    	/*List<Role> userRoles = new ArrayList<Role>();
    	
    	User user = userService.getByUsername(userDetails.getUsername());	
    	userRoles = (List<Role>) user.getRoles();
    	for (Role role: userRoles) {
    		for ( Permission perm : role.getPermissions()) {
				permissions.add(perm.getName());
				System.out.println(perm.getName());
			}
    	}*/
        System.out.println("Korisnik " +userDetails.getUsername() + "je "+ userDetails.isEnabled());
        UserDTO userDto = new UserDTO(userDetails.getUsername(), roles);
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(userDto, token));
    }

    

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

  
    private Authentication authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return authentication;
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordRequest passwordReset){
    	
    	//TODO Resetovanje zaboravljene lozinke
    return  ResponseEntity.status( HttpStatus.OK).body("Password reseted");
    }
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordRequest passwordReset){
    	//TODO Promena lozinke korisnika, mora biti ulogovan i ne sme biti admin.
    	
    return  ResponseEntity.status( HttpStatus.OK).body("Password changed");
    }
}
