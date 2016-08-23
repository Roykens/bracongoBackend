package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Circuit;
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
public class ICircuitDaoTest {
    
    private List<Circuit> circuits;
    
    private ICircuitDao circuitDaoMock;
    
    public ICircuitDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        circuitDaoMock = EasyMock.createMock(ICircuitDao.class);
        Circuit circ = new Circuit();
        circ.setCode("H1");
        circuits = new ArrayList<Circuit>();
        circuits.add(circ);
                
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findAllActive method, of class ICircuitDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllActive() throws Exception {
        System.out.println("findAllActive");
        EasyMock.expect(circuitDaoMock.findAllActive()).andReturn(circuits);
        EasyMock.replay(circuitDaoMock);
        List<Circuit> result = circuitDaoMock.findAllActive();
        assertEquals(circuits, result);
    }

    
    
}
