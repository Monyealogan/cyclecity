package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ReportController {

    private final ProductRepository productRepository;
    private final PartRepository partRepository;

    public ReportController(ProductRepository productRepository, PartRepository partRepository) {
        this.productRepository = productRepository;
        this.partRepository = partRepository;
    }

    @GetMapping("/generateReport")
    public void generateReport(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=inventory_report.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("Inventory Report"));
        document.add(new Paragraph("Parts List"));

        PdfPTable partTable = new PdfPTable(3);
        partTable.addCell("Name");
        partTable.addCell("Price");
        partTable.addCell("Inventory");

        List<Part> parts = StreamSupport.stream(partRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        for (Part part : parts) {
            partTable.addCell(part.getName());
            partTable.addCell(String.valueOf(part.getPrice()));
            partTable.addCell(String.valueOf(part.getInv()));
        }
        document.add(partTable);

        document.add(new Paragraph("Products List"));
        PdfPTable productTable = new PdfPTable(3);
        productTable.addCell("Name");
        productTable.addCell("Price");
        productTable.addCell("Inventory");

        List<Product> products = StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        for (Product product : products) {
            productTable.addCell(product.getName());
            productTable.addCell(String.valueOf(product.getPrice()));
            productTable.addCell(String.valueOf(product.getInv()));
        }
        document.add(productTable);

        document.close();
    }
}
