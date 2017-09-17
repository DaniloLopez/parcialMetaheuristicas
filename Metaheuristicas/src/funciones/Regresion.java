/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danilo
 */
public class Regresion extends Funcion{
    
    double solucionIdeal = 0.001;    
    ArrayList<Double> datosArchivo;

    public Regresion(double min, double max, ArrayList<Double> datos) {
        super(min, max, "Regresion");
        this.datosArchivo = datos;
    }

    @Override
    public Individuo copy(Individuo s) {
        try {
            return (Individuo)s.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Sphere.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }       
    }

    @Override
    public boolean validateIdealSolution(Individuo s) {        
        return !(s.getFitness() <= solucionIdeal);
    }

    @Override
    public double calculateFitness(Individuo s) {
        return evaluarDatos(s.getCromosoma());
    }

    public double evaluarDatos(Double[] crom){
        ArrayList<Double> valoresY = new ArrayList<>();
        ArrayList<Double> valoresAux = new ArrayList<>();
        int con = 1;
        int aux = 1;
        for (Double valorArch : datosArchivo) {
            if( aux != 10){
                valoresAux.add(valorArch);
                aux ++;
            }else{                
                valoresY.add(evaluarFuncion(valoresAux, crom));
                //System.out.println("++++++++++++++++++++++++  cont: " + con);
                con ++;
                valoresAux.clear();
                aux = 1;
            }
        }        
        double error = calcularErrorCuadratico(valoresY);
        //System.out.println("error:  "  + error);
        return error;
    }
    
    public double evaluarFuncion(ArrayList<Double> valoresAux, Double[] crom){
        double res = 0;
        int i = 0;        
        int j = 0;
        valoresAux.add(0, 1.0);
        int tam = valoresAux.size();                                
        for (Double coeficiente : crom) {
            //System.out.println("i: " + i + "  j: " + j);            
            res = res + (coeficiente *  valoresAux.get(i) * valoresAux.get(j));
            i = i + 1;
            if(i == tam){
                j ++;
                i = j;                                                
            }
        }       
        //System.out.println("resultado " + res);
        return res;
    }

    private double calcularErrorCuadratico(ArrayList<Double> valoresY) {
        int i = 9;
        double sum = 0;
        for (Double yi : valoresY) {
            sum = sum + ((yi - datosArchivo.get(i)) * (yi - datosArchivo.get(i)) );            
            i = i + 10;
        }        
        double errCuad = sum / valoresY.size();
        return errCuad;
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
