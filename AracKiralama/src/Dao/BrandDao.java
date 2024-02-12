package Dao;

import Core.Db;
import Entity.Brand;
import Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection con;
    public BrandDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<Brand> findAll(){ //veritabanından tüm marka kayıtları çekip arraylistte depoladık
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand ORDER BY brand_id ASC";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                brandList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return brandList;
    }
    public boolean save(Brand brand){ //yeni marka eklemek için
        String query = "INSERT INTO public.brand (brand_name) VALUES (?) ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, brand.getName());
            return pr.executeUpdate() != -1 ; // executeUpdate() etkilenen satır sayısını döndürür

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean update(Brand brand){ //markayı güncellemek için
        String query = "UPDATE public.brand SET brand_name = ? WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, brand.getName());
            pr.setInt(2,brand.getId());
            return pr.executeUpdate() != -1 ;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete (int id){ //markayı silmek için
        String query = "DELETE FROM public.brand WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1 ;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Brand getById(int id){  //id'ye göre brand nesnesi döner
        Brand obj = null;
        String query = "SELECT * FROM public.brand WHERE brand_id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = this.match(rs);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return obj;
    }
    public Brand match(ResultSet rs) throws SQLException {  //database verisinden entity verisine dönüştürme
        Brand obj = new Brand();
        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));

        return obj;
    }
}