package DeliveryMangment.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class ContractorMainMenu {

    static Contractor c;
    static void refresh(JTabbedPane tabs){
        tabs.remove(2);
        tabs.remove(1);
        tabs.remove(0);
        currentorder(tabs);
        order(tabs);
        history(tabs);
       
    }
    public static void main(JPanel ContractorMenu ){
        JTabbedPane tabs=new JTabbedPane(JTabbedPane.TOP);
        
        c= new Contractor();
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

        ContractorMenu.setLayout(new BorderLayout());
        ContractorMenu.add(topPanel, BorderLayout.NORTH);
        ContractorMenu.add(tabs, BorderLayout.CENTER);
       
    }
    public static void order(JTabbedPane tabs){
        JPanel b = new JPanel();
        b.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel packagesPanel =new JPanel();
        
        String s = "select Package.PackageId, Package.PackageInitialLocation, Package.PackageFinalDestination, Customer.CustomerPhonenum from Package inner join Customer on Package.CustomerId=Customer.CustomerId and Packageshipped=0";

        if(Db.sqlReturn(s)==1){
            packagesPanel.setLayout(new BoxLayout(packagesPanel, BoxLayout.Y_AXIS));
            
            Object[][] rows = tableFormatter(Db.sqlPackagesRead(s));
            Object[] columns = {"Package Id","Initial Location","Final Destination","Phone number"};
            
            
            JTable packageTable = new JTable(rows,columns);
            packageTable.setEnabled(false);
            width(packageTable);
            JScrollPane PackageTable = new JScrollPane(packageTable);
            PackageTable.setSize(400, 0);
            PackageTable.setOpaque(false);
            

            JPanel packageLp =new JPanel();
            JLabel packagesLabel = new JLabel("Available Deliveries");
            packagesLabel.setFont(new Font("Arial", 0, 33));
            packageLp.add(packagesLabel);
            packageLp.setOpaque(false);



            JPanel selectedPackage = new JPanel();
            selectedPackage.setOpaque(false);
            JTextField selectedPackagField =new JTextField(15);
            JButton sp = new JButton("Select");
            sp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a){
                    String s = "select * from Package where PackageId="+selectedPackagField.getText()+" and Packageshipped=0";
                    if(Db.sqlReturn(s)==1){
                       Db.createTransaction(selectedPackagField.getText());
                       refresh(tabs);
                    }
                }
            });
            selectedPackage.add(new JLabel("Insert Package id: "));
            selectedPackage.add(selectedPackagField);



            
            packagesPanel.add(packageLp);
            packagesPanel.add(PackageTable);
            packagesPanel.add(selectedPackage);
            packagesPanel.add(sp);

        }else{
            JPanel NoPackages = new JPanel();
            NoPackages.setOpaque(false);
            NoPackages.setLayout(new BoxLayout(NoPackages, BoxLayout.Y_AXIS));
            JLabel noPackages = new JLabel("No Packages, try Later.");
            noPackages.setFont(new Font("Arial", 0, 33));

            packagesPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));

            NoPackages.add(noPackages);
            packagesPanel.add(NoPackages);
        }

        b.add(packagesPanel);
        packagesPanel.setBackground(Color.ORANGE);
        b.setBackground(Color.ORANGE);
        tabs.addTab("Find contract", b);
    }
    public static void history(JTabbedPane tabs){
        JPanel h = new JPanel();
    

        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));

        String st = "select * from Transactions where IndividualContractorId="+c.id+"and TransactionStatus in (-1,1)";

        if(Db.sqlReturn(st)==1){
            historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
            
            String s = "select Package.PackageId,Package.PackageInitialLocation,Package.PackageFinalDestination,Transactions.TransactionStatus from Package inner join Transactions on Transactions.PackageId=Package.PackageId and Transactions.IndividualContractorId="+c.id;
            Object[][] rows = tableFormatter(Db.sqlPackagesHistory(s));
            Object[] columns = {"Package Id","Initial Location","Final Destination","Transaction Status"};
            
            
            JTable packageTable = new JTable(rows,columns);
            packageTable.setEnabled(false);
            width(packageTable);
            JScrollPane PackageTable = new JScrollPane(packageTable);
            PackageTable.setSize(400, 0);
            PackageTable.setOpaque(false);
            

            JPanel packageLp =new JPanel();
            JLabel packagesLabel = new JLabel("Recent Deliveries");
            packagesLabel.setFont(new Font("Arial", 0, 33));
            packageLp.add(packagesLabel);
            packageLp.setOpaque(false);


            JScrollPane hPane = new JScrollPane(PackageTable);
            hPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            historyPanel.add(packageLp);
            historyPanel.add(hPane);

  

        }else{
            JPanel NoPackages = new JPanel();
            NoPackages.setOpaque(false);
            NoPackages.setLayout(new BoxLayout(NoPackages, BoxLayout.Y_AXIS));
            JLabel noPackages = new JLabel("No History");
            noPackages.setFont(new Font("Arial", 0, 33));

            historyPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));

            NoPackages.add(noPackages);
            historyPanel.add(NoPackages);
        }
        h.add(historyPanel);
        h.setBackground(Color.ORANGE);
        tabs.addTab("History", h);
    }


    public static void currentorder(JTabbedPane tabs){
        JPanel d = new JPanel();
        d.setLayout(new BorderLayout());
        d.setBackground(Color.LIGHT_GRAY);

        JLabel welcome = new JLabel("Welcome "+c.name+",");
        welcome.setBackground(Color.WHITE);
        welcome.setFont(new Font("Arial", 0, 33));

        JPanel detailsPanel = new JPanel();


        String s = "select * from Transactions where IndividualContractorId="+c.id+" and TransactionStatus=0";
        if(Db.sqlReturn(s)==1){
            String[] detailStrings = new String[7];
            Db.packageDetailsql(c.id, detailStrings);
            JPanel infoPanel =new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));


            JLabel desc = new JLabel("Package Description");
            desc.setFont(new Font("Arial", 0, 33));
         

            JLabel id = new JLabel("ID: "+detailStrings[0]);
            id.setFont(new Font("Arial", 0, 25));
            JLabel initialLocation = new JLabel("Initial Location: "+detailStrings[1]);
            initialLocation.setFont(new Font("Arial", 0, 25));
            JLabel finalDestination = new JLabel("Final Destination: "+detailStrings[2]);
            finalDestination.setFont(new Font("Arial", 0, 25));
            JLabel volume = new JLabel("Volume: "+detailStrings[3]+"m^3");
            volume.setFont(new Font("Arial", 0, 25));
            JLabel Weight = new JLabel("Weight: "+detailStrings[4]+"kgs");
            Weight.setFont(new Font("Arial", 0, 25));
            JLabel description = new JLabel("Package Description: "+detailStrings[5]);
            description.setFont(new Font("Arial", 0, 25));
            JLabel price = new JLabel("price: "+detailStrings[6]);
            price.setFont(new Font("Arial", 0, 25));

            
            JButton Delivered = new JButton("Delivered");
            Delivered.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a){
                    String s = "Update Transactions set TransactionStatus=1 where PackageId="+detailStrings[0];
                    try {
                        Db.executeSql(s);
                    } catch (Exception e) {
                      System.out.println("contractor 1");
                    }
                    refresh(tabs);
                }
            });
            JButton Cancelled = new JButton("Cancel");
            Cancelled.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a){
                    String s = "Update Transactions set TransactionStatus=-1 where PackageId="+detailStrings[0];
                    try {
                        Db.executeSql(s);
                    } catch (Exception e) {
                      System.out.println("contractor 2");
                    }
                    refresh(tabs);
                }
            });


      
            infoPanel.add(desc);
            infoPanel.add(id);
            infoPanel.add(initialLocation);
            infoPanel.add(finalDestination);
            infoPanel.add(volume);
            infoPanel.add(Weight);
            infoPanel.add(description);
            infoPanel.add(price);
            infoPanel.add(Delivered);
            infoPanel.add(Cancelled);
            
               


            detailsPanel.add(infoPanel);
            detailsPanel.setBackground(Color.orange);
            desc.setBackground(Color.ORANGE);
        }else{
            JPanel NoDelivery = new JPanel();
            NoDelivery.setLayout(new BoxLayout(NoDelivery, BoxLayout.Y_AXIS));
            JLabel noDelivery = new JLabel("No ongoing Contract");
            noDelivery.setFont(new Font("Arial", 0, 33));

            detailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));

            NoDelivery.add(noDelivery);
            detailsPanel.add(NoDelivery);
        }




        d.add(detailsPanel,BorderLayout.CENTER);
        d.add(welcome,BorderLayout.NORTH);
        tabs.addTab("Delivery Details", d);
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

    public static void width(JTable a){
        a.setRowHeight(30);
        TableColumnModel c = a.getColumnModel();
        for(int i=0; i<a.getColumnCount();i++){
            c.getColumn(i).setMinWidth(100);;
        }
    }
}
