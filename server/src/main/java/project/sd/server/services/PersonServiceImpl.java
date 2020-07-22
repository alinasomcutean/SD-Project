package project.sd.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.server.dto.PersonDto;
import project.sd.server.model.Account;
import project.sd.server.model.Person;
import project.sd.server.repository.AccountRepo;
import project.sd.server.repository.PersonRepo;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepo personRepo;
    private final AccountRepo accountRepo;
    private final AccountService accountService;

    public List<PersonDto> findAllPersons() {
        List<Person> persons = personRepo.findAll();
        List<PersonDto> personDTOS = new ArrayList<>();

        for (Person person : persons) {
            personDTOS.add(new PersonDto(person));
        }
        return personDTOS;
    }

    public PersonDto insertPerson(String firstName, String lastName, String gender, String phone,
                                  String username, String password, String email, String accountType) {
        // Insert the new account
        Account account = accountService.insertAccount(username, password, email, accountType);

        // Create a new person
        Person person = Person.builder().firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .phone(phone)
                .account(account).build();
        personRepo.save(person);

        return new PersonDto(person);
    }

    public PersonDto deletePerson(Integer id) {
        Person person = personRepo.findById(id).orElseThrow(RuntimeException::new);
        PersonDto personDto = new PersonDto(person);
        personRepo.deleteById(id);
        return personDto;
    }

    public PersonDto updatePerson(Integer personId, String firstName, String lastName, String gender, String phone,
                             String username, String password, String email, String accountType) {
        // Find the existing person with the given id
        Person person = personRepo.findById(personId).orElseThrow(RuntimeException::new);

        // Update person's account
        Account account = accountService.updateAccount(person.getAccount().getId(), username, password, email, accountType);

        // Set the new data for the person
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setGender(gender);
        person.setPhone(phone);
        person.setAccount(account);

        personRepo.save(person);

        return new PersonDto(person);
    }
}
