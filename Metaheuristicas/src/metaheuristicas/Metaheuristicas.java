package metaheuristicas;

import HillClimbing.*;
import funciones.Individuo;
import Utilidades.Aleatorio;
import Archivo.EscribirArchivo;
import Utilidades.Estadistica;
import Utilidades.Resultado;
import Utilidades.ResultadoFuncion;
import Utilidades.ResultadoIndividuo;
import Archivo.LeerArchivoPrueba;
import funciones.*;
import java.util.ArrayList;

/**
 *
 * @author Danilo
 */
public class Metaheuristicas {

    /**
     * @param args the command line arguments
     */
    
    private static Estadistica objEstadistica = new Estadistica();
    private static EscribirArchivo objEscribirArchivo = new EscribirArchivo();
    private static String nombreArchivo = "resultado.txt";
    
    private static ArrayList<Resultado> resFinal = new ArrayList<>();    
    private static int dimensiones = 55;  
    private static int cantPruebas = 5 ;
    private static ArrayList<Double> datos;    
    private static Aleatorio random ;
    private static ArrayList<HillClimbingAbstract> algoritmos = new ArrayList<>();
    
    
    
    public static void main(String[] args) {     
                        
        //Numero de veces que va a realizar la busqueda
        int     EFOs = 1000000;
        
        //probabilidad de adicionar ruido(escojer artos ceros)       
        double  prob = 0.01;
        
        //amplitud para calcular aleatorio
        double  radio = 0.9;
        
        //valores maximo y minimo de busqueda
        int     min = -10;
        int     max = 10;
        
        //leer datos del archivo de verificacion
        LeerArchivoPrueba lap = new LeerArchivoPrueba();        
        datos = lap.leerArchivo("2017-09-13-Proyecto-datos-regresion.txt");                        
                        
        int     paso = 1;                        
                        
        //instancia de la clase Regresion
        Regresion regPol  = new Regresion(min, max, datos);        
                                        
        algoritmos.add(new HillClimbing(regPol));                
                
        //vector de resultados        
        ArrayList<ResultadoIndividuo> res = new ArrayList();                
        ResultadoFuncion resFun;
        
        System.out.println("Calculando...");
        for (HillClimbingAbstract algHC : algoritmos) {                    
            for (int i = 0; i < cantPruebas; i++) {
                random = new Aleatorio(i);
                //generar vector  de aleatorios
                Double vectorAleatorios[] = random.vectorAleatorioDecimalProb(
                        algHC.getFuncion().getMin(), algHC.getFuncion().getMax(), 
                        dimensiones, 0.7, 1
                );
                
                double start = System.currentTimeMillis();
                
                //iniciar algoritmo hill climbing
                resFun = algHC.inicio(new Individuo(vectorAleatorios, algHC.getFuncion(), prob, radio, paso),EFOs);                
                
                double end = System.currentTimeMillis() - start;
                
                res.add(new ResultadoIndividuo( algHC.getNonmbreFuncion(),algHC.getClass().getSimpleName(), 
                                                resFun.getIteracion(), end, resFun.getResultado()));                
                
                System.out.println("+++++  Iteracion " + i + " res " + resFun.getResultado().toString() );                
            }
            objEstadistica.calcularEstadisticas(res, algHC, dimensiones, resFinal);
            //res.clear();
            System.out.println("********encontrado:  alg -> "+algHC.getClass().getSimpleName());
        }
        
        
        //escribir el resultado en el archivo
        boolean estado = objEscribirArchivo.excribirResultados(nombreArchivo, resFinal);                           
        if(estado){
            System.out.println("Programa finalizado.");
        }else{
            System.out.println("No se completo la escritura de resultados en el archivo.");
        }
        objEscribirArchivo.excribirSolucion(nombreArchivo, res);
    }//fin clase main                                        
}