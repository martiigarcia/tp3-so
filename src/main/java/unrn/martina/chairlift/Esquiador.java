package unrn.martina.chairlift;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Esquiador extends Thread {
    private int id;
    private ReentrantLock lock;
    private Condition sillaDisponible;
    private Condition sillaLlena;


    public Esquiador(int id, ReentrantLock lock, Condition sillaDisponible, Condition sillaLlena) {
        this.id = id;
        this.lock = lock;
        this.sillaDisponible = sillaDisponible;
        this.sillaLlena = sillaLlena;
    }

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