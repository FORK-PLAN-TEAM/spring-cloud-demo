package com.zypcy.springcloud.poi_demo.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zypcy.springcloud.poi_demo.entity.Student;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel导出帮助类
 * 1、用HSSFWorkbook打开或者创建“Excel文件对象”
 * 2、用HSSFWorkbook对象返回或者创建Sheet对象
 * 3、用Sheet对象返回行对象，用行对象得到Cell对象
 * 4、对Cell对象读写。
 * 5、将生成的HSSFWorkbook放入HttpServletResponse中响应到前端页面
 */
public class ExcelUtil {

    public static void main(String[] args)throws Exception{
        testExportExcel();
    }
    //测试导出excel
    public static void testExportExcel(){
        try {
            String[] title = {"序号" , "姓名" ,"年龄" ,"生日"};
            List<Student> list = Student.getStudents();
            //1.通过forEach循环，生成list数组
            List<String[]> excelDatas = new ArrayList<>();
            list.forEach( student -> {
                String[] content = new String[4];
                content[0] = StrUtil.toString(student.getId());
                content[1] = student.getName();
                content[2] = StrUtil.toString(student.getAge());
                content[3] = student.getBrith();
                excelDatas.add(content);
            });
            //生成exel内容，二维String数组
            String[][] values = excelDatas.toArray(new String[0][title.length]);
            HSSFWorkbook wb = getHSSFWorkbook("学生表一",title , values);
            String targentPath = CommonUtil.getRootPath() + "\\src\\main\\resources\\static\\poi_template\\" + DateUtil.format(new Date(),"yyyyMMddHHmmssSSS")  + ".xlsx";
            FileOutputStream fout = new FileOutputStream(targentPath);
            wb.write(fout);
            fout.close();
            System.out.println("导出excel成功，文件地址：" + targentPath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                cell = row.createCell(j);
                cell.setCellValue(values[i][j]);
                cell.setCellStyle(style);
            }
        }
        return wb;
    }

    /**
     * 发送响应流方法
     * @param response
     * @param fileName
     */
    public static void setResponseStream(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            new RuntimeException("导出失败，原因：" + e.getMessage());
        }
    }
}
