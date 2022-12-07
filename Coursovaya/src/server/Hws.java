package server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Hws implements Serializable {
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<Integer> companyId = new ArrayList<Integer>();
    private ArrayList<Integer> hwId = new ArrayList<Integer>();
    private ArrayList<Integer> amount = new ArrayList<Integer>();
    private ArrayList<String> dateStart = new ArrayList<String>();
    private ArrayList<String> dateEnd = new ArrayList<String>();

    public Hws(ResultSet rs) {
        try {
            while (rs.next()){
                setId(rs.getInt(1));
                setCompanyId(rs.getInt(2));
                setHwId(rs.getInt(3));
                setDateStart(rs.getString(4));
                setDateEnd(rs.getString(5));
                setAmount(rs.getInt(6));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void print(){
        for (int i = 0; i < id.size(); i++){
            System.out.print(id.get(i)+" ");
            System.out.print(companyId.get(i)+" ");
            System.out.print(hwId.get(i)+" ");
            System.out.print(dateStart.get(i)+" ");
            System.out.print(dateEnd.get(i)+" ");
            System.out.println(amount.get(i));
        }
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<Integer> getCompanyId() {
        return companyId;
    }

    public ArrayList<Integer> getHwId() {
        return hwId;
    }

    public ArrayList<Integer> getAmount() {
        return amount;
    }

    public ArrayList<String> getDateStart() {
        return dateStart;
    }

    public ArrayList<String> getDateEnd() {
        return dateEnd;
    }

    public void setId(Integer id) {
        this.id.add(id);
    }

    public void setCompanyId(Integer companyId) {
        this.companyId.get(companyId);
    }

    public void setHwId(Integer hwId) {
        this.hwId.get(hwId);
    }

    public void setAmount(Integer amount) {
        this.amount.get(amount);
    }

    public void setDateStart(String dateStart) {
        this.dateStart.add(dateStart);
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd.add(dateEnd);
    }
}
