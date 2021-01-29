package MODEL;

import CONNECTION.connection;
import VIEW.*;
import CONTROLLER.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class m_dashboard implements c_dashboard {

    @Override
    public void setDashboard(v_dashboard dashboard) {
        dashboard.username.setText(m_session.getUsername());
        dashboard.setTitle("DASHBOARD - RESTAURANT ROSO LEGO");
        ImageIcon myimage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/IMG/asw.png")));
        Image img1 = myimage.getImage();
        Image img2 = img1.getScaledInstance(dashboard.logo.getWidth(), dashboard.logo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        dashboard.logo.setIcon(i);
        ImageIcon myimage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/IMG/user_log.png")));
        Image img3 = myimage1.getImage();
        Image img4 = img3.getScaledInstance(dashboard.user_Log.getWidth(), dashboard.user_Log.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i1 = new ImageIcon(img4);
        dashboard.user_Log.setIcon(i1);
    }
}
