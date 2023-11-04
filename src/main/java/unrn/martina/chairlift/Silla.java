package unrn.martina.chairlift;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Silla extends Thread {
    private int id;
    private ReentrantLock lock;
    private Condition sillaDisponible;
    private Condition sillaLlena;
    private int contador = 0;

    public Silla(int id, ReentrantLock lock, Condition sillaDisponible, Condition sillaLlena) {
        this.id = id;
        this.lock = lock;
        this.sillaDisponible = sillaDisponible;
        this.sillaLlena = sillaLlena;
    }

    @Override
    public void run() {
        try {
            lock.lock();

            System.out.println("Silla #" + id + " disponible. Esperando esquiadores...");

            while (contador < 4) {
                sillaDisponible.signal();
                contador++;
            }
            if (contador == 4) {
                sillaLlena.await();
            }

            System.out.println("Silla #" + id + " llena. Subiendo...");
            Thread.sleep(5000); // Tiempo que tarda en subir la silla

            System.out.println("Silla bajando");

            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}