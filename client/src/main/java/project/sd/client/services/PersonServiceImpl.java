package project.sd.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.client.api.UserApi;
import project.sd.client.dto.PersonDto;
import project.sd.client.util.SingletonRetrofit;
import retrofit2.Callback;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private UserApi userApi = SingletonRetrofit.getInstance().getRetrofit().create(UserApi.class);

    @Override
    public void getUsers(Callback<List<PersonDto>> callback) {
        userApi.getUsers().enqueue(callback);
    }

    @Override
    public void createUser(String firstName, String lastName, String gender, String phone, String username, String password, String email, String accountType, Callback<PersonDto> callback) {
        userApi.createUser(firstName, lastName, gender, phone, username, password, email, accountType).enqueue(callback);
    }

    @Override
    public void deleteUser(Integer id, Callback<PersonDto> callback) {
        userApi.deleteUser(id).enqueue(callback);
    }

    @Override
    public void updateUser(Integer id, String firstName, String lastName, String gender, String phone, String username, String password, String email, String accountType, Callback<PersonDto> callback) {
        userApi.updateUser(id, firstName, lastName, gender, phone, username, password, email, accountType).enqueue(callback);
    }
}
