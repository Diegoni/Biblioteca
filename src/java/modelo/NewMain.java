/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.Normalizer;

/**
 *
 * @author Tommy
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nombre = "Tomás Ezequiel";
        String apellido = "Camargo";
        
        String[] arregloNombre = nombre.split(" ");
        String[] arregloApellido = apellido.split(" ");
        
        String temp = arregloNombre[0].toLowerCase()+arregloApellido[0].toLowerCase();
        
        temp = Normalizer.normalize(temp, Normalizer.Form.NFD);
        temp = temp.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        System.out.println(temp);
    }
    
}
