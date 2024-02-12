package View;

import Core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Layout extends JFrame {
    public void guiInitilaze (int width, int height){

        this.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); //JFrame'in kapanınca pprogram sona ermez
        this.setTitle("Rent a Car");
        this.setSize(width, height);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y",this.getSize()));
        this.setVisible(true);
    }

    public void createTable(DefaultTableModel model, JTable table, Object[] columns, ArrayList<Object[]> rows){
        //kullanıcıya göstermek için
        model.setColumnIdentifiers(columns);
        table.setModel(model); //swingte tablo böyle çalışıyor.
        table.getTableHeader().setReorderingAllowed(false);//sütunların yeri manuel değiştirilmesin istiyoruz
        table.getColumnModel().getColumn(0).setMaxWidth(75); // column genişlik ölçüsü
        table.setEnabled(false); //çift tıklayıp manipülasyonu engellemek için

        DefaultTableModel clearModel = (DefaultTableModel) table.getModel(); // başlangıçta boş olsun
        clearModel.setRowCount(0);

        if (rows == null){
            rows = new ArrayList<>(); //exception almamak için
        }

        for (Object[] row : rows){
            model.addRow(row);
        }
    }

    public int getTableSelectedRow(JTable table, int index){ //tablodan seçilen veriyi almak ve daha sonra bu veriyi kullanmak için
       return Integer.parseInt(table.getValueAt(table.getSelectedRow(),index).toString());
    }

    public void tableRowSelect(JTable table) { //mouse press ile bir satırın seçilmesini sağlama
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }
}



