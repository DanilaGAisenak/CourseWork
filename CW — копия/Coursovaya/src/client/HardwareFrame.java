package client;

import server.Hardware;
import server.Software;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HardwareFrame extends JFrame implements ActionListener, WindowListener {
    private JPanel panel;
    private HardwareTableModel htm = new HardwareTableModel();
    private JScrollPane scroll;
    private JPanel ePanel;
    private JTable hwTable;
    private JButton edit;
    private JButton delete;
    private JButton register;
    private JButton close;
    private JButton refresh;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private ArrayList<Integer> id;
    private ArrayList<Integer> price;
    private ArrayList<String> name;
    private ArrayList<String> manufacturer;
    private server.Hardware hardware;
    private AdminFrame adf;
    private String swId;
    private String swName;
    private String swPrice;
    private String swManufacturer;
    private int flag=0;
    private int num;//количество строк в таблице

    public HardwareFrame(JPanel ePanel, ObjectInputStream ois, ObjectOutputStream oos,
                         AdminFrame adf, int num, server.Hardware hw) {
        this.ePanel = ePanel;
        this.ois = ois;
        this.oos = oos;
        this.adf = adf;
        this.num = num;
        this.id = hw.getId();
        this.price = hw.getPrice();
        this.name = hw.getName();
        this.manufacturer = hw.getManufacturer();

        setTitle("Аппаратное обеспечение");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(200, 0);
        setSize(1200, 800);

        panel = new JPanel();
        panel.setLayout(null);

        for (int i = 0; i < this.num; i++){
            String[] row = new String[4];
            row[0] = id.get(i).toString();
            row[1] = name.get(i);
            row[2] = price.get(i).toString();
            row[3] = manufacturer.get(i);
            htm.addData(row);
        }

        hwTable = new JTable(htm);
        hwTable.setSize(700,600);
        hwTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (hwTable.getSelectedRowCount() > 0){
                    edit.setEnabled(true);
                    delete.setEnabled(true);
                    int row = hwTable.getSelectedRow();
                    setSwId((String) hwTable.getValueAt(row, 0));
                    setSwName((String) hwTable.getValueAt(row, 1));
                    setSwPrice((String) hwTable.getValueAt(row, 2));
                    setSwManufacturer((String) hwTable.getValueAt(row, 3));
                }
                else {
                    edit.setEnabled(false);
                    delete.setEnabled(false);
                }
            }
        });
        scroll = new JScrollPane(hwTable);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setLocation(450,80);
        scroll.setSize(700,600);
        panel.add(scroll);

        register = new JButton("Добавить АО");
        register.setSize(190,40);
        register.setLocation(550,25);
        register.addActionListener(this::actionPerformed);
        panel.add(register);

        delete = new JButton("Удалить АО");
        delete.setSize(190,40);
        delete.setLocation(745,25);
        delete.addActionListener(this::actionDeletePerformed);
        delete.setVisible(true);
        delete.setEnabled(false);
        panel.add(delete);

        edit = new JButton("Изменить АО");
        edit.setSize(190,40);
        edit.setLocation(940,25);
        edit.addActionListener(this::actionEditPerformed);
        edit.setVisible(true);
        edit.setEnabled(false);
        panel.add(edit);

        close = new JButton("Выход");
        close.setSize(100,40);
        close.setLocation(1050, 700);
        close.addActionListener(this::actionClosePerformed);
        close.setVisible(true);
        panel.add(close);

        refresh = new JButton("Обновить таблицу");
        refresh.setSize(150,40);
        refresh.setLocation(895, 700);
        refresh.addActionListener(this::actionRefPerformed);
        refresh.setVisible(true);
        refresh.setEnabled(false);
        panel.add(refresh);

        setContentPane(panel);
        panel.setVisible(true);
    }

    private void actionRefPerformed(ActionEvent actionEvent) {
        if (flag==0){
            Integer numReg = 10;
            try {
                oos.writeUTF(numReg.toString());
                oos.flush();
                Integer num1 = 0;
                num1 = (Integer) ois.readObject();
                if (num1 > hwTable.getRowCount() & num1 - hwTable.getRowCount() == 1) {
                    this.hardware = (Hardware) ois.readObject();
                    this.id = this.hardware.getId();
                    this.name = this.hardware.getName();
                    this.price = this.hardware.getPrice();
                    this.manufacturer = this.hardware.getManufacturer();
                    String[] row = new String[4];
                    row[0] = id.get(num1 - 1).toString();
                    row[1] = name.get(num1 - 1);
                    row[2] = price.get(num1 - 1).toString();
                    row[3] = manufacturer.get(num1-1).toString();
                    htm.addData(row);
                    hwTable.repaint();
                    refresh.setEnabled(false);
                } else
                if (num1 > hwTable.getRowCount()) {
                    this.hardware = (Hardware) ois.readObject();
                    this.id = this.hardware.getId();
                    this.name = this.hardware.getName();
                    this.price = this.hardware.getPrice();
                    this.manufacturer = this.hardware.getManufacturer();
                    for (int i = hwTable.getRowCount(); i < num1; i++) {
                        String[] row = new String[4];
                        row[0] = id.get(i).toString();
                        row[1] = name.get(i);
                        row[2] = price.get(i).toString();
                        row[3] = manufacturer.get(i).toString();
                        htm.addData(row);
                    }
                    hwTable.repaint();
                    refresh.setEnabled(false);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            flag++;
        }
        else if(flag==2){
            htm.deleteData();
            hwTable.repaint();
            Integer numReg = 10;
            try {
                oos.writeUTF(numReg.toString());
                oos.flush();
                Integer num1 = 0;
                num1 = (Integer) ois.readObject();
                this.hardware = (Hardware) ois.readObject();
                this.id = this.hardware.getId();
                this.name = this.hardware.getName();
                this.price = this.hardware.getPrice();
                this.manufacturer = this.hardware.getManufacturer();
                for (int i = 0; i < num1; i++) {
                    String[] row = new String[4];
                    row[0] = id.get(i).toString();
                    row[1] = name.get(i);
                    row[2] = price.get(i).toString();
                    row[3] = manufacturer.get(i).toString();
                    htm.addData(row);
                }
                hwTable.repaint();
                refresh.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            flag++;
        }

    }

    private void actionClosePerformed(ActionEvent actionEvent) {
        this.dispose();
        adf.setVisible(true);
    }

    private void actionEditPerformed(ActionEvent actionEvent) {
        Delete del = new Delete(null, true, ois, oos, 12, swId, swName, swPrice, swManufacturer);
        del.setVisible(true);
        refresh.setEnabled(true);
        delete.setEnabled(false);
        edit.setEnabled(false);
        flag = 2;
    }

    private void actionDeletePerformed(ActionEvent actionEvent) {
        Delete del = new Delete(null, true, ois,oos, 13, swId, swName, swPrice, swManufacturer);
        del.setVisible(true);
        refresh.setEnabled(true);
        delete.setEnabled(false);
        edit.setEnabled(false);
        flag=2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RegisterDialog rd = new RegisterDialog(null, true, ois, oos, 11);
        rd.setVisible(true);
        flag=0;
        refresh.setEnabled(true);
    }

    public void setSwId(String swId) {
        this.swId = swId;
    }

    public void setSwName(String swName) {
        this.swName = swName;
    }

    public void setSwPrice(String swPrice) {
        this.swPrice = swPrice;
    }

    public void setSwManufacturer(String swManufacturer) {
        this.swManufacturer = swManufacturer;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
