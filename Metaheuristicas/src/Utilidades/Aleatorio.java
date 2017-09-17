package Utilidades;

import funciones.Individuo;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Danilo
 */
public class Aleatorio {
    
    public int semilla ;
    public static Random numAleatorio;            
    
    public Aleatorio(int sem){   
        if(null == numAleatorio){
            this.semilla = sem;
            numAleatorio = new Random(semilla);
        }
    }        
    
    public double nextAleatorio(int cantDec){
        double numero = numAleatorio.nextDouble();        
        String number = String.format("%."+cantDec+"f", numero);
        String str = String.valueOf(number);
        String num = str.substring(0, str.indexOf(','));        
        String numDec = str.substring(str.indexOf(',') + 1);        
        
        return Double.parseDouble(num +"." + numDec);
    }
    
    
    
    /**
     * funcion para calcular un vector de numero aleatorios     
     * @param min valor minimo del rango
     * @param max valor maximo del rango
     * @param cantidad
     * @param prob probabilidad de colocar un numero dferente de cero
     * @param cantDecimales cantidad de decimales a manejar
     * @return numero aleatorio entre min y max
     */    
    public Double[] vectorAleatorioDecimalProb(double min, double max, int cantidad, double prob, int cantDecimales){                
        ArrayList<Double> vec = new ArrayList<>();   
        
        //ciclo desde cero hasta cantidad-1
        for (int i = 0; i < cantidad; i++) {                        
            if(numAleatorio.nextDouble() >= prob){
                // Numero real aleatorio entre min y max                
                vec.add(nextAleatorio(cantDecimales) * (max - min) + min);           
            }else{
                vec.add(0.0);
            }                    
        }                        
        return  vec.toArray(new Double[cantidad]);
    }
    
    public Double[] vectorAleatorioDecimal(double min, double max, int cantidad){
        ArrayList<Double> vec = new ArrayList<>();                   
        //ciclo desde cero hasta cantidad-1
        for (int i = 0; i < cantidad; i++) {                        
            if(numAleatorio.nextDouble() >= 0.7){
                // Numero real aleatorio entre min y max
                vec.add(numAleatorio.nextDouble()*(max - min) + min);           
            }else{
                vec.add(0.0);
            }                    
        }                        
        return  vec.toArray(new Double[cantidad]);
    }
    
    public double numberRandomByGaussian(){
        double u = 0;
        double o = 0;
        double x = 0, y = 0, w = 0;
        
        do{
            x = numAleatorio.nextDouble();
            y = numAleatorio.nextDouble();
            w = (x*x) + (y*y);
        }while(0 < w || w < 1);
        
        double g = u + x*o*(Math.sqrt(-2*((Math.log(w))/w)));
        double h = u + y*o*(Math.sqrt(-2*((Math.log(w))/w)));
        
        return g;                
    }
    
    
    /**
     * perturba un valor dependiendo del radio del individuo
     * @param i
     * @return 
     */
    public double random(Individuo i){
        //encontrar distancia entre -r y r
        double distancia = 2*i.getHalfRangeNoise();
        //encontrar aleatorio etre -r y r
        double ale = numAleatorio.nextDouble()*distancia - i.getHalfRangeNoise();
        return ale;
    }
    
    
    /**
     * funcion para calcular un numero entero aleatorio entre un rango de valores
     * @param semilla
     * @param min valor minimo del rango
     * @param max valor maximo del rango
     * @return numero aleatorio entre min y max
     */    
    public int IntegerRandom(int semilla, int min, int max){                
        // Numero entero entre min y max
        int al = numAleatorio.nextInt(max-min) + min;                                                
        return (int) (Math.random()*max + min);
    }       
    
    
    
}
