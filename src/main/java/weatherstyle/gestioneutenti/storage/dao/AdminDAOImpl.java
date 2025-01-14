package weatherstyle.gestioneutenti.storage.dao;

import weatherstyle.gestioneutenti.applicationlogic.logic.beans.Admin;
import weatherstyle.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Francesco Giuseppe Zammarrelli
 * La classe Admin dao.
 */
public class AdminDAOImpl implements AdminDAOInterface {
    @Override
    public Admin doRetrieveAdminByEmailAndPassword(String email,String password) {
        try (Connection connection =  ConnectionPool.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * from admin WHERE email = ? AND password = ?");

            statement.setString(1,email);
            statement.setString(2,password);

            ResultSet res =  statement.executeQuery();
            if (res.next()) {
                return creaAdmin(res);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Admin doRetrieveAdminById(int id) {
        try (Connection connection =  ConnectionPool.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * from admin WHERE id = ?");

            statement.setInt(1,id);

            ResultSet res =  statement.executeQuery();
            if (res.next()) {
                return creaAdmin(res);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea admin admin.
     *
     * @param res the res
     * @return the admin
     * @throws SQLException the sql exception
     */
    public Admin creaAdmin(ResultSet res) throws SQLException {
        Admin admin = new Admin();

        int id =  res.getInt(1);
        String nome =  res.getString(2);
        String cognome  = res.getString(3);
        String email =  res.getString(4);
        String password =  res.getString(5);

        admin.setId(id);
        admin.setNome(nome);
        admin.setCognome(cognome);
        admin.setEmail(email);
        admin.setPassword(password);

        return admin;
    }
}
