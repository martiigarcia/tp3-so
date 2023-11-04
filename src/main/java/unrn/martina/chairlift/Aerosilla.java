package unrn.martina.chairlift;


import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Aerosilla extends Thread {
    private int id;
    private ReentrantLock lock;
    private Condition aerosillaDisponible;
    private Condition aerosillaLlena;
    private Semaphore barreraDeAerosillas;
    private int contadorEsquiadoresLock = 0;

    public Aerosilla(int id, ReentrantLock lock, Condition aerosillaDisponible, Condition aerosillaLlena, Semaphore barreraDeAerosillas) {
        this.id = id;
        this.lock = lock;
        this.aerosillaDisponible = aerosillaDisponible;
        this.aerosillaLlena = aerosillaLlena;
        this.barreraDeAerosillas = barreraDeAerosillas;
    }

    @Override
    public void run() {
        try {
            barreraDeAerosillas.acquire();
            lock.lock();
            System.out.println("Aerosilla #" + id + " disponible. Esperando esquiadores...");

            while (contadorEsquiadoresLock < 4) {
                aerosillaDisponible.signal();
                contadorEsquiadoresLock++;
            }
            if (contadorEsquiadoresLock == 4) {
                aerosillaLlena.await();
            }

            System.out.println("Aerosilla #" + id + " llena. Subiendo...");
            System.out.println("Dejando los esquiadores en la cima de la montaña");
            Thread.sleep(1500); // Tiempo que tarda en subir la silla
            System.out.println("Aerosilla bajando");
            System.out.println("......................");

            lock.unlock();
            barreraDeAerosillas.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}