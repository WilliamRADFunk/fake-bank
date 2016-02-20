/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

public interface Buffer {
    public void makeDeposit(int value);
    public void makeWithdrawal(int value);
    public String getLog();
}
