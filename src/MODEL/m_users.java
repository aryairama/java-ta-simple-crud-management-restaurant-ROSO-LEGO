package MODEL;

import CONNECTION.connection;
import VIEW.*;
import CONTROLLER.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class m_users implements c_users {

    private DefaultTableModel model;

    @Override
    public void tampil(v_users users) throws SQLException {
        users.setTitle("Management Users - RESTAURANT ROSO LEGO");
        model = new DefaultTableModel();
        users.tabel_user.setModel(model);
        model.addColumn("id");
        model.addColumn("Username");
        model.addColumn("Email");
        model.addColumn("password");
        model.addColumn("roles");
        m_session.setSelect_row("");
        try {
            Statement stat = (Statement) connection.getKoneksi().createStatement();
            ResultSet res = stat.executeQuery("select * from users");
            while (res.next()) {
                Object[] obj = new Object[5];
                obj[0] = res.getString("id");
                obj[1] = res.getString("username");
                obj[2] = res.getString("email");
                obj[3] = res.getString("password");
                obj[4] = res.getString("roles");
                model.addRow(obj);
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(users, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }

    @Override
    public void simpan(v_users users) throws SQLException {
        if (cekInput(users) == "empty") {
            JOptionPane.showMessageDialog(users, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(users) == "isset") {
            if (cekUnikField(users.email.getText(), "email", "users") == "duplicate") {
            } else {
                try {
                    Connection con = connection.getKoneksi();
                    PreparedStatement prepare = con.prepareStatement("insert users values(null,?,?,?,?)");
                    prepare.setString(1, users.email.getText());
                    prepare.setString(2, users.username.getText());
                    prepare.setString(3, users.password.getText());
                    if(users.admin.isSelected()){
                        prepare.setString(4, "admin");
                    }else if(users.kasir.isSelected()){
                        prepare.setString(4, "kasir");
                    }
                    prepare.executeUpdate();
                    tampil(users);
                    reset(users);
                    JOptionPane.showMessageDialog(users, "Data Berhasil di Simpan");
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(users, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    @Override
    public void ubah(v_users users) throws SQLException {
        if (cekInput(users) == "empty") {
            JOptionPane.showMessageDialog(users, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(users) == "isset") {
            if (m_session.getSelect_row() == "") {
                JOptionPane.showMessageDialog(users, "Mohon Untuk Memilih data yang ingin diubah");
            } else {
                try {
                    Connection con = connection.getKoneksi();
                    PreparedStatement prepare = con.prepareStatement("update users set email=?,username=?,password=?,roles=? where id =?");
                    prepare.setString(1, users.email.getText());
                    prepare.setString(2, users.username.getText());
                    prepare.setString(3, users.password.getText());
                    if(users.admin.isSelected()){
                        prepare.setString(4, "admin");
                    }else if(users.kasir.isSelected()){
                        prepare.setString(4, "kasir");
                    }
                    prepare.setString(5, m_session.getSelect_row());
                    prepare.executeUpdate();
                    tampil(users);
                    reset(users);
                    JOptionPane.showMessageDialog(users, "Data Berhasil di Ubah");
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(users, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    @Override
    public void hapus(v_users users) throws SQLException {
        if (cekInput(users) == "empty") {
            JOptionPane.showMessageDialog(users, "Mohon Untuk Mengisi Semua Field Yang Ada",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cekInput(users) == "isset") {
            try {
                Connection con = connection.getKoneksi();
                PreparedStatement prepare = con.prepareStatement("delete from users where id = ?");
                prepare.setString(1, m_session.getSelect_row());
                prepare.executeUpdate();
                tampil(users);
                reset(users);
                JOptionPane.showMessageDialog(users, "Data Berhasil di hapus");
            } catch (SQLException err) {
                JOptionPane.showMessageDialog(users, err.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void pilih(v_users users) throws SQLException {
        int i = users.tabel_user.getSelectedRow();
        if (i == -1) {
            return;
        }
        String roles = model.getValueAt(i, 4).toString();
        m_session.setSelect_row("" + model.getValueAt(i, 0));
        users.email.setText("" + model.getValueAt(i, 2));
        users.username.setText("" + model.getValueAt(i, 1));
        users.password.setText("" + model.getValueAt(i, 3));
        if (users.admin.getText().equals(roles)) {
            users.admin.setSelected(true);
        } else {
            users.kasir.setSelected(true);
        }
    }

    public void reset(v_users users) {
        users.email.setText("");
        users.password.setText("");
        users.username.setText("");
        users.roles.clearSelection();
        m_session.setSelect_row("");
    }

    public String cekInput(v_users users) throws SQLException {
        if ("".equals(users.username.getText())
                || "".equals(users.email.getText())
                || "".equals(users.password.getText())) {
            return "empty";
        } else {
            return "isset";
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
