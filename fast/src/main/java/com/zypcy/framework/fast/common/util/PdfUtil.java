package com.zypcy.framework.fast.common.util;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;


public class PdfUtil {

    public static void main(String[] args)throws Exception
    {
        BaseFont bfChinese = BaseFont.createFont();
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);

        // 第一步，创建document对象
        Rectangle rectPageSize = new Rectangle(PageSize.A4);

        //下面代码设置页面横置
        //rectPageSize = rectPageSize.rotate();

        //创建document对象并指定边距
        Document doc = new Document(rectPageSize,50,50,50,50);
        Document document = new Document();
        try
        {
            // 第二步,将Document实例和文件输出流用PdfWriter类绑定在一起
            //从而完成向Document写，即写入PDF文档
            String outPutPath = FilePathUtil.getOfficeExportPath() + IdWorker.getDateId() + ".pdf";
            PdfWriter.getInstance(document,new FileOutputStream(outPutPath));
            //第3步,打开文档
            document.open();
            //第3步,向文档添加文字. 文档由段组成
            document.add(new Paragraph("Hello World"));
            document.add(new Paragraph("你好！"));
            document.add(new Paragraph("我是pdf导出示例类"));

            Paragraph par = new Paragraph("世界你好",FontChinese);
            document.add(par);

            PdfPTable table = new PdfPTable(3);
            PdfPCell thCell = new PdfPCell();
            thCell.setColspan(3);
            com.itextpdf.text.BaseColor baseColor = new com.itextpdf.text.BaseColor(180,180,180);
            thCell.setBackgroundColor(baseColor);
            //thCell.addElement(new Paragraph("表格头" , FontChinese));
            table.addCell(new Paragraph("列1" , FontChinese));
            table.addCell(new Paragraph("列2" , FontChinese));
            table.addCell(new Paragraph("列3" , FontChinese));
            for(int i=0;i<12;i++)
            {
                PdfPCell cell = new PdfPCell();
                //cell.addElement(new Paragraph("表格内容" + i , FontChinese));
                table.addCell(new Paragraph("id-" + i, FontChinese));
                table.addCell(new Paragraph("age-" + i, FontChinese));
                table.addCell(new Paragraph("content-" + i, FontChinese));
            }
            document.add(table);

        }
        catch (DocumentException de)
        {
            System.err.println(de.getMessage());
        }
        catch (IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
        //关闭document
        document.close();

        System.out.println("生成HelloPdf成功！");
    }

}
