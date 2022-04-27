/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tuanlm.dto.AccountsDTO;
import tuanlm.utils.DBConector;

/**
 *
 * @author MINH TUAN
 */
public class AccountsDAO {

    private Connection cnn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cnn != null) {
            cnn.close();
        }
    }

    public boolean checkExistEmail(String email) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select email from Accounts where email = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean createAccount(AccountsDTO dto) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "insert into Accounts(email, name, role, password,status) values (?,?,?,?,?)";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getEmail());
                ps.setString(2, dto.getName());
                ps.setString(3, "student");
                ps.setString(4, dto.getPassword());
                ps.setString(5, "New");
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public AccountsDTO checkLogin(String email, String password) throws NamingException, SQLException {
        try {
            cnn = DBConector.makeConnection();
            if (cnn != null) {
                String sql = "select email, password, role, name from Accounts where email = ? and password = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if(rs.next()) {
                    String role = rs.getString("role");
                    String name = rs.getString("name");
                    AccountsDTO dto = new AccountsDTO(email, name, password, role, name);
                    return dto;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

}
