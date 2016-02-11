/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller implements Initializable
{
    private int numDepositThreads = 3;
    private int numWithdrawalThreads = 6;
    private List<Thread> deposits = new ArrayList();
    private List<Thread> withdrawals = new ArrayList();
    public Account acct = null;

    @FXML
    private Button btn_start;
    @FXML
    private Button btn_stop;
    @FXML
    private TextField text_deposits;
    @FXML
    private TextField text_withdrawals;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        assert btn_start != null : "fx:id=\"btn_start\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        assert btn_stop != null : "fx:id=\"btn_stop\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        btn_stop.setDisable(true);
        assert text_deposits != null : "fx:id=\"text_deposits\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        assert text_withdrawals != null : "fx:id=\"text_withdrawals\" was not injected: check your FXML file 'balanceSheet.fxml'.";
    }
    // Creates account. Disables GUI components that shouldn't be accessible during runtime. Creates account.
    // Creates, and starts, the requisite deposit and withdrawal threads, adding them to their respective lists.
    @FXML
    private void start()
    {
        try
        {
            this.numDepositThreads = Integer.parseInt(text_deposits.getText());
            this.numWithdrawalThreads = Integer.parseInt(text_withdrawals.getText());
            btn_start.setDisable(true);
            btn_stop.setDisable(false);
            text_deposits.setDisable(true);
            text_withdrawals.setDisable(true);

            acct = new Account(0);
            for(int i = 0; i < this.numDepositThreads; i++)
            {
                Deposit dep = new Deposit((i+1), acct);
                Thread tdep = new Thread(dep);
                deposits.add(tdep);
                tdep.start();
            }
            for(int j = 0; j < this.numWithdrawalThreads; j++)
            {
                Withdrawal wit = new Withdrawal((j+1), acct);
                Thread twit = new Thread(wit);
                withdrawals.add(twit);
                twit.start();
            }

        }
        catch(Exception e) {System.out.println("DEBUG: -----Start-----\n" + e + "DEBUG: ------End------\n");}
    }
    // Deletes account. Enables relevant GUI elements, while disabling the stop button. All threads are interrupted, and
    // removed from their respective lists.
    @FXML
    private void stop()
    {
        this.numDepositThreads = 3;
        this.numWithdrawalThreads = 6;
        text_deposits.setText("3");
        text_withdrawals.setText("6");
        btn_start.setDisable(false);
        btn_stop.setDisable(true);
        text_deposits.setDisable(false);
        text_withdrawals.setDisable(false);

        acct = null;
        for(Thread td : deposits)
        {
            td.interrupt();
        }
        for(Thread tw : withdrawals)
        {
            tw.interrupt();
        }
        deposits.clear();
        withdrawals.clear();
    }
}
