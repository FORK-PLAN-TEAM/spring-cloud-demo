package com.zypcy.sensitiveserialize.util;

import org.springframework.util.StringUtils;

/**
 * @Author zhuyu
 * @Time 2020-06-19 11:02
 * @Description 脱敏帮助类 描述
 */
public class SensitiveUtil {

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位，例子：*************5762
     * @param cardNo
     * @return
     */
    public static String cardNo(String cardNo){
        if(StringUtils.isEmpty(cardNo)){
            return "";
        }
        int len = cardNo.length();
        if(len > 4){
            return "**************" + cardNo.substring((len -4) , len);
        }else {
            return cardNo;
        }
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏，例子:138******1234
     * @param tellPhone
     * @return
     */
    public static String tellPhone(String tellPhone){
        if(StringUtils.isEmpty(tellPhone)){
            return "";
        }
        int len = tellPhone.length();
        if(len == 11){
            return tellPhone.substring(0,3) + "****" + tellPhone.substring(7 , len);
        }else {
            return tellPhone;
        }
    }
}
