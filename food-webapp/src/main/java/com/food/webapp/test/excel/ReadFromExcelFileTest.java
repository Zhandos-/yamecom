package com.food.webapp.test.excel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author daniyar.artykov
 */
public class ReadFromExcelFileTest {

    public static void main(String[] args) {
        File file = new File("src/main/resources/excel/file.xls");
        Workbook w;
        try {
            w = Workbook.getWorkbook(file);

            Sheet[] sheets = w.getSheets();
            System.out.println(">> Sheets quantity = " + sheets.length);
            for (Sheet sheet : sheets) {
                System.out.println("Sheets name = " + sheet.getName());
                for (int j = 0; j < sheet.getRows(); j++) {
                    for (int i = 0; i < sheet.getColumns(); i++) {
                        Cell cell = sheet.getCell(i, j);
                        System.out.println("" + cell.getContents());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
