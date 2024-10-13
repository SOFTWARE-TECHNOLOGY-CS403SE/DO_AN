package org.example.advancedrealestate_be.service;

import com.nimbusds.jose.JOSEException;
import org.example.advancedrealestate_be.dto.request.AuthenticationRequest;
import org.example.advancedrealestate_be.dto.request.IntrospectRequest;
import org.example.advancedrealestate_be.dto.request.LogoutRequest;
import org.example.advancedrealestate_be.dto.request.RefreshRequest;
import org.example.advancedrealestate_be.dto.response.AuthenticationResponse;
import org.example.advancedrealestate_be.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public void logout(LogoutRequest request) throws ParseException, JOSEException;

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
