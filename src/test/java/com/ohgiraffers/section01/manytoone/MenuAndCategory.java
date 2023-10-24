package com.ohgiraffers.section01.manytoone;

import javax.persistence.*;

//Category 엔티티를 참조하고 있는 menu엔티티
@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class MenuAndCategory {

    @Id //PK이기에 생성
    private int menuCode;
    private String menuName;
    private int menuPrice;
    @ManyToOne //1. 다중성 어노테이션 작성
    @JoinColumn(name = "categoryCode")   // FK에 해당하는 컬럼명 //2. join할 때 사용하는 컬럼 명시, 어떤 것을 join하고 있는지 명시, 컬럼명이 다른 경우 TBL_TABLE에 대한 컬러명을 작성해야 함
    private Category category;
    private String orderableStatus;

    public MenuAndCategory() {
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    public MenuAndCategory(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "MenuAndCategory{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }

}
