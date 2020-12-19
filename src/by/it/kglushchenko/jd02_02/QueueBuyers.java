package by.it.kglushchenko.jd02_02;

import java.util.ArrayDeque;
import java.util.Deque;

public class QueueBuyers {

    // нужно обеспечить безопасное добавление несклькиз покупателей
    private static final Deque<Buyer> deque = new ArrayDeque<>(); //ok? ок!
    // если использовать пенсионеров - нужно использовать LinkedList

    // из какого-то потока добавляют покупателя
    // внутри этой очереди может прийти одновременно 2 покупателя и одновременно доктукиваться к методу add
    // значит ему нужно обеспечить потокобезопасность

    static synchronized void add(Buyer buyer){   // создали критическую секцию с помощьб synchronized
        deque.addLast(buyer); // добавляет в конец покупателя
    }

    static synchronized Buyer extract(){   // возвращает из очереди  кого-то
        // remove не используем - генерирует Exception если очередь пустая
        return deque.pollFirst(); // возвращает null если в очереди никого нет. Этот метод будет вызывать кассир
        // когда кассир будет извлекать, он будет проверять, есть там такой покупатель или нет
        // если он есть, то он его обслуживает, если его нет - кассир делает что-то свое...
    }

    // никто не влезет внуть, пока кто-то обладает мониторами add и extract
}