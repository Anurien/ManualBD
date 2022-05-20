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
     * Crea una instancia de la clase MySQL y realiza tod0 el código de
     * conexión, consulta y muestra de resultados.
     */
    public Prueba() {
        // Se mete tod0 en un try por los posibles errores de MySQL
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
                ResultSet rs = dao.listaDePersonas1();
                ConversorResultSetADefaultTableModel.rellena(rs, modelo);
                tabla.tomaDatos(modelo);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rellena(ResultSet rs, DefaultTableModel modelo) {
        ConversorResultSetADefaultTableModel.configuraColumnas(rs, modelo);
        ConversorResultSetADefaultTableModel.vaciaFilasModelo(modelo);
        ConversorResultSetADefaultTableModel.anhadeFilasDeDatos(rs, modelo);
    }

}
