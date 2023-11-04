package unrn.martina.chairlift;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        int cantidadEsquiadores = 12;
        int cantidadSillas = 3;

        ReentrantLock lock = new ReentrantLock(true);
        ReentrantLock lockk = new ReentrantLock(true);
        Condition sillaDisponible = lock.newCondition();
        Condition sillaLlena = lock.newCondition();
        ReentrantLock contadorLock = new ReentrantLock();

        for (int i = 1; i <= cantidadEsquiadores; i++) {
            new Esquiador(i, lock, sillaDisponible, sillaLlena, contadorLock, lockk).start();
        }

        for (int i = 1; i <= cantidadSillas; i++) {
            new Silla(i, lock, sillaDisponible, sillaLlena).start();
        }

    }
}

//        Silla[] sillas = new Silla[cantidadSillas];
//        Thread[] hilosSillas = new Thread[cantidadSillas];
//
//        for (int i = 0; i < cantidadSillas; i++) {
//            sillas[i] = new Silla(i, lock, sillaDisponible, sillaLlena, monitor);
//            hilosSillas[i] = new Thread(sillas[i]);
//            hilosSillas[i].start();
//        }