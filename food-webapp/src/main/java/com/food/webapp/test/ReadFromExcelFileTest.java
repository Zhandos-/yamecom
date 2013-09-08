package com.food.webapp.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import jxl.Workbook;
import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author daniyar.artykov
 */
public class ReadFromExcelFileTest {

    public static void main(String[] args) {
        File file = new File("src/main/resources/excel/file.xlsx");

        try {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            System.out.println("<<<<<<<<<< xlsx >>>>>>>>>>");
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                        Cell cell = row.getCell(k);
                        if (cell.getCellType() == 0) {
                            System.out.println((long) cell.getNumericCellValue());
                        } else if (cell.getCellType() == 1) {
                            System.out.println(cell.getStringCellValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (e instanceof InvalidFormatException || e instanceof POIXMLException) {
                try {
                    Workbook w = Workbook.getWorkbook(file);
                    System.out.println("<<<<<<<<<< xls >>>>>>>>>>");
                    jxl.Sheet[] sheets = w.getSheets();
                    System.out.println(">> Sheets quantity = " + sheets.length);
                    for (jxl.Sheet sheet : sheets) {
                        System.out.println("Sheets name = " + sheet.getName());
                        for (int j = 0; j < sheet.getRows(); j++) {
                            for (int i = 0; i < sheet.getColumns(); i++) {
                                jxl.Cell cell = sheet.getCell(i, j);
                                System.out.println(cell.getContents());
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }
    }
}