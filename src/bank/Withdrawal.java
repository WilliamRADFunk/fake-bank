/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

import java.util.Random;

public class Withdrawal implements Runnable
{
    // Value altered when interrupted. Keeps thread alive while true.
    private boolean alive = true;
    private int threadId = 0;
    private Random rand = new Random();
    private Account account = null;

    public Withdrawal(int id, Account acct)
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
                int amt = 0;
                if(account.lock.tryLock())
                {
                    do {
                        amt = rand.nextInt(199) + 1;
                    } while (amt % 2 != 0);
                    if (account.getBalance() >= amt)
                    {
                        account.makeWithdrawal(amt);
                        System.out.println("I'm witThread# " + threadId + " of $" + amt + ", and the balance is: $" + account.getBalance());
                        account.lock.unlock();
                    } else
                    {
                        System.out.println("I'm witThread# " + threadId + " of $" + amt + ", FUNDS: $" + account.getBalance() + " INSUFFICIENT");
                        account.lackOfFunds.await();
                        account.lock.unlock();
                    }
                    Thread.sleep(600);
                }
                else
                {
                    System.out.println("Oops, busy. My bad...wit");
                    Thread.sleep(300);
                }
            }
            catch (InterruptedException e)
            {
                System.out.println("DEBUG: -----Start-----\n" + e + "DEBUG: ------End------\n");
                this.alive = false;
            }
        }
    }
}
