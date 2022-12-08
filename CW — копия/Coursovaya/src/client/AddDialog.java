package client;

import com.toedter.calendar.JDateChooser;
import server.Hardware;
import server.Software;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddDialog extends JDialog implements ActionListener {
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

    public AddDialog(Frame owner, boolean modal, ObjectInputStream ois, ObjectOutputStream oos, int num,
                     Software sw, Integer swAmount, String comIdSel, String comNameSel, Integer choice) {
        super(owner, modal);
        if (choice==0) {
            this.comId = comIdSel;
            this.comName = comNameSel;
            this.choice = choice;
            this.ois = ois;
            this.oos = oos;
            this.number = num; // Код операции
            this.sw = sw;
            this.swAmount = swAmount;
            this.id = sw.getId();
            this.priceList = sw.getPrice();
            this.nameList = sw.getName();
            this.manufacturer = sw.getManufacturer();
            String[] items = new String[nameList.size()];
            for (int i = 0; i < nameList.size(); i++) {
                items[i] = nameList.get(i);
            }
            cbSw = new JComboBox<>(items);
            setTitle("Добавление");
            setResizable(false);
            setSize(500, 400);
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

            cbSw.setSize(200, 40);
            cbSw.setLocation(150, 130);
            cbSw.setVisible(true);
            panel.add(cbSw);

            dateChooser = new JDateChooser();
            dateChooser.setSize(200, 40);
            dateChooser.setLocation(150, 210);
            dateChooser.setVisible(true);
            panel.add(dateChooser);

            nameLa = new JLabel("Компания");
            nameLa.setSize(100, 20);
            nameLa.setLocation(150, 20);
            nameLa.setVisible(true);
            panel.add(nameLa);

            priceLa = new JLabel("ПО");
            priceLa.setSize(100, 20);
            priceLa.setLocation(150, 100);
            priceLa.setVisible(true);
            panel.add(priceLa);

            manufacturerLa = new JLabel("Дата окончания");
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
        else if (choice==1){
            this.comId = comIdSel;
            this.comName = comNameSel;
            this.choice = choice;
            this.ois = ois;
            this.oos = oos;
            this.number = num; // Код операции
            this.sw = sw;
            this.swAmount = swAmount;
            this.id = sw.getId();
            this.priceList = sw.getPrice();
            this.nameList = sw.getName();
            this.manufacturer = sw.getManufacturer();
            setTitle("Удаление");
            setResizable(false);
            setSize(500, 400);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(null);

            name = new JTextField();
            name.setText(comId);
            name.setSize(200, 40);
            name.setLocation(150, 50);
            name.setVisible(true);
            name.setEnabled(false);
            panel.add(name);


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
    }



    private void actionCancelPerformed(ActionEvent actionEvent) {
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (choice == 0) {
            try {
                Integer idPost = 0;
                swSel = (String) cbSw.getSelectedItem();
                String date = sdf.format(dateChooser.getDate());
                for (int i = 0; i < nameList.size(); i++) {
                    if (swSel.equals(nameList.get(i))) {
                        idPost = id.get(i);
                        i = nameList.size();
                    }
                }
                oos.writeUTF(number.toString());
                oos.flush();
                String res = comId + " " + idPost.toString() + " " + date;
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
        } else if (choice==1) {
            try {
                oos.writeUTF(number.toString());
                oos.flush();
                oos.writeUTF(comId+" "+comId);
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
