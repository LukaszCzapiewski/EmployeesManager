package ŁC;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

class WorkerThread implements Runnable {
    private final Random random = new Random();
    private final String name1;
    private final String name2;
    private int money1;
    private int money2;
    private final Employee player1;
    private final Employee player2;
    private int stake;
    private Timer timer;
    JTextArea area;
    ArrayBlockingQueue<Employee> players;
    JFrame f = new JFrame("Animation");


    public WorkerThread(Employee player1, Employee player2, JTextArea area,ArrayBlockingQueue<Employee> players) {
        this.player1 = player1;
        this.player2 = player2;
        name1 = player1.getName();
        name2 = player2.getName();
        this.money1 = player1.getMoney();
        this.money2 = player2.getMoney();
        this.area = area;
        this.players=players;
        stake = player1.getMoney()/10;
    }
    public int tossCoin() {

        return random.nextBoolean() ? 1 : 0;
    }

    public String matchPenny(int coin1, int coin2) {
        return coin1 == coin2 ? name1 : name2;
    }


    public void run() {
        try {
            createAndShowGUI();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(900); //dlugosc animacji
            } catch (InterruptedException e) {
                if (Thread.interrupted()) {
                    break;
                }
            }
            area.append("\n Pierwszy gracz:  " + name1 + " stan konta: " + money1 + "          Drugi gracz: " + name2 + " Stan konta:      " + money2);
            if (money1 < 1000 || money2 < 1000) {
                stake = Math.min(money1, money2);
            }
            if (matchPenny(tossCoin(), tossCoin()).equals(name1)) {
                money1 = money1 + stake;
                money2 = money2 - stake;
            } else {
                money1 = money1 - stake;
                money2 = money2 + stake;
            }
            if (money2==0) break;
            if (money1==0) break;
        }

        area.append("\n Pierwszy gracz:  " + name1 + " stan konta: " + money1 + "          Drugi gracz: " + name2 + " Stan konta:      " + money2);
        player1.setMoney(money1);
        player2.setMoney(money2);


        try {
            players.put(money1 > money2 ? player1 : player2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        f.setVisible(false);



    }

    private void createAndShowGUI() throws MalformedURLException {
        Icon icon = new ImageIcon("images/gif.gif");
        JLabel label = new JLabel(icon);
        f.getContentPane().add(label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}

