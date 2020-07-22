package project.sd.server.services;

import project.sd.server.dto.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> findAllPersons();

    PersonDto insertPerson(String firstName, String lastName, String gender, String phone,
                           String username, String password, String email, String accountType);

    PersonDto deletePerson(Integer id);

    PersonDto updatePerson(Integer personId, String firstName, String lastName, String gender, String phone,
                      String username, String password, String email, String accountType);
}
