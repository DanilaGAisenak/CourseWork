package client;

import java.io.Serializable;

public interface StatusTableModel extends Serializable {
    int getRowCount();

    int getColumnCount();

    String getColumnName(int columnIndex);
}
