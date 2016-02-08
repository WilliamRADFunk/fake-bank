# fake-bank
A simple demonstration of Java and multi-threaded programming using competing deposit and withdrawal threads.<br/><br/>

Main focus of this application is to demonstrate rudimentary ability in multi-threaded programming, while practicing my Java skills.<br/><br/>

User establishes the number of deposit and withdrawal threads they want in play, and the press the "start" button.<br/><br/>

Threads compete for access to the single Account instance's balance. A semaphore allows only one thread at a time to access its critical section, thus ensuring only one thread is manipulating the balance at any given time.<br/><br/>

Precautions were also taken to insure that a withdrawal thread cannot deduct money from the balance (even if it has acquired the semaphone) if there is insufficient funds. It's made to wait until a deposit threads adds a sufficient amount into the account.<br/><br/>

This program uses FXML, Scene Builder 2.0, and implements a basic GUI for the user.<br/><br/>

Screenshots were uploaded to demonstrate the program in its working form.<br/><br/>


