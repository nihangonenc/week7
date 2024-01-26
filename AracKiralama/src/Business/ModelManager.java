package Business;

import Core.Helper;
import Dao.ModelDao;
import Entity.Model;

import java.util.ArrayList;

public class ModelManager {
    private final ModelDao modelDao = new ModelDao();
    public Model getById(int id) {
        return this.modelDao.getById(id);
    }
    public ArrayList<Model> findAll() {
        return this.modelDao.findAll();
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList) {
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        for (Model obj : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getBrand().getName();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getYear();
            rowObject[i++] = obj.getFuel();
            rowObject[i++] = obj.getGear();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }
    public boolean save(Model model) {

        if (this.getById(model.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.modelDao.save(model);
    }
    public boolean update(Model model) {

        if (this.getById(model.getId()) == null) {
            Helper.showMsg(model.getId() + " ID kayıtlı model bulunamadı.");
            return false;
        }
        return this.modelDao.update(model);
    }

    // Delete a model by its ID
    public boolean delete(int id) {
        // Check if the model with the given ID exists before deleting
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı model bulunamadı");
            return false;
        }
        return this.modelDao.delete(id);
    }
    public ArrayList<Model> getByListBrandId(int brandId) {
        return this.modelDao.getByListBrandId(brandId);
    }

    /*// Get models by Brand ID


    // Search for models based on criteria for table display
    public ArrayList<Model> searchForTable(int brandId, Model.Fuel fuel, Model.Gear gear, Model.Type type) {
        // Base query to select models
        String select = "SELECT * FROM public.model";

        // List to store conditions for WHERE clause
        ArrayList<String> whereList = new ArrayList<>();

        // Add condition for Brand ID
        if (brandId != 0) {
            whereList.add("model_brand_id = " + brandId);
        }

        // Add conditions for Fuel, Gear, and Type
        if (fuel != null) {
            whereList.add("model_fuel = '" + fuel.toString() + "'");
        }

        if (gear != null) {
            whereList.add("model_gear = '" + gear.toString() + "'");
        }

        if (type != null) {
            whereList.add("model_type = '" + type.toString() + "'");
        }

        // Concatenate WHERE conditions
        String whereStr = String.join(" AND ", whereList);
        String query = select;

        // Add WHERE conditions to the base query
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        // Execute query to get a list of searched models
        return this.modelDao.selectByQuery(query);
    }
    */

}