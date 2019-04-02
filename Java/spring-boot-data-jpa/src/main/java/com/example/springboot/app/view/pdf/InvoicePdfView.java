package com.example.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.utils.Constants;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

// Is the same view name that we use to show an invoice's details via HTML (invoice/details.html)
// This view is returned by the method 'details' inside 'InvoiceControllerImpl.java'. 
// This 'details' method will be used to render the details in HTML, PDF, Excel, etc...
// To know which format has to be rendered, the 'details' method will check the 'format' parameter passed to the URL
// If the 'format' parameter is missed then by default the content is rendered in HTML
@Component("invoice/details")
public class InvoicePdfView extends AbstractPdfView {

  @Override
  protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

    // Retrieving the invoice instance passed to the view from the model object
    Invoice invoice = (Invoice) model.get(Constants.ATTRIBUTE_INVOICE_KEY);
    
    // We are going to replicate the invoice details view (invoice/details.html). We replicate the 3 tables: Customer data, Invoice data and invoice's lines data
    PdfPCell cell = null; // We are going to use this cell to add customized cells to the each table
    // 1. Table Customer information -> 1 column and 3 rows
    PdfPTable tableCustomerInfo = new PdfPTable(1);
    tableCustomerInfo.setSpacingAfter(20); // Space after table
    cell = new PdfPCell(new Phrase("Customer information"));
    cell.setBackgroundColor(new Color(184, 218, 255)); // RGB format
    cell.setPadding(8F);
//    tableCustomerInfo.addCell("Customer information");
    tableCustomerInfo.addCell(cell);
    tableCustomerInfo.addCell(invoice.getClient().getName().concat(" ").concat(invoice.getClient().getSurname()));
    tableCustomerInfo.addCell(invoice.getClient().getEmail());

    // 2. Table Invoice data -> 1 column and 3 rows
    PdfPTable tableInvoiceData = new PdfPTable(1);
    tableInvoiceData.setSpacingAfter(20);
    cell = new PdfPCell(new Phrase("Invoice Data"));
    cell.setBackgroundColor(new Color(195, 230, 203)); // RGB format
    cell.setPadding(8F);
//    tableInvoiceData.addCell("Invoice Data");
    tableInvoiceData.addCell(cell);
    tableInvoiceData.addCell("Reference: " + invoice.getId());
    tableInvoiceData.addCell("Description: " + invoice.getDescription());
    tableInvoiceData.addCell("Creation date: " + invoice.getCreationDate());
    
    // 3. Table Invoice's lines data ->  4 column and YYY rows (Header + as many as invoice's lines)
    PdfPTable tableInvoiceLines = new PdfPTable(4);
    tableInvoiceLines.setWidths(new float[] {3.5F, 1, 1, 1});
    /*

    tableInvoiceLines.addCell("Product");
    tableInvoiceLines.addCell("Price");
    tableInvoiceLines.addCell("Quantity");
    tableInvoiceLines.addCell("Total");
    
    */
    cell = new PdfPCell(new Phrase("Product"));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    tableInvoiceLines.addCell(cell);
    cell = new PdfPCell(new Phrase("Price"));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    tableInvoiceLines.addCell(cell);
    cell = new PdfPCell(new Phrase("Quantity"));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    tableInvoiceLines.addCell(cell);
    cell = new PdfPCell(new Phrase("Total"));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    tableInvoiceLines.addCell(cell);
    // Invoice's lines
    /*
     
    invoice.getInvoiceLines().forEach( it -> {
      tableInvoiceLines.addCell(it.getProduct().getName());
      tableInvoiceLines.addCell(it.getProduct().getPrice().toString());
      tableInvoiceLines.addCell(it.getQuantity().toString());
      tableInvoiceLines.addCell(it.calculateAmount().toString());
    });
    
    */
    invoice.getInvoiceLines().forEach( it -> {
      tableInvoiceLines.addCell(it.getProduct().getName());
      PdfPCell otherCell = new PdfPCell(new Phrase(it.getProduct().getPrice().toString()));
      otherCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
      tableInvoiceLines.addCell(otherCell);
      otherCell = new PdfPCell(new Phrase(it.getQuantity().toString()));
      otherCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
      tableInvoiceLines.addCell(otherCell);
      otherCell = new PdfPCell(new Phrase(it.calculateAmount().toString()));
      otherCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
      tableInvoiceLines.addCell(otherCell);
    });
    
    // Invoice Total amount
    cell = new PdfPCell(new Phrase("Total: "));
    cell.setColspan(3);
    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    cell.setPaddingRight(8F);
    tableInvoiceLines.addCell(cell);
    cell = new PdfPCell(new Phrase(invoice.getTotal().toString()));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    tableInvoiceLines.addCell(cell);
//  tableInvoiceLines.addCell(invoice.getTotal().toString());

    // Adding the tables to the Document object. This object represents our PDF document
    document.add(tableCustomerInfo);
    document.add(tableInvoiceData);
    document.add(tableInvoiceLines);
    
  }

}
