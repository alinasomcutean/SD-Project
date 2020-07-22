package project.sd.client.services.factoryPattern;

public class ReportFactory {

    public Report getReport(String reportType) {
        if ("txt".equals(reportType)) {
            return new TxtReport();
        }
        return new PdfReport();
    }
}
