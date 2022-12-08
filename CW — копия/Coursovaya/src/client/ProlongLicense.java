package client;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProlongLicense extends JDialog implements ActionListener {
    private JPanel firstPanel;
    private Integer[] items = {1,2,3,4,5,6,7,8,9,10};
    private JComboBox cBox = new JComboBox<>(items);
    private JLabel howLong = new JLabel("На какой срок хотите продлить лицензию?");
    private Integer idLi;
    private JButton sendFirst;
    private JButton cancelFirst;

    private JPanel secondPanel;
    private JTextField accountNumber;
    private JLabel acc = new JLabel("Номер счета");
    private JTextField sum;
    private JLabel sumLa = new JLabel("Сумма");
    private JButton sendSecond;
    private JButton cancelSecond;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Integer number;
    private String idLicense;
    private Integer selected;
    private server.Software sw;
    private server.Hardware hw;
    private String swId;
    private ArrayList<Integer> price;
    private Integer summa;

    public ProlongLicense(Frame owner, boolean modal, ObjectInputStream ois, ObjectOutputStream oos, int num/*18*/,
                          String idLicense, server.Software sw, server.Hardware hw, String swId) {
        super(owner, modal);
        this.ois = ois;
        this.oos = oos;
        this.number = num;
        this.idLicense = idLicense;
        this.swId = swId;

        setTitle("Продление лицензии");
        setResizable(false);
        setSize(500, 400);
        setLocationRelativeTo(null);

        firstPanel = new JPanel();
        firstPanel.setLayout(null);

        howLong.setSize(200,20);
        howLong.setLocation(150,20);
        howLong.setVisible(true);
        firstPanel.add(howLong);

        cBox.setSize(200,40);
        cBox.setLocation(150,50);
        cBox.setVisible(true);
        firstPanel.add(cBox);

        sendFirst = new JButton("Отправить");
        sendFirst.setSize(120,40);
        sendFirst.setLocation(190,100);
        sendFirst.addActionListener(this::actionPerformed);
        sendFirst.setVisible(true);
        firstPanel.add(sendFirst);
        cancelFirst = new JButton("Отмена");
        cancelFirst.setSize(120,40);
        cancelFirst.setLocation(190,150);
        cancelFirst.addActionListener(this::actionCancelPerformed);
        cancelFirst.setVisible(true);
        firstPanel.add(cancelFirst);

        setContentPane(firstPanel);

        secondPanel = new JPanel();
        secondPanel.setLayout(null);

        accountNumber = new JTextField();
        accountNumber.setSize(200,40);
        accountNumber.setLocation(150,50);
        accountNumber.setVisible(true);
        secondPanel.add(accountNumber);
        sum = new JTextField();
        sum.setSize(200,40);
        sum.setLocation(150,130);
        sum.setVisible(true);
        sum.setEnabled(false);
        this.price = sw.getPrice();
        summa = price.get(Integer.parseInt(swId)-1);

        secondPanel.add(sum);


        acc.setVisible(true);
        acc.setLocation(150,20);
        acc.setSize(200,20);
        secondPanel.add(acc);
        sumLa.setVisible(true);
        sumLa.setLocation(150,100);
        sumLa.setSize(200,20);
        secondPanel.add(sumLa);

        sendSecond = new JButton("Отправить");
        sendSecond.setSize(120,40);
        sendSecond.setLocation(190,200);
        sendSecond.addActionListener(this::action2Performed);
        sendSecond.setVisible(true);
        secondPanel.add(sendSecond);
        cancelSecond = new JButton("Отмена");
        cancelSecond.setSize(120,40);
        cancelSecond.setLocation(190,250);
        cancelSecond.addActionListener(this::actionCancel2Performed);
        cancelSecond.setVisible(true);
        secondPanel.add(cancelSecond);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selected = (Integer) cBox.getSelectedItem();
        summa*=selected;
        sum.setText(summa.toString());
        setContentPane(secondPanel);
        setVisible(true);
    }
    public void action2Performed(ActionEvent e){
        if (!accountNumber.getText().isEmpty() & !sum.getText().isEmpty()) {
            try {
                oos.writeUTF(number.toString());
                oos.flush();
                String res = selected + " " + idLicense;
                oos.writeUTF(res);
                oos.flush();
                String line = ois.readUTF();
                if (line.equals("Command proceeded")) {
                    WarningDialog wd = new WarningDialog(null, true, secondPanel, "Успешно");
                    line = "";
                    this.dispose();
                } else {
                    WarningDialog wd = new WarningDialog(null, true, secondPanel, "Ошибка");
                    line = "";
                    this.dispose();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void actionCancelPerformed(ActionEvent e){
        this.dispose();
    }
    public void actionCancel2Performed(ActionEvent e){
        this.dispose();
    }
}
