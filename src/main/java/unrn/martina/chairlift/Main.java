package unrn.martina.chairlift;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static int CONTADOR_ESQUIADORES = 0;

    public static void main(String[] args) throws InterruptedException {
        int cantidadEsquiadores = 12;
        int cantidadAerosillas = 3;
        Semaphore barreraDeAerosillas = new Semaphore(1);

        ReentrantLock lock = new ReentrantLock(true);
        Condition aerosillaDisponible = lock.newCondition();
        Condition aerosillaLlena = lock.newCondition();
        ReentrantLock contadorEsquiadoresLock = new ReentrantLock(true);


        for (int i = 1; i <= cantidadEsquiadores; i++) {
            new Esquiador(i, lock, aerosillaDisponible, aerosillaLlena, contadorEsquiadoresLock).start();
        }

        for (int i = 1; i <= cantidadAerosillas; i++) {
            new Aerosilla(i, lock, aerosillaDisponible, aerosillaLlena, barreraDeAerosillas).start();
        }

    }
}
