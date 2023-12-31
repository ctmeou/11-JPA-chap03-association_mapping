package com.ohgiraffers.section02.onetomany;

import javax.persistence.*;
import java.util.List;

@Entity(name = "category_and_menu")
@Table(name = "tbl_category")
public class CategoryAndMenu {
    @Id //categoryCode = PK
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
    @OneToMany(cascade = CascadeType.PERSIST) //영속성 정의 //카테고리 입장에서 메뉴를 바로볼 때 하나의 카테고리는 여러 개의 메뉴를 참조하기 떄문에 OneToMany
    @JoinColumn(name = "categoryCode")
    private List<Menu> menuList;

    public CategoryAndMenu() {
    }

    public CategoryAndMenu(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "CategoryAndMenu{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                ", menuList=" + menuList +
                '}';
    }
}
