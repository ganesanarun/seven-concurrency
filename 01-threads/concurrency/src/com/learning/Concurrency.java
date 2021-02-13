package com.learning;

import static java.lang.Thread.sleep;

public class Concurrency {

    public static void main(String[] args) throws InterruptedException {
        helloWorld();
        synchronised(); // race condition - (behavior that depends on the relative timing of operations)
        puzzle();
        deadlocked();
    }

    private static void deadlocked() throws InterruptedException {
        Chopstick first = new Chopstick(1);
        Chopstick second = new Chopstick(2);
        Chopstick third = new Chopstick(3);
        Chopstick fourth = new Chopstick(4);
        Chopstick fifth = new Chopstick(5);

        Philosopher philosopher = new Philosopher(first, second);
        Philosopher philosopher2 = new Philosopher(second, first);
        Philosopher philosopher3 = new Philosopher(third, fourth);
        Philosopher philosopher4 = new Philosopher(fourth, fifth);
        Philosopher philosopher5 = new Philosopher(fifth, first);

        philosopher.start();
        philosopher2.start();
        philosopher3.start();
        philosopher4.start();
        philosopher5.start();
        philosopher.join();
        philosopher2.join();
        philosopher3.join();
        philosopher4.join();
        philosopher5.join();
    }

    static boolean answerReady = false;
    static int answer = 0;
    private static void puzzle() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            answer = 42;
            answerReady = true;
        });

        Thread t2 = new Thread(() -> {
            if (answerReady) {
                System.out.printf("The meaning of life is: %d\n", answer);
            } else {
                System.out.println("I don't know the answer");
            }
        });

        Thread t3 = new Thread(() -> {
            while (!answerReady) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("The meaning of life is: " + answer);
        });
        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();
    }

    private static void synchronised() throws InterruptedException {
        final Counter counter = new Counter();
        class CountingThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            }
        }

        CountingThread countingThread1 = new CountingThread();
        CountingThread countingThread2 = new CountingThread();
        countingThread1.start();
        countingThread2.start();
        countingThread1.join();
        countingThread2.join();
        System.out.printf("Counter: %d%n", counter.getCount());
    }

    static class Counter {
        private int count;
        public synchronized void increment() {
            ++count;
        }
        public int getCount() {
            return count;
        }
    }

    private static void helloWorld() throws InterruptedException {
        Thread myThread = new Thread(() -> System.out.println("Hello from new thread!"));
        myThread.start();
        Thread.yield();
        System.out.println("Hello from main thread!");
        myThread.join();
    }
}

