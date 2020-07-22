package project.sd.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.sd.server.dto.AccountDto;
import project.sd.server.dto.PersonDto;
import project.sd.server.services.AccountService;
import project.sd.server.services.PersonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final PersonService personService;
    private final AccountService accountService;

    @GetMapping("/users")
    public List<PersonDto> getUsers() {
        return personService.findAllPersons();
    }

    @PostMapping("/addUser")
    public PersonDto createUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String gender,
                                @RequestParam String phone, @RequestParam String username, @RequestParam String password,
                                @RequestParam String email, @RequestParam String accountType) {
        return personService.insertPerson(firstName, lastName, gender, phone, username, password, email, accountType);
    }

    @DeleteMapping("/deleteUser/{id}")
    public PersonDto deleteUser(@PathVariable Integer id) {
        PersonDto personDto = personService.deletePerson(id);
        accountService.deleteAccountByPersonId(id);
        return personDto;
    }

    @PostMapping("/updateUser")
    public PersonDto updateUser(@RequestParam Integer id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String gender,
                                @RequestParam String phone, @RequestParam String username, @RequestParam String password,
                                @RequestParam String email, @RequestParam String accountType) {
        return personService.updatePerson(id, firstName, lastName, gender, phone, username, password, email, accountType);
    }

    @PostMapping("/editSubscriptions")
    public AccountDto editSubscriptions(@RequestParam String username, @RequestParam String subscriptions) {
        return accountService.editSubscriptions(username, subscriptions);
    }
}
