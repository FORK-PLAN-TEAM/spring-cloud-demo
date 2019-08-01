package com.zypcy.framework.fast.common.util;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * word 基础参数
 * zhuyu
 */
@Data
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
}
