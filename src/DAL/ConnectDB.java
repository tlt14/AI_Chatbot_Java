package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private final String username = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost/ai_chatbot";
    public Connection connect(){
        Connection conn;
        try {
            conn = DriverManager.getConnection(url,username,password);
            System.out.println(conn);
            if(conn!= null){
                System.out.println("connected"  );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(conn);
        return conn;
    }

}
