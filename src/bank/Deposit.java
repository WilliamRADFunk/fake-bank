/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

import java.util.Random;

public class Deposit implements Runnable
{
    // Value altered when interrupted. Keeps thread alive while true.
    private boolean alive = true;
    private Random rand = new Random();
    private Buffer account;

    // Constructor
    public Deposit(Buffer account) {this.account = account;}
    // Manual interrupt of thread.
    public void kill() {this.alive = false;}
    public void run()
    {
        while(this.alive)
        {
            try
            {
                int amt;
                do {amt = rand.nextInt(199) + 1;}while(amt % 2 != 0);
                this.account.makeDeposit(amt);
                Thread.sleep(800);
            }
            catch (InterruptedException e)
            {
                System.out.println("DEBUG: -----Start-----\n" + e + "DEBUG: ------End------\n");
                this.alive = false;
            }
        }
    }
}
