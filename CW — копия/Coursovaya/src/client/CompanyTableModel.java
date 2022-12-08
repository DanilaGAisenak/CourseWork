package client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CompanyTableModel extends AbstractTableModel {
    private int columnCount = 4;
    private ArrayList<String[]> data;

    public CompanyTableModel() {
        data = new ArrayList<String[]>();
        for (int i = 0; i < data.size(); i++) {
            data.add(new String[getColumnCount()]);
        }
    }
    public void addData(String[] row){
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
            case 0: return "idКомпании";
            case 1: return "Название_Компании";
            case 2: return "Количество_рабочих_станций";
            case 3: return "id_Пользователя";
        }
        return "";
    }
}
