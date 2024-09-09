package com.springBootProject.Service;

import com.springBootProject.Model.Order;
import com.springBootProject.Repository.OrderRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Service
public class ReportService {
    @Autowired
    private OrderRepository orderRepository;



    public byte[] generateReport() throws IOException {
        List<Order> orders = orderRepository.findAll();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        writeHeaderLine(sheet);
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Order order :orders){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, order.getId(),style);
            createCell(row, columnCount++, order.getName(),style);
            createCell(row, columnCount++, order.getQty(),style);
            createCell(row, columnCount++, order.getPrice(),style);

        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        }finally {
            bos.close();
        }
        return bos.toByteArray();

    }

    private void createCell (Row row, int columnCount, Object value, CellStyle style){
        Cell cell = row.createCell(columnCount);
        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }  else if (value instanceof Double) {
            cell.setCellValue((Double) value);
            
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine(Sheet sheet){
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Order_Id");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Order_Name");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Order_Qty");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Order_Price");
    }
}
