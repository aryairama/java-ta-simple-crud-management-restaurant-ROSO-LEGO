package MODEL;

public class m_session {

    private static String id_log, username, email,select_row,roles;

    public static String get_Id_Log() {
        return id_log;
    }

    public static void set_Id_Log(String id_log) {
        m_session.id_log = id_log;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String aEmail) {
        email = aEmail;
    }

    public static String getSelect_row() {
        return select_row;
    }

    public static void setSelect_row(String aSelect_row) {
        select_row = aSelect_row;
    }

    public static String getRoles() {
        return roles;
    }

    public static void setRoles(String aRoles) {
        roles = aRoles;
    }
    
    
}
