package com.zypcy.framework.fast.common.util;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * word 文本内容参数
 * zhuyu
 */
public class WordTextParam extends WordBaseParam{

    /**
     * 内容
     */
    private String content;

    public WordTextParam(){}

    public WordTextParam(String content , ParagraphAlignment align , String fontFamily , int fontSize){
        this.content = content;
        super.setAlign(align);
        super.setFontFamily(fontFamily);
        super.setFontSize(fontSize);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
