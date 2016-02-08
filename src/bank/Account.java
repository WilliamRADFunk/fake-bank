/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

public class Account {
    private static boolean accessKey = false;
    private static int balance = 0;

    public Account(int startingBalance)
    {
        balance = startingBalance;
    }
    public void makeDeposit(int value)
    {
        balance += value;
    }
    public boolean isWithdrawalValid(int value)
    {
        if(balance >= value) return true;
        else return false;
    }
    public void makeWithdrawal(int value)
    {
        balance -= value;
    }
}
