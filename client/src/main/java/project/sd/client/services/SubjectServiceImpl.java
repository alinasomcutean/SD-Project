package project.sd.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.client.api.BookApi;
import project.sd.client.dto.SubjectDto;
import project.sd.client.util.SingletonRetrofit;
import retrofit2.Callback;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService{

    private BookApi bookApi = SingletonRetrofit.getInstance().getRetrofit().create(BookApi.class);

    @Override
    public void findAllSubjects(Callback<List<SubjectDto>> callback) {
        bookApi.getSubjects().enqueue(callback);
    }
}
