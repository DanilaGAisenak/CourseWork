package client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class HardwareTableModel extends AbstractTableModel {
    private int columnCount = 4;
    private ArrayList<String[]> data;

    public HardwareTableModel() {
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
            case 0: return "id_АО";
            case 1: return "Название_АО";
            case 2: return "Цена_АО";
            case 3: return "Производитель_АО";
        }
        return "";
    }
}
