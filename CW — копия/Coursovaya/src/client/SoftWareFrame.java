package client;

import server.Software;
import server.User;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SoftWareFrame extends JFrame implements ActionListener, WindowListener {
    private JPanel panel;
    private SoftwareTableModel stm = new SoftwareTableModel();
    private JScrollPane scroll;
    private JPanel ePanel;
    private JTable softwTable;
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
    private server.Software software;
    private AdminFrame adf;
    private String swId;
    private String swName;
    private String swPrice;
    private String swManufacturer;
    private int flag=0;
    private int num;//количество строк в таблице

    public SoftWareFrame(JPanel ePanel, ObjectInputStream ois, ObjectOutputStream oos,
                         AdminFrame adf, int num, server.Software sw) {
        this.ePanel = ePanel;
        this.ois = ois;
        this.oos = oos;
        this.adf = adf;
        this.num = num;
        this.id = sw.getId();
        this.price = sw.getPrice();
        this.name = sw.getName();
        this.manufacturer = sw.getManufacturer();

        setTitle("Программное обеспечение");
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
            stm.addData(row);
        }

        softwTable = new JTable(stm);
        softwTable.setSize(700,600);
        softwTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (softwTable.getSelectedRowCount() > 0){
                    edit.setEnabled(true);
                    delete.setEnabled(true);
                    int row = softwTable.getSelectedRow();
                    setSwId((String) softwTable.getValueAt(row, 0));
                    setSwName((String) softwTable.getValueAt(row, 1));
                    setSwPrice((String) softwTable.getValueAt(row, 2));
                    setSwManufacturer((String) softwTable.getValueAt(row, 3));
                }
                else {
                    edit.setEnabled(false);
                    delete.setEnabled(false);
                }
            }
        });
        scroll = new JScrollPane(softwTable);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setLocation(450,80);
        scroll.setSize(700,600);
        panel.add(scroll);

        register = new JButton("Добавить ПО");
        register.setSize(190,40);
        register.setLocation(550,25);
        register.addActionListener(this::actionPerformed);
        panel.add(register);

        delete = new JButton("Удалить ПО");
        delete.setSize(190,40);
        delete.setLocation(745,25);
        delete.addActionListener(this::actionDeletePerformed);
        delete.setVisible(true);
        delete.setEnabled(false);
        panel.add(delete);

        edit = new JButton("Изменить ПО");
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
            Integer numReg = 6;
            try {
                oos.writeUTF(numReg.toString());
                oos.flush();
                Integer num1 = 0;
                num1 = (Integer) ois.readObject();
                if (num1 > softwTable.getRowCount() & num1 - softwTable.getRowCount() == 1) {
                    this.software = (Software) ois.readObject();
                    this.id = this.software.getId();
                    this.name = this.software.getName();
                    this.price = this.software.getPrice();
                    this.manufacturer = this.software.getManufacturer();
                    String[] row = new String[4];
                    row[0] = id.get(num1 - 1).toString();
                    row[1] = name.get(num1 - 1);
                    row[2] = price.get(num1 - 1).toString();
                    row[3] = manufacturer.get(num1-1).toString();
                    stm.addData(row);
                    softwTable.repaint();
                    refresh.setEnabled(false);
                } else
                    if (num1 > softwTable.getRowCount()) {
                    this.software = (Software) ois.readObject();
                    this.id = this.software.getId();
                    this.name = this.software.getName();
                    this.price = this.software.getPrice();
                    this.manufacturer = this.software.getManufacturer();
                    for (int i = softwTable.getRowCount(); i < num1; i++) {
                        String[] row = new String[4];
                        row[0] = id.get(i).toString();
                        row[1] = name.get(i);
                        row[2] = price.get(i).toString();
                        row[3] = manufacturer.get(i).toString();
                        stm.addData(row);
                    }
                    softwTable.repaint();
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
            stm.deleteData();
            softwTable.repaint();
            Integer numReg = 6;
            try {
                oos.writeUTF(numReg.toString());
                oos.flush();
                Integer num1 = 0;
                num1 = (Integer) ois.readObject();
                this.software = (Software) ois.readObject();
                this.id = this.software.getId();
                this.name = this.software.getName();
                this.price = this.software.getPrice();
                this.manufacturer = this.software.getManufacturer();
                for (int i = 0; i < num1; i++) {
                    String[] row = new String[4];
                    row[0] = id.get(i).toString();
                    row[1] = name.get(i);
                    row[2] = price.get(i).toString();
                    row[3] = manufacturer.get(i).toString();
                    stm.addData(row);
                }
                softwTable.repaint();
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
        Delete del = new Delete(null, true, ois, oos, 9, swId, swName, swPrice, swManufacturer);
        del.setVisible(true);
        refresh.setEnabled(true);
        delete.setEnabled(false);
        edit.setEnabled(false);
        flag = 2;
    }

    private void actionDeletePerformed(ActionEvent actionEvent) {
        Delete del = new Delete(null, true, ois,oos, 8, swId, swName, swPrice, swManufacturer);
        del.setVisible(true);
        refresh.setEnabled(true);
        delete.setEnabled(false);
        edit.setEnabled(false);
        flag=2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RegisterDialog rd = new RegisterDialog(null, true, ois, oos, 7);
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
