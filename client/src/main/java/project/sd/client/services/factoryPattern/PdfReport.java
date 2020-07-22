package project.sd.client.services.factoryPattern;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.DirectoryChooser;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.BorrowDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PdfReport implements Report {

    @Override
    public void generateReport(List<BookDto> books) throws FileNotFoundException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(null);

        if (selectedDir != null) {
            try {
                // Create a new document
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedDir.getAbsolutePath() + "Report.pdf"));
                document.open();
                reportContent(document, books);
                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void reportContent(Document document, List<BookDto> books) throws DocumentException {
        document.add(new Paragraph("Report with the current books and their history"));
        Paragraph paragraph = new Paragraph();
        addEmptyLineToReport(paragraph, 1);
        document.add(paragraph);

        // Create table with books
        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("Title"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Author"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Borrow Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Due Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Current status"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        for (BookDto b : books) {
            for (BorrowDto borrow : b.getBorrows()) {
                table.addCell(b.getTitle());
                table.addCell(b.getAuthor());
                table.addCell(borrow.getBorrowedDate());
                table.addCell(borrow.getDueDate());
                table.addCell(b.getStatus());
            }
        }

        document.add(table);
    }

    private static void addEmptyLineToReport(Paragraph paragraph, int nr) {
        for (int i = 0; i < nr; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
