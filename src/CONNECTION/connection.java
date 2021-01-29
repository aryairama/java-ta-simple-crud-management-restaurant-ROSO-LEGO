package CONNECTION;

import java.sql.*;
import javax.swing.JOptionPane;

public class connection {

    private static Connection conn;

    public static Connection getKoneksi() {
        String host = "jdbc:mysql://localhost:3306/rosolego?serverTimezone=UTC",
                user = "root",
                pass = "";
        try {
            conn = (Connection) DriverManager.getConnection(host, user, pass);
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return conn;
    }
}
