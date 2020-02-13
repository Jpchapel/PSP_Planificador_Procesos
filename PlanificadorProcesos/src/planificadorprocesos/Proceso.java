/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package planificadorprocesos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Proceso extends Thread{
    private int memoria;
    private boolean procesoSO;
    private Sistema sistema;

    public Proceso(String name, int memoria, boolean procesoSO, Sistema sistema) {
        super(name);
        this.memoria = memoria;
        this.procesoSO = procesoSO;
        this.sistema = sistema;
    }

    public int getMemoria() {
        return memoria;
    }

    public boolean isProcesoSO() {
        return procesoSO;
    }

    @Override
    public void run() {
        try {
            sistema.ejecutar(this);
            
            Thread.sleep((long) (Math.random() * 150));
        } catch (InterruptedException ex) {
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            sistema.finalizar(this);
        }
    }
        
}
