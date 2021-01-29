package MODEL;

import CONNECTION.connection;
import VIEW.*;
import CONTROLLER.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class m_penjualan implements c_penjualan {

    private DefaultComboBoxModel combo;
    private DefaultTableModel model;

    @Override
    public void comboboxMakanan(v_penjualan penjualan) throws SQLException {
        penjualan.setTitle("Management Penjualan - RESTAURANT ROSO LEGO");
        Statement stat = (Statement) connection.getKoneksi().createStatement();
        ResultSet res = stat.executeQuery("select id,nama_menu from menu");
        combo = new DefaultComboBoxModel();
        while (res.next()) {
            combo.addElement(new m_combobox_menu(res.getString("nama_menu"), res.getInt("id")));
        }
        penjualan.makanan.setModel(combo);
    }

    @Override
    public void totalBayar(v_penjualan penjualan) throws SQLException {
        Object item = penjualan.makanan.getSelectedItem();
        int id_menu = ((m_combobox_menu) item).getId();
        int jumlah = (int) penjualan.jumlah.getValue();
        Statement stat = (Statement) connection.getKoneksi().createStatement();
        ResultSet res = stat.executeQuery("select harga_menu from menu where id ='" + id_menu + "'");
        res.next();
        penjualan.total.setText("" + jumlah * res.getInt("harga_menu"));
    }

    @Override
    public void tampil(v_penjualan penjualan) throws SQLException {
        penjualan.setTitle("Management Penjualan Makanan dan Minuman - RESTAURANT ROSO LEGO");
        ((JSpinner.DefaultEditor) penjualan.jumlah.getEditor()).getTextField().setEditable(false);
        model = new DefaultTableModel();
        penjualan.tabel_penjualan.setModel(model);
        model.addColumn("id");
        model.addColumn("Pembeli");
        model.addColumn("Menu");
        model.addColumn("Jumlah Pesan");
        model.addColumn("Total Bayar");
        model.addColumn("Jam Pesan");
        model.addColumn("Petugas");
        m_session.setSelect_row("");
        try {
            Statement stat = (Statement) connection.getKoneksi().createStatement();
            ResultSet res
                    = stat.executeQuery("select menu.nama_menu as menu,users.username as petugas,"
                            + "penjualan.pembeli,penjualan.jumlah,penjualan.total,penjualan.waktu,"
                            + "penjualan.id from penjualan inner join menu on penjualan.menu = menu.id "
                            + "inner join users on penjualan.petugas = users.id");
            while (res.next()) {
                Object[] obj = new Object[7];
                obj[0] = res.getString("penjualan.id");
                obj[1] = res.getString("penjualan.pembeli");
                obj[2] = res.getString("menu");
                obj[3] = res.getString("penjualan.jumlah");
                obj[4] = res.getString("penjualan.total");
                obj[5] = res.getString("penjualan.waktu");
                obj[6] = res.getString("petugas");
                model.addRow(obj);
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(penjualan, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void pilih(v_penjualan penjualan) throws SQLException {
        int i = penjualan.tabel_penjualan.getSelectedRow();
        if (i == -1) {
            return;
        }
        m_session.setSelect_row("" + model.getValueAt(i, 0));
        Statement stat = (Statement) connection.getKoneksi().createStatement();
        ResultSet res = stat.executeQuery("select menu from penjualan where id ='" + m_session.getSelect_row() + "'");
        res.next();
        penjualan.jumlah.setValue(Integer.parseInt("" + model.getValueAt(i, 3)));
        penjualan.total.setText("" + model.getValueAt(i, 4));
        penjualan.pembeli.setText("" + model.getValueAt(i, 1));
        penjualan.makanan.getModel().setSelectedItem(new m_combobox_menu(model.getValueAt(i, 2).toString(), res.getInt("menu")));
    }

    @Override
    public void simpan(v_penjualan penjualan) throws SQLException {
        Object item = penjualan.makanan.getSelectedItem();
        int id_menu = ((m_combobox_menu) item).getId();
        if (cekInput(penjualan) == "empty") {
            JOptionPane.showMessageDialog(penjualan, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(penjualan) == "isset") {
            try {
                Connection con = connection.getKoneksi();
                PreparedStatement prepare = con.prepareStatement("insert penjualan values(null,?,?,?,?,null,?)");
                prepare.setString(1, penjualan.pembeli.getText());
                prepare.setInt(2, id_menu);
                prepare.setString(3, penjualan.jumlah.getValue().toString());
                prepare.setString(4, penjualan.total.getText());
                prepare.setString(5, m_session.get_Id_Log());
                prepare.executeUpdate();
                tampil(penjualan);
                reset(penjualan);
            } catch (SQLException err) {
                JOptionPane.showMessageDialog(penjualan, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void ubah(v_penjualan penjualan) throws SQLException {
        Object item = penjualan.makanan.getSelectedItem();
        int id_menu = ((m_combobox_menu) item).getId();
        if (cekInput(penjualan) == "empty") {
            JOptionPane.showMessageDialog(penjualan, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(penjualan) == "isset") {
            if (m_session.getSelect_row() == "") {
                JOptionPane.showMessageDialog(penjualan, "Mohon Untuk Memilih data yang ingin diubah");
            } else {
                try {
                    Connection con = connection.getKoneksi();
                    PreparedStatement prepare = con.prepareStatement("update penjualan set pembeli=?,menu=?,jumlah=?,total=? where id =?");
                    prepare.setString(1, penjualan.pembeli.getText());
                    prepare.setInt(2, id_menu);
                    prepare.setString(3, penjualan.jumlah.getValue().toString());
                    prepare.setString(4, penjualan.total.getText());
                    prepare.setString(5, m_session.getSelect_row());
                    prepare.executeUpdate();
                    tampil(penjualan);
                    reset(penjualan);
                    JOptionPane.showMessageDialog(penjualan, "Data Berhasil di Ubah");
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(penjualan, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

    public void hapus(v_penjualan penjualan) throws SQLException {
        if (cekInput(penjualan) == "empty") {
            JOptionPane.showMessageDialog(penjualan, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(penjualan) == "isset") {
            try {
                Connection con = connection.getKoneksi();
                PreparedStatement prepare = con.prepareStatement("delete from penjualan where id = ?");
                prepare.setString(1, m_session.getSelect_row());
                prepare.executeUpdate();
                tampil(penjualan);
                reset(penjualan);
                JOptionPane.showMessageDialog(penjualan, "Data Berhasil di hapus");
            } catch (SQLException err) {
                JOptionPane.showMessageDialog(penjualan, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void reset(v_penjualan penjualan) throws SQLException {
        penjualan.pembeli.setText("");
        penjualan.makanan.setSelectedIndex(0);
        penjualan.jumlah.setValue(1);
        m_session.setSelect_row("");
    }

    public String cekInput(v_penjualan penjualan) throws SQLException {
        if ("".equals(penjualan.pembeli.getText())) {
            return "empty";
        } else {
            return "isset";
        }
    }

}
