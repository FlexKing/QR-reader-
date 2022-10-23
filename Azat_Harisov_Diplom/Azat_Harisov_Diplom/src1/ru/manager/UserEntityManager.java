package ru.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ru.ApplicationMain;
import ru.entity.UserEntity;

public class UserEntityManager {
    public UserEntityManager() {
    }

    public static void addRow(UserEntity userEntity) throws SQLException {
        Connection connection = ApplicationMain.getConnection();

        try {
            String query = "INSERT INTO user (email, name, password) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query, 1);
            statement.setString(1, userEntity.getEmail());
            statement.setString(2, userEntity.getName());
            statement.setString(3, userEntity.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                userEntity.setId(resultSet.getInt(1));
            }
        } catch (Throwable var6) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }
            }

            throw var6;
        }

        if (connection != null) {
            connection.close();
        }

    }

    public static UserEntity getRowByEmail(String email) throws SQLException {
        Connection connection = ApplicationMain.getConnection();

        UserEntity var5;
        label43: {
            try {
                String query = "SELECT * FROM user WHERE email = ?;";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    var5 = new UserEntity(resultSet.getInt("id_user"), resultSet.getString("name"), resultSet.getString("password"));
                    break label43;
                }

                var5 = null;
            } catch (Throwable var7) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (connection != null) {
                connection.close();
            }

            return var5;
        }

        if (connection != null) {
            connection.close();
        }

        return var5;
    }
}