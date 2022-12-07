package server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Hardware implements Serializable {
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<Integer> price = new ArrayList<Integer>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> manufacturer = new ArrayList<String>();

    public Hardware(ResultSet rs) {
        try{
            while (rs.next()){
                setId(rs.getInt(1));
                setName(rs.getString(2));
                setPrice(rs.getInt(3));
                setManufacturer(rs.getString(4));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public Hardware(Hardware obj){
        this.id = obj.getId();
        this.name = obj.getName();
        this.price = obj.getPrice();
        this.manufacturer = obj.getManufacturer();
    }

    public void print(){
        for (int i = 0; i < id.size(); i++){
            System.out.print(id.get(i)+" ");
            System.out.print(name.get(i)+" ");
            System.out.print(price.get(i)+" ");
            System.out.println(manufacturer.get(i));
        }
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<Integer> getPrice() {
        return price;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<String> getManufacturer() {
        return manufacturer;
    }

    public void setId(Integer id) {
        this.id.add(id);
    }

    public void setPrice(Integer price) {
        this.price.add(price);
    }

    public void setName(String name) {
        this.name.add(name);
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.add(manufacturer);
    }
}
