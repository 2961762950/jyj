package com.bnzy.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class read {
    public static void main(String[] args) throws IOException {
        //1.获取工作薄
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("E:\\youxixiaz\\游戏\\资源发布1\\hws.xlsx");
        //2.获取工作表对象
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        //3.获取行
//        for (Row cells : sheetAt) {
//            //4.获取单元格
//            for (Cell cell : cells) {
//                //5.获取单元格内容
//                cell.getStringCellValue();
//                System.out.println(cell);
//            }
//        }
//---------------------------分界线---上使用增强for循环-----下使用普通循环
        //获取sheet的最后一个索引
        int lastRowNum = sheetAt.getLastRowNum();
//        读取行
        for (int i = 0; i < lastRowNum; i++) {
            XSSFRow row = sheetAt.getRow(i);
            //行不为空
            if (row != null) {
                //获取行的最后一个索引
                int rowLastCellNum = row.getLastCellNum();
//                获取单元格
                for (int j = 0; j < rowLastCellNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    //单元格是否为空
                    if (cell != null) {
//                        读取单元格的内容
                        String s = cell.getStringCellValue();
                        System.out.println(s);
                    }
                }

            }
        }

        xssfWorkbook.close();
    }
}
