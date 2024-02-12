package Dao;

import Core.Db;
import Core.Helper;
import Entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;
    public UserDao() {

        this.con = Db.getInstance();
    }
    public ArrayList<User> findAll(){ //veritabanından tüm user kayıtları çekip arraylistte depoladık
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                userList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;

    }
    public User findByLogin(String username, String password){ // belirli kullanıcı adı ve şifreye sahip kullanıcıyı temsil eden bir User nesnesi döndürür
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_pass = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1,username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }
    public User match(ResultSet rs) throws SQLException { //database verisinden entity verisine dönüştürme
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword((rs.getString("user_pass")));
        obj.setRole(rs.getString("user_role"));
        return obj;
    }
}

