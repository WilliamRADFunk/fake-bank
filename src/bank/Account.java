/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

import java.util.concurrent.locks.*;

public class Account implements bank.Buffer {
    // Mutual Exclusion Lock. Keeps all threads synchronized without using the synchronized keyword.
    private ReentrantLock lock = new ReentrantLock();
    // Represents the lock's conditions.
    private Condition canRead = lock.newCondition();
    private Condition canWithdrawal = lock.newCondition();
    // Bank account balance. This is the critical section value protected by Semaphore.
    private int balance = 0;
    // Conditional determining if thread is currently accessing the account.
    private boolean occupied = false;
    private String log = "";

    // Constructor
    public Account() {}
    // Account has enough money, now a withdrawal is made.
    public void makeWithdrawal(int value)
    {
        lock.lock();
        try
        {
            while(occupied) {canWithdrawal.await();}
            occupied = true;
            if(value <= balance)
            {
                balance -= value;
                String transaction;
                if(value < 10) transaction = "\t\t\t\t\t\t\t\t\t\tWithdrawal ($" + value + ")\t\t\t\t\t$" + balance +
                                             "\n" + "-------------------------------------------------------------" +
                                             "---------------------------------------------------------------\n";
                else transaction = "\t\t\t\t\t\t\t\t\t\tWithdrawal ($" + value + ")\t\t\t\t\t$" + balance + "\n" +
                                   "---------------------------------------------------------------------------" +
                                   "-------------------------------------------------\n";
                setLog(transaction);
            }
            else
            {
                String transaction = "\t\t\t\t\t\t\t\t\t\tWithdrawal ($" + value + ")\t\t\t\t\tINSUFFICIENT FUNDS!\n" +
                                     "------------------------------------------------------------------------------" +
                                     "----------------------------------------------\n";
                setLog(transaction);
            }
            occupied = false;
            canRead.signalAll();
        }
        catch ( InterruptedException exception ) {exception.printStackTrace();}
        finally {lock.unlock();}
    }
    // Adds money to the account after Semaphore is acquired.
    @Override
    public void makeDeposit(int value)
    {
        lock.lock();
        try
        {
            while(occupied) {canRead.await();}
            occupied = true;
            balance += value;
            String transaction = "Deposit ($" + value + ")\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t$" + balance + "\n" +
                                 "--------------------------------------------------------------------------" +
                                 "--------------------------------------------------\n";
            setLog(transaction);
            occupied = false;
            canWithdrawal.signalAll();
            canRead.signalAll();
        }
        catch ( InterruptedException exception ) {exception.printStackTrace();}
        finally {lock.unlock();}
    }
    // Getter for the account's transaction log.
    @Override
    public String getLog() {
        return log;
    }
    // Setter for the account's transaction log.
    public void setLog(String log) {
        this.log += log;
    }
}
