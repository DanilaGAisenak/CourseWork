package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegisterDialog extends JDialog implements ActionListener {
    private JPanel panel;
    private JTextField name;
    private JTextField price;
    private JTextField manufacturer;
    private JLabel nameLa;
    private JLabel priceLa;
    private JLabel manufacturerLa;
    private JButton sendReg;
    private JButton cancelReg;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Integer number;

    public RegisterDialog(Frame owner, boolean modal, ObjectInputStream ois, ObjectOutputStream oos, int num) {
        super(owner, modal);
        this.ois = ois;
        this.oos = oos;
        this.number = num;
        setTitle("Добавление");
        setResizable(false);
        setSize(500, 400);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        name = new JTextField();
        name.setSize(200,40);
        name.setLocation(150, 50);
        name.setVisible(true);
        panel.add(name);

        price = new JTextField();
        price.setSize(200, 40);
        price.setLocation(150, 130);
        price.setVisible(true);
        panel.add(price);

        manufacturer = new JTextField();
        manufacturer.setSize(200, 40);
        manufacturer.setLocation(150, 210);
        manufacturer.setVisible(true);
        panel.add(manufacturer);

        nameLa = new JLabel("Название");
        nameLa.setSize(100,20);
        nameLa.setLocation(150,20);
        nameLa.setVisible(true);
        panel.add(nameLa);

        priceLa = new JLabel("Цена");
        priceLa.setSize(100, 20);
        priceLa.setLocation(150, 100);
        priceLa.setVisible(true);
        panel.add(priceLa);

        manufacturerLa = new JLabel("Производитель");
        manufacturerLa.setSize(100, 20);
        manufacturerLa.setLocation(150, 180);
        manufacturerLa.setVisible(true);
        panel.add(manufacturerLa);

        sendReg = new JButton("Отправить");
        sendReg.setSize(120, 40);
        sendReg.setLocation(190, 260);
        sendReg.addActionListener(this::actionPerformed);
        sendReg.setVisible(true);
        panel.add(sendReg);

        cancelReg = new JButton("Отмена");
        cancelReg.setSize(120, 40);
        cancelReg.setLocation(190, 310);
        cancelReg.setVisible(true);
        cancelReg.addActionListener(this::actionCancelPerformed);
        panel.add(cancelReg);

        setContentPane(panel);
    }

    private void actionCancelPerformed(ActionEvent actionEvent) {
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            oos.writeUTF(number.toString());
            oos.flush();
            String res = name.getText()+" "+price.getText()+" "+manufacturer.getText();
            oos.writeUTF(res);
            oos.flush();
            String line = ois.readUTF();
            if (line.equals("Command proceeded")){
                WarningDialog wd = new WarningDialog(null, true, panel, "Успешно");
                line="";
                this.dispose();
            }
            else {
                WarningDialog wd = new WarningDialog(null, true, panel, "Ошибка");
                line="";
                this.dispose();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
