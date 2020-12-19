package by.it.evstratov.jd02_02;

import java.util.List;

public class Cashier implements Runnable{

    private final int number;
    private volatile static int openCashiers = 0;

    public Cashier(int number) {
        this.number = number;
        addCashier();
    }

    private static void addCashier(){
        synchronized (Cashier.class){
            openCashiers++;
        }
    }

    @Override
    public void run() {
        System.out.println(this + "opened");

        while (!Dispatcher.marketIsClosed()){
            Buyer buyer = QueueBuyers.extract();
            if(buyer != null){
                int t = Helper.getRandom(2000,5000);
                Helper.sleep(t);
                Dispatcher.printCheck(this,buyer);
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (buyer){
                    buyer.setRunnable(true);
                    buyer.notify();
                }
            }else{
                synchronized (Dispatcher.lock){
                    if(openCashiers == 1){
                        System.out.println(this + "в ожидании покупателей (единственная открытая касса)");
                        try {
                            if(Dispatcher.marketIsOpened()){
                                Dispatcher.lock.wait();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        openCashiers--;
                        Dispatcher.clearNumberForCashier(number);
                        System.out.println(this + "закрывается. Осталось открытых касс - "+openCashiers);
                        break;
                    }
                }
            }
        }
        System.out.println(this + "closed");
    }

    @Override
    public String toString() {
        return "Cashier №" + number + " ";
    }

    public static int getOpenCashiers() {
        synchronized (Cashier.class){
            return openCashiers;
        }
    }

    public int getNumber() {
        return number;
    }
}