package CONTROLLER;

import VIEW.*;
import java.sql.*;

public interface c_penjualan {

    public void comboboxMakanan(v_penjualan penjualan) throws SQLException;
    
    public void totalBayar(v_penjualan penjualan) throws SQLException;
    
    public void tampil(v_penjualan penjualan) throws SQLException;

    public void pilih(v_penjualan penjualan) throws SQLException;

    public void simpan(v_penjualan penjualan) throws SQLException;

    public void ubah(v_penjualan penjualan) throws SQLException;

    public void hapus(v_penjualan penjualan) throws SQLException;

    public void reset(v_penjualan penjualan) throws SQLException;
}
