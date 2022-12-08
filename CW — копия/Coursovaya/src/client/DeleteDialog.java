package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteDialog extends JDialog implements ActionListener {
    private JTextField loginDelete;
    private JTextField passwordDelete;
    private JLabel loginLa;
    private JLabel passLa;
    private JLabel delId;
    private JButton sendDelete;
    private JButton cancelDelete;
    private JPanel panel;
    private JPanel epanel;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String id;
    private String log;
    private String pas;
    private int choice;
    public DeleteDialog(Frame owner, boolean modal, JPanel epanel,
                        ObjectInputStream ois, ObjectOutputStream oos,
                        String id, String log, String pas, int choice) {
        super(owner, modal);
        if (choice == 0) {
            this.epanel = epanel;
            this.ois = ois;
            this.oos = oos;
            this.id = id;
            this.log = log;
            this.pas = pas;
            this.choice = choice;
            setTitle("Удаление");
            setSize(500, 400);
            setResizable(false);
            setLocationRelativeTo(null);
            panel = new JPanel();
            panel.setLayout(null);

            loginDelete = new JTextField(log);
            loginDelete.setSize(200, 40);
            loginDelete.setLocation(150, 80);
            loginDelete.setVisible(true);
            loginDelete.setEnabled(false);
            panel.add(loginDelete);

            passwordDelete = new JTextField(pas);
            passwordDelete.setSize(200, 40);
            passwordDelete.setLocation(150, 160);
            passwordDelete.setVisible(true);
            passwordDelete.setEnabled(false);
            panel.add(passwordDelete);

            delId = new JLabel(this.id.toString());
            delId.setSize(50, 20);
            delId.setLocation(150, 20);
            delId.setVisible(true);
            panel.add(delId);

            loginLa = new JLabel("Логин");
            loginLa.setSize(50, 20);
            loginLa.setLocation(150, 50);
            loginLa.setVisible(true);
            panel.add(loginLa);

            passLa = new JLabel("Пароль");
            passLa.setSize(50, 20);
            passLa.setLocation(150, 130);
            passLa.setVisible(true);
            panel.add(passLa);

            sendDelete = new JButton("Отправить");
            sendDelete.setSize(120, 40);
            sendDelete.setLocation(190, 220);
            sendDelete.addActionListener(this::actionPerformed);
            sendDelete.setVisible(true);
            panel.add(sendDelete);

            cancelDelete = new JButton("Отмена");
            cancelDelete.setSize(120, 40);
            cancelDelete.setLocation(190, 270);
            cancelDelete.setVisible(true);
            cancelDelete.addActionListener(this::actionCancelPerformed);
            panel.add(cancelDelete);

            setContentPane(panel);
        }
        else if (choice==1) {
            this.epanel = epanel;
            this.ois = ois;
            this.oos = oos;
            this.id = id;
            this.log = log;
            this.pas = pas;
            this.choice = choice;
            setTitle("Изменение");
            setSize(500, 400);
            setResizable(false);
            setLocationRelativeTo(null);
            panel = new JPanel();
            panel.setLayout(null);

            loginDelete = new JTextField(log);
            loginDelete.setSize(200, 40);
            loginDelete.setLocation(150, 80);
            loginDelete.setVisible(true);
            panel.add(loginDelete);

            passwordDelete = new JTextField(pas);
            passwordDelete.setSize(200, 40);
            passwordDelete.setLocation(150, 160);
            passwordDelete.setVisible(true);
            panel.add(passwordDelete);

            delId = new JLabel(this.id.toString());
            delId.setSize(50, 20);
            delId.setLocation(150, 20);
            delId.setVisible(true);
            panel.add(delId);

            loginLa = new JLabel("Логин");
            loginLa.setSize(50, 20);
            loginLa.setLocation(150, 50);
            loginLa.setVisible(true);
            panel.add(loginLa);

            passLa = new JLabel("Пароль");
            passLa.setSize(50, 20);
            passLa.setLocation(150, 130);
            passLa.setVisible(true);
            panel.add(passLa);

            sendDelete = new JButton("Отправить");
            sendDelete.setSize(120, 40);
            sendDelete.setLocation(190, 220);
            sendDelete.addActionListener(this::actionPerformed);
            sendDelete.setVisible(true);
            panel.add(sendDelete);

            cancelDelete = new JButton("Отмена");
            cancelDelete.setSize(120, 40);
            cancelDelete.setLocation(190, 270);
            cancelDelete.setVisible(true);
            cancelDelete.addActionListener(this::actionCancelPerformed);
            panel.add(cancelDelete);

            setContentPane(panel);

        }
    }

    private void actionCancelPerformed(ActionEvent actionEvent) {
        epanel.setVisible(true);
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String res = delId.getText() + " " + loginDelete.getText() + " " + passwordDelete.getText();
        Integer num = choice==0? 4: 5;
        try{
            oos.writeUTF(num.toString());
            oos.flush();
            oos.writeUTF(res);
            oos.flush();
            String line= ois.readUTF();
            System.out.println(line);
            if (line.equals("Command proceeded")){
                WarningDialog wd = new WarningDialog(null, true, panel, "Успешно");
                epanel.setVisible(true);
                line="";
                this.dispose();
            }
            else {
                WarningDialog wd = new WarningDialog(null, true, panel, "Ошибка");
                epanel.setVisible(true);
                line="";
                this.dispose();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
