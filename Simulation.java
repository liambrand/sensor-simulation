/* Simulation
 * Sensor sample interval is 500 ms. Nominal sensor value random-walks up
 * and down from an initial value of 100; sensor "noise" = 5.
 *
 * Text output of readings and associated nominal values are output on
 * console and can be redirected to a log file for experimentation.
 */
import java.util.*;

public class Simulation {
  private DataDisplay display;
  private SensorSim sensor;
  private FaultySensorSim faultySens;
  private double sensorNom;       //nominal value -- random-walks
  private final double sensorErr; //fixed
  //private ArrayList<Double> readings = new ArrayList<Double>();
  private int iterations;
  private double total;
  private double mean;

  public Simulation(double n, double e, int regSens, int faultySens) { //constructor
    sensorNom = n;
    sensorErr = e;
    display = new DataDisplay();
    runSimulation(regSens, faultySens);
  }

  public void runSimulation(int regSens, int faultySens)  {
    Random rng = new Random();

    SensorSim[] sensors = new SensorSim[regSens + faultySens];
    
    // Create sensors
    for(int i = 0; i < sensors.length; i++) {
      // Regular sensors
      if(i < regSens) {
        sensors[i] = new SensorSim(sensorNom, sensorErr);
      }
      // Faulty sensors
      else {
        sensors[i] = new FaultySensorSim(sensorNom, sensorErr, 100, 30);
      }
      sensors[i].start();
    }
    
    //System.out.println("READING \t| NOM \t| DEVIATION \t| MEAN \t| STD DEVIATION");
    System.out.println("READING \t| NOM \t| DEVIATION |");
    
    
    /*while(true) {
      double rdg = 0;
      double standardDeviation = 0;
      double s = 0;
      
      // Get readings from sensors
      for(int i = 0; i < sensors.length; i++) {
        rdg =+ sensors[i].getRdg();
      }
      
    }*/
    
    while(true) {
      iterations++;           
      double rdg = 0;
      //double standardDeviation = 0;
      //double squared = 0;
      
      // Get readings from sensors
      for(int i = 0; i < sensors.length; i++) {
        rdg =+ sensors[i].getRdg();
      }
      
      // Get avg rdg value
      rdg = rdg/sensors.length;
      
      // Get mean rdg for all readings
      
      
      
      
      //iterations ++;
      //total += rdg;
      //mean = total / iterations;
        
      //double meanTakeaway = rdg - mean;
	    //squared += meanTakeaway * meanTakeaway;
      //standardDeviation = squared / iterations;
	    //standardDeviation = Math.sqrt(standardDeviation);
	    
	    System.out.printf("%7.2f \t(%5.1f): \t%4.1f\n", rdg, sensorNom, rdg-sensorNom);
              

      //System.out.printf("%7.2f \t(%5.1f): \t%4.1f \t%4.1f  \t%4.1f\n", rdg, sensorNom, rdg-sensorNom, mean, standardDeviation);

      //sensor:output(nominal):difference on console; can be redirected to log
      display.update(rdg, sensorNom); 
      if (rng.nextBoolean())  //nominal sensor output random-walks up & down
        sensorNom++;
      else
        sensorNom --;
      for(int i = 0; i < sensors.length; i++) {
        sensors[i].setNominal(sensorNom);
      }
      try {  // 0.5-second sleep
        Thread.sleep(500);
      } catch (InterruptedException ix) {}
   }
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Using defaults initial nominal = 100.0, noise = 5.0, regular sensors = 1, faulty sensors = 0");
      System.out.println("For other settings use java Simulation <nom> <noise> <regSens> <faultySens>");
      new Simulation(100, 5, 1, 0);
    }
    else {
	new Simulation(Double.parseDouble(args[0]), Double.parseDouble(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
    }
  } 

} //end class Simulation
