package server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User implements Serializable {
    private ArrayList<Integer>id = new ArrayList<Integer>();
    private ArrayList<String>login = new ArrayList<String>();
    private ArrayList<String>password = new ArrayList<String>();

    public User(ResultSet rs) {
        try {
            while (rs.next()) {
                setId(rs.getInt(1));
                setLogin(rs.getString(2));
                setPassword(rs.getString(3));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public User(User obj){
        this.id = obj.getId();
        this.login = obj.getLogin();
        this.password = obj.getPassword();
    }
    public void print() {
        for (int i = 0; i < id.size(); i++){
            System.out.print(id.get(i)+" ");
            System.out.print(login.get(i)+" ");
            System.out.println(password.get(i));
        }
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<String> getLogin() {
        return login;
    }

    public ArrayList<String> getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id.add(id);
    }

    public void setLogin(String login) {
        this.login.add(login);
    }

    public void setPassword(String password) {
        this.password.add(password);
    }
}
