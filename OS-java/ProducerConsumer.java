//producer-consumer concept in java

import java.util.List;
import java.util.ArrayList;

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> sharedList = new ArrayList<Integer>();
        Thread thread1 = new Thread(new Producer(sharedList));
        Thread thread2 = new Thread(new Consumer(sharedList));
        thread1.start();
        thread2.start();
        // thread1.join();
        // thread2.join();
        }
}
// producer class
class Producer implements Runnable {
    List<Integer> sharedList = null;
    //set a size of sharedlist which means producer can produce atmost 5 element ata atime
    final int MAX_SIZE = 5;
    private int i = 0;

    // constructor or producer class
    public Producer(List<Integer> sharedList) {
        super();
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //calling produce function in run method
                produce(i++);
            } catch (InterruptedException exception) {

            }
        }
    }

    public void produce(int i) throws InterruptedException {
        synchronized (sharedList) {
            //if shared list is full then it is waiting for consumer
            while (sharedList.size() == MAX_SIZE) {
                System.out.println("Sharedlist is Full...waiting for the consumer to be consumed");
                sharedList.wait();
            }
        }
        synchronized (sharedList) {
            System.out.println("producer produced element" + i);
            //if not full then add in sharedlist and notify to consumer
            sharedList.add(i);
            Thread.sleep(500);
            sharedList.notify();
        }
    }
}

// consumer class
class Consumer implements Runnable {
    List<Integer> sharedList = null;

    // constructor or consumer class
    public Consumer(List<Integer> sharedList) {
        super();
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException exception) {

            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (sharedList) {
            while (sharedList.isEmpty()) {
                //if sharedlist is empty waiting for producer
                System.out.println("Sharedlist is empty...waiting for the producer  to be produced");
                sharedList.wait();
            }
        }
        synchronized (sharedList) {

            Thread.sleep(1000);
            System.out.println("Consumed the element" + sharedList.remove(0));
            sharedList.notify();
        }
    }

}
