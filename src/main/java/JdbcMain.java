import java.sql.*;

public class JdbcMain {
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO: Load the SQLite JDBC driver (JDBC class implements java.sql.Driver)
        Class.forName("org.sqlite.JDBC");
        // TODO: Create a DB connection
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:contactmgr.db")) {

            // TODO: Create a SQL statement
            Statement statement = connection.createStatement();

            // TODO: Create a DB table
            statement.executeUpdate("DROP TABLE IF EXISTS contacts");
            statement.executeUpdate("CREATE TABLE contacts " +
                    "(id INTEGER PRIMARY KEY, firstname STRING, lastname STRING, email STRING, phone INT(10))");

            // TODO: Insert a couple contacts
            Contact c = new Contact("Kaleigh", "Keller", "barkbark@gmail.com", 5555557788L);
            save(c, statement);
            Contact c2 = new Contact("JT", "Keller", "jtkeller2@gmail.com", 1235557788L);
            save(c2, statement);

            // TODO: Fetch all the records from the contacts table
            ResultSet rs = statement.executeQuery("SELECT  * FROM contacts");

            // TODO: Iterate over the ResultSet & display contact info
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                System.out.printf("%s %s (%d)\n", firstname, lastname, id);
            }
        } catch (SQLException ex) {
            // Display connection or query errors
            System.err.printf("There was a database error: %s%n", ex.getMessage());
        }
    }

    public static void save(Contact contact, Statement statement) throws SQLException {
        // Compose the query
        String sql = "INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('%s', '%s', '%s', '%d')";
        sql = String.format(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());

        //Execute the query
        statement.executeUpdate(sql);
    }
}