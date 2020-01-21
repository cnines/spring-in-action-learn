package com.chengxu.demo.spel;

import com.chengxu.demo.spel.entity.InstA;
import com.chengxu.demo.spel.entity.Sing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GlobalConfig.class)
public class GlobalConfigTest {

    // ------------使用 bean ID 来引用 bean-----------
    @Value("#{instA}")
    private Object obj;

    // -----------引用 bean 的属性--------------------
    @Value("#{instA.name}")
    private String name;


    // ------------引用 bean 的方法-------------------
    @Value("#{instA.getName().toUpperCase()}")
    private String upperName;


    // -------------引用类的静态方法------------------
    @Value("#{T(com.chengxu.demo.spel.util.MathUtil).getBigNum(99,100)}")
    private int bigger;

    @Value("#{T(com.chengxu.demo.spel.util.MathUtil).getNum()}")
    private int num;


    // -------------------使用运算符-----------------------
    @Value("#{20 + 5}")
    // @Value("#{30 - 5}")
    // @Value("#{5 * 5}")
    // @Value("#{50 / 2}")
    private int intResult;

    @Value("#{10 ge 100}")
    // @Value("#{10 eq 100}")
    // @Value("#{10 gt 100}")
    // @Value("#{10 >= 100}")
    // @Value("#{10 == 100}")
    // @Value("#{10 > 100}")
    private boolean boolResult;

    // 使用三元运算符 ( ternary )
    @Value("#{instA.age == 22 ? 'yes' : 'no'}")
    private String stringResult;

    // 使用 Elvis 表达式
    @Value("#{instA.prop ?: 'default'}")
    private String defaultString;
    // ---------------------------------------------------

    // ---------------使用正则表达式-----------------------
    @Value("#{instA.email matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com'}")
    private boolean regResult;
    // ---------------------------------------------------

    // ----------------计算集合----------------------------
    @Value("#{useInstA.titles[2].toUpperCase()[3]}")
    private char charResult;

    // 查询运算符
    @Value("#{useInstA.sings.?[author != null]}")
    private List<Sing> sings;

    @Value("#{useInstA.sings.^[author != null]}")
    private Sing first;

    @Value("#{useInstA.sings.$[author != null]}")
    private Sing last;

    // 投影运算符
    @Value("#{useInstA.sings.![author]}")
    private List<String> authors;
    // ---------------------------------------------------

    @Test
    public void testRefBeanByBeanId() {
        assertTrue(obj instanceof InstA);
    }

    @Test
    public void testRefBeanProperty() {
        assertEquals("cnines", name);
    }

    @Test
    public void testRefBeanMethod() {
        assertEquals("CNINES", upperName);
    }

    @Test
    public void testUseStaticMethod() {
        assertEquals(100, bigger);
        assertEquals(2, num);
    }

    @Test
    public void testUseOperator() {
        assertEquals(25, intResult);
        assertFalse(boolResult);
        assertEquals("yes", stringResult);
        assertEquals("default", defaultString);
        assertTrue(regResult);
        assertEquals('H', charResult);

        assertEquals("李宗盛", sings.get(0).getAuthor());
        assertEquals("孙燕姿", sings.get(1).getAuthor());
        assertEquals("陈粒", sings.get(2).getAuthor());

        assertEquals("李宗盛", first.getAuthor());

        assertEquals("陈粒", last.getAuthor());

        assertEquals("李宗盛", authors.get(0));
        assertEquals("孙燕姿", authors.get(1));
        assertEquals("陈粒", authors.get(2));

    }

}