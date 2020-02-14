/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificadorprocesos;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Sistema {

    private final int MAX_MEMORIA = 4194304;
    private int memoriaDisponible = MAX_MEMORIA;
    private LinkedList<Proceso> procesoSO;
    private LinkedList<Proceso> procesoUsuario;
    private LinkedList<Proceso> procesoEjecucion;

    public Sistema() {
        procesoSO = new LinkedList<>();
        procesoEjecucion = new LinkedList<>();
        procesoUsuario = new LinkedList<>();
    }

    public boolean hayMemoria(Proceso proceso) {
        return memoriaDisponible - proceso.getMemoria() >= 0;
    }

    public boolean esMiTurno(Proceso procesos, Queue<Proceso> cola) {
        return cola.peek().equals(procesos);
    }

    private boolean hayProcesoSistema() {
            return procesoSO.size() > 0;
    }

    public synchronized void ejecutar(Proceso proceso) throws InterruptedException {
        if (proceso.isProcesoSO()) {
            ejecutarProcesoSO(proceso);
        } else {
            ejecutarProcesoUsuario(proceso);
        }
        memoriaDisponible -= proceso.getMemoria();

        imprimirEstado(proceso);
    }

    private synchronized void ejecutarProcesoUsuario(Proceso proceso) throws InterruptedException {
        procesoUsuario.add(proceso);
        
        imprimirEstado(proceso);
        
        while (!hayMemoria(proceso) || !esMiTurno(proceso, procesoUsuario) || hayProcesoSistema()) {
            wait();
        }
        
        procesoEjecucion.add(procesoUsuario.poll());
    }

    private synchronized void ejecutarProcesoSO(Proceso proceso) throws InterruptedException {
        procesoSO.add(proceso);
        
        imprimirEstado(proceso);
        
        while (!hayMemoria(proceso) || !esMiTurno(proceso, procesoSO)) {
            wait();
        }
        
        procesoEjecucion.add(procesoSO.poll());
    }

    public synchronized void finalizar(Proceso proceso) {
        procesoEjecucion.remove(proceso);

        memoriaDisponible += proceso.getMemoria();

        imprimirEstado(proceso);

        notifyAll();
    }

    private synchronized void imprimirEstado(Proceso proceso) {
        System.out.println("-------- Estado del sistema --------\n");
        System.out.println("Memoria disponible = " + memoriaDisponible);
        System.out.println("\nCola procesos usuario: " + getProcesos(procesoUsuario));
        System.out.println("\nCola procesos Sistema: " + getProcesos(procesoSO));
        System.out.println("\nProcesos en ejecucion: " + getProcesos(procesoEjecucion) + "\n");
    }

    private String getProcesos(LinkedList<Proceso> procesos) {
        StringBuilder sb = new StringBuilder();
        for (Proceso proceso : procesos) {
            sb.append(String.format(" %s (%d bytes)", proceso.getName(), proceso.getMemoria()));
        }
        return sb.toString();
    }

}
