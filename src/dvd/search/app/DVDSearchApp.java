package dvd.search.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DVDSearchApp extends JFrame {
    
    JTextField txtTitle;
    JButton butSearch;
    JList list;
    JScrollPane scrollResults;
    ArrayList <String> dvdList;
    
    
    public DVDSearchApp() {
        URI mainClass = null;
        try{
            mainClass = DVDSearchApp.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        }catch (URISyntaxException use){
            use.printStackTrace();
            System.exit(-1);
        }
//        System.out.println(mainClass.getPath());
//        final String mainDir = mainClass.getPath().substring(0, mainClass.getPath().indexOf("build"));    
        setTitle("DVD Search App");
        setLayout(null);
        
        DefaultListModel dlm = new DefaultListModel();
        list = new JList(dlm);
        dvdList = new ArrayList();
        txtTitle = new JTextField();
        txtTitle.setBounds(30, 30, 150, 25);
        butSearch = new JButton("Title Search");
        butSearch.setBounds(200, 30, 120, 25);
        scrollResults = new JScrollPane(list);
        scrollResults.setBounds(30, 85, 290, 150);
        
        try {
            File app = new File(mainClass.getPath());
            FileInputStream fileInputStream = new FileInputStream(new File(app.getParent() + "\\TITLES.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet worksheet = workbook.getSheet("Sheet1");
            
            for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
                HSSFRow row = worksheet.getRow(i);
                HSSFCell title = row.getCell(0);
                dvdList.add(title.getStringCellValue());
            }
            
        } catch (Exception e) {
            txtTitle.setText("Failed to Build DVD List");
            System.out.println(e);
        }
        
        butSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    dlm.removeAllElements();
                    
                    for (String curVal : dvdList) {
                        String tempVal = curVal.toLowerCase();
                        if(tempVal.contains(txtTitle.getText().toLowerCase())) {
                            dlm.addElement(curVal);
                        }
                    }
                } catch (Exception e) {
                    txtTitle.setText("Something Went Wrong!");
                    System.out.println(e);
                }
            }
        });
        
        add(txtTitle);
        add(butSearch);
        add(scrollResults);
                
        setSize(360, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DVDSearchApp();
    }
}
