package ba.edu.ibu.roms;

public class RomsProvider {

    private int rows_count = 3;
    private int cols_count = 3;

    public RomsProvider() {

    }

    public int getRowsCount() {
        return rows_count;
    }
    public void setRowsCount(int rows_count) {
        this.rows_count = rows_count;
    }
    public int getColsCount() {
        return cols_count;
    }
    public void setColsCount(int cols_count) {
        this.cols_count = cols_count;
    }

}
