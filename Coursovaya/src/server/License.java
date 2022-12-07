package server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class License implements Serializable {
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<Integer> companyId = new ArrayList<Integer>();
    private ArrayList<Integer> swId = new ArrayList<Integer>();
    private ArrayList<String> dateEnd = new ArrayList<String>();

    public License(ResultSet rs) {
        try {
            while (rs.next()) {
                setId(rs.getInt(1));
                setCompanyId(rs.getInt(2));
                setSwId(rs.getInt(3));
                setDateEnd(rs.getString(4));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public License(License obj){
        this.id = obj.getId();
        this.companyId = obj.getCompanyId();
        this.swId = obj.getSwId();
        this.dateEnd = obj.getDateEnd();
    }
    public void print() {
        for (int i = 0; i < id.size(); i++){
            System.out.print(id.get(i)+" ");
            System.out.print(companyId.get(i)+" ");
            System.out.println(swId.get(i));
        }
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<Integer> getCompanyId() {
        return companyId;
    }

    public ArrayList<Integer> getSwId() {
        return swId;
    }

    public ArrayList<String> getDateEnd() {
        return dateEnd;
    }

    public void setId(Integer id) {
        this.id.add(id);
    }

    public void setCompanyId(Integer companyId) {
        this.companyId.add(companyId);
    }

    public void setSwId(Integer swId) {
        this.swId.add(swId);
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd.add(dateEnd);
    }
}
