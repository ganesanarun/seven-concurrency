package com.learning;

public class Concurrency {

    public static void main(String[] args) throws InterruptedException {
        helloWorld();
    }

    private static void helloWorld() throws InterruptedException {
        Thread myThread = new Thread(() -> System.out.println("Hello from new thread!"));
        myThread.start();
        Thread.yield();
        System.out.println("Hello from main thread!");
        myThread.join();
    }
}
