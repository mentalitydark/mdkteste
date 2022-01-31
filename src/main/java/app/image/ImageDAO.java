package app.image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.config.Database;

public class ImageDAO {
    private static Connection connection = Database.getConnection();
    public static List<Image> selectAll() {
        try {
            String sql = "CALL `selectImages`()";
            ResultSet rs = connection.prepareStatement(sql).executeQuery();
            List<Image> images = new ArrayList<>();
            while (rs.next()) {
                String imageName = rs.getString("name");
                String userName = rs.getString("username");
                String imageURL = userName+"/"+rs.getString("url");
                Integer imageUserId = rs.getInt("user_id");
                Integer imageId = rs.getInt("id");
                Image image = new Image(imageName, imageURL, imageUserId);
                image.setUser_name(userName);
                image.setId(imageId);
                images.add(image);
            }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Ocorreu um erro ao pesquisar images.\nCódigo do erro: "+e.getErrorCode());
        }
    }
    public static Image selectOne(Integer id) {
        try {
            String sql = "CALL `selectImage(?)`";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Image image = null;
            if(rs.next()) {
                String imageName = rs.getString("name");
                String imageURL = rs.getString("url");
                Integer imageUserId = rs.getInt("user_id");
                Integer imageId = rs.getInt("id");
                String userName = rs.getString("username");
                image = new Image(imageName, imageURL, imageUserId);
                image.setUser_name(userName);
                image.setId(imageId);
            }
            return image;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Ocorreu um erro ao pesquisar image.\nCódigo do erro: "+e.getErrorCode());
        }
    }
    public static Integer insertImage(Image image) {
        try {
            String sql = "CALL `insertImage`(?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, image.getName());
            stmt.setString(2, image.getUrl());
            stmt.setInt(3, image.getUser_id());
            stmt.execute();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }
    public static Integer updateImage(Integer id, String name) {
        try {
            String sql = "CALL `updateImage`(?. ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.execute();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }
    public static Integer deleteImage(Integer id) {
        try {
            String sql = "CALL `deleteImage`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }
    public static List<Image> selectUserImages(String username) {
        try {
            String sql = "CALL `selectUserImages`(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            List<Image> images = new ArrayList<>();
            while(rs.next()) {
                String imageName = rs.getString("name");
                String userName = rs.getString("username");
                String imageURL = userName+"/"+rs.getString("url");
                Integer imageUserId = rs.getInt("user_id");
                Integer imageId = rs.getInt("id");
                Image image = new Image(imageName, imageURL, imageUserId);
                image.setUser_name(userName);
                image.setId(imageId);
                images.add(image);
            }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Ocorreu um erro ao pesquisar imagens\nCódigo do erro: " + e.getMessage());
        }
    }
}
