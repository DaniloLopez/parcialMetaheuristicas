/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import HillClimbing.HillClimbingAbstract;
import java.util.ArrayList;

/**
 *
 * @author Danilo
 */
public class Estadistica {
        
    /**
     * calcular estadisticas de los valores almacenados en la lista valoresEntrada
     * @param valoresEntrada lista de informacion
     * @param algHC nombre de funcion utilizada 
     * @param dimensiones nuemro de dimensiones del cromosoma
     * @param resFinal lista para almacenar resultado
     */
    public  void calcularEstadisticas(ArrayList<ResultadoIndividuo> valoresEntrada, HillClimbingAbstract algHC, int dimensiones,  ArrayList<Resultado> resFinal) {
        double promItr = promedioIteraciones(valoresEntrada);
        double mejor = mejorOptimo(valoresEntrada);
        double peor = peorOptimo(valoresEntrada);
        double promedios = promedioOptimos(valoresEntrada);
        double desviacion = desviacionOptimos(valoresEntrada);
        double tiempo = tiempoPromedio(valoresEntrada);
        
        resFinal.add(new Resultado(
                valoresEntrada.get(0).getFuncion(),
                valoresEntrada.get(0).getAlgoritmo(),
                dimensiones,
                valoresEntrada.get(0).getIteracion(),
                mejor,
                peor,
                promedios,
                desviacion,
                tiempo));
    }
    
    /**
     * calcual el promedio de las iteraciones
     * @param resultados lista con informacion
     * @return promedio de iteraciones
     */
    public double promedioIteraciones(ArrayList<ResultadoIndividuo> resultados) {
        double sum = 0;
        for (ResultadoIndividuo resultado : resultados) {
            sum = sum + resultado.getIteracion();
        }
        return sum / resultados.size();
    }

    /**
     * calcula el mejor optimo de la lista resultados
     * @param resultados
     * @return valor del mejor fitness
     */
    public double mejorOptimo(ArrayList<ResultadoIndividuo> resultados) {
        double mejor = resultados.get(0).getIndviduo().getFitness();        
        for (ResultadoIndividuo resultado : resultados) {
            if(resultado.getIndviduo().getFitness() < mejor){
                mejor = resultado.getIndviduo().getFitness();                
            }            
        }        
        return mejor;
    }

    /**
     * calcual el peor optimo de la informacion de la lista
     * @param resultados
     * @return valor del peor fitness
     */
    public double peorOptimo(ArrayList<ResultadoIndividuo> resultados) {
        double peor = resultados.get(0).getIndviduo().getFitness();
        for (ResultadoIndividuo resultado : resultados) {
            if(resultado.getIndviduo().getFitness() > peor){
                peor = resultado.getIndviduo().getFitness();
            }
        }
        return peor;
    }

    /**
     * calcula el promedio de los optimos
     * @param resultados
     * @return valor con el promedio de fitness
     */
    public double promedioOptimos(ArrayList<ResultadoIndividuo> resultados) {
        double suma = 0;
        for (ResultadoIndividuo resultado : resultados) {
            suma += resultado.getIndviduo().getFitness();
        }
        return suma/resultados.size();
    }

    /**
     * calcula la desviacion estandar de los optimos
     * @param resultados
     * @return valor de la desviacion de el fitness
     */
    public double desviacionOptimos(ArrayList<ResultadoIndividuo> resultados) {
        double prom = promedioOptimos(resultados);
        double media = 0;
        for (ResultadoIndividuo resultado : resultados) {
            media += Math.pow((resultado.getIndviduo().getFitness() - prom),2);            
        }
        return media/(resultados.size()-1);
    }

    /**
     * calucal el timepo promedio de ejecucion de cada iteracion
     * @param resultados
     * @return  promedio de los tiempos de iteracion
     */
    public double tiempoPromedio(ArrayList<ResultadoIndividuo> resultados) {
        double suma = 0;
        for (ResultadoIndividuo resultado : resultados) {
            suma += resultado.getTiempo();
        }
        return suma/resultados.size();
    }        
}