package org.perficient.registrationsystem.controllers.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.perficient.registrationsystem.dto.LoginDto;
import org.perficient.registrationsystem.dto.TokenDto;
import org.perficient.registrationsystem.services.impl.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * Class AuthController Created on 20/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@RestController
@RequestMapping("/api/v1/auth")
@Api( tags = "Clients")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "This method is used to authenticate the users 'login'.")
    public TokenDto login(@RequestBody LoginDto loginDto) {
        // Authenticating the login
        var t = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                        loginDto.getPassword())
        );

        // If The user login successfully search for more information
        var userDetails = service.loadUserByUsername(loginDto.getEmail());

        //Generates The token
        return jwtUtilService.generateToken(userDetails);
    }
}
