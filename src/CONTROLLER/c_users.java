/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;
import VIEW.*;
import java.sql.*;
/**
 *
 * @author SENG PENTING YAKIN
 */
public interface c_users {
    public void tampil(v_users users) throws SQLException;
    public void pilih(v_users users) throws SQLException;
    public void simpan(v_users users) throws SQLException;
    public void ubah(v_users users) throws SQLException;
    public void hapus(v_users users) throws SQLException;
}
