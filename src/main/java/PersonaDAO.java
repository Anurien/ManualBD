import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 * Clase que permite el acceso a la base de datos
 *
 * @author nuria
 */
public class PersonaDAO {

    /**
     * Permite registrar un empleado
     *
     * @param persona
     */
    public void registrarPersona(PersonaVO persona) {
        DbConnection conex = new DbConnection();
        try {
            Statement estatuto = conex.getConnection().createStatement();
            estatuto.executeUpdate("INSERT INTO persona VALUES ('" + persona.getIdPersona() + "', '"
                    + persona.getNombrePersona() + "', '" + persona.getEdadPersona() + "', '"
                    + persona.getProfesionPersona() + "', '" + persona.getTelefonoPersona() + "')");
            JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close();
            conex.desconectar();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No se Registro la persona");
        }
    }

    /**
     * permite consultar el empleado asociado al documento enviado
     * como parametro
     *
     * @param documento
     * @return
     */
    public ArrayList<PersonaVO> consultarPersona(int documento) {
        ArrayList<PersonaVO> miEmpleado = new ArrayList<PersonaVO>();
        DbConnection conex = new DbConnection();
        try {
            PreparedStatement consulta = conex.getConnection().prepareStatement("SELECT * FROM persona where id = ? ");
            consulta.setInt(1, documento);
            //para que devuelva un objeto
            ResultSet res = consulta.executeQuery();
            if (res.next()) {
                PersonaVO persona = new PersonaVO();
                persona.setIdPersona(Integer.parseInt(res.getString("id")));
                persona.setNombrePersona(res.getString("nombre"));
                persona.setEdadPersona(Integer.parseInt(res.getString("edad")));
                persona.setProfesionPersona(res.getString("profesion"));
                persona.setTelefonoPersona(Integer.parseInt(res.getString("telefono")));
                //aqui recoges el objeto
                miEmpleado.add(persona);
            }
            res.close();
            consulta.close();
            conex.desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar la Persona\n" + e);
        }
        return miEmpleado;
    }

    /**
     * permite consultar la lista de empleados
     *
     * @return
     */
    public ArrayList<PersonaVO> listaDePersonas() {
        ArrayList<PersonaVO> miEmpleado = new ArrayList<PersonaVO>();
        DbConnection conex = new DbConnection();

        try {
            PreparedStatement consulta = conex.getConnection().prepareStatement("SELECT * FROM persona");
            ResultSet res = consulta.executeQuery();
            while (res.next()) {
                PersonaVO persona = new PersonaVO();
                persona.setIdPersona(Integer.parseInt(res.getString("id")));
                persona.setNombrePersona(res.getString("nombre"));
                persona.setEdadPersona(Integer.parseInt(res.getString("edad")));
                persona.setProfesionPersona(res.getString("profesion"));
                persona.setTelefonoPersona(Integer.parseInt(res.getString("telefono")));
                miEmpleado.add(persona);
            }
            res.close();
            consulta.close();
            conex.desconectar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar la Persona\n" + e);
        }
        return miEmpleado;
    }

    /**
     * permite consultar la lista de empleados
     *
     * @return
     */
    public ResultSet listaDePersonas1() {
        ArrayList<PersonaVO> miEmpleado = new ArrayList<PersonaVO>();
        DbConnection conex = new DbConnection();
        ResultSet res = null;
        try {
            PreparedStatement consulta = conex.getConnection().prepareStatement("SELECT * FROM persona");
            res = consulta.executeQuery();

            conex.desconectar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar la Persona\n" + e);
        }
        return res;
    }

    /**
     * permite consultar el empleado asociado al documento enviado
     * como parametro
     *
     * @param documento
     * @return
     */
    public ArrayList<PersonaVO> borrarPersona(int documento) {
        ArrayList<PersonaVO> miEmpleado = new ArrayList<PersonaVO>();
        DbConnection conex = new DbConnection();

        try {
            PreparedStatement consulta = conex.getConnection().prepareStatement("DELETE FROM persona where id = ? ");
            consulta.setInt(1, documento);
            consulta.executeUpdate();
            ResultSet res = consulta.executeQuery();
            if (res.next()) {
                PersonaVO persona = new PersonaVO();
                miEmpleado.remove(persona);
            }
            res.close();
            consulta.close();
            conex.desconectar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo borrar la Persona\n" + e);
        }
        return miEmpleado;
    }

    /**
     * Actualiza los datos de la persona en la base de datos
     *
     * @param u Persona del que se quiere actualizar los datos
     */
    public void actualizaPersona(int documento, PersonaVO u) {
        ArrayList<PersonaVO> miEmpleado = new ArrayList<PersonaVO>();
        DbConnection conex = new DbConnection();

        try {
            PreparedStatement actualiza = conex.getConnection().prepareStatement(
                    "UPDATE persona SET nombre=?,edad=?, profesion=?, telefono=? WHERE id=?");
            actualiza.setString(1, u.getNombrePersona());
            actualiza.setInt(2, u.getEdadPersona());
            actualiza.setString(3, u.getProfesionPersona());
            actualiza.setInt(4, u.getTelefonoPersona());
            actualiza.setInt(5, u.getIdPersona());
            actualiza.executeUpdate();

            actualiza.close();
            conex.desconectar();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
