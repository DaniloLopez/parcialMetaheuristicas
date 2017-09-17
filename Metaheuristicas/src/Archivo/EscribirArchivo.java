/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import Archivo.Archivo;
import Utilidades.Resultado;
import Utilidades.ResultadoIndividuo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Danilo
 */
public class EscribirArchivo {
    
    String encabezado;
    Date objDate;
    DateFormat hourdateFormat;
    Archivo file;
    String nombre ;

    public EscribirArchivo() {
        file = new Archivo();                
        encabezado = "FUNCIÓN\t\tALGORITMO\t    D\tPRO.ITR\t\t MEJOR OPTIMO\t\tPEOR OPTIMO\t\tPROM OPTIMOS\t     DESVIACION OPTIMOS\t     TIEMPO PROMEDIO";
    }
    
    
    /**
     * Escribe los datos de una lista en un nuevo archivo creado con el nombre deseado
     * @param nom nombre del archivo
     * @param resFinal lista con informacion
     * @return true si la operacon se realizo, false si la operacion no se realizo
     */
    public boolean excribirResultados(String nom,  ArrayList<Resultado> resFinal) {                
        objDate = new Date();        
        hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = hourdateFormat.format(objDate);
        
        hourdateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
        this.nombre = hourdateFormat.format(objDate) + "  " +nom;
        try{
            file.abrirArchivo(nombre, true, false);
            file.escribirArchivo(fecha, true);            
            file.escribirArchivo(encabezado, true);
            file.escribirArchivo("", true);
            String nombreFun = resFinal.get(0).getFuncion();
            for (Resultado res : resFinal) {                
                if(!nombreFun.equals(res.getFuncion())){
                    file.escribirArchivo("", true);
                    nombreFun = res.getFuncion();
                }
                encabezado = organizarCadena(res);
                //escribir linea en el archivo
                file.escribirArchivo(encabezado, true);                
            }
            file.escribirArchivo("\n", false);        
        }catch(Exception e){
            System.out.println("error manipulando un archivo. ex = " + e.getMessage());
            return false;
        }finally{
         // En el finally se cierra el fichero, para asegurarse que se cierra si todo va bien o si salta una excepcion.
            try{                    
               if( null != file ){   
                  file.cerrarArchivo();
               }
            }catch (Exception e2){             
                System.out.println("error cerando un archivo. ex = " + e2.getMessage());
                return false;
            }
        }
        return true;
    }
    
    /**
     * Organiza el contenido del objeto res para que se escriba en cada linea del archivo
     * @param res lista con informacion
     * @return cadena de texto con informacion de objeto res
     */
    private  String organizarCadena (Resultado res){
        String cadena = formatoCadenaTexto(res.getFuncion(),15) +
                        formatoCadenaTexto(res.getAlgoritmo(),20) + 
                        res.getD() + 
                        " " + formatoDecimales(res.getPromedioIteracion(),1,6)+
                        " " + formatoDecimales(res.getMejor_optimo(),10,10) + 
                        " " + formatoDecimales(res.getPeor_optimo(),10,10) + 
                        " " + formatoDecimales(res.getPromedioOptimos(), 10,10) + 
                        " " + formatoDecimales(res.getDesviacionOptimos(),10,15) + 
                        " " + formatoDecimales(res.getTiempoPromedio(), 3,1);        
        return cadena;
    }
    
    /**
     * Dar formato apropiado a los decimales para facilitar la lectura al usuario
     * @param valorInicial nuero decimal a formatear
     * @param numeroDecimales cantidad de decimales que debe tener el numero de salida
     * @param espacios longitud de la cadena de salida
     * @return valorInicial formateado con decimales = numeroDecimales
     */
    public String formatoDecimales(double valorInicial, int numeroDecimales ,int espacios) {                
        String number = String.format("%."+numeroDecimales+"f", valorInicial);
        String str = String.valueOf(number);
        String num = str.substring(0, str.indexOf(','));        
        String numDec = str.substring(str.indexOf(',') + 1);
        
        for (int i = num.length(); i < espacios; i++) {
            num = " " + num;
        }
        
        for (int i = numDec.length(); i < espacios;  i++) {
            numDec = numDec + " ";
        }
        
        return num +"." + numDec;
    }
    
    /**
     * rellenar una cadena con espacios dependiendo el tamaño deseado
     * @param cadena cadena de texto 
     * @param tamaño longitud que se desea tenga la cadena
     * @return cadena de longitud: tamaño
     */
    public  String formatoCadenaTexto(String cadena, int tamaño){        
        for (int i = cadena.length(); i < tamaño; i++) {
            cadena= cadena + " ";
        }
        return cadena;        
    }

    
    
    
    
        

    public void excribirSolucion(String nombreArchivo, ArrayList<ResultadoIndividuo> res) {                                    
        ResultadoIndividuo mejor = res.get(0);        
        for (ResultadoIndividuo re : res) {
            if(re.getIndviduo().getFitness() < mejor.getIndviduo().getFitness()){
                mejor = re;
            }
        }                
        try{
            file.abrirArchivo(this.nombre, true, true);
            file.escribirArchivo("", true);
            file.escribirArchivo("Resultado: [     ", false);
            for (Double valor : mejor.getIndviduo().getCromosoma()) {
                //escribir linea en el archivo
                file.escribirArchivo(valor + "     ", false);
            }
            file.escribirArchivo("]\n", false);
        }catch(Exception e){
            System.out.println("Error manipulando un archivo. ex = " + e.getMessage());            
        }finally{
         // En el finally se cierra el fichero, para asegurarse que se cierra si todo va bien o si salta una excepcion.
            try{                    
               if( null != file ){   
                  file.cerrarArchivo();
               }
            }catch (Exception e2){             
                System.out.println("error cerando un archivo. ex = " + e2.getMessage());                
            }
        }        
    }
}
