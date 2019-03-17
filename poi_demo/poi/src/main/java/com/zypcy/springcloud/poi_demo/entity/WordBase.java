package com.zypcy.springcloud.poi_demo.entity;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * word 基础参数
 * zhuyu
 */
@Data
public class WordBase {

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
