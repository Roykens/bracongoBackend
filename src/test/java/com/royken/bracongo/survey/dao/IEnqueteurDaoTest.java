package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class IEnqueteurDaoTest {
    
    private IEnqueteurDao enqueteurDaoMock;
    
    public IEnqueteurDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        enqueteurDaoMock = EasyMock.createMock(IEnqueteurDao.class);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findEnqueteurByUsernameAndPassword method, of class IEnqueteurDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindEnqueteurByUsernameAndPassword() throws Exception {
        System.out.println("findEnqueteurByUsernameAndPassword");
        String username = "admin";
        String password = "admin";
        Enqueteur expResult = new Enqueteur();
        expResult.setUsername(username);
        EasyMock.expect(enqueteurDaoMock.findEnqueteurByUsernameAndPassword(username,password)).andReturn(expResult);
        EasyMock.replay(enqueteurDaoMock);
        Enqueteur result = enqueteurDaoMock.findEnqueteurByUsernameAndPassword(username, password);
        expResult.setCodeSecret(password);
       
    }
    
}
