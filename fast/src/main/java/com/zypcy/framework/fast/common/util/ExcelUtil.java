package com.zypcy.framework.fast.common.util;

import com.zypcy.framework.fast.common.error.BusinessException;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * excel导出帮助类
 * 1、用HSSFWorkbook打开或者创建“Excel文件对象”
 * 2、用HSSFWorkbook对象返回或者创建Sheet对象
 * 3、用Sheet对象返回行对象，用行对象得到Cell对象
 * 4、对Cell对象读写。
 * 5、将生成的HSSFWorkbook放入HttpServletResponse中响应到前端页面
 * 使用方法:
     List<Cashbook> list = getDatalist();
     String[] title = {"记录时间", "入账内容", "账目金额", "备注"};
     List<String[]> exportDataList = list.parallelStream().map(item -> new String[]{
       DateUtil.format(item.getRecordTime() , "yyyy-MM-dd"),
       item.getCashDetail(),
       item.getAmount().toString(),
       item.getRemark()
     }).collect(Collectors.toList());
     HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook("账目信息", title, exportDataList.toArray(new String[0][title.length]), null);
     workbook.write(response.getOutputStream());
     ExcelUtil.setResponseStream(response, "账目信息" +IdWorker.getDateId()+ ".xls");
 */
public class ExcelUtil {
    //存储当前导入的 HSSFWorkbook 对象
    public static final ThreadLocal<HSSFWorkbook> IMPORT_WB_HOLDER = ThreadLocal.withInitial(() -> new HSSFWorkbook());
    //存储导入模板的列标题
    public static final ThreadLocal<String[]> IMPORT_CELL_TITLES_HOLDER = ThreadLocal.withInitial(() -> new String[0]);
    //存储当前导入的模板中列的数量
    public static final ThreadLocal<Integer> IMPORT_CELL_NUM_HOLDER = ThreadLocal.withInitial(() -> 0);

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##########");
    private static final SimpleDateFormat NYR_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容 , 内容数组与标题的列一一对应
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<String[]> values, HSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
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
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            String[] cols = values.get(i);
            for (int j = 0; j < cols.length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(cols[j]);
            }
        }
        return wb;
    }


    //下载，发送响应流方法
    public static void setResponseStream(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            new BusinessException("导出失败，原因：" + e.getMessage());
        }
    }

    /**
     * 验证模板有效性,  不要手动捕获抛出的异常
     *
     * @param importInputStream 导入文件的输入流
     * @param templateName      模板文件的名称  请确保 文件存在 resources/template 目录下
     */
    public static void verificationTemplate(InputStream importInputStream, String templateName) {
        if (importInputStream == null) {
            throw new BusinessException("导入文件输入流异常");
        }
        //加载定义的模板
        InputStream tempInputStream = getTemplate(templateName);
        if (tempInputStream == null) {
            throw new BusinessException(String.format("模板文件%s不存在", templateName));
        }

        HSSFWorkbook importWB;
        try {
            importWB = new HSSFWorkbook(importInputStream);
            HSSFWorkbook tempWB = new HSSFWorkbook(tempInputStream);

            doVerification(importWB, tempWB);
        } catch (Exception e) {
            throw new BusinessException("模板文件不一致，请按照模板修改导入文件内容");
        }
    }

    /**
     * 获取指定模板文件流
     *
     * @param templateName
     * @return
     */
    public static InputStream getTemplate(String templateName) {
        return getTemplate("/template/", templateName);
    }

    public static InputStream getTemplate(String path, String templateName) {
        return ExcelUtil.class.getResourceAsStream((path.endsWith("/") ? path : path + "/") + templateName + ".xls");
    }

    private static void doVerification(HSSFWorkbook importWB, HSSFWorkbook tempWB) {
        HSSFSheet importSheet = importWB.getSheetAt(0);
        HSSFSheet tempSheet = tempWB.getSheetAt(0);

        HSSFRow importRow0 = importSheet.getRow(0);
        HSSFRow tempRow0 = tempSheet.getRow(0);

        int importCellNum = importRow0.getPhysicalNumberOfCells();
        int tempCellsNum = tempRow0.getPhysicalNumberOfCells();
        if (importCellNum < tempCellsNum) {
            throw new RuntimeException(); //模板不一致
        }
        String[] cellTitles = new String[tempCellsNum];
        for (int i = 0; i < tempCellsNum; i++) {
            String tempCellTitle = tempRow0.getCell(i).getStringCellValue();
            if(!tempCellTitle.equals(importRow0.getCell(i).getStringCellValue())){
                throw new RuntimeException(); //模板不一致
            }
            cellTitles[i] = tempCellTitle;
        }

        IMPORT_WB_HOLDER.set(importWB);
        IMPORT_CELL_TITLES_HOLDER.set(cellTitles);
        IMPORT_CELL_NUM_HOLDER.set(tempCellsNum);
    }


    public static List<String[]> buildListOfSheet(HSSFSheet sheet) {
        return buildListOfSheet(sheet, 1, 0);
    }

    /**
     * 从sheet中将数据构建成集合
     *
     * @param sheet
     * @param startRow
     * @param startCol
     * @return
     */
    public static List<String[]> buildListOfSheet(HSSFSheet sheet, int startRow, int startCol) {
        if (sheet == null) {
            return Collections.emptyList();
        }
        List<String[]> rst = new ArrayList<>();
        for (int rowIdx = startRow, maxRow = sheet.getLastRowNum(); rowIdx <= maxRow; rowIdx++) {
            HSSFRow row = sheet.getRow(rowIdx);
            int endCol = IMPORT_CELL_NUM_HOLDER.get(); //row.getLastCellNum();
            String[] rowStr = new String[endCol - startCol + 2];
            int idx = 0;
            rowStr[idx] = Integer.toString(rowIdx);
            for (int colIdx = startCol; colIdx <= endCol; colIdx++) {
                idx++;
                String value = ExcelUtil.getCellValueAsString(row.getCell(colIdx));
                if (!StringUtils.isEmpty(value)) {
                    value = value.trim();
                }
                rowStr[idx] = value;
            }
            rst.add(rowStr);
        }
        return rst;
    }

    /**
     * 获取单元格值的字符串形式
     *
     * @param cell
     * @return
     */
    private static String getCellValueAsString(HSSFCell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    String str = NYR_DATE_FORMAT.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    return str;
                } else {
                    double num = cell.getNumericCellValue();
                    return DECIMAL_FORMAT.format(num);
                }
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }

    }
}
