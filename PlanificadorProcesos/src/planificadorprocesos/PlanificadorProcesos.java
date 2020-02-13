/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package planificadorprocesos;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class PlanificadorProcesos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Proceso proceso00 = new Proceso("proceso00", 2000000, true, sistema);
        Proceso proceso01 = new Proceso("proceso01", 2000000, true, sistema);
        Proceso proceso02 = new Proceso("proceso02", 2000000, false, sistema);
        Proceso proceso03 = new Proceso("proceso03", 2000000, true, sistema);
        Proceso proceso04 = new Proceso("proceso04", 2000000, false, sistema);
        Proceso proceso05 = new Proceso("proceso05", 2000000, false, sistema);
        
        proceso00.start();
        proceso01.start();
        proceso02.start();
        proceso03.start();
        proceso04.start();
        proceso05.start();
    }
    
}
