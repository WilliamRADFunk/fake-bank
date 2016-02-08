/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

public class Account {
    // Bank account balance. This is the critical section value protected by Semaphore.
    private static int balance = 0;
    // Constructor
    public Account(int startingBalance)
    {
        balance = startingBalance;
    }
    // Adds money to the account after Semaphore is acquired.
    public void makeDeposit(int value)
    {
        balance += value;
    }
    // Checks to make sure there's enough money is in the account before withdrawing.
    public boolean isWithdrawalValid(int value)
    {
        if(balance >= value) return true;
        else return false;
    }
    // Account has enough money, now a withdrawal is made.
    public void makeWithdrawal(int value)
    {
        balance -= value;
    }
}
