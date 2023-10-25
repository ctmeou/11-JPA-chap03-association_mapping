package com.ohgiraffers.section03.bidirection;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiDirectionAssociationTests {

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
    public void 양방향_연관관계_매핑_조회_테스트() {

        // given
        int menuCode = 10;
        int categoryCode = 10;

        // when
        /* 진짜 연관 관계는 최초 조회 시 join 결과를 인출한다. */
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        /* 가짜 연관 관계는 Category 엔티티만 조회하고 필요 시 연관 관계 엔티티를 조회하는 쿼리를 다시 실행하게 된다. */
        Category foundCategory = entityManager.find(Category.class, categoryCode);

        // then
        assertEquals(menuCode, foundMenu.getMenuCode());
        assertEquals(categoryCode, foundCategory.getCategoryCode());

        /* 주의 사항
         * toString 오버라이딩 시 양방향 연관 관계는 재귀 호출이 일어나기 때문에 stackOverFlowError가 발생한다.
         * 엔티티의 주인이 아닌 쪽이 toString에서 연관 객체 부분이 출력되지 않도록 삭제한다.
         * 특히 자동 완성 or 롬북 라이브러리 사용 시 주의한다. */
        System.out.println(foundMenu);
        System.out.println(foundCategory);

        //조회 시 본인 밖에 없음(최초 조회 시) 다시 조회 시 menuList를 참조해서 출력됨
        /* 메뉴 리스트가 필요한 순간 다시 조회 쿼리가 동작하며 참조된다. */
        foundCategory.getMenuList().forEach(System.out::println);
        //양방향 참조이기 때문에 메뉴에서부터 카테고리를 참조하고 참조된 카테고리로부터 메뉴리스트를 참조를 할 수 있다.
        /* 양방향 참조 시 메뉴 -> 카테고리 -> 메뉴리스트 참조도 가능하다. */
        foundMenu.getCategory().getMenuList().forEach(System.out::println);

    }

    //하나의 테이블에만 insert할 경우
    @Test
    public void 양방향_연관관계_주인_객체를_이용한_삽입_테스트() {

        // given
        Menu menu = new Menu();
        menu.setMenuCode(2222);
        menu.setMenuName("연관관계주인메뉴");
        menu.setMenuPrice(10000);
        menu.setOrderableStatus("Y");
        menu.setCategory(entityManager.find(Category.class, 4)); //신규 카테고리가 아닌 기존 카테고리일 경우 엔티티 설정

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(menu);
        entityTransaction.commit();

        // then
        Menu foundMenu = entityManager.find(Menu.class, menu.getMenuCode());
        assertEquals(menu.getMenuCode(), foundMenu.getMenuCode());
        System.out.println(foundMenu);

    }

    //주인이 아닌 경우에 삽입을 할 경우
    @Test
    public void 양방향_연관관계_주인이_아닌_객체를_이용한_삽입_테스트() {

        // given
        Category category = new Category();
        category.setCategoryCode(1004);
        category.setCategoryName("양방향카테고리");
        category.setRefCategoryCode(1);

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(category);
        entityTransaction.commit();

        // then
        Category foundCategory = entityManager.find(Category.class, category.getCategoryCode());
        assertEquals(category.getCategoryCode(), foundCategory.getCategoryCode());
        System.out.println(foundCategory);

    }

}
