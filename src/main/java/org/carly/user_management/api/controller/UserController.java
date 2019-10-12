package org.carly.user_management.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.carly.shared.utils.mail_service.MailService;
import org.carly.user_management.api.model.CarlyUserRest;
import org.carly.user_management.api.model.LoginRest;
import org.carly.user_management.api.model.UserRest;
import org.carly.shared.security.config.LoggedUserProvider;
import org.carly.user_management.core.model.Password;
import org.carly.user_management.core.model.User;
import org.carly.user_management.core.service.UserService;
import org.carly.shared.security.model.LoggedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final MailService mailService;
    private final LoggedUserProvider loggedUserProvider;

    public UserController(UserService userService,
                          MailService mailService,
                          LoggedUserProvider loggedUserProvider) {
        this.userService = userService;
        this.mailService = mailService;
        this.loggedUserProvider = loggedUserProvider;
    }

    @PostMapping("/registration")
    public User registerUserAccount(@Valid @RequestBody UserRest userRest,
                                    BindingResult result,
                                    WebRequest request) {
        return userService.createUser(userRest, request);
    }

    @GetMapping("/registrationConfirmation")
    public String confirmRegistration(WebRequest request,
                                      @RequestParam("token") String token) {
        String response = userService.confirmRegistration(request, token);
        return ResponseEntity.ok(response).getBody();
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER','OPERATIONS')")
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request,
                                                @RequestParam("email") String email) {
        return userService.resetUserPassword(request, email);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'OPERATIONS')")
    @GetMapping("/changePassword")
    public ResponseEntity changePassword(@RequestParam("id") String id,
                                         @RequestParam("token") String token) {
        return userService.changePassword(id, token);
    }

    @PreAuthorize("hasAnyAuthority('CHANGE_PASSWORD_PRIVILEGE', 'OPERATIONS')")
    @GetMapping("/savePassword")
    public ResponseEntity saveNewPassword(@Valid @RequestBody Password password) {
        LoggedUser loggedUser = loggedUserProvider.provideCurrent();
        return userService.saveNewPassword(loggedUser, password.getNewPassword());
    }

    @GetMapping("/login")
    public CarlyUserRest login(@Valid @RequestBody LoginRest userRest,
                               BindingResult result) {
        return userService.login(userRest);
    }

    //for mail test
    @GetMapping("/send")
    public void sendMail() {
        mailService.sendSimpleEmail("ryba.marcin20@gmail.com", "Carly company mail sender", "Nasz mail który bedzie potwierdzał rejerstracje:P");
    }
}