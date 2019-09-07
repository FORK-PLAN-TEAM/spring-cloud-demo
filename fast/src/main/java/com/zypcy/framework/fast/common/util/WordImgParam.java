package com.zypcy.framework.fast.common.util;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * word 图片内容参数
 * zhuyu
 */
public class WordImgParam extends WordBaseParam{

    /**
     * 图片路径数组
     * String[] imgs = {"D:\\bar.png","D:\\pie.png"};
     */
    private String[] imgs;
    /**
     * 图片宽，默认350
     */
    private int width = 350;
    /**
     * 图片高，默认180
     */
    private int height = 180;

    public WordImgParam(){}

    public WordImgParam(String[] imgs , ParagraphAlignment align , String fontFamily , int fontSize){
        this.imgs = imgs;
        super.setAlign(align);
        super.setFontFamily(fontFamily);
        super.setFontSize(fontSize);
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
