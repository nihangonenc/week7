package Dao;

import Core.Db;
import Entity.Brand;
import Entity.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDao {
    private Connection con;
    private final BrandDao brandDao = new BrandDao();
    public ModelDao() {
        this.con = Db.getInstance();
    }
    public Model getById(int id) { //id'ye göre model objesi döndürür
        Model obj = null;
        String query = "SELECT * FROM public.model WHERE (model_id) = ?";
        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public ArrayList<Model> findAll(){  //veritabanından tüm model kayıtları çekip arraylistte depoladık
        return this.selectByQuery("SELECT * FROM public.model ORDER BY model_id ASC");
    }
    public ArrayList<Model> getByListBrandId(int brandId){ // marka id'ye göre model listesi döndürür
        return this.selectByQuery("SELECT * FROM public.model WHERE model_brand_id = " + brandId);
    }
    public ArrayList<Model> selectByQuery(String query) { //sorguya uygun model listesi döndürür
        ArrayList<Model> modelList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                modelList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelList;
    }
    public boolean save(Model model) { //model kaydı
        String query = "INSERT INTO public.model " +
                "(" +
                "model_brand_id," +
                "model_name," +
                "model_type," +
                "model_year," +
                "model_fuel," +
                "model_gear" +
                ")" +
                "VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, model.getBrand_id());
            ps.setString(2, model.getName());
            ps.setString(3, model.getType().toString());
            ps.setString(4, model.getYear());
            ps.setString(5, model.getFuel().toString());
            ps.setString(6, model.getGear().toString());
            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean update(Model model) { //model güncelleme
        String query = "UPDATE public.model SET " +
                "model_brand_id = ? , " +
                "model_name = ? , " +
                "model_type = ? , " +
                "model_year = ? , " +
                "model_fuel = ? , " +
                "model_gear = ? " +
                "WHERE model_id = ?";
        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, model.getBrand_id());
            ps.setString(2, model.getName());
            ps.setString(3, model.getType().toString());
            ps.setString(4, model.getYear());
            ps.setString(5, model.getFuel().toString());
            ps.setString(6, model.getGear().toString());
            ps.setInt(7, model.getId());
            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete(int id) { //model silme
        String query = "DELETE FROM public.model WHERE model_id = ?";
        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public Model match(ResultSet rs) throws SQLException { //veritabanı verilerini uygulamanın içinde kullanılabilir bir nesne tipine dönüştürmek için
        Model model = new Model();
        model.setId(rs.getInt("model_id"));
        model.setName(rs.getString("model_name"));
        model.setFuel(Model.Fuel.valueOf(rs.getString("model_fuel")));
        model.setGear(Model.Gear.valueOf(rs.getString("model_gear")));
        model.setType(Model.Type.valueOf(rs.getString("model_type")));
        model.setYear(rs.getString("model_year"));
        model.setBrand(this.brandDao.getById(rs.getInt("model_brand_id")));
        model.setBrand_id(rs.getInt("model_brand_id"));
        return model;
    }
}
