package BLL;

import DAL.UserDAL;
import DTO.User;


public class UserBLL {
    public boolean InsertUser(User user){
        return new UserDAL().InsertUser(user);
    }
    public boolean Login(User user){
        return  new UserDAL().Login(user);
    }

}
