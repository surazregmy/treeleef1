package com.example.treeleef1.PdfUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public abstract class AbstractPdf {


    protected ByteArrayInputStream prepare() throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4, 10, 10, 5, 5);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        document.open();
        write(document, pdfWriter);
        document.close();
        return new ByteArrayInputStream(outputStream.toByteArray());

    }

    protected void write(Document document, PdfWriter pdfWriter) throws DocumentException {
        document.add(new Paragraph("PDF" + LocalDateTime.now()));
    }

    public ByteArrayInputStream generate() throws DocumentException, FileNotFoundException {
        ByteArrayInputStream bis = prepare();
        return bis;
    }

}
