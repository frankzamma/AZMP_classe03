package weatherstyle.gestioneguardaroba.storage.dao;

import net.sf.saxon.expr.Component;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Guardaroba;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Maglia;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Pantaloni;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Scarpe;
import weatherstyle.utils.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe implementa tutti i metodi dell'interfaccia
 * <code>GuardarobaDAOInterface</code>
 */
public class GuardarobaDAOImpl implements GuardarobaDAOInterface {

    @Override
    public Guardaroba doRetrieveGuardarobaById(int id) {
        try (Connection connection =  ConnectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from Guardaroba WHERE ID = ?");

            statement.setInt(1,id);

            ResultSet res =  statement.executeQuery();
            if (res.next()) {
                return creaGuardaroba(res);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean doSaveGuardaroba(int idUtente) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement statement =  connection.prepareStatement(
                    "insert into Guardaroba (ID, numeroCapi) values (?,0)",Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1,idUtente);

            int res =  statement.executeUpdate();

            if (res == 0) {
                return false;
            } else {
                return true;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Questo metodo restituisce un oggetto <code>Guardaroba</code>.
     * Viene creato a partire dal risultato della query.
     */
    public Guardaroba creaGuardaroba(ResultSet res) throws SQLException {
        Guardaroba g = new Guardaroba();

        //chiamare i metodi di sopra da spostare e mettere le liste nel guardaroba
        int id =  res.getInt(1);
        int numeroCapi = res.getInt(2);

        g.setId(id);
        g.setNumeroCapi(numeroCapi);

        return g;
    }
}
