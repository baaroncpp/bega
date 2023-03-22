package com.bwongo.authserver.auth;

import com.bwongo.authserver.model.AuthenticationRequest;
import com.bwongo.authserver.model.AuthenticationResponse;
import com.bwongo.authserver.model.ValidateTokenRequest;
import com.bwongo.authserver.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(path = "authenticate", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping(path = "validate/token", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateToken(@RequestBody ValidateTokenRequest validateTokenRequest){
        return ResponseEntity.ok(authenticationService.validateToken(validateTokenRequest));
    }
}
