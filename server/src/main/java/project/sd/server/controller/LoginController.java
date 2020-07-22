package project.sd.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.sd.server.dto.AccountDto;
import project.sd.server.repository.AccountRepo;
import project.sd.server.repository.PersonRepo;
import project.sd.server.services.AccountService;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final AccountService accountService;
    private final AccountRepo accountRepo;
    private final PersonRepo personRepo;

    @PostMapping(value = "login")
    public AccountDto login(@RequestParam String username, @RequestParam String password) {
        return accountService.findByUsernameAndPassword(username, password);
    }
}
