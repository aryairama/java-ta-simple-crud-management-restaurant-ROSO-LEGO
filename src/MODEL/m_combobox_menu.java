package MODEL;

public class m_combobox_menu {

    public String value;
    public int id;

    public m_combobox_menu(String value, int id) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return value;
    }

}
