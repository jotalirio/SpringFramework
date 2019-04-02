package com.example.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// poi.ss is for xlsx Excel extensions
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.models.entity.InvoiceLine;
import com.example.springboot.app.utils.Constants;

// We are differencing this component against the PDF component by adding the .xlsx extension to the component name
@Component("invoice/details.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    Invoice invoice = (Invoice) model.get(Constants.ATTRIBUTE_INVOICE_KEY);
    
    // Creating our Excel sheet from the Workbook object
    Sheet sheet = workbook.createSheet("Invoice Spring");
    
    // Customer information
    Row row = sheet.createRow(0);  // Row 0
    Cell cell = row.createCell(0); // Column 0
    cell.setCellValue("Customer information");
    row = sheet.createRow(1); // Row 1
    cell = row.createCell(0); // Column 0
    cell.setCellValue(invoice.getClient().getName().concat(" ").concat(invoice.getClient().getSurname()));
    row = sheet.createRow(2); // Row 2
    cell = row.createCell(0); // Column 0
    cell.setCellValue(invoice.getClient().getEmail());
    
    // Invoice data. Short way to do the same
    sheet.createRow(4).createCell(0).setCellValue("Invoice data");
    sheet.createRow(5).createCell(0).setCellValue("Reference: " + invoice.getId());
    sheet.createRow(6).createCell(0).setCellValue("Description: " + invoice.getDescription());
    sheet.createRow(7).createCell(0).setCellValue("Creation date: " + invoice.getCreationDate());
    
    // Invoice's lines
    Row header = sheet.createRow(9);
    header.createCell(0).setCellValue("Product");
    header.createCell(1).setCellValue("Price");
    header.createCell(2).setCellValue("Quantity");
    header.createCell(3).setCellValue("Total");
    int invoiceLineRowNumber = 10;
    for (InvoiceLine invoiceLine : invoice.getInvoiceLines()) {
      Row invoiceLineRow = sheet.createRow(invoiceLineRowNumber++);
      invoiceLineRow.createCell(0).setCellValue(invoiceLine.getProduct().getName());
      invoiceLineRow.createCell(1).setCellValue(invoiceLine.getProduct().getPrice());
      invoiceLineRow.createCell(2).setCellValue(invoiceLine.getQuantity().toString());
      invoiceLineRow.createCell(3).setCellValue(invoiceLine.calculateAmount());
    }
    Row invoiceTotalRow = sheet.createRow(invoiceLineRowNumber++);
    invoiceTotalRow.createCell(2).setCellValue("Total: ");
    invoiceTotalRow.createCell(3).setCellValue(invoice.getTotal());

  }

}
