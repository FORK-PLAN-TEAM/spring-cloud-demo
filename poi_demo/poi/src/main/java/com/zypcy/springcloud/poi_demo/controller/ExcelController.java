package com.zypcy.springcloud.poi_demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zypcy.springcloud.poi_demo.entity.Student;
import com.zypcy.springcloud.poi_demo.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("excel")
public class ExcelController {

    @RequestMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        String[] title = {"序号", "姓名", "年龄", "生日"};
        List<Student> list = Student.getStudents();
        //2.通过响应式流，生成list数组
        List<String[]> excelDatas = list.stream().map(student -> new String[]{
                StrUtil.toString(student.getId()),
                student.getName(),
                StrUtil.toString(student.getAge()),
                student.getBrith()
        }).collect(Collectors.toList());
        //生成exel内容，二维String数组
        String[][] values = excelDatas.toArray(new String[0][title.length]);
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook("学生表一", title, values);
        String wordId = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        ExcelUtil.setResponseStream(response, "学生表" + wordId + ".xlsx");
        wb.write(response.getOutputStream());
    }
}
