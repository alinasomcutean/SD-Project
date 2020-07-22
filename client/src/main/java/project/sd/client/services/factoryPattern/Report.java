package project.sd.client.services.factoryPattern;

import project.sd.client.dto.BookDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface Report {

    void generateReport(List<BookDto> books) throws FileNotFoundException;
}
