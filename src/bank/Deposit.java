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
    private int threadId = 0;
    private Random rand = new Random();
    private Account account = null;

    public Deposit(int id, Account acct)
    {
        this.threadId = id;
        this.account = acct;
    }
    public void run()
    {
        while(this.alive)
        {
            try
            {
                account.lackOfFunds.signalAll();
                if(account.lock.tryLock())
                {
                    int amt = 0;
                    do {amt = rand.nextInt(199) + 1;}while(amt % 2 != 0);
                    account.makeDeposit(amt);
                    System.out.println("I'm depThread# " + threadId + " of $" + amt + ", and the balance is: " + account.getBalance());
                    account.lock.unlock();
                    Thread.sleep(600);
                } else
                {
                    System.out.println("Oops, busy. My bad...dep");
                    Thread.sleep(300);
                }
            }
            catch (InterruptedException e)
            {
                System.out.println("DEBUG: -----Start-----\n" + e + "DEBUG: ------End------\n");
                account.lock.unlock();
                this.alive = false;
            }
        }
    }
}
