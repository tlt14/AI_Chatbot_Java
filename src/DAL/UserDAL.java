package DAL;

import DTO.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {
    private final Connection conn ;
    public UserDAL(){
        conn = new ConnectDB().connect();
    }
    public boolean InsertUser(User user){
        try {
            String sql = "INSERT INTO `user` ( `username`, `password`) VALUES (?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,user.getUsername());
            stmt.setString(2, user.getPassword());
            int row = stmt.executeUpdate();
            if(row==1){
                System.out.println(123);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    public boolean Login(User user){
        try {
            String sql = "SELECT * FROM `user` where `username` = ? and `password` = ? limit 1;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet row = stmt.executeQuery();
            if(row.next()){
                System.out.println("có");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
