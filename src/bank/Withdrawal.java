/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 � Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

public class Withdrawal implements Runnable
{
    private boolean alive = true;

    public void run()
    {
        while(this.alive)
        {
            try
            {
                System.out.println("I'm a withdrawal!");
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                System.out.println("DEBUG: -----Start-----\n" + e + "DEBUG: ------End------\n");
                this.alive = false;
            }
        }
    }
}
