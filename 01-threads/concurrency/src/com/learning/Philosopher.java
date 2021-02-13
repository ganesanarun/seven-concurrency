package com.learning;

import java.util.Random;

public class Philosopher extends Thread {

    private final Chopstick left;
    private final Chopstick right;

    Philosopher(Chopstick left, Chopstick right) {
        if (left.getId() < right.getId()) {
            this.left = left;
            this.right = right;
        } else {
            this.left = right;
            this.right = left;
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    System.out.println("Picked up both chop sticks");
                }
            }
        }
    }
}
