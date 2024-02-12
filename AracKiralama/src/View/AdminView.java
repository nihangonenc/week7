package View;

import Business.BookManager;
import Business.BrandManager;
import Business.CarManager;
import Business.ModelManager;
import Core.ComboItem;
import Core.Helper;
import Entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private JComboBox<ComboItem> cmb_s_model_brand;
    private JComboBox<Model.Type> cmb_s_model_type;
    private JComboBox<Model.Fuel> cmb_s_model_fuel;
    private JComboBox<Model.Gear> cmb_s_model_gear;
    private JButton btn_search_model;
    private JButton btn_cncl_model;
    private JPanel pnl_car;
    private JScrollPane scrl_car;
    private JTable tbl_car;
    private JPanel pnl_booking;
    private JScrollPane scrl_booking;
    private JTable tbl_booking;
    private JFormattedTextField fld_strt_date;
    private JFormattedTextField fld_fnsh_date;
    private JComboBox<Model.Gear> cmb_booking_gear;
    private JComboBox<Model.Fuel> cmb_booking_fuel;
    private JComboBox<Model.Type> cmb_booking_type;
    private JButton btn_booking_search;
    private JButton btn_cncl_booking;
    private JPanel pnl_book;
    private JScrollPane scrl_book;
    private JTable tbl_book;
    private JComboBox<ComboItem> cmb_book_car;
    private JButton btn_book_clean;
    private JButton btn_book_srch;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private DefaultTableModel tmdl_booking = new DefaultTableModel();
    private DefaultTableModel tmdl_book = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;
    private Object[] col_model;
    private JPopupMenu car_menu;
    private JPopupMenu booking_menu;
    private Object[] col_car;
    private BookManager bookManager;
    private JPopupMenu book_menu;
    private Object[] col_book;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.bookManager = new BookManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;

        if (this.user == null) {
            dispose(); //pencereyi kapatır
        }
        this.lbl_welcome.setText("Hoş Geldiniz : " + this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();

        loadModelTable(null);

        loadModelComponent();
        loadModelFilter();

        loadCarTable();
        loadCarComponent();

        loadBookingTable(null);
        loadBookingComponent();
        loadBookingFilter();

        loadBookTable(null);
        loadBookComponent();
        loadBookFilterCar();
    }
    private void loadComponent(){
        this.btn_logout.addActionListener(e -> {
            dispose();
            LoginView loginView = new LoginView();
        });
    }
    public void loadBookingFilter(){
        this.cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_booking_type.setSelectedItem(null);
        this.cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_booking_gear.setSelectedItem(null);
        this.cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_booking_fuel.setSelectedItem(null);

    }
    public void loadBookingTable(ArrayList<Object[]> carList) {
        Object[] col_booking_list= {"ID", "Marka" , "Model" , "Plaka","Renk","KM","Yıl","Tip","Yakıt Türü","Vites"};
        this.createTable(this.tmdl_book, this.tbl_book, col_booking_list, carList);

    }
    public void loadBookingComponent(){
        tableRowSelect(this.tbl_book);
        this.book_menu = new JPopupMenu();
        this.book_menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectedCarID = this.getTableSelectedRow(this.tbl_book,0);
            BookView bookingView = new BookView(
                    this.carManager.getById(selectedCarID),
                    this.fld_strt_date.getText(),
                    this.fld_fnsh_date.getText()
            );
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                    loadBookTable(null);
                }
            });
        });
        this.tbl_book.setComponentPopupMenu(book_menu);

        btn_booking_search.addActionListener(e->{
            ArrayList<Car> carList = this.carManager.searchForBooking(
                    fld_strt_date.getText(),
                    fld_fnsh_date.getText(),
                    (Model.Type)cmb_booking_type.getSelectedItem(),
                    (Model.Gear)cmb_booking_gear.getSelectedItem(),
                    (Model.Fuel)cmb_booking_fuel.getSelectedItem()
            );

            ArrayList<Object[] > carBookingRow = this.carManager.getForTable(this.col_car.length,carList);
            loadBookingTable(carBookingRow);
        });
        btn_cncl_booking.addActionListener(e -> {
            loadBookingFilter();
        });
    }


    private void loadBookComponent() {
        //tableSelectedRow(this.tbl_book);
        this.book_menu = new JPopupMenu();
        this.book_menu.add("İptal Et").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBookId = this.getTableSelectedRow(this.tbl_book, 0);
                if (this.bookManager.delete(selectBookId)) {
                    Helper.showMsg("done");
                    loadBookTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_book.setComponentPopupMenu(book_menu);

        btn_book_srch.addActionListener(e -> {
            ComboItem selectedCar = (ComboItem) this.cmb_book_car.getSelectedItem();
            int carId = 0;
            if (selectedCar != null) {
                carId = selectedCar.getKey();
            }

            ArrayList<Book> bookListBySearch = this.bookManager.searchForTable(carId);
            ArrayList<Object[]>bookRowListBySearch = this.bookManager.getForTable(this.col_book.length,bookListBySearch);
            loadBookTable(bookRowListBySearch);
        });

        btn_book_clean.addActionListener(e -> {
            loadBookFilterCar();
        });
    }
    public void loadBookTable(ArrayList<Object[]> bookList) { //tabloyu doldurma
        col_book = new Object[]{"ID", "Plaka", "Araç Marka", "Araç Model", "Müşteri", "Telefon", "Mail", "T.C.", "Başlangıç Tarihi", "Bitiş Tarihi", "Fiyat"};
        if (bookList == null) {
            bookList = this.bookManager.getForTable(col_book.length, this.bookManager.findAll());
        }
        createTable(this.tmdl_book, this.tbl_book, col_book, bookList);
    }

    public void loadBookFilterCar() { // araba seçimi için kullanılacak JComboBox'i doldurma
        this.cmb_book_car.removeAllItems(); //önceki seçenekleri temizler
        for (Car obj : this.carManager.findAll()) {
            this.cmb_book_car.addItem(new ComboItem(obj.getId(), obj.getPlate()));
        }
        this.cmb_book_car.setSelectedItem(null);
    }
    public void loadCarTable(){

        col_car =  new Object[] {"ID", "Marka", "Model","Plaka","Renk","KM","Yıl", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length,this.carManager.findAll());
        this.createTable(this.tmdl_car, this.tbl_car, col_car, carList);

    }
    public void loadCarComponent(){
        tableRowSelect(this.tbl_car);
        this.car_menu = new JPopupMenu();
        this.car_menu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.car_menu.add("Güncelle").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(tbl_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectCarId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });

        });
        this.car_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectCarId = this.getTableSelectedRow(tbl_car, 0);
                if (this.carManager.delete(selectCarId)) {
                    Helper.showMsg("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();


                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_car.setComponentPopupMenu(car_menu);
    }
    private void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_menu = new JPopupMenu();
        this.model_menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model()); // yeni değer eklendiğinde tabloyu güncelleyecek
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model,0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId)); // seçili id'i databaseden alıyor
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectModelId = this.getTableSelectedRow(tbl_model,0);
                if (this.modelManager.delete(selectModelId)){
                    Helper.showMsg("done");
                    loadModelTable(null); //sildikten sonra tabloyu güncelle
                }else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(model_menu); //Sağ tıkta seçenekler çıkıyor

        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
                brandId = selectedBrand.getKey();
            }

            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem()
            );
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.btn_cncl_model.addActionListener(e -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);

        });
    }
    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        if (modelList == null) {
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }
        createTable(tmdl_model, this.tbl_model, col_model, modelList);
    }

    public void loadModelFilter() { //comboboxlara seçilecek seçenekler sıfırlanıyor TEMİZLEMEDE BURASI

        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();
    }
    public void loadModelFilterBrand(){
        this.cmb_s_model_brand.removeAllItems();
        for( Brand obj: brandManager.findAll()){
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }
    public void loadBrandComponent() {
        tableRowSelect(this.tbl_brand);
        this.brand_menu = new JPopupMenu(); //marka satırına sağ tıklayınca 3 seçenek çıkacak
        this.brand_menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();//brand update işlemlerinde model tablosunda da değişiklik olsun istiyoruz
                }
            });
        });
        this.brand_menu.add("Güncelle").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(tbl_brand,0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId)); // seçili id'i databaseden alıyor
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                    loadBookTable(null);
                }
            });

        });

        this.brand_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectBrandId = this.getTableSelectedRow(tbl_brand,0);
                if (this.brandManager.delete(selectBrandId)){
                    Helper.showMsg("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();//sildikten sonra tabloyu güncelle
                    loadCarTable();
                }else {
                    Helper.showMsg("error");
                }

            }
        });
        this.tbl_brand.setComponentPopupMenu(brand_menu);
    }
    public void loadBrandTable () {
        //modelin columnlarını belirledik
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }
    private void createUIComponents() throws ParseException {
        this.fld_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_strt_date.setText("16/12/2021");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("16/12/2022");
    }

}
