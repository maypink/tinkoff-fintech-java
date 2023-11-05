package com.maypink.tinkoff.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
class AuthentificationResource {

    private final RegistrationService registrationService;

    @GetMapping("/registration")
    public String performRegistration(@ModelAttribute("userCredentials") UserCredentialsDto userCredentialsDto){
        return "/registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("userCredentials") UserCredentialsDto userCredentialsDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:/registration";
        }
        registrationService.register(userCredentialsDto);
        return "redirect:/login";
    }
}
