import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Prueba {
    /**
     * Clase para manejo de la base de datos
     */
    private DbConnection bd;
    private PersonaDAO dao;

    /**
     * Clase encargada de construir y visualizar la ventana
     */
    private Interfaz tabla;

    /**
     * Clase donde se guardan los datos leidos de base de datos
     */
    private DefaultTableModel modelo;

    /**
     * Crea una instancia de la clase MySQL y realiza todo el código de
     * conexión, consulta y muestra de resultados.
     */
    public Prueba() {
        // Se mete todo en un try por los posibles errores de MySQL
        try {
            // Se instancian las clases necesarias
            bd = new DbConnection();
            modelo = new DefaultTableModel();
            tabla = new Interfaz();
            dao = new PersonaDAO();

            // Se comienza.
            bd.getConnection();
            tabla.creaYMuestraVentana();

            // Bucle infinito, cada segundo se reconsulta la base de datos
            // y se muestran los resultados en pantalla
            while (true) {
                ResultSet rs = (ResultSet) dao.listaDePersonas();
                ConversorResultSet.rellena(rs, modelo);
                tabla.tomaDatos(modelo);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rellena(ResultSet rs, DefaultTableModel modelo) {
        configuraColumnas(rs, modelo);
        vaciaFilasModelo(modelo);
        anhadeFilasDeDatos(rs, modelo);
    }

    /**
     * Añade al DefaultTableModel las filas correspondientes al ResultSet.
     *
     * @param rs     El resultado de la consulta a base de datos
     * @param modelo El DefaultTableModel que queremos rellenar.
     */
    private static void anhadeFilasDeDatos(ArrayList<PersonaVO> rs,
                                           DefaultTableModel modelo) {
        int numeroFila = 0;
        try {
            // Para cada registro de resultado en la consulta
            while (rs.next()) {
                // Se crea y rellena la fila para el modelo de la tabla.
                Object[] datosFila = new Object[modelo.getColumnCount()];
                for (int i = 0; i < modelo.getColumnCount(); i++)
                    datosFila[i] = rs.getObject(i + 1);

                modelo.addRow(datosFila);
                numeroFila++;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
