package com.damon.read;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since
 */
public class ReadExcel {


    public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {

        Workbook wb = WorkbookFactory.create(new File("C:\\Users\\Administrator\\Desktop\\公司文件\\test.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        int rowNum = sheet.getLastRowNum();
        System.out.println("总行数：" + rowNum);

        for (Row row : sheet) {
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                //单元格名称
                System.out.print("cellRef.formatAsString " + cellRef.formatAsString());
                System.out.print(" - ");

                //通过获取单元格值并应用任何数据格式（Date，0.00，1.23e9，$ 1.23等），获取单元格中显示的文本
                String text = formatter.formatCellValue(cell);
                System.out.println("text" + text);

                //获取值并自己格式化
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:// 字符串型
                        System.out.println(cell.getRichStringCellValue().getString());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:// 数值型
                        if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则 ，获取该cell的date值
                            System.out.println(cell.getDateCellValue());
                        } else {// 纯数字
                            System.out.println(cell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:// 布尔
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:// 公式型
                        System.out.println(cell.getCellFormula());
                        break;
                    case Cell.CELL_TYPE_BLANK:// 空值
                        System.out.println();
                        break;
                    case Cell.CELL_TYPE_ERROR: // 故障
                        System.out.println();
                        break;
                    default:
                        System.out.println();
                }
            }
        }

    }

    public Workbook readExcel(String filepath) {
        if (filepath == null) {
            return null;
        }
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Workbook wb = null;
        try {
            InputStream is = new FileInputStream(filepath);
            if (".xls".equals(ext)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(ext)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

}
