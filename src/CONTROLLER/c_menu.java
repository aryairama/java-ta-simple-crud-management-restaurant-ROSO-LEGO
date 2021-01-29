package CONTROLLER;

import VIEW.*;
import java.sql.*;

public interface c_menu {

    public void tampil(v_menu menu) throws SQLException;

    public void pilih(v_menu menu) throws SQLException;

    public void simpan(v_menu menu) throws SQLException;

    public void ubah(v_menu menu) throws SQLException;

    public void hapus(v_menu menu) throws SQLException;
    
    public void reset(v_menu menu) throws SQLException;
}
