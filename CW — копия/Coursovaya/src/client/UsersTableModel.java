package client;

import server.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class UsersTableModel extends AbstractTableModel {
    private int columnCount = 3;
    private ArrayList<String[]> data;

    public UsersTableModel() {
        data = new ArrayList<String[]>();
        for(int i = 0; i < data.size(); i++){
            data.add(new String[getColumnCount()]);
        }
    }

    public void addData(String[] row ){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        data.add(rowTable);
        this.fireTableDataChanged();
    }

    public void deleteData(){
        data.clear();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = data.get(rowIndex);
        return row[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex){
            case 0: return "idПользователя";
            case 1: return "Логин";
            case 2: return "пароль";
        }
        return "";
    }
}
