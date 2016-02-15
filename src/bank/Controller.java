/*
** Name: William Funk
** Course: CNT 4714 Spring 2016
** Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
** Due Date: February 14, 2016
*/
package bank;

import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class Controller implements Initializable
{
    private int numDepositThreads = 3;
    private int numWithdrawalThreads = 6;
    private List<Deposit> deposits = new ArrayList();
    private List<Withdrawal> withdrawals = new ArrayList();
    private ExecutorService application;
    public Buffer account;

    @FXML
    private Button btn_start;
    @FXML
    private Button btn_stop;
    @FXML
    private TextField text_deposits;
    @FXML
    private TextField text_withdrawals;
    @FXML
    private TextArea text_transactions;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        assert btn_start != null : "fx:id=\"btn_start\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        assert btn_stop != null : "fx:id=\"btn_stop\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        btn_stop.setDisable(true);
        assert text_deposits != null : "fx:id=\"text_deposits\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        assert text_withdrawals != null : "fx:id=\"text_withdrawals\" was not injected: check your FXML file 'balanceSheet.fxml'.";
        assert text_transactions != null : "fx:id=\"text_transactions\" was not injected: check your FXML file 'balanceSheet.fxml'.";
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

            application = Executors.newFixedThreadPool( numDepositThreads + numWithdrawalThreads );

            account = new Account(text_transactions);

            System.out.println( "Deposits\t\t\t\t\t\t\t\t\tWithdrawals\t\t\t\t\t\t\t\t\t\tBalance" );
            System.out.println( "--------\t\t\t\t\t\t\t\t\t-----------\t\t\t\t\t\t\t\t\t\t-------\n" );

            try {
                for(int i = 0; i < this.numDepositThreads; i++)
                {
                    Deposit dep = new Deposit(account);
                    deposits.add(dep);
                    application.execute( dep );
                }
                for(int j = 0; j < this.numWithdrawalThreads; j++)
                {
                    Withdrawal wit = new Withdrawal(account);
                    withdrawals.add(wit);
                    application.execute( wit );
                }
            }
            catch( Exception exception) {
                exception.printStackTrace();
            }
        }
        catch(Exception e) {System.out.println("DEBUG: -----Start-----\n" + e + "DEBUG: ------End------\n");}
    }
    // Deletes account. Enables relevant GUI elements, while disabling the stop button. All threads are interrupted, and
    // removed from their respective lists.
    @FXML
    private void stop()
    {
        application.shutdown();

        this.numDepositThreads = 3;
        this.numWithdrawalThreads = 6;
        text_deposits.setText("3");
        text_withdrawals.setText("6");
        btn_start.setDisable(false);
        btn_stop.setDisable(true);
        text_deposits.setDisable(false);
        text_withdrawals.setDisable(false);

        account = null;

        deposits.forEach(bank.Deposit::kill);
        withdrawals.forEach(bank.Withdrawal::kill);
        deposits.clear();
        withdrawals.clear();
    }
}
