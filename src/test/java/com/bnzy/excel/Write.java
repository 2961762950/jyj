package com.bnzy.excel;

import com.bnzy.domian.School;
import com.bnzy.domian.Student;
import com.bnzy.service.SchoolService;
import com.bnzy.service.StudentService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class Write {
    @Autowired
    private StudentService studentService;


    @Test
    public void write() throws IOException {
        //1。创建工作薄对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建工作表对象
        XSSFSheet sheet = workbook.createSheet("教育局--版程序员纳");
        //3.创建行
        XSSFRow row = sheet.createRow(0);
        //设置单元格数据
        row.createCell(0).setCellValue("学号");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("姓名");
        row.createCell(3).setCellValue("年龄");
        row.createCell(4).setCellValue("毕业学校");
        row.createCell(5).setCellValue("当前层次");

        List<Student> list = studentService.list();
        //当前操作的行
        int rows=1;
        for (Student student : list) {
            System.out.println(student.toString());
            System.out.println(rows);
            XSSFRow row1 = sheet.createRow(rows);
            //设置单元格数据
            row1.createCell(0).setCellValue(student.getId());
            row1.createCell(1).setCellValue(student.getGender());
            row1.createCell(2).setCellValue(student.getStudentName());
            row1.createCell(3).setCellValue(student.getAge());
            row1.createCell(4).setCellValue(student.getGraduateSchool());
            row1.createCell(5).setCellValue(student.getLevel());

            rows+=1;
        }



        FileOutputStream fos = new FileOutputStream("E:\\youxixiaz\\游戏\\资源发布1\\student.xlsx");
        workbook.write(fos);


        fos.close();
        workbook.close();
    }
}
