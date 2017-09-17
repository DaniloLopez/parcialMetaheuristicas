package Archivo;

import Archivo.Archivo;
import java.util.ArrayList;

/**
 *
 * @author Danilo
 */
public class LeerArchivoPrueba {
    Archivo file;
    ArrayList<Double> datos;

    public LeerArchivoPrueba() {
        file = new Archivo();
        datos = new ArrayList<>();
    }    
    
    public ArrayList<Double> leerArchivo(String ruta){                
        file.abrirArchivo(ruta, false, false);
        String linea = file.leerArchivo();
        String s = linea;
        while(file.puedeLeer()){
            linea = file.leerArchivo();
            transformarLinea(linea);
        }
        file.cerrarArchivo();
        return datos;
    }
    
    private void transformarLinea(String linea){
        String valor = "";
        boolean flag = false;
        for (int i = 0; i < linea.length(); i++) {               
            if(linea.charAt(i) != ' '){
                flag = true;
                valor = valor + linea.charAt(i);                    
            }else{
                if (flag || (i+1 == linea.length())){
                    datos.add(formatearValor(valor));
                    flag = false;
                    valor = "";
                }
            }
        }
        datos.add(formatearValor(valor));
    }
    
    private double formatearValor(String texto){        
        try{
            String valor = texto.replace(',', '.');
            //System.out.println(Double.parseDouble(valor));
            double num = Double.parseDouble(valor);            
            return num;   
        }catch(java.lang.NumberFormatException nfe){
            System.out.println("No se puede dar formato de numero a la cadena.");
            System.exit(0);
        }     
        return 0;
    }
    
    public double redondearDecimales(double valorInicial, int numeroDecimales){                     
        int n = 1;
        for (int i = 0; i < numeroDecimales; i++) {
            n = n*10;
        }
        double rounded = (double) Math.round(valorInicial * n) / n;             
        return rounded;
    }
     
}
