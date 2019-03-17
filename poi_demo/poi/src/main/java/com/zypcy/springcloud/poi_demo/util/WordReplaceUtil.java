package com.zypcy.springcloud.poi_demo.util;

import cn.hutool.core.date.DateUtil;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * 替换word文档内容
 */
public class WordReplaceUtil {

    public static void main(String[] args) throws Exception, IOException {
        testReplaceAndSaveDoc();
    }

    //测试替换word内容
    private static void testReplaceAndSaveDoc(){
        //替换 [sum] 标签 与 习近平 字样
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("[sum]", "长沙");
        map.put("习近平", "朱宇");
        String wordId = DateUtil.format(new Date(),"yyyyMMddHHmmssSSS");
        String sourcePath = CommonUtil.getRootPath() + "\\src\\main\\resources\\static\\poi_template\\replace_word_template.docx";
        String targetPath = CommonUtil.getRootPath() + "\\src\\main\\resources\\static\\poi_template\\" + wordId  + ".docx";
        try {
            replaceAndSaveDoc(sourcePath, map , targetPath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取word模板替换变量，并根据目标路径生成新的word文档
     * @param fis        模版文件流
     * @param param       要替换的键值对
     * @param targetPath 生成文档目标路径
     */
    public static void replaceAndSaveDoc(InputStream fis, Map<String, Object> param , String targetPath) throws FileNotFoundException{
        if(fis == null){
            throw new RuntimeException("请传入源文件");
        }
        XWPFDocument doc = replaceDoc(fis, param);
        outPutWord(doc , targetPath);
    }

    /**
     * 读取word模板替换变量，并根据目标路径生成新的word文档
     * @param sourcePath  文档源路径
     * @param param       要替换的键值对
     * @param targetPath 生成文档目标路径
     */
    public static void replaceAndSaveDoc(String sourcePath, Map<String, Object> param , String targetPath) throws FileNotFoundException{
        // 读取word模板
        File f = new File(sourcePath);
        if(!f.exists()){
            throw new RuntimeException("未读取到源文件");
        }
        InputStream fis = new FileInputStream(f);
        XWPFDocument doc = replaceDoc(fis, param);
        outPutWord(doc , targetPath);
    }

    /**
     * 读取word模板并替换变量
     * @param fis   模版文件流
     * @param param 要替换的键值对
     * @return
     */
    private static XWPFDocument replaceDoc(InputStream fis, Map<String, Object> param) {
        try {
            //InputStream fis = new FileInputStream(f);
            //InputStream fis = new FileInputStream(filePath);
            XWPFDocument doc = new XWPFDocument(fis);
            //处理段落
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            processParagraph(paragraphList,doc,param);
            //处理表格
            Iterator<XWPFTable> it = doc.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                        processParagraph(paragraphListTable, doc, param);
                    }
                }
            }
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 替换文字
     * @param paragraphList
     * @param doc
     * @param param
     */
    private static void processParagraph(List<XWPFParagraph> paragraphList, XWPFDocument doc,Map<String, Object> param){
        if(paragraphList != null && paragraphList.size() > 0){
            for(XWPFParagraph paragraph:paragraphList){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if(text != null){
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if(text.indexOf(key) != -1){
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                    //System.out.println(text);//放开注释可打印读取到的word内容
                                }
                                else{
                                    text = text.replace(key, "");
                                }
                            }
                        }
                        if(isSetText){
                            run.setText(text,0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 输出word到目标路径
     * @param doc 文档对象
     * @param targetPath 目标路径
     * @throws FileNotFoundException
     */
    private static void outPutWord(XWPFDocument doc , String targetPath) throws FileNotFoundException{
        try {
            if (doc != null) {
                OutputStream os = new FileOutputStream(targetPath);
                doc.write(os);
                os.close();
                System.out.println("已替换word文件，文件地址：" + targetPath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
