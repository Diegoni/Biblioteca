/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComprobarCaracteresEspeciales;

import controlador.ControladorAutor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebastian
 */
public class ComprobarCaracteresEspecialesTest {
    
    ControladorAutor controlador;
    public ComprobarCaracteresEspecialesTest() {
        controlador = new ControladorAutor();

    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void letrasTest(){
        String [] palabras ={"a","C"};
        boolean resultado = controlador.comprobarCaracteresEspeciales(palabras);
        boolean resultadoEsperado = false;
        assertEquals(resultadoEsperado, resultado);    
    }
    @Test
    public void palabrasTest(){
        String [] palabras ={"Hola","Chau"};
        boolean resultado = controlador.comprobarCaracteresEspeciales(palabras);
        boolean resultadoEsperado = false;
        assertEquals(resultadoEsperado, resultado);
    }
    @Test
    public void palabrasLatinasTest(){
        String [] palabras ={"Sebastián","Matías","ñ"};
        boolean resultado = controlador.comprobarCaracteresEspeciales(palabras);
        boolean resultadoEsperado = false;
        assertEquals(resultadoEsperado, resultado);
    }
    @Test
    public void palabrasConComas(){
        String [] palabras ={"Alomenjor,","d;e"};
        boolean resultado = controlador.comprobarCaracteresEspeciales(palabras);
        boolean resultadoEsperado = true;
        assertEquals(resultadoEsperado, resultado);
    }
    @Test
    public void numerosTest(){
        String [] palabras ={"1","3"};
        boolean resultado = controlador.comprobarCaracteresEspeciales(palabras);
        boolean resultadoEsperado = false;
        assertEquals(resultadoEsperado, resultado);    
    }
    
    @Test
    public void guionTest(){
        String [] palabras ={"1200","Lit-9"};
        boolean resultado = controlador.comprobarCaracteresEspeciales(palabras);
        boolean resultadoEsperado = false;
        assertEquals(resultadoEsperado, resultado);    
    }
    @Test
    public void espacio(){
        String [] palabras ={"1"," "};
        boolean resultado = controlador.comprobarEspaciosEnBlanco(palabras);
        boolean resultadoEsperado = true;
        assertEquals(resultadoEsperado, resultado);        
    }
    @Test
    public void espacio2(){
        String [] palabras ={"1",""};
        boolean resultado = controlador.comprobarEspaciosEnBlanco(palabras);
        boolean resultadoEsperado = true;
        assertEquals(resultadoEsperado, resultado);        
    }@Test
    public void espacio3(){
        String [] palabras ={"1","1"};
        boolean resultado = controlador.comprobarEspaciosEnBlanco(palabras);
        boolean resultadoEsperado = false;
        assertEquals(resultadoEsperado, resultado);        
    }
}
