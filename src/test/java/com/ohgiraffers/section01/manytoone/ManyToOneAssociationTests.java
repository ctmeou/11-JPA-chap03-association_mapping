package com.ohgiraffers.section01.manytoone;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManyToOneAssociationTests {

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
    public void 다대일_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {

        // given
        int menuCode = 15;

        // when
        // 조회 시 조인 구문이 실행되며 연관 테이블을 함께 조회해온다.
        MenuAndCategory foundMenu = entityManager.find(MenuAndCategory.class, menuCode);
        Category menuCategory = foundMenu.getCategory();

        // then
        //값이 존재하는지, 어떤 값으로 존재하는지 확인
        assertNotNull(menuCategory);
        System.out.println("menuCategory = " + menuCategory);

    }

    //jpql = 객체지향쿼리 이용해서 test
    @Test
    public void 다대일_연관관계_객체지향쿼리_사용한_카테고리_이름_조회_테스트() {

        // given
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = 15";

        // when
        // 조회 시 조인 구문이 실행되며 연관 테이블을 함께 조회해온다.
        String categoryName = entityManager.createQuery(jpql, String.class).getSingleResult();
                                        //(수행하고 싶은 jpql 문자열, 받을 타입)

        // given
        assertNotNull(categoryName);
        System.out.println("categoryName = " + categoryName);

    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트() {

    }

}
