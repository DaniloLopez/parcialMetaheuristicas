/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HillClimbing;

import Utilidades.ResultadoFuncion;
import funciones.Funcion;
import funciones.Individuo;

/**
 *
 * @author Danilo
 */
public abstract class HillClimbingAbstract {       

    private Funcion funcion;

    public HillClimbingAbstract(Funcion function) {
        this.funcion = function;
    }        
    
    /**
     * inicio hill climbing steeppest ascent y steeppest ascent with replacement
     * @param individuo individuo con el que realizara las operaciones de modificar individuo
     * @param EFOs numero de veces que se realiza la exploracion
     * @return 
     */
    public abstract ResultadoFuncion inicio(Individuo individuo, int EFOs);
    
    /**
     * retorna el nombre de la funcion que se esta evaluando
     * @return nombre de la funcion
     */
    public abstract String getNonmbreFuncion();

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    
    
}



