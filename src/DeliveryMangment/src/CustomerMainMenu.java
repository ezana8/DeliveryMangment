package DeliveryMangment.src;

import java.util.ArrayList;
import DeliveryMangment.src.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerMainMenu {
    static Customer c;

    static void refresh(JTabbedPane tabs){
        tabs.remove(2);
        tabs.remove(1);
        tabs.remove(0);
        currentorder(tabs);
        order(tabs);
        history(tabs);
       
    }
    public static void main(JPanel CustomerMenu ){
        JTabbedPane tabs=new JTabbedPane(JTabbedPane.TOP);
      
        c=new Customer();
        c.id=SignInPage.id;
        c.name=SignInPage.name;
        currentorder(tabs);
        order(tabs);
        history(tabs);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton signOutButton = new JButton("Sign out");
        signOutButton.setBackground(new Color(37, 37, 37));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignInPage.id = 0;
                SignInPage.name = null;
                CardLayout cardLayout = (CardLayout) Main.mainPanel.getLayout();
                cardLayout.show(Main.mainPanel, "SignIn");
            }
        });
        topPanel.add(signOutButton);

        CustomerMenu.setLayout(new BorderLayout());
        CustomerMenu.add(topPanel, BorderLayout.NORTH);
        CustomerMenu.add(tabs, BorderLayout.CENTER);
       
    }
    public static void order(JTabbedPane tabs){
        JPanel trackMainp = new JPanel();
        trackMainp.setLayout(new FlowLayout(FlowLayout.CENTER));

        String st ="select PackageId,PackageInitialLocation,PackageFinalDestination from Package where CustomerId="+c.id+" and PackageShipped=0";
        if(Db.sqlReturn(st)==-1){
            JPanel noOrderPanel = new JPanel();
            noOrderPanel.setLayout(new BoxLayout(noOrderPanel, BoxLayout.Y_AXIS));
            
            JLabel noOrder = new JLabel("No pending orders");
            noOrder.setFont(new Font("Arial", 0, 33));
            noOrderPanel.add(noOrder);
            noOrderPanel.setOpaque(false);;
            trackMainp.add(noOrderPanel);
        }else {
           
            JPanel tablePanel =new JPanel();
            tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel tablePanelsub =new JPanel();
            tablePanelsub.setLayout(new BoxLayout(tablePanelsub, BoxLayout.Y_AXIS));
            
            
            JPanel l =new JPanel();
            l.setOpaque(false);
            JLabel L = new JLabel("Pending orders");
            L.setFont(new Font("Arial", 0, 33));
            l.add(L);

            Object[][] rows = tableFormatter(Db.sqlPendingPackagesRead(st));
            Object[] columns = {"PackageId","PackageInitialLocation","PackageFinalDestination"};

            
                           
            JTable t= new JTable(rows,columns);
            t.setEnabled(false);
            JScrollPane ts= new JScrollPane(t);
            ContractorMainMenu.width(t);
            ts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        
            
            tablePanelsub.add(l);
            tablePanelsub.add(ts);
            tablePanel.add(tablePanelsub);
            trackMainp.add(tablePanel);
            tablePanelsub.setBackground(Color.WHITE);
        }

        trackMainp.setBackground(Color.LIGHT_GRAY);
        tabs.addTab("Track orders", trackMainp);
    }


    public static void history(JTabbedPane tabs){
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.LINE_AXIS));
        String st="select* from VsentPackages where CustomerId="+c.id; 

        if(Db.sqlReturn(st)==-1){
            JPanel noOrderPanel = new JPanel();
            noOrderPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
            
            JLabel noOrder = new JLabel("No history");
            noOrder.setFont(new Font("Arial", 0, 33));
            noOrderPanel.add(noOrder);
        
            historyPanel.add(noOrderPanel);
        }else {
           
            JPanel tablePanel =new JPanel();
            tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel tablePanelsub =new JPanel();
            tablePanelsub.setLayout(new BoxLayout(tablePanelsub, BoxLayout.Y_AXIS));
            
            
            JPanel l =new JPanel();
            l.setOpaque(false);
            JLabel L = new JLabel("Orders History");
            L.setFont(new Font("Arial", 0, 33));
            l.add(L);

            Object[][] rows = tableFormatter(Db.sqlPackagesHistoryRead(st));
           
            Object[] columns = {"PackageId","CustomerId","First Name","Last Name","Start","End"};
      
            
                           
            JTable t= new JTable(rows,columns);
            t.setEnabled(false);
            //ContractorMainMenu.width(t);
            JScrollPane ts= new JScrollPane(t);
            ts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            t.setMinimumSize(new Dimension(600, 400));
            
            

            
        
          
            tablePanelsub.add(l);
            tablePanelsub.add(ts);
            tablePanel.add(tablePanelsub);
            historyPanel.add(tablePanel);

            tablePanelsub.setBackground(Color.WHITE);
        }

        historyPanel.setBackground(Color.YELLOW);
        tabs.addTab("History", historyPanel);
    }


    public static void currentorder(JTabbedPane tabs){
        JPanel pmain = new JPanel();
        pmain.setLayout(new BorderLayout());

        JLabel welcome = new JLabel("Welcome "+c.name+",");
        welcome.setFont(new Font("Arial", 0, 33));


        JPanel packageInfo = new JPanel();
        packageInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));

        JPanel packageInfoFields = new JPanel();
        packageInfoFields.setLayout(new BoxLayout(packageInfoFields, BoxLayout.Y_AXIS));
        packageInfoFields.setOpaque(false);

        JPanel Title = new JPanel();
        Title.setOpaque(false);
        JLabel title =new JLabel("Package order");
        title.setFont(new Font("Arial", 0, 33));
        Title.add(title);



        JPanel packageDescriptionPanel = new JPanel();
        packageDescriptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JTextArea packageDescriptionTArea = new JTextArea(5, 50);
        packageDescriptionTArea.setLineWrap(true);
       

        packageDescriptionPanel.add(new JLabel("Package Description: "));
        packageDescriptionPanel.add(packageDescriptionTArea);


        JPanel packageWeightPanel = new JPanel();
        packageWeightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JTextField packageWeightField = new JTextField(10);

        packageWeightPanel.add(new JLabel("Package Weight in kgs(maximum of 2000): "));
        packageWeightPanel.add(packageWeightField);


        JPanel packageVolumePanel = new JPanel();
        packageVolumePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 14, 10));
        
        JTextField packageVolumeField = new JTextField(10);


        packageVolumePanel.add(new JLabel("Package Volume in m^3(maximum of 16): "));
        packageVolumePanel.add(packageVolumeField);


        JPanel packageInitialLocationPanel = new JPanel();
        packageInitialLocationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 14, 10));
        
        JTextField packageInitialLocationField = new JTextField(30);


        packageInitialLocationPanel.add(new JLabel("Package Initial Location: "));
        packageInitialLocationPanel.add(packageInitialLocationField);


        JPanel packageDestinationPanel = new JPanel();
        packageDestinationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 10));
        
        JTextField packageDestinationField = new JTextField(30);


        packageDestinationPanel.add(new JLabel("Package Destination: "));
        packageDestinationPanel.add(packageDestinationField);


        JPanel packagePricePanel = new JPanel();
        packagePricePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 10));
        
        JTextField packagePriceField = new JTextField(7);


        packagePricePanel.add(new JLabel("Package Price in ETB: "));
        packagePricePanel.add(packagePriceField);


        JPanel packageOrderPanel = new JPanel();
        packageOrderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 23, 10));
        
        JButton packageOrderButton = new JButton("Order Delivery");
        packageOrderButton.setFocusPainted(false);
        packageOrderButton.setBackground(Color.ORANGE);
        packageOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!packageDescriptionTArea.getText().isEmpty() && !packageWeightField.getText().isEmpty() && !packageInitialLocationField.getText().isEmpty() && !packageDestinationField.getText().isEmpty() && !packagePriceField.getText().isEmpty()){ 
                    String st = "insert into Package values ('"+packageInitialLocationField.getText()+"','"+packageDestinationField.getText()+"',"+packageWeightField.getText()+","+packageVolumeField.getText()+","+packagePriceField.getText()+",'"+packageDescriptionTArea.getText()+"',0,"+c.id+")";
                    try {
                        Db.executeSql(st);
                    } catch (Exception s) {
                      System.out.println("contractor 1");
                    }
                    refresh(tabs);
                }
            }
        });
        packageOrderPanel.add(packageOrderButton);

       

        packageInfoFields.add(Title);
        packageInfoFields.add(packageDescriptionPanel);
        packageInfoFields.add(packageWeightPanel);
        packageInfoFields.add(packageVolumePanel);
        packageInfoFields.add(packageInitialLocationPanel);
        packageInfoFields.add(packageDestinationPanel);
        packageInfoFields.add(packagePricePanel);
        packageInfoFields.add(packageOrderPanel);
        packageInfo.add(packageInfoFields);
        pmain.add(welcome,BorderLayout.NORTH);
        pmain.add(packageInfo,BorderLayout.CENTER);

        Title.setBackground(Color.ORANGE);
        packageInfo.setBackground(Color.ORANGE);
        tabs.addTab("Order delivery", pmain);
    }

    public static Object[][] tableFormatter(ArrayList<ArrayList<String>> arr){
        int rows = arr.size();
        int columns = arr.isEmpty() ? 0 : arr.get(0).size();
        Object[][] o = new Object[rows][columns];
        for(int i = 0; i <rows;i++){
            for(int j = 0;j<columns;j++){
                o[i][j]= arr.get(i).get(j);
            }
        }
        return o;
    }
  
}
