package app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null) {
            String host = "localhost";
            String port = "3306";
            String database = "instagram";
            String user = "root";
            String password = "mysql";

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conectado!");
            } catch (SQLException e) {
                System.out.println("Erro ao conectar!");
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão!");
                e.printStackTrace();
            }
        }
    }

}
