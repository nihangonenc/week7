package Business;

import Core.Helper;
import Dao.BrandDao;
import Dao.UserDao;
import Entity.Brand;
import Entity.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class BrandManager {
    private final BrandDao brandDao;
    private final ModelManager modelManager;

    public BrandManager() {
        this.brandDao = new BrandDao();
        this.modelManager = new ModelManager();
    }
    public ArrayList <Object[]> getForTable(int size) { // Defaulttable a setlemek için Arraye dönüşüm. Sütun sayısı kadar içerde obje oluşturduk
        ArrayList <Object[]> brandRowList = new ArrayList<>();
        for(Brand brand :this.findAll()){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRowList.add(rowObject);
        }
        return brandRowList;
    }
    public ArrayList<Brand> findAll(){
        return this.brandDao.findAll();
    }
    public boolean save(Brand brand){
        if(brand.getId() != 0){ //
            Helper.showMsg("error");
        }
        return this.brandDao.save(brand);
    }
    public Brand getById(int id){
        return this.brandDao.getById(id);
    } //daodaki metodu uygula
    public boolean update(Brand brand){
        if (this.getById(brand.getId()) == null){ //eşleşen id yoksa bulunamadı mesajı
            Helper.showMsg("notFound");

        }
        return this.brandDao.update(brand); //uygun bir id ise daodaki metodu uygula
    }
    public boolean delete(int id){
        if (this.getById(id) == null){ //veritabanında böyle bir row yoksa
         Helper.showMsg(id + " ID kayıtlı marka bulunamadı");
         return false;
        }
        for (Model model : this.modelManager.getByListBrandId(id)) { //markalar kısmında marka silince o markaya ait modeller de siliniyor
            this.modelManager.delete(model.getId());
        }
        return this.brandDao.delete(id); // veri varsa daoda yaptığımız metodu uygular
    }

}
