package MODEL;

import CONNECTION.connection;
import VIEW.*;
import CONTROLLER.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class m_login implements c_login {

    @Override
    public void login(v_login login) throws SQLException {
        try {
            Statement stat = (Statement) connection.getKoneksi().createStatement();
            String sql = "select * from users where email = '" + login.user.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                String passwords = String.valueOf(login.password.getPassword());
                if (passwords.equals(res.getString("password"))) {
                    m_session.set_Id_Log(res.getString("id"));
                    m_session.setUsername(res.getString("username"));
                    m_session.setEmail(res.getString("email"));
                    m_session.setRoles(res.getString("roles"));
                    new v_dashboard().setVisible(true);
                    login.dispose();
                } else {
                    JOptionPane.showMessageDialog(login, "Password Salah");
                    login.password.setText("");
                    login.user.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(login, "User tidak ditemukan");
                login.user.setText("");
                login.user.requestFocus();
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(login, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
