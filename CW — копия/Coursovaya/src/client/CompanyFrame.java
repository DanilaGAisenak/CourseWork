package client;

import server.Company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CompanyFrame extends JFrame implements ActionListener {
    private JPanel panel;
    private CompanyTableModel ctm = new CompanyTableModel();
    private JScrollPane scroll;
    private JPanel ePanel;
    private JTable liTable;
    private JButton edit;
    private JButton delete;
    private JButton close;
    private JButton refresh;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private ArrayList<Integer> id;
    private ArrayList<Integer> idUser;
    private ArrayList<Integer> amount;
    private ArrayList<String> name;
    private server.Company company;
    private AdminFrame adf;
    private String comId;
    private String comIdUser;
    private String comName;
    private String comAmount;
    private int flag=0;
    private int num;//количество строк в таблице

    public CompanyFrame(JPanel ePanel, ObjectInputStream ois, ObjectOutputStream oos,
                        AdminFrame adf, int num, Company com) {
        this.ePanel = ePanel;
        this.ois = ois;
        this.oos = oos;
        this.adf = adf;
        this.num = num;
        this.company = com;
        this.id = com.getId();
        this.name = com.getName();
        this.amount = com.getAmount();
        this.idUser = com.getIdUser();

        setTitle("Компании");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(200, 0);
        setSize(1200, 800);

        panel = new JPanel();
        panel.setLayout(null);

        for (int i = 0; i < num; i++){
            String[] row = new String[4];
            row[0] = id.get(i).toString();
            row[1] = name.get(i).toString();
            row[2] = amount.get(i).toString();
            row[3] = idUser.get(i).toString();
            ctm.addData(row);
        }
        liTable = new JTable(ctm);
        liTable.setSize(700,600);
        liTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (liTable.getSelectedRowCount() > 0){
                    edit.setEnabled(true);
                    delete.setEnabled(true);
                    int row = liTable.getSelectedRow();
                    setComId((String) liTable.getValueAt(row, 0));
                    setComName((String) liTable.getValueAt(row, 1));
                    setComAmount((String) liTable.getValueAt(row, 2));
                    setComIdUser((String) liTable.getValueAt(row, 3));
                }
                else {
                    edit.setEnabled(false);
                    delete.setEnabled(false);
                }
            }
        });
        scroll = new JScrollPane(liTable);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setLocation(450,80);
        scroll.setSize(700,600);
        panel.add(scroll);

        delete = new JButton("Удалить Компанию");
        delete.setSize(190,40);
        delete.setLocation(745,25);
        delete.addActionListener(this::actionDeletePerformed);
        delete.setVisible(true);
        delete.setEnabled(false);
        panel.add(delete);

        edit = new JButton("Изменить Компанию");
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
        refresh.addActionListener(this::actionPerformed);
        refresh.setVisible(true);
        refresh.setEnabled(false);
        panel.add(refresh);

        setContentPane(panel);
        setVisible(true);
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public void setComIdUser(String comIdUser) {
        this.comIdUser = comIdUser;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public void setComAmount(String comAmount) {
        this.comAmount = comAmount;
    }

    private void actionDeletePerformed(ActionEvent actionEvent) {
        AddCompany ac = new AddCompany(null, true,ois,oos,23,Integer.parseInt(this.comId),3);
        ac.setVisible(true);
        refresh.setEnabled(true);
        flag=0;
    }

    private void actionEditPerformed(ActionEvent actionEvent) {
        //comId
        AddCompany ac = new AddCompany(null, true,ois,oos,22,Integer.parseInt(this.comId),1);
        ac.setVisible(true);
        refresh.setEnabled(true);
        flag=0;
    }

    private void actionClosePerformed(ActionEvent actionEvent) {
        this.dispose();
        adf.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (flag==0){
            Integer numReg = 14;
            try {
                oos.writeUTF(numReg.toString());
                oos.flush();
                //license
                Integer num1 = 0;
                num1 = (Integer) ois.readObject();
                server.License license = (server.License) ois.readObject();
                //license

                //hws
                Integer numberH = 0;
                numberH = (Integer) ois.readObject();
                server.Hws hws = (server.Hws) ois.readObject();
                //hws

                //com
                ctm.deleteData();
                Integer numberC = 0;
                numberC = (Integer) ois.readObject();
                server.Company com = (server.Company) ois.readObject();
                this.id = com.getId();
                this.name = com.getName();
                this.amount = com.getAmount();
                this.idUser = com.getIdUser();
                for (int i = 0; i < numberC; i++){
                        String[] row = new String[4];
                        row[0] = id.get(i).toString();
                        row[1] = name.get(i);
                        row[2] = amount.get(i).toString();
                        row[3] = idUser.get(i).toString();
                        ctm.addData(row);
                }
                liTable.repaint();
                refresh.setEnabled(false);
                //com
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            flag++;
        }
    }
}
