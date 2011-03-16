/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pep.bp.realinterface.conf.ProtocolConfig;

import org.exolab.castor.mapping.MappingException;
import org.xml.sax.InputSource;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.*;

/**
 *
 * @author Thinkpad
 */
public class ProtocolConfigTest {

    public ProtocolConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ProtocolConfig config = ProtocolConfig.getInstance();

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFormat method, of class ProtocolConfig.
     */
    @Test
    public void testGetFormat() throws FileNotFoundException, MappingException, IOException {
        try {
            Mapping map = new Mapping();
            map.loadMapping("protocol-data-config-mapping.xml");
            File file = new File("protocol-data-config.xml");
            Reader reader = new FileReader(file);
            Unmarshaller unmarshaller = new Unmarshaller(map);

            ProtocolCommandItems read = (ProtocolCommandItems) unmarshaller.unmarshal(reader);
            read.FillMap();
            assertEquals(read.getDataItem("1004000101").getFormat(),"BIN");
            assertEquals(read.getDataItem("1004000102").getFormat(),"BIN");
            assertEquals(read.getDataItem("1004000103").getFormat(),"BIN");
        } catch (MarshalException ex) {
            Logger.getLogger(ProtocolConfigTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ValidationException ex) {
            Logger.getLogger(ProtocolConfigTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
