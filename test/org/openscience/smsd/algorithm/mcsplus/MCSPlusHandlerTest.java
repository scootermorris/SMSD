/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openscience.smsd.algorithm.mcsplus;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.IChemObjectReader.Mode;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.normalize.SMSDNormalizer;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.smsd.Isomorphism;
import org.openscience.smsd.interfaces.Algorithm;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Asad
 */
public class MCSPlusHandlerTest {

    public MCSPlusHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
     * Test of searchMCS method, of class MCSPlusHandler.
     */
    @Test
    public void testSearchMCS() {
        try {
            System.out.println("searchMCS");
            SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
            IAtomContainer target = null;
            target = sp.parseSmiles("C\\C=C/Nc1cccc(c1)N(O)\\C=C\\C\\C=C\\C=C/C");
            IAtomContainer queryac = null;
            queryac = sp.parseSmiles("Nc1ccccc1");
            MCSPlusHandler smsd1 = new MCSPlusHandler(queryac, target, true, false);
            assertNotNull(smsd1.getFirstMapping());
        } catch (InvalidSmilesException ex) {
            Logger.getLogger(MCSPlusHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of set method, of class MCSPlusHandler.
     * @throws InvalidSmilesException
     */
    @Test
    public void testSet_IAtomContainer_IAtomContainer() throws InvalidSmilesException {
        System.out.println("set");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer target = sp.parseSmiles("C\\C=C/Nc1cccc(c1)N(O)\\C=C\\C\\C=C\\C=C/C");
        IAtomContainer queryac = sp.parseSmiles("Nc1ccccc1");

        MCSPlusHandler smsd1 = new MCSPlusHandler(queryac, target, true, false);
        assertNotNull(smsd1.getFirstMapping());

    }

    /**
     * Test of set method, of class MCSPlusHandler.
     * @throws Exception
     */
    @Test
    public void testSet_IMolecule_IMolecule() throws Exception {
        System.out.println("set");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IMolecule target = sp.parseSmiles("C\\C=C/Nc1cccc(c1)N(O)\\C=C\\C\\C=C\\C=C/C");
        IMolecule queryac = sp.parseSmiles("Nc1ccccc1");

        MCSPlusHandler smsd1 = new MCSPlusHandler(queryac, target, true, false);
        assertNotNull(smsd1.getFirstMapping());
    }

    /**
     * Test of set method, of class MCSPlusHandler.
     * @throws InvalidSmilesException
     */
    @Test
    public void testSet_AtomContainer_AtomContainer() throws InvalidSmilesException {
        System.out.println("set");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());

        IAtomContainer target = sp.parseSmiles("C\\C=C/Nc1cccc(c1)N(O)\\C=C\\C\\C=C\\C=C/C");
        IAtomContainer queryac = sp.parseSmiles("Nc1ccccc1");
        MCSPlusHandler instance = new MCSPlusHandler(queryac, target, true, false);
        assertNotNull(instance.getFirstMapping());
    }

    /**
     * Test of getAllAtomMapping method, of class MCSPlusHandler.
     * @throws InvalidSmilesException
     */
    @Test
    public void testGetAllAtomMapping() throws InvalidSmilesException {
        System.out.println("getAllAtomMapping");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer query = sp.parseSmiles("Nc1cccc(NO)c1");
        IAtomContainer target = sp.parseSmiles("Nc1ccccc1");


        MCSPlusHandler smsd1 = new MCSPlusHandler(query, target, true, false);
        assertNotNull(smsd1.getFirstMapping());
        assertEquals(2, smsd1.getAllAtomMapping().size());
    }

    /**
     * Test of getAllMapping method, of class MCSPlusHandler.
     * @throws InvalidSmilesException
     */
    @Test
    public void testGetAllMapping() throws InvalidSmilesException {
        System.out.println("getAllMapping");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer query = sp.parseSmiles("Nc1cccc(NO)c1");
        IAtomContainer target = sp.parseSmiles("Nc1ccccc1");


        MCSPlusHandler smsd1 = new MCSPlusHandler(query, target, true, true);
        assertNotNull(smsd1.getFirstMapping());
        assertEquals(2, smsd1.getAllMapping().size());
    }

    /**
     * Test of getFirstAtomMapping method, of class MCSPlusHandler.
     * @throws InvalidSmilesException
     */
    @Test
    public void testGetFirstAtomMapping() throws InvalidSmilesException {
        System.out.println("getFirstAtomMapping");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer query = sp.parseSmiles("Nc1cccc(NO)c1");
        IAtomContainer target = sp.parseSmiles("Nc1ccccc1");


        MCSPlusHandler smsd1 = new MCSPlusHandler(query, target, true, false);
        assertNotNull(smsd1.getFirstMapping());

        assertEquals(7, smsd1.getFirstAtomMapping().getCount());
    }

    /**
     * Test of getFirstMapping method, of class MCSPlusHandler.
     * @throws InvalidSmilesException
     * @throws CDKException  
     */
    @Test
    public void testGetFirstMapping() throws InvalidSmilesException, CDKException {
        System.out.println("getFirstMapping");
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer query = sp.parseSmiles("Nc1cccc(NO)c1");
        IAtomContainer target = sp.parseSmiles("Nc1ccccc1");

        SMSDNormalizer.percieveAtomTypesAndConfigureAtoms(target);
        CDKHueckelAromaticityDetector.detectAromaticity(target);

        SMSDNormalizer.percieveAtomTypesAndConfigureAtoms(query);
        CDKHueckelAromaticityDetector.detectAromaticity(query);

        MCSPlusHandler smsd1 = new MCSPlusHandler(query, target, true, false);
        assertNotNull(smsd1.getFirstMapping());
        assertEquals(7, smsd1.getFirstMapping().size());
    }
}
