import java.sql.*;

public class CRUD {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_network_mikheil_ghibradze", "root", "Mixomixo123");
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void read() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from social_network_mikheil_ghibradze.user");
                printResultSet(resultSet);
                printMetaData(resultSet);

                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void write() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into social_network_mikheil_ghibradze.user(id,full_name,birth_date,friends_count) values(?,?,?,?)");
                preparedStatement.setInt(1,1); //aq 1 vamateb magram mere vshli
                preparedStatement.setString(2, "Mikheil Ghibradze");
                preparedStatement.setDate(3, new java.sql.Date(2021, 02, 19));
                preparedStatement.setString(4, "100");
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("-----Error while writing-----");
                } else {
                    System.out.println("-----Successfully written----");
                }
                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void update() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update social_network_mikheil_ghibradze.user set friends_count=? where friends_count >= ?");
                preparedStatement.setString(1, "200");
                preparedStatement.setInt(2, 20);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("-----Error while update-----");
                } else {
                    System.out.println("-----Successfully updated----");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void delete() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from social_network_mikheil_ghibradze.user where id=?");
                preparedStatement.setInt(1, 1);
                int rowsEffected = preparedStatement.executeUpdate();

                if (rowsEffected < 1) {
                    System.out.println("-----Error while Deleting-----");
                } else {
                    System.out.println("-----Successfully Deleted----");
                }

                preparedStatement.close();
                closeConnection(connection);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fullName = resultSet.getString("full_name");
            Date birthDate = resultSet.getDate("birth_date");
            String friendsCount = resultSet.getString("friends_count");
            System.out.printf("User ID is: %d \n", id);
            System.out.printf("Full Name and UserName: %s \n", fullName);
            System.out.printf("All friends: %s \n", friendsCount);
            System.out.printf("BirthDate of the user: %s \n", birthDate.toString());
        }
    }

    private static void printMetaData(ResultSet resultSet) throws SQLException {
        System.out.printf("The Table Name is: %s \n", resultSet.getMetaData().getTableName(1));

        for (int i = 1; i<=resultSet.getMetaData().getColumnCount(); i++) {
            System.out.printf("Column: %s \n", resultSet.getMetaData().getColumnName(i));
        }
    }
    private static void closeConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }
}
