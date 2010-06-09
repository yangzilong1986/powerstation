package org.pssframework.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Baocj
 * Date: 2010-5-16
 * Time: 22:31:44
 * To change this template use File | Settings | File Templates.
 */
public class JuintTest {

    @Before
    public void one() {
    }

    @After
    public void two() {

    }

    @Test
    public void fuck() {
        String a = "123";
        assertNotNull(a);
        assertFalse(false);
        assertTrue(true);
        assertEquals(a,"123");
    }

}
