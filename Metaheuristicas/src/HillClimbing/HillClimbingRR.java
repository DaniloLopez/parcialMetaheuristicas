    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HillClimbing;

import Utilidades.Aleatorio;
import Utilidades.ResultadoFuncion;
import funciones.Funcion;
import funciones.Individuo;

/**
 *
 * @author Danilo
 */
public class HillClimbingRR extends HillClimbingAbstract{
    
        
    private Individuo s;
    private Individuo r;
    private Individuo best; 
    private double t;
    
    Aleatorio ut = new Aleatorio(0);

    public HillClimbingRR(Funcion function) {
        super(function);        
        this.s = null;
        this.r = null;
        this.best = null;
    }        

    @Override
    public ResultadoFuncion inicio(Individuo individuo, int EFOs) {
        int iteracion = -1;
        s = individuo;
        best = super.getFuncion().copy(s);
        double time = 20;
        do{                        
            do{
                r = individuo.tweak(s, super.getFuncion().getMin(), super.getFuncion().getMax());
                if (r.getFitness() < s.getFitness()){
                    s = r;
                } 
                time --;
            }while(super.getFuncion().validateIdealSolution(s) && time < 0);
            time = 20;
            if (s.getFitness() < best.getFitness()){
                best = super.getFuncion().copy(s);
            } 
            s = findRandomIndividuo(s);
            iteracion++;            
        }while(super.getFuncion().validateIdealSolution(best) && iteracion < EFOs);        
        return new ResultadoFuncion(best, iteracion);
    }
    
    /**
     * encuentra un individuo aleatorio
     * @param ind nesesario para conocer la dimension, ruido, rango y paso
     * @return individuo con cromosoma aleatorio
     */
    public Individuo findRandomIndividuo(Individuo ind){        
        int semilla = (int) (Math.random() * 100);
        int dimensiones = ind.getCromosoma().length;
        Double vectorAleatorios[] = ut.vectorAleatorioDecimal(super.getFuncion().getMin(), super.getFuncion().getMax(), dimensiones);
        return new Individuo(vectorAleatorios, super.getFuncion(), ind.getProbabilitiAddNoise(),ind.getHalfRangeNoise(),ind.getPaso());
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }
    
    @Override
    public String getNonmbreFuncion() {
        return super.getFuncion().getNombreFuncion();
    }
    
}
