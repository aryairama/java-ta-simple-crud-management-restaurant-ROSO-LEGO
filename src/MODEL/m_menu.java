package MODEL;

import CONNECTION.connection;
import VIEW.*;
import CONTROLLER.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class m_menu implements c_menu {

    private DefaultTableModel model;

    @Override
    public void tampil(v_menu menu) throws SQLException {
        menu.setTitle("Management Menu Makanan dan Minuman - RESTAURANT ROSO LEGO");
        model = new DefaultTableModel();
        menu.tabel_makanan.setModel(model);
        model.addColumn("id");
        model.addColumn("Nama Menu");
        model.addColumn("Tipe Menu");
        model.addColumn("Harga Menu");
        m_session.setSelect_row("");
        try {
            Statement stat = (Statement) connection.getKoneksi().createStatement();
            ResultSet res = stat.executeQuery("select * from menu");
            while (res.next()) {
                Object[] obj = new Object[4];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nama_menu");
                obj[2] = res.getString("tipe_menu");
                obj[3] = res.getString("harga_menu");
                model.addRow(obj);
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(menu, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void pilih(v_menu menu) throws SQLException {

        int i = menu.tabel_makanan.getSelectedRow();
        if (i == -1) {
            return;
        }
        String menus = model.getValueAt(i, 2).toString();
        m_session.setSelect_row("" + model.getValueAt(i, 0));
        menu.nama_menu.setText("" + model.getValueAt(i, 1));
        menu.harga_menu.setText("" + model.getValueAt(i, 3));
        if (menu.makanan.getText().equals(menus)) {
            menu.makanan.setSelected(true);
        } else {
            menu.minuman.setSelected(true);
        }

    }

    @Override
    public void simpan(v_menu menu) throws SQLException {
        if (cekInput(menu) == "empty") {
            JOptionPane.showMessageDialog(menu, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(menu) == "isset") {
            if (cekUnikField(menu.nama_menu.getText(), "nama_menu", "menu") == "duplicate") {
            } else {
                try {
                    Connection con = connection.getKoneksi();
                    PreparedStatement prepare = con.prepareStatement("insert menu values(null,?,?,?)");
                    prepare.setString(1, menu.nama_menu.getText());
                    if (menu.minuman.isSelected()) {
                        prepare.setString(2, "minuman");
                    } else if (menu.makanan.isSelected()) {
                        prepare.setString(2, "makanan");
                    }
                    prepare.setString(3, menu.harga_menu.getText());
                    prepare.executeUpdate();
                    tampil(menu);
                    reset(menu);
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(menu, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    @Override
    public void ubah(v_menu menu) throws SQLException {
        if (cekInput(menu) == "empty") {
            JOptionPane.showMessageDialog(menu, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(menu) == "isset") {
            if (m_session.getSelect_row() == "") {
                JOptionPane.showMessageDialog(menu, "Mohon Untuk Memilih data yang ingin diubah");
            } else {
                try {
                    Connection con = connection.getKoneksi();
                    PreparedStatement prepare = con.prepareStatement("update menu set nama_menu=?,tipe_menu=?,harga_menu=? where id = ?");
                    prepare.setString(1, menu.nama_menu.getText());
                    if (menu.minuman.isSelected()) {
                        prepare.setString(2, "minuman");
                    } else if (menu.makanan.isSelected()) {
                        prepare.setString(2, "makanan");
                    }
                    prepare.setString(3, menu.harga_menu.getText());
                    prepare.setString(4, m_session.getSelect_row());
                    prepare.executeUpdate();
                    tampil(menu);
                    reset(menu);
                    JOptionPane.showMessageDialog(menu, "Data Berhasil di Ubah");
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(menu, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void hapus(v_menu menu) throws SQLException {
        if (cekInput(menu) == "empty") {
            JOptionPane.showMessageDialog(menu, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(menu) == "isset") {
            try {
                Connection con = connection.getKoneksi();
                PreparedStatement prepare = con.prepareStatement("delete from menu where id = ?");
                prepare.setString(1, m_session.getSelect_row());
                prepare.executeUpdate();
                tampil(menu);
                reset(menu);
                JOptionPane.showMessageDialog(menu, "Data Berhasil di hapus");
            } catch (SQLException err) {
                JOptionPane.showMessageDialog(menu, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void reset(v_menu menu) throws SQLException {
        menu.harga_menu.setText("");
        menu.nama_menu.setText("");
        menu.buttonGroup1.clearSelection();
        m_session.setSelect_row("");
    }

    public String cekInput(v_menu menu) throws SQLException {
        if ("".equals(menu.harga_menu.getText())
                || "".equals(menu.nama_menu.getText())
                || menu.buttonGroup1.getSelection() == null) {
            return "empty";
        } else {
            return "isset";
        }
    }
    
    public void validasiAngka(KeyEvent e,v_menu menu) {
        if (Character.isAlphabetic(e.getKeyChar())) {
            e.consume();
            JOptionPane.showMessageDialog(menu, "Masukan Hanya Angka", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String cekUnikField(String input, String field, String tabel) throws SQLException {
        try {
            Statement stat = (Statement) connection.getKoneksi().createStatement();
            String sql = "select * from " + tabel + " where " + field + " = '" + input + "'";
            ResultSet res = stat.executeQuery(sql);
            res.next();
            if (res.getRow() > 0) {
                JOptionPane.showMessageDialog(null, field + " sudah terdaftar", "Warning", JOptionPane.WARNING_MESSAGE);
                return "duplicate";
            } else {
                return "unique";
            }
        } catch (SQLException e) {
            return "error";
        }
    }
}
