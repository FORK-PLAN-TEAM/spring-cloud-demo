package com.zypcy.springcloud.poi_demo.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zypcy.springcloud.poi_demo.entity.WordImg;
import com.zypcy.springcloud.poi_demo.entity.WordTable;
import com.zypcy.springcloud.poi_demo.entity.WordText;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导出word文档
 */
public class WordExportUtil {

    public static void main(String[] args) throws Exception, IOException {
        testExportDoc();
    }

    //测试组合list，生成word时，会根据list添加各种类型内容的顺序生成到word中
    private static void testExportDoc(){
        String content ="    额尔古纳河在1689年的《中俄尼布楚条约》中成为中国和俄罗斯的界河，额尔古纳河上游称海拉尔河，源于大兴安岭西侧，西流至阿该巴图山脚， 折而北行始称额尔古纳河。折而北行始称额尔古纳河。额尔古纳河在黑龙江省漠河县以西的内蒙古自治区额尔古纳右旗的恩和哈达附近与流经俄罗斯境内的石勒额尔古纳河在黑龙江省漠河县以西的内蒙古自治区额尔古纳右旗的恩和哈达附近与流经俄罗斯境内的石勒喀河汇合后始称黑龙江。沿额尔古纳河沿岸地区土地肥沃，森林茂密，水草丰美， 鱼类品种很多，动植物资源丰富，宜农宜木，是人类理想的天堂。";
        ParagraphAlignment align = ParagraphAlignment.CENTER;
        String fontFamily = "仿宋";
        int fontSize = 13;
        String wordId = DateUtil.format(new Date(),"yyyyMMddHHmmssSSS") ;
        String targetPath = CommonUtil.getRootPath() + "\\src\\main\\resources\\static\\poi_template\\" + wordId + ".docx";
        try {
            long startTime = System.currentTimeMillis();

            ArrayList<Object> list = new ArrayList<>();

            WordText textParam = new WordText(content , align , fontFamily , fontSize);
            list.add(textParam);

            WordTable tableParam  = new WordTable();
            tableParam.setRowHeight(400);
            String[] header = {"境内河流","境外河流","合计"};
            tableParam.setHeader(header);
            List<String[]> values = new ArrayList<>();
            String[] v1 = {"1","2","3"};
            String[] v2 = {"4","5","6"};
            String[] v3 = {"7","8","9"};
            values.add(v1);
            values.add(v2);
            values.add(v3);
            tableParam.setValues(values);
            list.add(tableParam);

            WordText textParam2 = new WordText("123 "+ content , align , fontFamily , fontSize);
            list.add(textParam2);

            WordImg imgParam = new WordImg();
            String[] imgs = {
                    "http://47.93.9.148:8881/static/newImgs/swiper-img.png",
                    "http://175.6.46.184:9007/api/hget/8626d206-8c84-4de7-848c-a7b1058b6725.jpg",
                    "http://www.xfc.gov.cn/up_files/image/2019-3-4/20190304221838.jpg"};
            imgParam.setImgs(imgs);
            list.add(imgParam);

            exportDoc(list, targetPath);

            System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 检查路径
     * @param path
     * @throws Exception
     */
    private static void checkPath(String path){
        if(!(path.contains(".doc") || path.contains(".docx"))){
            throw new RuntimeException("生成word目标路径需包含.doc、.docx");
        }
        //检查文件目录，不存在则创建
        String mkPath = path.substring(0, path.lastIndexOf("\\"));
        File file = new File(mkPath);
        if(!file.exists()){
            file.mkdirs();
        }
    }


    /**
     * 导出 word （包含文本、图片、表格几种类型，需自己组合list）
     * 生成word时，会根据list添加各种类型内容的顺序生成到word中
     * @param list 内容list = [WordText , WordImg , WordTable]
     * @param targetPath 生成文档目标路径
     * @throws Exception
     */
    public static void exportDoc(ArrayList<Object> list , String targetPath) throws Exception {
        //检测路径
        checkPath(targetPath);
        //组装word
        XWPFDocument doc = generatorDocument(list);
        //生成word
        outPutWord(doc , targetPath);
    }

    /**
     * 导出文本类型 word
     * @param textParam
     * @param targetPath
     */
    public static void exportTextDoc(WordText textParam , String targetPath){
        //检测路径
        checkPath(targetPath);
        //组装word
        XWPFDocument doc = new XWPFDocument();
        appendWordText(doc , textParam);
        //生成word
        outPutWord(doc , targetPath);
    }

    /**
     * 导出图片类型 word
     * 图片导出请使用 WordXWPFDocument
     * @param imgParam
     * @param targetPath
     */
    public static void exportImgDoc(WordImg imgParam , String targetPath){
        //检测路径
        checkPath(targetPath);
        //组装word
        WordXWPFDocument doc = new WordXWPFDocument();
        appendWordImg(doc , imgParam);
        //生成word
        outPutWord(doc , targetPath);
    }

    /**
     * 导出表格类型 word
     * @param tableParam
     * @param targetPath
     */
    public static void exportTableDoc(WordTable tableParam , String targetPath){
        //检测路径
        checkPath(targetPath);
        //组装word
        XWPFDocument doc = new XWPFDocument();
        appendWordTable(doc , tableParam);
        //生成word
        outPutWord(doc , targetPath);
    }

    /**
     * 组装word
     * @param list
     * @return
     */
    private static XWPFDocument generatorDocument(ArrayList<Object> list){
        WordXWPFDocument doc = new WordXWPFDocument();
        list.forEach( item -> {
            if(item instanceof WordText){
                appendWordText(doc , (WordText)item);
            }else if(item instanceof WordImg){
                appendWordImg(doc , (WordImg)item);
            }else if(item instanceof WordTable){
                appendWordTable(doc , (WordTable)item);
            }
        });
        return doc;
    }

    /**
     * 向word中追加文本内容
     * @param doc XWPFDocument
     * @param textParam 文本数据模型
     */
    private static void appendWordText(XWPFDocument doc , WordText textParam){
        //添加文本
        if(textParam != null && !StrUtil.isEmpty(textParam.getContent())){
            XWPFParagraph para = doc.createParagraph();
            para.setAlignment(textParam.getAlign());//设置左对齐
            XWPFRun run = para.createRun();
            run.setFontFamily(textParam.getFontFamily());
            run.setFontSize(textParam.getFontSize());
            run.setText(textParam.getContent());
            doc.createParagraph();
        }
    }

    /**
     * 向word中追加图标内容
     * @param doc XWPFDocument
     * @param imgParam 图片数据模型
     */
    private static void appendWordImg(WordXWPFDocument doc , WordImg imgParam){
        //添加图片
        if(imgParam != null && imgParam.getImgs() != null && imgParam.getImgs().length > 0){
            XWPFParagraph para;
            XWPFRun run;
            String[] imgs = imgParam.getImgs();
            try {
                for(int i=0;i<imgs.length;i++){
                    para = doc.createParagraph();
                    para.setAlignment(imgParam.getAlign());//设置对齐方式
                    run = para.createRun();
                    InputStream input = getImgInputStream(imgs[i]);
                    //InputStream input = new FileInputStream(imgs[i]);
                    String filename = imgs[i].indexOf("\\") > 0 ? imgs[i].substring(imgs[i].lastIndexOf("\\") + 1) : imgs[i].substring(imgs[i].lastIndexOf("/") + 1);
                    doc.addPictureData(input, CommonUtil.getDocumentImgType(imgs[i]));
                    doc.createPicture(doc.getAllPictures().size() - 1 , imgParam.getWidth() , imgParam.getHeight() , para);
                    //run.addPicture(input, getDocumentImgType(imgs[i]), filename, Units.toEMU(imgParam.getWidth()), Units.toEMU(imgParam.getHeight()));
                    input.close();
                    //图片下面创建一行文字，表示图片的名称
                    para = doc.createParagraph();
                    para.setAlignment(ParagraphAlignment.CENTER);//设置对齐方式
                    run = para.createRun();
                    run.setFontFamily(imgParam.getFontFamily());
                    run.setFontSize(11);
                    run.setText(filename);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            doc.createParagraph();
        }
    }

    /**
     * 向word中追加表格内容
     * @param doc XWPFDocument
     * @param tableParam 表格数据模型
     */
    private static void appendWordTable(XWPFDocument doc, WordTable tableParam){
        //添加表格
        if(tableParam != null && tableParam.getHeader() != null && tableParam.getValues() != null){
            XWPFParagraph para;
            XWPFRun run;
            XWPFTable table  = doc.createTable(tableParam.getRows(),tableParam.getCols());
            table.setCellMargins(tableParam.getTop(), tableParam.getLeft(), tableParam.getBottom(), tableParam.getRight());
            //table.addNewCol();//添加新列
            //table.createRow();//添加新行
            XWPFTableRow row;
            XWPFTableCell cell;
            CTTcPr cellPr;
            //循环行
            for(int j = 0; j < tableParam.getRows(); j++){
                row = table.getRow(j);
                row.setHeight(tableParam.getRowHeight());
                //循环列
                for(int i=0;i< tableParam.getCols();i++){
                    cell = row.getCell(i);
                    cellPr = cell.getCTTc().addNewTcPr();
                    cellPr.addNewTcW().setW(BigInteger.valueOf(tableParam.getColWidth()));
                    para = cell.getParagraphs().get(0);
                    para.setAlignment(tableParam.getAlign());
                    run = para.createRun();
                    run.setFontFamily(tableParam.getFontFamily());
                    run.setFontSize(tableParam.getFontSize());
                    if(j==0){//第一行输出标题
                        run.setBold(true);
                        run.setText(tableParam.getHeader()[i]);
                    }
                    else{//输出表格内容
                        run.setText(String.valueOf(tableParam.getValues().get(j -1)[i]));
                    }
                }
            }
            doc.createParagraph();
        }
    }

    /**
     * 输出成word
     * @param doc XWPFDocument
     * @param targetPath 生成文件目标地址
     */
    private static void outPutWord(XWPFDocument doc , String targetPath){
        //生成word
        try {
            OutputStream os = new FileOutputStream(targetPath);
            doc.write(os);
            if(os!=null){
                try{
                    os.close();
                    System.out.println("已生成word文件，文件地址："+ targetPath);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }
    }

    /**
     * 获取图片数据流
     * @param imgPath
     * @return
     * @throws Exception
     */
    private static InputStream getImgInputStream(String imgPath) throws Exception{
        if(imgPath.startsWith("http://")){
            //new一个URL对象
            URL url = new URL(imgPath);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            return inStream;
            /*
            //可以使用 OkHttp3 下载网络图片
            final Request request = new Request.Builder()
                    .url(imgPath)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().byteStream();*/
        }else{
            return new FileInputStream(imgPath);
        }
    }
}
