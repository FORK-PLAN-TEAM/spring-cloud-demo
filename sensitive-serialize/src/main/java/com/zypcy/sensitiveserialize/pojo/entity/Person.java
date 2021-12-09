package com.zypcy.sensitiveserialize.pojo.entity;

import com.zypcy.sensitiveserialize.annotation.Sensitive;
import com.zypcy.sensitiveserialize.annotation.SensitiveType;

import java.time.LocalDate;

/**
 * @Author zhuyu
 * @Time 2020-06-19 10:46
 * @Description 描述
 */

public class Person {

    private String id;
    private String name;
    @Sensitive(value = SensitiveType.CARD_NO)
    private String cardNo;
    @Sensitive(value = SensitiveType.TELL_PHONE)
    private String tellPhone;
    private LocalDate createDate;

    public Person() {
    }

    public Person(String id, String name, String cardNo, String tellPhone, LocalDate createDate) {
        this.id = id;
        this.name = name;
        this.cardNo = cardNo;
        this.tellPhone = tellPhone;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTellPhone() {
        return tellPhone;
    }

    public void setTellPhone(String tellPhone) {
        this.tellPhone = tellPhone;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
