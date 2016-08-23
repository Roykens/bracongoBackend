package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Boisson;
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

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class IBoissonDaoTest {

    private IBoissonDao boissonDaoMock;

    private List<Boisson> bi;

    public IBoissonDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Boisson boi = new Boisson();
        boi.setActive(1);
        boi.setIsBracongo(true);
        boi.setNom("Nkoyi");
        boi.setTypeBoisson(TypeBoisson.BI);
        bi = new ArrayList<Boisson>();
        bi.add(boi);
        boissonDaoMock = EasyMock.createMock(IBoissonDao.class);
    }

    @After
    public void tearDown() {
        EasyMock.verify(boissonDaoMock);
        boissonDaoMock = null;
    }

    /**
     * Test of findAllByEnterpriseAndType method, of class IBoissonDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllByEnterpriseAndType() throws Exception {
        System.out.println("findAllByEnterpriseAndType");
        boolean isBracongo = true;
        TypeBoisson typeBoisson = TypeBoisson.BI;
        EasyMock.expect(boissonDaoMock.findAllByEnterpriseAndType(isBracongo, typeBoisson)).andReturn(bi);
        EasyMock.replay(boissonDaoMock);
        List<Boisson> boissons = boissonDaoMock.findAllByEnterpriseAndType(isBracongo, typeBoisson);
        assertEquals(bi, boissons);
    }

    /**
     * Test of findByName method, of class IBoissonDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByName() throws Exception {
        System.out.println("findByName");
        String name = "Nkoyi";
        Boisson boisson = new Boisson();
        boisson.setNom(name);
        EasyMock.expect(boissonDaoMock.findByName(name)).andReturn(boisson);
        EasyMock.replay(boissonDaoMock);
        Boisson result = boissonDaoMock.findByName(name);
        assertNotNull(result);
    }

    /**
     * Test of findAllActive method, of class IBoissonDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllActive() throws Exception {
        System.out.println("findAllActive");
        EasyMock.expect(boissonDaoMock.findAllActive()).andReturn(bi);
        EasyMock.replay(boissonDaoMock);
        List<Boisson> boissons = boissonDaoMock.findAllActive();
        assertEquals(bi, boissons);
    }

}
