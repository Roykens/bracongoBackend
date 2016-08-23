package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class IFormatBoissonDaoTest {
    
    private IFormatBoissonDao formatDaoMock;
    private List<FormatBoisson> boissons;
    
    public IFormatBoissonDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        formatDaoMock = EasyMock.createMock(IFormatBoissonDao.class);
        boissons = new ArrayList<FormatBoisson>();
        FormatBoisson fmb = new FormatBoisson();
        fmb.setActive(1);
        fmb.setPrix(200);
        boissons.add(fmb);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findAllActive method, of class IFormatBoissonDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllActive() throws Exception {
        System.out.println("findAllActive");
        System.out.println("findAllActive");
        EasyMock.expect(formatDaoMock.findAllActive()).andReturn(boissons);
        EasyMock.replay(formatDaoMock);
        List<FormatBoisson> bi = formatDaoMock.findAllActive();
        assertEquals(bi, boissons);
    }

    /**
     * Test of findAllByTypeForEnterprise method, of class IFormatBoissonDao.
     */
    @Test
    @Ignore
    public void testFindAllByTypeForEnterprise() throws Exception {
        System.out.println("findAllByTypeForEnterprise");
        Boolean bracongo = null;
        TypeBoisson typeBoisson = null;
////        IFormatBoissonDao instance = new IFormatBoissonDaoImpl();
////        List<FormatBoisson> expResult = null;
////        List<FormatBoisson> result = instance.findAllByTypeForEnterprise(bracongo, typeBoisson);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
    }

    /**
     * Test of findByBoissonAndFormat method, of class IFormatBoissonDao.
     */
    @Test
    @Ignore
    public void testFindByBoissonAndFormat() throws Exception {
        System.out.println("findByBoissonAndFormat");
        Boisson boisson = null;
//        Format format = null;
//        IFormatBoissonDao instance = new IFormatBoissonDaoImpl();
//        FormatBoisson expResult = null;
//        FormatBoisson result = instance.findByBoissonAndFormat(boisson, format);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of findByBoisson method, of class IFormatBoissonDao.
     */
    @Test
    @Ignore
    public void testFindByBoisson() throws Exception {
        System.out.println("findByBoisson");
        Boisson boisson = null;
//        IFormatBoissonDao instance = new IFormatBoissonDaoImpl();
//        List<FormatBoisson> expResult = null;
//        List<FormatBoisson> result = instance.findByBoisson(boisson);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllByTypeForEnterpriseAndFormat method, of class IFormatBoissonDao.
     */
    @Test
    @Ignore
    public void testFindAllByTypeForEnterpriseAndFormat() throws Exception {
        System.out.println("findAllByTypeForEnterpriseAndFormat");
        Boolean bracongo = null;
        TypeBoisson typeBoisson = null;
//        Format format = null;
//        IFormatBoissonDao instance = new IFormatBoissonDaoImpl();
//        List<FormatBoisson> expResult = null;
//        List<FormatBoisson> result = instance.findAllByTypeForEnterpriseAndFormat(bracongo, typeBoisson, format);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    
}
