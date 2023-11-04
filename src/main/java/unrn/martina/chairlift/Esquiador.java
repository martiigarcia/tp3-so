package unrn.martina.chairlift;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Esquiador extends Thread {
    private int id;
    private ReentrantLock lock;
    private ReentrantLock lockk;
    private Condition sillaDisponible;
    private Condition sillaLlena;
    private ReentrantLock contadorLock;
    private int contador;

    public Esquiador(int id, ReentrantLock lock, Condition sillaDisponible, Condition sillaLlena, ReentrantLock contadorLock, ReentrantLock lockk) {
        this.id = id;
        this.lock = lock;
        this.lockk = lockk;
        this.sillaDisponible = sillaDisponible;
        this.sillaLlena = sillaLlena;
        this.contadorLock = contadorLock;
        this.contador = 0;
    }

    // Run de objeto Esquiador:
    @Override
    public void run() {
        try {
            lock.lock();
            sillaDisponible.await();
            System.out.println("ID esquiador: " + id);

            sillaLlena.signal(); // El último en sentarse señaliza a la silla

            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}