package project.sd.client.services;

import project.sd.client.dto.PersonDto;
import retrofit2.Callback;

import java.util.List;

public interface PersonService {

    void getUsers(Callback<List<PersonDto>> callback);

    void createUser(String firstName, String lastName, String gender, String phone,String username, String password, String email, String accountType,
                    Callback<PersonDto> callback);

    void deleteUser(Integer id, Callback<PersonDto> callback);

    void updateUser(Integer id, String firstName, String lastName, String gender, String phone,String username,
                    String password, String email, String accountType, Callback<PersonDto> callback);
}
