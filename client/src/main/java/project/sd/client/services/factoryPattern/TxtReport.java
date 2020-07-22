package project.sd.client.services.factoryPattern;

import javafx.stage.DirectoryChooser;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.BorrowDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TxtReport implements Report {
    @Override
    public void generateReport(List<BookDto> books) throws FileNotFoundException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(null);
        if(selectedDir != null) {
            try {
                FileWriter file = new FileWriter(new File(selectedDir.getAbsolutePath(), "Report.txt"));
                reportContent(file, books);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void reportContent(FileWriter file, List<BookDto> books) throws IOException{
        file.write("Report with the current books and their history\n");
        file.write(String.format("%n %-60s %-40s %-40s %-40s %-40s %n", "Title", "Author", "Borrow Date", "Due Date", "Current Status"));

        for (BookDto b : books) {
            for (BorrowDto borrow : b.getBorrows()) {
                file.write(String.format("%-60s %-40s %-40s %-40s %-40s %n",
                        b.getTitle(),
                        b.getAuthor(),
                        borrow.getBorrowedDate(),
                        borrow.getDueDate(),
                        b.getStatus()));
            }
        }
    }
}
