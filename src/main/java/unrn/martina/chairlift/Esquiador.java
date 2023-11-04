package unrn.martina.chairlift;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Esquiador extends Thread {
    private int id;
    private ReentrantLock contadorEsquiadoresLock;
    private ReentrantLock lock;
    private Condition aerosillaDisponible;
    private Condition aerosillaLlena;


    public Esquiador(int id, ReentrantLock lock, Condition aerosillaDisponible, Condition aerosillaLlena, ReentrantLock contadorEsquiadoresLock) {
        this.id = id;
        this.lock = lock;
        this.aerosillaDisponible = aerosillaDisponible;
        this.aerosillaLlena = aerosillaLlena;
        this.contadorEsquiadoresLock = contadorEsquiadoresLock;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("Llego el esquiador "+ id);
            aerosillaDisponible.await();
            System.out.println("El esquiador #" + id+ " se sento en la aerosilla");

            contadorEsquiadoresLock.lock();
            Main.CONTADOR_ESQUIADORES++;
            if (Main.CONTADOR_ESQUIADORES == 4) {
                System.out.println("*Ultimo esquiador");
                aerosillaLlena.signal(); // El último en sentarse señaliza a la silla
                Main.CONTADOR_ESQUIADORES = 0;
            }
            contadorEsquiadoresLock.unlock();

            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}