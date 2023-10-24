package com.ohgiraffers.section01.manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "category_section01") //test를 여러 번 하기 위해 name 속성 작성해서 구분
@Table(name = "tbl_category") //table 이름
public class Category {
    @Id //pk에 @Id를 붙임
    private int categoryCode; //컬럼 어노테이션 없이 매칭될 수 있게 xml에 작성해뒀기 때문에 생략
    private String categoryName;
    private Integer refCategoryCode;

    public Category() {
    }

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
