import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Creates threads to output numbers 1-9 then 9-1 until the user hits stop on the gui.
 */
public class Counter implements Runnable {

    public static JButton startButton; //creating the start button
    public static JButton stopButton; //creating the stop button
    public static Thread t1 = new Thread(new Counter());   //creating the first thread

    public void run() {
        while(!(t1.isInterrupted())){  //while the thread isn't interrupted
            for (int i = 1; i <= 9; i++) {
                System.out.print(i);   //output 1-9

                try {
                    Thread.sleep(300);  //stop for .3 sec
                } catch (InterruptedException ex) {
                    return;  //if interrupted end the loop
                }
            }

            System.out.println();

            try {
                Thread.sleep(3000);  //stop for 3 sec for other thread to execute
            }catch (InterruptedException ex){
                return;    //if interrupted end the loop
            }
        }
    }

    public static void main(String[] args) {
        var gui = new GUI();  //creating the gui
        gui.setVisible(true);
        gui.setTitle("Counter");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(200,100);

        startButton.addActionListener(e->{ //action listener for the start button
            t1 = new Thread(new Counter());
            Counter2.t2 = new Thread(new Counter2());  //creating both threads

            t1.start();  //starting both threads
            Counter2.t2.start();
        });

        stopButton.addActionListener(e->{  //action listener for the stop button
            t1.interrupt();  //interrupting both threads
            Counter2.t2.interrupt();
        });

    }
}
class Counter2 implements Runnable {
    public static Thread t2 = new Thread(new Counter2());   //creating the second thread
    public void run(){
        while(!(t2.isInterrupted())){  //while thread is not interrupted

            try {
                Thread.sleep(3000);  //wait for 3 sec for other thread
            }catch (InterruptedException ex){
                return;  //end loop if interrupted
            }

            for (int i = 9; i >= 1; i--) {  //output number 9-1
                System.out.print(i);

                try {
                    Thread.sleep(300);  //wait for .3 sec
                } catch (InterruptedException ex) {
                    return;  //end loop if interrupted
                }
            }
            System.out.println();
        }
    }
}

class GUI extends JFrame{
    GUI(){  //creates the gui
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);  //creating the panel
        this.add(panel);

        Counter.startButton = new JButton("Start");
        Counter.stopButton = new JButton("Stop");
        panel.add(Counter.startButton); panel.add(Counter.stopButton);

    }
}
