package com.ohgiraffers.section02.onetomany;


import com.ohgiraffers.section01.manytoone.Category;
import org.junit.jupiter.api.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OneToManyAssociationTests {

    //application 당 1개만
    private static EntityManagerFactory entityManagerFactory;

    //스레드 세이프 하지 않고, 요청 당 1개
    private EntityManager entityManager;

    @BeforeAll //junit에서 오는 어노테이션, 테스트가 진행되기 전에 한 번 진행된다.
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach //테스트 하나가 진행되기 전에 한 번씩
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll //테스트가 끝나기 전에 한 번만
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach //테스트가 끝날 떄마다 한 번씩
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 일대다_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {

        // given
        int categoryCode = 10;

        // when
        // 일대다 연관 관계의 경우 category 테이블만 조회하고 연관된 menu 테이블은 아직 조회하지 않는다.
        CategoryAndMenu categoryAndMenu = entityManager.find(CategoryAndMenu.class, categoryCode);

        // then
        assertNotNull(categoryAndMenu);
        // 해당 데이터가 사용되는 경우 연관된 menu 테이블을 조회하는 구문이 실행된다.
        System.out.println("categoryAndMenu.getMenuList = " + categoryAndMenu.getMenuList());

    }

    @Test
    public void 일대다_연관관계_객체_삽입_테스트() {

        // given
        CategoryAndMenu categoryAndMenu = new CategoryAndMenu();
        categoryAndMenu.setCategoryCode(777);
        categoryAndMenu.setCategoryName("일대다추가카테고리");
        categoryAndMenu.setRefCategoryCode(1);

        List<Menu> menuList = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuCode(7777);
        menu.setMenuName("일대다아이스크림");
        menu.setMenuPrice(50000);
        menu.setCategoryCode(categoryAndMenu.getCategoryCode());
        menu.setOrderableStatus("Y");

        menuList.add(menu);

        //menuList는 categoryAndMenu에 들어가야 함
        //categoryAndMenu에 저장하고 싶은 값이 세팅됨
        categoryAndMenu.setMenuList(menuList);

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); //Transaction을 가져와서 시작하고
        entityManager.persist(categoryAndMenu); //categoryAndMenu 저장하고
        entityTransaction.commit(); //commit해줌

        // then
        CategoryAndMenu foundCategoryAndMenu = entityManager.find(CategoryAndMenu.class, 777);
        System.out.println("foundCategoryAndMenu = " + foundCategoryAndMenu);

    }

}
