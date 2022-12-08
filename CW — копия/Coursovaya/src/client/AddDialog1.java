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

public class AddDialog1 extends JDialog implements ActionListener {
    private JPanel panel;
    private JTextField name;
    private JTextField amount;
    private JLabel nameLa;
    private JLabel priceLa;
    private JLabel manufacturerLa;
    private JLabel amLa;
    private JLabel endDateLa;
    private JButton sendReg;
    private JButton cancelReg;
    private JDateChooser dateChooser;
    private JDateChooser dateChooser2;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private JComboBox cbSw;
    private JComboBox cbHw;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Integer number;
    private server.Software sw;
    private server.Hardware hw;
    private Integer swAmount;
    private Integer hwAmount;
    //software
    private ArrayList<Integer> id;
    private ArrayList<Integer> priceList;
    private ArrayList<String> nameList;
    private ArrayList<String> manufacturer;
    //software

    //hardware
    private ArrayList<Integer> idHw;
    private ArrayList<Integer> priceListHw;
    private ArrayList<String> nameListHw;
    private ArrayList<String> manufacturerHw;
    //hardware
    private String comId;
    private String comName;
    private String swSel;
    private String hwSel;
    private Integer choice;
    private String idHws;
    private String comIdHws;
    private String hwIdSel;
    private String dateStart;
    private String dateEnd;
    private String amHw;
    public AddDialog1(Frame owner, boolean modal, ObjectInputStream ois, ObjectOutputStream oos, int num,
                     server.Hardware hw, Integer hwAmount, String comIdSel/*idHws*/, String comNameSel/*comIdHws*/,
                      String hwIdSel,String dateStart, String dateEnd, String amHw,Integer choice) {
        super(owner, modal);
        if (choice == 0) {
            this.choice = choice;
            this.comId = comIdSel;
            this.comName = comNameSel;
            this.ois = ois;
            this.oos = oos;
            this.number = num; // Код операции
            this.hw = hw;
            this.hwAmount = hwAmount;
            this.idHw = hw.getId();
            this.nameListHw = hw.getName();
            this.priceListHw = hw.getPrice();
            this.manufacturerHw = hw.getManufacturer();
            String[] items = new String[nameListHw.size()];
            for (int i = 0; i < nameListHw.size(); i++) {
                items[i] = nameListHw.get(i);
            }
            cbHw = new JComboBox<>(items);
            setTitle("Добавление");
            setResizable(false);
            setSize(500, 600);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(null);

            name = new JTextField();
            name.setText(comName);
            name.setSize(200, 40);
            name.setLocation(150, 50);
            name.setVisible(true);
            name.setEnabled(false);
            panel.add(name);

            cbHw.setSize(200, 40);
            cbHw.setLocation(150, 130);
            cbHw.setVisible(true);
            panel.add(cbHw);

            dateChooser = new JDateChooser();
            dateChooser.setSize(200, 40);
            dateChooser.setLocation(150, 210);
            dateChooser.setVisible(true);
            panel.add(dateChooser);
            dateChooser2 = new JDateChooser();
            dateChooser2.setSize(200, 40);
            dateChooser2.setLocation(150, 290);
            dateChooser2.setVisible(true);
            panel.add(dateChooser2);

            amount = new JTextField();
            amount.setSize(200, 40);
            amount.setLocation(150, 370);
            amount.setVisible(true);
            panel.add(amount);

            nameLa = new JLabel("Компания");
            nameLa.setSize(100, 20);
            nameLa.setLocation(150, 20);
            nameLa.setVisible(true);
            panel.add(nameLa);

            priceLa = new JLabel("AО");
            priceLa.setSize(100, 20);
            priceLa.setLocation(150, 100);
            priceLa.setVisible(true);
            panel.add(priceLa);

            manufacturerLa = new JLabel("Дата Покупки");
            manufacturerLa.setSize(100, 20);
            manufacturerLa.setLocation(150, 180);
            manufacturerLa.setVisible(true);
            panel.add(manufacturerLa);

            endDateLa = new JLabel("Дата Окончания использования");
            endDateLa.setSize(200, 20);
            endDateLa.setLocation(150, 260);
            endDateLa.setVisible(true);
            panel.add(endDateLa);

            amLa = new JLabel("Количество");
            amLa.setSize(100, 20);
            amLa.setLocation(150, 340);
            amLa.setVisible(true);
            panel.add(amLa);

            sendReg = new JButton("Отправить");
            sendReg.setSize(120, 40);
            sendReg.setLocation(190, 460);
            sendReg.addActionListener(this::actionPerformed);
            sendReg.setVisible(true);
            panel.add(sendReg);

            cancelReg = new JButton("Отмена");
            cancelReg.setSize(120, 40);
            cancelReg.setLocation(190, 510);
            cancelReg.setVisible(true);
            cancelReg.addActionListener(this::actionCancelPerformed);
            panel.add(cancelReg);

            setContentPane(panel);
        } else if (choice==1) {
            this.choice = choice;
            this.idHws = comIdSel;
            this.comIdHws = comNameSel;
            this.hwIdSel = hwIdSel;
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.amHw = amHw;
            this.ois = ois;
            this.oos = oos;
            this.number = num; // Код операции
            this.hw = hw;
            this.hwAmount = hwAmount;
            this.idHw = hw.getId();
            this.nameListHw = hw.getName();
            this.priceListHw = hw.getPrice();
            this.manufacturerHw = hw.getManufacturer();
            String[] items = new String[nameListHw.size()];
            for (int i = 0; i < nameListHw.size(); i++) {
                items[i] = nameListHw.get(i);
            }
            cbHw = new JComboBox<>(items);
            setTitle("Изменение");
            setResizable(false);
            setSize(500, 600);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(null);

            name = new JTextField();
            name.setText(comIdHws);
            name.setSize(200, 40);
            name.setLocation(150, 50);
            name.setVisible(true);
            name.setEnabled(false);
            panel.add(name);

            cbHw.setSize(200, 40);
            cbHw.setLocation(150, 130);
            cbHw.setVisible(true);
            panel.add(cbHw);

            dateChooser = new JDateChooser();
            dateChooser.setSize(200, 40);
            dateChooser.setLocation(150, 210);
            dateChooser.setVisible(true);
            panel.add(dateChooser);
            dateChooser2 = new JDateChooser();
            dateChooser2.setSize(200, 40);
            dateChooser2.setLocation(150, 290);
            dateChooser2.setVisible(true);
            panel.add(dateChooser2);

            amount = new JTextField();
            amount.setSize(200, 40);
            amount.setLocation(150, 370);
            amount.setVisible(true);
            amount.setText(amHw);
            panel.add(amount);

            nameLa = new JLabel("Компания");
            nameLa.setSize(100, 20);
            nameLa.setLocation(150, 20);
            nameLa.setVisible(true);
            panel.add(nameLa);

            priceLa = new JLabel("AО");
            priceLa.setSize(100, 20);
            priceLa.setLocation(150, 100);
            priceLa.setVisible(true);
            panel.add(priceLa);

            manufacturerLa = new JLabel("Дата Покупки");
            manufacturerLa.setSize(100, 20);
            manufacturerLa.setLocation(150, 180);
            manufacturerLa.setVisible(true);
            panel.add(manufacturerLa);

            endDateLa = new JLabel("Дата Окончания использования");
            endDateLa.setSize(200, 20);
            endDateLa.setLocation(150, 260);
            endDateLa.setVisible(true);
            panel.add(endDateLa);

            amLa = new JLabel("Количество");
            amLa.setSize(100, 20);
            amLa.setLocation(150, 340);
            amLa.setVisible(true);
            panel.add(amLa);

            sendReg = new JButton("Отправить");
            sendReg.setSize(120, 40);
            sendReg.setLocation(190, 460);
            sendReg.addActionListener(this::actionPerformed);
            sendReg.setVisible(true);
            panel.add(sendReg);

            cancelReg = new JButton("Отмена");
            cancelReg.setSize(120, 40);
            cancelReg.setLocation(190, 510);
            cancelReg.setVisible(true);
            cancelReg.addActionListener(this::actionCancelPerformed);
            panel.add(cancelReg);

            setContentPane(panel);
        } else if (choice == 3) {
            this.choice = choice;
            this.idHws = comIdSel;
            this.comIdHws = comNameSel;
            this.hwIdSel = hwIdSel;
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.amHw = amHw;
            this.ois = ois;
            this.oos = oos;
            this.number = num; // Код операции
            this.hw = hw;
            this.hwAmount = hwAmount;
            this.idHw = hw.getId();
            this.nameListHw = hw.getName();
            this.priceListHw = hw.getPrice();
            this.manufacturerHw = hw.getManufacturer();

            setTitle("Удаление");
            setResizable(false);
            setSize(500, 600);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(null);

            name = new JTextField();
            name.setText(idHws);
            name.setSize(200, 40);
            name.setLocation(150, 50);
            name.setVisible(true);
            name.setEnabled(false);
            panel.add(name);

            sendReg = new JButton("Отправить");
            sendReg.setSize(120, 40);
            sendReg.setLocation(190, 460);
            sendReg.addActionListener(this::actionPerformed);
            sendReg.setVisible(true);
            panel.add(sendReg);

            cancelReg = new JButton("Отмена");
            cancelReg.setSize(120, 40);
            cancelReg.setLocation(190, 510);
            cancelReg.setVisible(true);
            cancelReg.addActionListener(this::actionCancelPerformed);
            panel.add(cancelReg);

            setContentPane(panel);
        }
    }
    private void actionCancelPerformed(ActionEvent actionEvent) {
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (choice==0) {
            try {
                Integer idPost = 0;
                hwSel = (String) cbHw.getSelectedItem();
                String date = sdf.format(dateChooser.getDate());
                String date1 = sdf.format(dateChooser2.getDate());
                for (int i = 0; i < nameListHw.size(); i++) {
                    if (hwSel.equals(nameListHw.get(i))) {
                        idPost = idHw.get(i);
                        i = nameListHw.size();
                    }
                }
                oos.writeUTF(number.toString());
                oos.flush();
                String res = comId + " " + idPost.toString() + " " + date + " " + date1 + " " + amount.getText();
                oos.writeUTF(res);
                oos.flush();
                String line = ois.readUTF();
                if (line.equals("Command proceeded")) {
                    WarningDialog wd = new WarningDialog(null, true, panel, "Успешно");
                    line = "";
                    this.dispose();
                } else {
                    WarningDialog wd = new WarningDialog(null, true, panel, "Ошибка");
                    line = "";
                    this.dispose();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (choice == 1) {
            try {
                Integer idPost = 0;
                hwSel = (String) cbHw.getSelectedItem();
                String date = sdf.format(dateChooser.getDate());
                String date1 = sdf.format(dateChooser2.getDate());
                for (int i = 0; i < nameListHw.size(); i++) {
                    if (hwSel.equals(nameListHw.get(i))) {
                        idPost = idHw.get(i);
                        i = nameListHw.size();
                    }
                }
                oos.writeUTF(number.toString());
                oos.flush();
                String res = comIdHws + " " + idPost.toString() + " " + date + " " + date1 + " " + amount.getText()+" "+idHws;
                oos.writeUTF(res);
                oos.flush();
                String line = ois.readUTF();
                if (line.equals("Command proceeded")) {
                    WarningDialog wd = new WarningDialog(null, true, panel, "Успешно");
                    line = "";
                    this.dispose();
                } else {
                    WarningDialog wd = new WarningDialog(null, true, panel, "Ошибка");
                    line = "";
                    this.dispose();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (choice==3) {
            try {
                oos.writeUTF(number.toString());
                oos.flush();
                oos.writeUTF(idHws);
                oos.flush();
                String line = ois.readUTF();
                if (line.equals("Command proceeded")) {
                    WarningDialog wd = new WarningDialog(null, true, panel, "Успешно");
                    line = "";
                    this.dispose();
                } else {
                    WarningDialog wd = new WarningDialog(null, true, panel, "Ошибка");
                    line = "";
                    this.dispose();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
