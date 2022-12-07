package server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Company implements Serializable {
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<Integer> idUser = new ArrayList<Integer>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> amount = new ArrayList<>();

    public Company(ResultSet rs) {
        try{
            while (rs.next()){
                setId(rs.getInt(1));
                setName(rs.getString(2));
                setIdUser(rs.getInt(3));
                setAmount(rs.getInt(4));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public Company(Company obj){
        this.id = obj.getId();
        this.name = obj.getName();
        this.idUser = obj.getIdUser();
        this.amount = obj.getAmount();
    }

    public void print(){
        for (int i = 0; i < id.size(); i++){
            System.out.print(id.get(i)+" ");
            System.out.print(name.get(i)+" ");
            System.out.print(idUser.get(i)+" ");
            System.out.println(amount.get(i));
        }
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<Integer> getIdUser() {
        return idUser;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<Integer> getAmount() {
        return amount;
    }

    public void setId(Integer id) {
        this.id.add(id);
    }

    public void setIdUser(Integer idUser) {
        this.idUser.add(idUser);
    }

    public void setName(String name) {
        this.name.add(name);
    }

    public void setAmount(Integer amount) {
        this.amount.add(amount);
    }
}
