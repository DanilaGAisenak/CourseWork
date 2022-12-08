package client;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Delete extends JDialog implements ActionListener {
    private JPanel panel;
    private JTextField name;
    private JTextField price;
    private JTextField manufacturer;
    private JLabel nameLa;
    private JLabel priceLa;
    private JLabel manufacturerLa;
    private JLabel id;
    private JButton sendReg;
    private JButton cancelReg;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Integer number;
    private String idDel;
    private String priceDel;
    private String nameDel;
    private String manufacturerDel;

    public Delete(Frame owner, boolean modal, ObjectInputStream ois, ObjectOutputStream oos,
                  Integer number, String swId, String swName,
                  String swPrice, String swManufacturer) {
        super(owner, modal);
        this.ois = ois;
        this.oos = oos;
        this.number = number;
        this.idDel = swId;
        this.priceDel = swPrice;
        this.nameDel = swName;
        this.manufacturerDel = swManufacturer;

        if (number == 8 || number == 13) {
            setTitle("Удаление");
            setResizable(false);
            setSize(500, 400);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(null);

            name = new JTextField();
            name.setSize(200, 40);
            name.setLocation(150, 50);
            name.setVisible(true);
            name.setEnabled(false);
            name.setText(swName);
            panel.add(name);

            price = new JTextField();
            price.setSize(200, 40);
            price.setLocation(150, 130);
            price.setVisible(true);
            price.setEnabled(false);
            price.setText(swPrice);
            panel.add(price);

            manufacturer = new JTextField();
            manufacturer.setSize(200, 40);
            manufacturer.setLocation(150, 210);
            manufacturer.setVisible(true);
            manufacturer.setEnabled(false);
            manufacturer.setText(swManufacturer);
            panel.add(manufacturer);

            id = new JLabel(swId.toString());
            id.setLocation(120, 15);
            id.setSize(35, 20);
            id.setVisible(true);
            panel.add(id);

            nameLa = new JLabel("Название");
            nameLa.setSize(100, 20);
            nameLa.setLocation(150, 20);
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
        } else
            if (number == 9 || number == 12) {
            setTitle("Изменение");
            setResizable(false);
            setSize(500, 400);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(null);

            name = new JTextField();
            name.setText(swName);
            name.setSize(200, 40);
            name.setLocation(150, 50);
            name.setVisible(true);
            panel.add(name);

            price = new JTextField();
            price.setText(swPrice);
            price.setSize(200, 40);
            price.setLocation(150, 130);
            price.setVisible(true);
            panel.add(price);

            manufacturer = new JTextField();
            manufacturer.setText(swManufacturer);
            manufacturer.setSize(200, 40);
            manufacturer.setLocation(150, 210);
            manufacturer.setVisible(true);
            panel.add(manufacturer);

            id = new JLabel(swId.toString());
            id.setLocation(120, 15);
            id.setSize(35, 20);
            id.setVisible(true);
            panel.add(id);

            nameLa = new JLabel("Название");
            nameLa.setSize(100, 20);
            nameLa.setLocation(150, 20);
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
            sendReg.addActionListener(this::actionEditPerformed);
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
    }

    private void actionEditPerformed(ActionEvent actionEvent) {
        try {
            oos.writeUTF(number.toString());
            oos.flush();
            oos.writeUTF(name.getText()+" "+price.getText()+" "+manufacturer.getText()+" "+id.getText());
            oos.flush();
            String line= ois.readUTF();
            System.out.println(line);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actionCancelPerformed(ActionEvent actionEvent) {
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            oos.writeUTF(number.toString());
            oos.flush();
            oos.writeUTF(idDel+" "+nameDel+" "+priceDel+" "+manufacturerDel);
            oos.flush();
            String line= ois.readUTF();
            System.out.println(line);
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
