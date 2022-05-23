import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interfaz {
    PersonaDAO daoP = new PersonaDAO();

    public void tabla() throws SQLException {

        ResultSet res = daoP.listaDePersonas1();

        String columns[] = {"id", "Nombre", "Edad", "Profesion", "Telefono"};
        String data[][] = new String[10][5];

        int i = 0;
        while (res.next()) {
            int id = res.getInt("id");
            String nom = res.getString("nombre");
            int age = res.getInt("edad");
            String pro = res.getString("profesion");
            String tlf = res.getString("telefono");
            data[i][0] = id + "";
            data[i][1] = nom;
            data[i][2] = age + "";
            data[i][3] = pro;
            data[i][4] = tlf;
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        JScrollPane pane = new JScrollPane(table);
        JFrame f = new JFrame("Tabla base");
        JPanel panel = new JPanel();
        panel.add(pane);
        f.add(panel);
        f.setSize(500, 250);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }
}

