package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Register extends JDialog implements ActionListener {
    private JTextField loginRegister;
    private JTextField passwordRegister;
    private JLabel loginLa;
    private JLabel passLa;
    private JButton sendRegister;
    private JButton cancelRegister;
    private JPanel panel;
    private JPanel epanel;
    //private Socket sock;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Register(Frame owner, boolean modal, JPanel epanel, ObjectInputStream ois, ObjectOutputStream oos) {
        super(owner, modal);
        this.epanel = epanel;
        this.ois = ois;
        this.oos = oos;
        setTitle("Регистрация пользователя");
        setSize(500, 400);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(null);

        loginRegister = new JTextField();
        loginRegister.setSize(200,40);
        loginRegister.setLocation(150, 80);
        loginRegister.setVisible(true);
        panel.add(loginRegister);

        passwordRegister = new JTextField();
        passwordRegister.setSize(200, 40);
        passwordRegister.setLocation(150, 160);
        passwordRegister.setVisible(true);
        panel.add(passwordRegister);

        loginLa = new JLabel("Логин");
        loginLa.setSize(50,20);
        loginLa.setLocation(150,50);
        loginLa.setVisible(true);
        panel.add(loginLa);

        passLa = new JLabel("Пароль");
        passLa.setSize(50, 20);
        passLa.setLocation(150, 130);
        passLa.setVisible(true);
        panel.add(passLa);

        sendRegister = new JButton("Отправить");
        sendRegister.setSize(120, 40);
        sendRegister.setLocation(190, 220);
        sendRegister.addActionListener(this::actionPerformed);
        sendRegister.setVisible(true);
        panel.add(sendRegister);

        cancelRegister = new JButton("Отмена");
        cancelRegister.setSize(120, 40);
        cancelRegister.setLocation(190, 270);
        cancelRegister.setVisible(true);
        cancelRegister.addActionListener(this::actionCancelPerformed);
        panel.add(cancelRegister);

        setContentPane(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String log;
        String pas;
        String res;
        String line="";
        log = loginRegister.getText();
        pas = passwordRegister.getText();
        res = log + " " + pas;
        Integer num = 2;
        try {
            oos.flush();
            oos.writeUTF(num.toString());
            oos.flush();
            oos.writeUTF(res);
            oos.flush();
            loginRegister.setText("");
            passwordRegister.setText("");
            line= ois.readUTF();
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
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void actionCancelPerformed(ActionEvent e) {
        epanel.setVisible(true);
        this.dispose();
    }

    public String getLoginRegister() {
        return loginRegister.getText();
    }

    public String getPasswordRegister() {
        return passwordRegister.getText();
    }
}
