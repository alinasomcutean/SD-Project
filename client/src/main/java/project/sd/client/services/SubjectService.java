package project.sd.client.services;

import project.sd.client.dto.SubjectDto;
import retrofit2.Callback;
import java.util.List;

public interface SubjectService {

    void findAllSubjects(Callback<List<SubjectDto>> callback);
}
