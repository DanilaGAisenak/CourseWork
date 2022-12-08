package client;

import server.License;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LicensesFrame extends JFrame implements ActionListener {
    private JPanel panel;
    private LicenseTableModel ltm = new LicenseTableModel();
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
    private ArrayList<Integer> companyId;
    private ArrayList<Integer> swId;
    private ArrayList<String> dateEnd;
    private server.License license;
    private AdminFrame adf;
    private String liId;
    private String liCompany;
    private String liDateEnd;
    private String liSwId;
    private int flag=0;
    private int num;//количество строк в таблице

    public LicensesFrame(JPanel ePanel, ObjectInputStream ois, ObjectOutputStream oos,
                         AdminFrame adf, int num, License li) {
        this.ePanel = ePanel;
        this.ois = ois;
        this.oos = oos;
        this.adf = adf;
        this.num = num;
        this.license = li;
        this.id = li.getId();
        this.companyId = li.getCompanyId();
        this.swId = li.getSwId();
        this.dateEnd = li.getDateEnd();

        setTitle("Лицензии");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(200, 0);
        setSize(1200, 800);

        panel = new JPanel();
        panel.setLayout(null);

        for (int i = 0; i < num; i++){
            String[] row = new String[4];
            row[0] = id.get(i).toString();
            row[1] = companyId.get(i).toString();
            row[2] = swId.get(i).toString();
            row[3] = dateEnd.get(i).toString();
            ltm.addData(row);
        }
        liTable = new JTable(ltm);
        liTable.setSize(700,600);
        liTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (liTable.getSelectedRowCount() > 0){
                    edit.setEnabled(true);
                    delete.setEnabled(true);
                    int row = liTable.getSelectedRow();
                    setLiId((String) liTable.getValueAt(row, 0));
                    setLiCompany((String) liTable.getValueAt(row, 1));
                    setLiSwId((String) liTable.getValueAt(row, 2));
                    setLiDateEnd((String) liTable.getValueAt(row, 3));
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

        delete = new JButton("Удалить Лицензию");
        delete.setSize(190,40);
        delete.setLocation(745,25);
        delete.addActionListener(this::actionDeletePerformed);
        delete.setVisible(true);
        delete.setEnabled(false);
        panel.add(delete);

        edit = new JButton("Продлить Лицензию");
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

    private void actionClosePerformed(ActionEvent actionEvent) {
        this.dispose();
        adf.setVisible(true);
    }

    private void actionEditPerformed(ActionEvent actionEvent) {
        Integer num = 6;
        try {
            oos.writeUTF(num.toString());
            oos.flush();
            Integer num1 = 0;
            num1 = (Integer) ois.readObject();
            server.Software sw = (server.Software) ois.readObject();
            num = 10;
            oos.writeUTF(num.toString());
            oos.flush();
            Integer hwAm=0;
            hwAm = (Integer) ois.readObject();
            server.Hardware hw = (server.Hardware) ois.readObject();
            ProlongLicense pl = new ProlongLicense(null, true,ois,oos,18,liId, sw, hw, liSwId);
            pl.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        flag=0;
        refresh.setEnabled(true);
    }

    private void actionDeletePerformed(ActionEvent actionEvent) {
        Integer num = 6;
        try {
            oos.writeUTF(num.toString());
            oos.flush();
            Integer num1 = 0;
            num1 = (Integer) ois.readObject();
            server.Software sw = (server.Software) ois.readObject();
            num = 10;
            oos.writeUTF(num.toString());
            oos.flush();
            Integer hwAm=0;
            hwAm = (Integer) ois.readObject();
            server.Hardware hw = (server.Hardware) ois.readObject();
            AddDialog ad = new AddDialog(null, true,ois,oos,19,sw,num1,liId,liCompany,1);
            ad.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        refresh.setEnabled(true);
        flag=0;
        
    }

    public void setLiId(String liId) {
        this.liId = liId;
    }

    public void setLiCompany(String liCompany) {
        this.liCompany = liCompany;
    }

    public void setLiDateEnd(String liDateEnd) {
        this.liDateEnd = liDateEnd;
    }

    public void setLiSwId(String liSwId) {
        this.liSwId = liSwId;
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
                this.id = license.getId();
                this.companyId = license.getCompanyId();
                this.swId = license.getSwId();
                this.dateEnd = license.getDateEnd();
                ltm.deleteData();
                for (int i = 0; i < num1; i++){
                    String[] row = new String[4];
                    row[0] = id.get(i).toString();
                    row[1] = companyId.get(i).toString();
                    row[2] = swId.get(i).toString();
                    row[3] = dateEnd.get(i);
                    ltm.addData(row);
                }
                liTable.repaint();
                //license

                //hws
                Integer numberH = 0;
                numberH = (Integer) ois.readObject();
                server.Hws hws = (server.Hws) ois.readObject();
                //hws

                //com
                Integer numberC = 0;
                numberC = (Integer) ois.readObject();
                server.Company com = (server.Company) ois.readObject();
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
