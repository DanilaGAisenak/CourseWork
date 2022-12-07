package client;

import server.Hws;
import server.License;

import javax.swing.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UserFrame extends JFrame implements ActionListener, WindowListener {

    private JPanel panel;
    private Integer id;
    private String login;
    private String pass;
    private SignIn objSi;
    private JLabel userLa;
    private JTextField userText;
    private JScrollPane scrollLicense;
    private JScrollPane scrollHws;
    private JTable licenseT;
    private JTable hwT;
    private HWSTableModel hwsm = new HWSTableModel();
    private LicenseTableModel ltm = new LicenseTableModel();
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private JButton addLicense;
    private JButton editLicense;
    private JButton deleteLicense;
    private JButton addHws;
    private JButton editHws;
    private JButton deleteHws;
    private JButton editUser;
    private JButton addCompany;
    private JButton editCompany;
    private ArrayList<Integer> idHws;
    private ArrayList<Integer> companyIdHw;
    private ArrayList<Integer> hwId;
    private ArrayList<Integer> amountHw;
    private ArrayList<String> dateStartHw;
    private ArrayList<String> dateEndHw;
    private ArrayList<Integer> idLicense;
    private ArrayList<Integer> companyIdSw;
    private ArrayList<Integer> swId;
    private ArrayList<String> dateEndSw;
    private server.Hws hws;
    private server.License license;
    private Integer amountHws;
    private Integer amountLicense;
    private String idLicenseSelected;
    private String companyIdSwSelected;
    private String swIdSelected;
    private String dateEndSwSelected;

    public UserFrame(ObjectInputStream ois, ObjectOutputStream oos, SignIn si, int id, String log, String pas,
                     Hws hws, License license, Integer amountHws, Integer amountLicense) {
        this.ois = ois;
        this.oos = oos;
        this.objSi = si;
        this.id = id; //id входящего клиента
        this.login = log; //логин входящего клиента
        this.pass = pas; // парольвходящего клиента
        this.hws = hws;
        this.license = license;
        this.idHws = hws.getHwId();
        this.companyIdHw = hws.getCompanyId();
        this.hwId = hws.getHwId();
        this.dateStartHw = hws.getDateStart();
        this.dateEndHw = hws.getDateEnd();
        this.amountHw = hws.getAmount();
        this.idLicense = license.getId();
        this.companyIdSw = license.getCompanyId();
        this.swId = license.getSwId();
        this.dateEndSw = license.getDateEnd();
        this.amountHws = amountHws;
        this.amountLicense = amountLicense;

        for (int i = 0; i < this.amountHws; i++){
            String[] row = new String[6];
            row[0] = idHws.get(i).toString();
            row[1] = companyIdHw.get(i).toString();
            row[2] = hwId.get(i).toString();
            row[3] = dateStartHw.get(i);
            row[4] = dateEndHw.get(i);
            row[5] = amountHw.get(i).toString();
            hwsm.addData(row);
        }
        for (int i = 0; i < this.amountLicense; i++){
            String[] row = new String[4];
            row[0] = idLicense.get(i).toString();
            row[1] = companyIdSw.get(i).toString();
            row[2] = swId.get(i).toString();
            row[3] = dateEndSw.get(i);
            ltm.addData(row);
        }

        setSize(1400,800);
        setTitle("Пользователь");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(100, 0);

        panel = new JPanel();
        panel.setLayout(null);

        licenseT = new JTable(ltm);
        licenseT.setSize(500,300);
        licenseT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (licenseT.getSelectedRowCount() > 0){
                    editLicense.setEnabled(true);
                    deleteLicense.setEnabled(true);
                    int row = licenseT.getSelectedRow();
                    setIdLicenseSelected((String) licenseT.getValueAt(row, 0));
                    setCompanyIdSwSelected((String) licenseT.getValueAt(row, 1));
                    setSwIdSelected((String) licenseT.getValueAt(row, 2));
                    setDateEndSwSelected((String) licenseT.getValueAt(row, 3));
                }
                else {
                    editLicense.setEnabled(false);
                    deleteLicense.setEnabled(false);
                }
            }
        });
        scrollLicense = new JScrollPane(licenseT);
        scrollLicense.setSize(500,300);
        scrollLicense.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollLicense.setLocation(50,50);
        panel.add(scrollLicense);

        setContentPane(panel);
        si.setVisible(false);
    }

    public void setIdLicenseSelected(String idLicenseSelected) {
        this.idLicenseSelected = idLicenseSelected;
    }

    public void setCompanyIdSwSelected(String companyIdSwSelected) {
        this.companyIdSwSelected = companyIdSwSelected;
    }

    public void setSwIdSelected(String swIdSelected) {
        this.swIdSelected = swIdSelected;
    }

    public void setDateEndSwSelected(String dateEndSwSelected) {
        this.dateEndSwSelected = dateEndSwSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
