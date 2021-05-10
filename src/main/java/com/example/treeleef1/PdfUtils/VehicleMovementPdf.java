package com.example.treeleef1.PdfUtils;

import com.example.treeleef1.model.Camera;
import com.example.treeleef1.model.Location;
import com.example.treeleef1.model.Vehicle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class VehicleMovementPdf extends AbstractPdf {
    private Vehicle vehicle;

    public VehicleMovementPdf(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    protected void write(Document document, PdfWriter pdfWriter) throws DocumentException {
        PdfPTable table = new PdfPTable(vehicle.getLocations().size());
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);

        //define font for table header
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);
        //define font for table body
        Font bodyfont = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);

        //Define body cell
        PdfPCell bodycell = new PdfPCell();
        bodycell.setBackgroundColor(BaseColor.WHITE);
        bodycell.setPadding(5);

        //Write table header
        cell.setPhrase(new Phrase("Vehicle Number", font));
        table.addCell(cell);

        bodycell.setPhrase(new Phrase(vehicle.getNumber(), bodyfont));
        table.addCell(bodycell);

        for (Location location : vehicle.getLocations()) {
            cell.setPhrase(new Phrase("Location", font));
            table.addCell(cell);
            bodycell.setPhrase(new Phrase(location.getName(), bodyfont));
            table.addCell(bodycell);
            cell.setPhrase(new Phrase("Camera Details", font));
            table.addCell(cell);
            for (Camera camera : location.getCameras()) {
                bodycell.setPhrase(new Phrase(camera.getName(), bodyfont));
                table.addCell(bodycell);
            }

        }
        document.add(table);
    }
}
