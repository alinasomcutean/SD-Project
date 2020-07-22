package project.sd.client.controller;

import javafx.application.Platform;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.JavaFxApplication;
import project.sd.client.dto.BookDto;
import project.sd.client.services.BookService;
import project.sd.client.services.factoryPattern.Report;
import project.sd.client.services.factoryPattern.ReportFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.List;

@FxmlView("admin.fxml")
@RestController
public class AdminController {

    @Inject
    private BookService bookService;

    private ReportFactory reportFactory = new ReportFactory();

    public void goBooks() {
        JavaFxApplication.changeScene("books");
    }

    public void goUsers() {
        JavaFxApplication.changeScene("users");
    }

    public void generatePdf() {
        bookService.findAllBooks(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                Platform.runLater(() -> {
                    Report report = reportFactory.getReport("pdf");
                    try {
                        report.generateReport(response.body());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

            }
        });
    }

    public void generateTxt() {
        bookService.findAllBooks(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                Platform.runLater(() -> {
                    Report report = reportFactory.getReport("txt");
                    try {
                        report.generateReport(response.body());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

            }
        });
    }
}
