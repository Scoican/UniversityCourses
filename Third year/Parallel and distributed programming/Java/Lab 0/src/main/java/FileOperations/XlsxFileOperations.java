package FileOperations;

import Utils.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class XlsxFileOperations implements FileOperations {
    @Override
    public void fileGenerator(String fileName, Integer size, Integer min, Integer max) {
        Workbook workbook = new XSSFWorkbook();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Numbers");

        StringBuilder generatedNumber;
        // Create cells
        for (int i = 0; i < size; i++) {
            Row headerRow = sheet.createRow(i);
            Cell cell = headerRow.createCell(0);
            generatedNumber = Utils.generateNumber(ThreadLocalRandom.current().nextInt(min, max + 1));
            cell.setCellValue(generatedNumber.toString());
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(fileName + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean fileComparator(String fileName1, String fileName2) {
        try {
            FileInputStream file1 = new FileInputStream(fileName1 + ".xlsx");
            FileInputStream file2 = new FileInputStream(fileName2 + ".xlsx");

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook1 = new XSSFWorkbook(file1);
            XSSFWorkbook workbook2 = new XSSFWorkbook(file2);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook1.getSheetAt(0);
            XSSFSheet sheet2 = workbook2.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator1 = sheet1.iterator();
            Iterator<Row> rowIterator2 = sheet2.iterator();
            while (rowIterator1.hasNext()) {
                Row row1 = rowIterator1.next();
                Iterator<Cell> cellIterator1 = row1.cellIterator();

                Row row2 = rowIterator2.next();
                Iterator<Cell> cellIterator2 = row2.cellIterator();

                Cell cell1 = cellIterator1.next();
                Cell cell2 = cellIterator2.next();

                if (!cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                    return false;
                }
            }
            file1.close();
            file2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
