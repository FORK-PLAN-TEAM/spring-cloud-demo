package com.zypcy.framework.fast.common.util;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * word 基础参数
 * zhuyu
 */
public class WordBaseParam {

    /**
     * 对齐方式
     */
    private ParagraphAlignment align = ParagraphAlignment.CENTER;
    /**
     * 字体样式
     */
    private String fontFamily = "仿宋";
    /**
     * 字体大小
     */
    int fontSize = 11;

    public ParagraphAlignment getAlign() {
        return align;
    }

    public void setAlign(ParagraphAlignment align) {
        this.align = align;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
