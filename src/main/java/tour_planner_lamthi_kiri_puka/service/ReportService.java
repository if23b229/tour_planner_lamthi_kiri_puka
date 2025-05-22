package tour_planner_lamthi_kiri_puka.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import tour_planner_lamthi_kiri_puka.model.Tour;
import tour_planner_lamthi_kiri_puka.model.TourLog;
import tour_planner_lamthi_kiri_puka.repository.TourRepository;
import tour_planner_lamthi_kiri_puka.repository.TourLogRepository;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;

    public ReportService(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
    }

    public void generateTourReport(Long tourId, String filePath) throws IOException {
        Tour tour = tourRepository.findById(tourId).orElse(null);
        if (tour == null) {
            throw new FileNotFoundException("Tour not found with ID: " + tourId);
        }

        try (PdfWriter writer = new PdfWriter(filePath)) {
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Tour Report"));
            document.add(new Paragraph("Tour Name: " + tour.getName()));
            document.add(new Paragraph("Description: " + tour.getDescription()));
            document.add(new Paragraph("Origin: " + tour.getOrigin()));
            document.add(new Paragraph("Destination: " + tour.getDestination()));
            document.add(new Paragraph("Transport Type: " + tour.getTransportType()));

            List<TourLog> tourLogs = tourLogRepository.findByTourId(tourId);

            Table table = new Table(new float[]{1, 1, 1, 1, 1, 1});
            table.addCell(new Cell().add(new Paragraph("Date")));
            table.addCell(new Cell().add(new Paragraph("Comment")));
            table.addCell(new Cell().add(new Paragraph("Difficulty")));
            table.addCell(new Cell().add(new Paragraph("Total Distance")));
            table.addCell(new Cell().add(new Paragraph("Total Time")));
            table.addCell(new Cell().add(new Paragraph("Rating")));

            for (TourLog log : tourLogs) {
                table.addCell(new Cell().add(new Paragraph(log.getLogDate().toString())));
                table.addCell(new Cell().add(new Paragraph(log.getComment())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(log.getDifficulty()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(log.getTotalDistance()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(log.getTotalTime()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(log.getRating()))));
            }

            document.add(table);
            document.close();
        }
    }

    public void generateSummaryReport(String filePath) throws IOException {
        List<Tour> tours = tourRepository.findAll();

        try (PdfWriter writer = new PdfWriter(filePath)) {
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Summary Report"));

            Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1});
            table.addCell(new Cell().add(new Paragraph("Tour ID")));
            table.addCell(new Cell().add(new Paragraph("Name")));
            table.addCell(new Cell().add(new Paragraph("Description")));
            table.addCell(new Cell().add(new Paragraph("Origin")));
            table.addCell(new Cell().add(new Paragraph("Destination")));
            table.addCell(new Cell().add(new Paragraph("Transport Type")));
            table.addCell(new Cell().add(new Paragraph("Logs Count")));

            for (Tour tour : tours) {
                table.addCell(new Cell().add(new Paragraph(tour.getId().toString())));
                table.addCell(new Cell().add(new Paragraph(tour.getName())));
                table.addCell(new Cell().add(new Paragraph(tour.getDescription())));
                table.addCell(new Cell().add(new Paragraph(tour.getOrigin())));
                table.addCell(new Cell().add(new Paragraph(tour.getDestination())));
                table.addCell(new Cell().add(new Paragraph(tour.getTransportType())));
                List<TourLog> tourLogs = tourLogRepository.findByTourId(tour.getId());
                table.addCell(new Cell().add(new Paragraph(String.valueOf(tourLogs.size()))));
            }

            document.add(table);
            document.close();
        }
    }
}
