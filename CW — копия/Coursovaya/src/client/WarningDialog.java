package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WarningDialog extends JDialog implements ActionListener {

    private JPanel panel;
    private JPanel panel1;
    private JButton btn;
    private JLabel la;

    public WarningDialog(Frame owner, boolean modal,JPanel ePanel) {
        super(owner, modal);
        this.panel1 = ePanel;
        setTitle("Внимание");
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(350,200);
        panel = new JPanel();
        panel.setLayout(null);

        btn = new JButton("OK");
        btn.setLocation(125, 100);
        btn.setSize(100, 25);
        btn.addActionListener(this::actionPerformed);
        btn.setVisible(true);
        panel.add(btn);

        la = new JLabel("Логин или пароль введены неверно");
        la.setLocation(50, 60);
        la.setSize(250, 20);
        la.setVisible(true);
        panel.add(la);

        setContentPane(panel);
        setVisible(true);
    }
    public WarningDialog(Frame owner, boolean modal,JPanel epanel, String str){
        super(owner, modal);
        this.panel1 = epanel;
        setTitle("Внимание");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(350,200);
        panel = new JPanel();
        panel.setLayout(null);

        btn = new JButton("OK");
        btn.setLocation(125, 100);
        btn.setSize(100, 25);
        btn.addActionListener(this::actionPerformed);
        btn.setVisible(true);
        panel.add(btn);

        la = new JLabel(str);
        la.setLocation(50, 60);
        la.setSize(250, 20);
        la.setVisible(true);
        panel.add(la);

        setContentPane(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel1.setVisible(true);
        this.dispose();
    }
}
