package app.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.config.Database;

public class UserDAO {
    private static Connection connection = Database.getConnection();
    public static List<User> selectAll() {
        try {
            String sql = "CALL `selectUsers`()";
            ResultSet rs = connection.prepareStatement(sql).executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                String userName = rs.getString("username");
                String userEmail = rs.getString("email");
                Integer userId = rs.getInt("id");
                User user = new User(userName, userEmail);
                user.setId(userId);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Ocorreu um erro ao pesquisar usuários.\nCódigo do erro: "+e.getErrorCode());
        }
    }
    public static User selectOne(Integer id) {
        try {
            String sql = "CALL `selectUser`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            if (rs.next()) {
                String userName = rs.getString("username");
                String userEmail = rs.getString("email");
                Integer userId = rs.getInt("id");
                user = new User(userName, userEmail);
                user.setId(userId);
                return user;
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Ocorreu um erro ao pesquisar o usuário.\nCódigo do erro: "+e.getErrorCode());
        }
    }
    public static Integer insertUser(User user) {
        try {
            String sql = "CALL `insertUser`(?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.execute();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }
    public static Integer updateUser(User user) {
        try {
            String sql = "CALL `updateUser`(?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.execute();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }
    public static Integer deleteUser(Integer id) {
        try {
            String sql = "CALL `deleteUser`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }
    public static User authentication(String username, String password) {
        try {
            String sql = "CALL `authUser`(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            if(rs.next()) {
                String userName = rs.getString("username");
                String userEmail = rs.getString("email");
                Integer userId = rs.getInt("id");
                user = new User(userName, userEmail);
                user.setPassword(password);
                user.setId(userId);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Ocorreu um erro ao autenticar usuário.\nCódigo do erro: "+e.getErrorCode());
        }
    }
}
