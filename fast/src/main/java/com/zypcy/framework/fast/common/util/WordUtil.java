package com.zypcy.framework.fast.common.util;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * excel帮助类
 * 朱宇 2019-08-01
 * exportDoc() 导出word
 * replaceAndSaveDoc() 替换word中指定字符串
 */
public class WordUtil {

    public static void main(String[] args) throws Exception, IOException {
        //System.out.println(FilePathUtil.getRootPath());
        testReplaceDoc();
        //testExportDoc();
    }

    //测试替换word内容
    public static void testReplaceDoc() {
        //替换 [sum] 标签 与 习近平 字样
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("[sum]", "长沙");
        map.put("习近平", "朱宇");
        //word模版地址，请注意模版放的位置与模版路径
        String sourcePath = "static/office_template/word_replace_tpl.docx";
        //导出地址
        String targetPath = FilePathUtil.getOfficeExportPath() + IdWorker.getDateId() + ".docx";
        try {
            replaceAndSaveDoc(sourcePath, map, targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试导出word
    //组合list，生成word时，会根据list添加各种类型内容的顺序生成到word中
    public static void testExportDoc() {
        String content = "    额尔古纳河在1689年的《中俄尼布楚条约》中成为中国和俄罗斯的界河，额尔古纳河上游称海拉尔河，源于大兴安岭西侧，西流至阿该巴图山脚， 折而北行始称额尔古纳河。折而北行始称额尔古纳河。额尔古纳河在黑龙江省漠河县以西的内蒙古自治区额尔古纳右旗的恩和哈达附近与流经俄罗斯境内的石勒额尔古纳河在黑龙江省漠河县以西的内蒙古自治区额尔古纳右旗的恩和哈达附近与流经俄罗斯境内的石勒喀河汇合后始称黑龙江。沿额尔古纳河沿岸地区土地肥沃，森林茂密，水草丰美， 鱼类品种很多，动植物资源丰富，宜农宜木，是人类理想的天堂。";
        ParagraphAlignment align = ParagraphAlignment.CENTER;
        String fontFamily = "仿宋";
        int fontSize = 13;
        String targetPath = FilePathUtil.getOfficeExportPath() + IdWorker.getDateId() + ".docx";
        try {
            long startTime = System.currentTimeMillis();

            ArrayList<Object> list = new ArrayList<>();

            WordTextParam textParam = new WordTextParam(content, align, fontFamily, fontSize);
            list.add(textParam);

            WordTableParam tableParam = new WordTableParam();
            tableParam.setRowHeight(400);
            String[] header = {"境内河流", "境外河流", "合计"};
            tableParam.setHeader(header);
            List<String[]> values = new ArrayList<>();
            String[] v1 = {"1", "2", "3"};
            String[] v2 = {"4", "5", "6"};
            String[] v3 = {"7", "8", "9"};
            values.add(v1);
            values.add(v2);
            values.add(v3);
            tableParam.setValues(values);
            list.add(tableParam);

            WordTextParam textParam2 = new WordTextParam("123 " + content, align, fontFamily, fontSize);
            list.add(textParam2);

            WordImgParam imgParam = new WordImgParam();
            String[] imgs = {
                    FilePathUtil.getResourceFilePath("static/images/index_bg2.jpg"),
                    "http://localhost:8088/images/index_bg1.jpg",
                    "https://pics1.baidu.com/feed/c8ea15ce36d3d5391cb7e9ae9c2ade55342ab00c.jpeg?token=b346738740779f33215b3770931903ab&s=6681D201A6F13D8E7DBDDDB50300D081"};
            imgParam.setImgs(imgs);
            list.add(imgParam);

            exportDoc(list, targetPath);

            System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出 word （包含文本、图片、表格几种类型，需自己组合list）
     * 生成word时，会根据list添加各种类型内容的顺序生成到word中
     *
     * @param list       内容list = [WordTextParam , WordImgParam , WordTableParam]
     * @param targetPath 生成文档目标路径
     * @throws Exception
     */
    public static void exportDoc(ArrayList<Object> list, String targetPath) throws Exception {
        //检测路径
        checkPath(targetPath);
        //组装word
        XWPFDocument doc = generatorDocument(list);
        //生成word
        outPutWord(doc, targetPath);
    }

    /**
     * 导出文本类型 word
     *
     * @param textParam
     * @param targetPath
     */
    public static void exportTextDoc(WordTextParam textParam, String targetPath) {
        //检测路径
        checkPath(targetPath);
        //组装word
        XWPFDocument doc = new XWPFDocument();
        appendWordText(doc, textParam);
        //生成word
        outPutWord(doc, targetPath);
    }

    /**
     * 导出图片类型 word
     * 图片导出请使用 WordXWPFDocument
     *
     * @param imgParam
     * @param targetPath
     */
    public static void exportImgDoc(WordImgParam imgParam, String targetPath) {
        //检测路径
        checkPath(targetPath);
        //组装word
        WordXWPFDocument doc = new WordXWPFDocument();
        appendWordImg(doc, imgParam);
        //生成word
        outPutWord(doc, targetPath);
    }

    /**
     * 导出表格类型 word
     *
     * @param tableParam
     * @param targetPath
     */
    public static void exportTableDoc(WordTableParam tableParam, String targetPath) {
        //检测路径
        checkPath(targetPath);
        //组装word
        XWPFDocument doc = new XWPFDocument();
        appendWordTable(doc, tableParam);
        //生成word
        outPutWord(doc, targetPath);
    }

    /**
     * 组装word
     *
     * @param list
     * @return
     */
    private static XWPFDocument generatorDocument(ArrayList<Object> list) {
        WordXWPFDocument doc = new WordXWPFDocument();
        list.forEach(item -> {
            if (item instanceof WordTextParam) {
                appendWordText(doc, (WordTextParam) item);
            } else if (item instanceof WordImgParam) {
                appendWordImg(doc, (WordImgParam) item);
            } else if (item instanceof WordTableParam) {
                appendWordTable(doc, (WordTableParam) item);
            }
        });
        return doc;
    }

    /**
     * 向word中追加文本内容
     *
     * @param doc       XWPFDocument
     * @param textParam 文本数据模型
     */
    private static void appendWordText(XWPFDocument doc, WordTextParam textParam) {
        //添加文本
        if (textParam != null && !StringUtils.isEmpty(textParam.getContent())) {
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
     *
     * @param doc      XWPFDocument
     * @param imgParam 图片数据模型
     */
    private static void appendWordImg(WordXWPFDocument doc, WordImgParam imgParam) {
        //添加图片
        if (imgParam != null && imgParam.getImgs() != null && imgParam.getImgs().length > 0) {
            XWPFParagraph para;
            XWPFRun run;
            String[] imgs = imgParam.getImgs();
            try {
                for (int i = 0; i < imgs.length; i++) {
                    para = doc.createParagraph();
                    para.setAlignment(imgParam.getAlign());//设置对齐方式
                    run = para.createRun();
                    InputStream input = getImgInputStream(imgs[i]);
                    //InputStream input = new FileInputStream(imgs[i]);
                    String filename = imgs[i].indexOf("\\") > 0 ? imgs[i].substring(imgs[i].lastIndexOf("\\") + 1) : imgs[i].substring(imgs[i].lastIndexOf("/") + 1);
                    doc.addPictureData(input, getDocumentImgType(imgs[i]));
                    doc.createPicture(doc.getAllPictures().size() - 1, imgParam.getWidth(), imgParam.getHeight(), para);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            doc.createParagraph();
        }
    }

    /**
     * 向word中追加表格内容
     *
     * @param doc        XWPFDocument
     * @param tableParam 表格数据模型
     */
    private static void appendWordTable(XWPFDocument doc, WordTableParam tableParam) {
        //添加表格
        if (tableParam != null && tableParam.getHeader() != null && tableParam.getValues() != null) {
            XWPFParagraph para;
            XWPFRun run;
            XWPFTable table = doc.createTable(tableParam.getRows(), tableParam.getCols());
            table.setCellMargins(tableParam.getTop(), tableParam.getLeft(), tableParam.getBottom(), tableParam.getRight());
            //table.addNewCol();//添加新列
            //table.createRow();//添加新行
            XWPFTableRow row;
            XWPFTableCell cell;
            CTTcPr cellPr;
            //循环行
            for (int j = 0; j < tableParam.getRows(); j++) {
                row = table.getRow(j);
                row.setHeight(tableParam.getRowHeight());
                //循环列
                for (int i = 0; i < tableParam.getCols(); i++) {
                    cell = row.getCell(i);
                    cellPr = cell.getCTTc().addNewTcPr();
                    cellPr.addNewTcW().setW(BigInteger.valueOf(tableParam.getColWidth()));
                    para = cell.getParagraphs().get(0);
                    para.setAlignment(tableParam.getAlign());
                    run = para.createRun();
                    run.setFontFamily(tableParam.getFontFamily());
                    run.setFontSize(tableParam.getFontSize());
                    if (j == 0) {//第一行输出标题
                        run.setBold(true);
                        run.setText(tableParam.getHeader()[i]);
                    } else {//输出表格内容
                        run.setText(String.valueOf(tableParam.getValues().get(j - 1)[i]));
                    }
                }
            }
            doc.createParagraph();
        }
    }

    /**
     * 输出成word
     *
     * @param doc        XWPFDocument
     * @param targetPath 生成文件目标地址
     */
    private static void outPutWord(XWPFDocument doc, String targetPath) {
        //生成word
        try {
            OutputStream os = new FileOutputStream(targetPath);
            doc.write(os);
            if (os != null) {
                try {
                    os.close();
                    System.out.println("已生成word文件！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    /**
     * 检查路径
     *
     * @param path
     * @throws Exception
     */
    private static void checkPath(String path) {
        if (!(path.contains(".doc") || path.contains(".docx"))) {
            throw new RuntimeException("生成word目标路径需包含.doc、.docx");
        }
    }

    /**
     * 读取word模板替换变量，并根据目标路径生成新的word文档
     *
     * @param sourcePath 文档源路径
     * @param param      要替换的键值对
     * @param targetPath 生成文档目标路径
     */
    public static void replaceAndSaveDoc(String sourcePath, Map<String, Object> param, String targetPath) {
        try {
            XWPFDocument doc = replaceDoc(sourcePath, param);
            if (doc != null) {
                OutputStream os = new FileOutputStream(targetPath);
                doc.write(os);
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取word模板并替换变量
     *
     * @param sourcePath 文档相对路径路径，static/office_template/word_replace_tpl.docx
     * @param param      要替换的键值对
     * @return
     */
    public static XWPFDocument replaceDoc(String sourcePath, Map<String, Object> param) {
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource(sourcePath);//用来读取resources下的文件
            if (!resource.exists()) {
                throw new Exception("未读取到源文件");
            }
            /*File f = resource.getFile();//new File(sourcePath);
            if(!f.exists()){
                throw new Exception("未读取到源文件");
            }
            InputStream fis = new FileInputStream(f);*/
            //InputStream fis = new FileInputStream(sourcePath);
            InputStream fis = resource.getInputStream();
            XWPFDocument doc = new XWPFDocument(fis);
            //处理段落
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            processParagraph(paragraphList, doc, param);
            //处理表格
            Iterator<XWPFTable> it = doc.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                        processParagraph(paragraphListTable, doc, param);
                    }
                }
            }
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void processParagraph(List<XWPFParagraph> paragraphList, XWPFDocument doc, Map<String, Object> param) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                    //System.out.println(text);//放开注释可打印读取到的word内容
                                } else {
                                    text = text.replace(key, "");
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取应用根目录
     *
     * @return
     */
    public static String getRootPath() {
        String rootPath = "";
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");
            rootPath = path.getAbsolutePath();

            //如果上传目录为/static/images/upload/，则可以如下获取：
            //在开发测试模式时，得到的地址为：{项目跟目录}/target/static/images/upload/
            //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/
            //File upload = new File(path.getAbsolutePath(), "static/images/upload/");
            //if (!upload.exists()) upload.mkdirs();
            //System.out.println("upload url:" + upload.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootPath;
    }

    /**
     * 获取图片对应的文档类型
     *
     * @param picName
     * @return
     */
    private static int getDocumentImgType(String picName) {
        //bmp|jpg|jpeg|png|gif|psd|dwg
        int format = XWPFDocument.PICTURE_TYPE_PNG;
        if (picName.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
        else if (picName.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
        else if (picName.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
        else if (picName.endsWith(".jpeg") || picName.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
        else if (picName.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
        else if (picName.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
        else if (picName.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
        else if (picName.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
        else if (picName.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
        else if (picName.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
        else if (picName.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
        return format;
    }

    //OkHttpClient
    /*private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //设置连接超时
            .connectTimeout(5 , TimeUnit.SECONDS)
            //设置读超时
            .readTimeout(5 , TimeUnit.SECONDS)
            //设置写超时
            .writeTimeout(5 , TimeUnit.SECONDS)
            //是否自动重连
            .retryOnConnectionFailure(true)
            .connectionPool(new ConnectionPool())
            .build();*/

    /**
     * 获取图片数据流（如果是网络图片则先下载到本地）
     *
     * @param imgPath
     * @return
     * @throws Exception
     */
    private static InputStream getImgInputStream(String imgPath) throws Exception {
        if (imgPath.startsWith("http://")) {
            //new一个URL对象
            URL url = new URL(imgPath);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            return inStream;

            /*final Request request = new Request.Builder()
                    .url(imgPath)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().byteStream();*/
        } else {
            return new FileInputStream(imgPath);
        }
    }
}
