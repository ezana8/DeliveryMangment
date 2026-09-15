package DeliveryMangment.src;

import DeliveryMangment.src.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;
import java.sql.*;
//import java.sql.Date;
import java.util.Date;

import javax.swing.*;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class SignInPage  {
    static int id;
    static String name;
    static int panelWidth;
    static int panelheight;

    static CardLayout cl;
   
    static JFrame Mf;
    
    static JPanel mP;

    static int a;


    static void main (JFrame mainFrame,CardLayout mL,JPanel mp,JPanel SignIn){



        cl = new CardLayout();
        mP=mp;
        Mf = mainFrame;
        JPanel signCard = new JPanel(cl);
        JPanel signMainp = new JPanel();
        signMainp.setLayout(new BoxLayout(signMainp, BoxLayout.Y_AXIS));
        
        JPanel title = new JPanel(new BorderLayout());
        title.setOpaque(false);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel backgroundPanel = new JLabel(new ImageIcon("images/bg.jpg"));
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setOpaque(false);

        JLabel titleLabel =new JLabel("Package Delivery", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 33));
        titleLabel.setForeground(new Color(28, 52, 84));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel logoLabel = new JLabel(new ImageIcon(new ImageIcon("images/bg.png").getImage().getScaledInstance(72, 72, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        title.add(logoLabel, BorderLayout.WEST);
        title.add(titleLabel, BorderLayout.CENTER);
        panelWidth=400;
        panelheight=500;

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        signMainp.setPreferredSize(new Dimension(panelWidth, panelheight));
        centerPanel.add(signMainp, gbc);
        
        signMainp.add(signCard);
        backgroundPanel.add(title, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        //mainFrame.add(iL);
        //mainFrame.add(signMainp);
       // mainFrame.add(title);
       
        signIn(signCard,mL);
        isignIn(signCard,mL);
        signUp(signCard,mL);
        isignUp(signCard,mL);
        SignIn.setLayout(new BorderLayout());
        SignIn.add(backgroundPanel, BorderLayout.CENTER);
       
 
    }

    

    static void signIn(JPanel signCard,CardLayout mL){
        

        JPanel signMainp = new JPanel();
        signMainp.setLayout(new BoxLayout(signMainp, BoxLayout.Y_AXIS));

    
        
        
        JPanel signTitle = new JPanel();
        signTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        signTitle.setOpaque(true);
        signTitle.setBackground(new Color(52, 138, 255));
        signTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
       
        JLabel Title = new JLabel("Customer");
        Title.setForeground(Color.WHITE);
        Title.setFont(new Font("Arial", Font.BOLD, 24));
        signTitle.add(Title);
       
    
       
        JPanel signFields = new JPanel();
        signFields.setLayout(new BoxLayout(signFields, BoxLayout.Y_AXIS));
        signFields.setOpaque(true);
        signFields.setBackground(new Color(245, 247, 250, 220));
        signFields.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(178, 201, 238), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel signUsername = new JPanel();
        signUsername.setOpaque(false);
        signUsername.setLayout(new BoxLayout(signUsername, BoxLayout.X_AXIS));
        signUsername.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel user = new JLabel("Email");
        user.setPreferredSize(new Dimension(90, 30));
        user.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField usern = new JTextField(18);
        usern.setPreferredSize(new Dimension(200, 30));
        usern.setMaximumSize(new Dimension(220, 30));


        signUsername.add(user);
        signUsername.add(Box.createHorizontalStrut(10));
        signUsername.add(usern);


        JPanel signpasswd = new JPanel();
        signpasswd.setOpaque(false);
        signpasswd.setLayout(new BoxLayout(signpasswd, BoxLayout.X_AXIS));
        signpasswd.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel pass = new JLabel("Password");
        pass.setPreferredSize(new Dimension(90, 30));
        pass.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField passw = new JPasswordField(18);
        passw.setPreferredSize(new Dimension(200, 30));
        passw.setMaximumSize(new Dimension(220, 30));


        signpasswd.add(pass);
        signpasswd.add(Box.createHorizontalStrut(10));
        signpasswd.add(passw);


        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        submitPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton signb = createStyledActionButton("Sign in", new Color(255, 153, 0));
        signb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String email = usern.getText().trim();
                String password = String.valueOf(passw.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(mP, "Invalid email or password");
                    return;
                }

                String query = "select CustomerId, CustomerFirstName, CustomerLastName from Customer where CustomerEmail = ? and CustomerPasswordHash = HASHBYTES('SHA2_512', CAST(? AS varchar(max)))";

                try (Connection con = DriverManager.getConnection(Db.connectionUrl);
                     PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, email);
                    ps.setString(2, password);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            id = rs.getInt("CustomerId");
                            name = rs.getString("CustomerFirstName") + " " + rs.getString("CustomerLastName");
                            Main.addCustomerPage();
                            mP.updateUI();
                            mL.show(mP, "CustomerMenu");
                        } else {
                            JOptionPane.showMessageDialog(mP, "Invalid email or password");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mP, "Invalid email or password");
                }
            }
        });
        submitPanel.add(signb);

    
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        JButton ic = createStyledActionButton("contractor", new Color(37, 37, 37));
        ic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                cl.show(signCard, "CSignInPage");
                
            }
        });

        JButton signUp = createStyledActionButton("sign up", new Color(37, 37, 37));
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
              
                cl.show(signCard, "SignUpPage");
                
        
            }
        });

        optionsPanel.add(ic);
        optionsPanel.add(signUp);

        
        signFields.add(signUsername);
        signFields.add(signpasswd);
        signFields.add(submitPanel);
        signFields.add(optionsPanel);


        signMainp.setOpaque(true);
        signMainp.setBackground(new Color(230, 235, 245));
        signMainp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 205), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        signMainp.add(signTitle);
        signMainp.add(signFields);

        signCard.add(signMainp,"SignInPage");
        
       
    }



    static void signUp(JPanel signCard,CardLayout mL) {
        JPanel signMainp = new JPanel();
        signMainp.setLayout(new BoxLayout(signMainp, BoxLayout.Y_AXIS));

       
        JPanel signTitle = new JPanel();
        signTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
       
        JLabel Title = new JLabel("Customer");
        signTitle.add(Title);
       
    
       
        JPanel signFields = new JPanel();
        signFields.setLayout(new BoxLayout(signFields, BoxLayout.Y_AXIS));

        




        JPanel newUserFirstname = new JPanel();
        newUserFirstname.setOpaque(false);
        newUserFirstname.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        
        JLabel first = new JLabel("First name");
        JTextField firstN = new JTextField(15);

        newUserFirstname.add(first);
        newUserFirstname.add(firstN);





        JPanel newUserMiddlename = new JPanel();
        newUserMiddlename.setOpaque(false);
        newUserMiddlename.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 5));
        
        JLabel Middle = new JLabel("Middle name");
        JTextField MiddleN = new JTextField(15);


        newUserMiddlename.add(Middle);
        newUserMiddlename.add(MiddleN);

       

        JPanel newUserLastname = new JPanel();
        newUserLastname.setOpaque(false);
        newUserLastname.setLayout(new FlowLayout(FlowLayout.LEFT, 31, 5));
        
        JLabel last = new JLabel("Last name");
        JTextField lastn = new JTextField(15);


        newUserLastname.add(last);
        newUserLastname.add(lastn);


        JPanel datePanel = new JPanel();
        datePanel.setOpaque(false);
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        Date currentDate = new Date();
        String[] years = new String [83];
        int j=0;
        for(int i=currentDate.getYear()+1801;i<currentDate.getYear()+1883;i++){
            years[j]=String.valueOf(i);
            j++;
        }
        JComboBox<String> yearBox = new JComboBox<>(years);
        
        String[] months = new String[12];
        for(int i=0;i<=11;i++){
            months[i]=String.valueOf(i+1);
        }
        JComboBox<String> monthBox = new JComboBox<>(months);
        
        int days=31;
       
        
        String[] day = new String[days];
        for(int i=0;i<days;i++){
            day[i]=String.valueOf(i+1);
        }
        JComboBox<String> dayBox = new JComboBox<>(day);
        
        datePanel.add(new JLabel("Date of Birth"));
        datePanel.add(new JLabel("Day"));
        
        datePanel.add((dayBox));
        datePanel.add(new JLabel("Month"));
        datePanel.add((monthBox));
        datePanel.add(new JLabel("Year"));
        datePanel.add((yearBox));

        JPanel newUserPhone = new JPanel();
        newUserPhone.setOpaque(false);
        newUserPhone.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        
        JLabel phone = new JLabel("Phone number");
        JTextField phoneNum = new JTextField(15);


        newUserPhone.add(phone);
        newUserPhone.add(phoneNum);


        JPanel newUserEmail = new JPanel();
        newUserEmail.setOpaque(false);
        newUserEmail.setLayout(new FlowLayout(FlowLayout.LEFT, 45, 5));
        
        JLabel email = new JLabel("Email");
        JTextField Email = new JTextField(15);


        newUserEmail.add(email);
        newUserEmail.add(Email);

       

        JPanel newpasswd = new JPanel();
        newpasswd.setOpaque(false);
        newpasswd.setLayout(new FlowLayout(FlowLayout.LEFT, 33, 5));
        JLabel pass = new JLabel("password");
        JPasswordField passw = new JPasswordField(15);
        JPanel cpasswd = new JPanel();
        cpasswd.setOpaque(false);
        cpasswd.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel cpass = new JLabel(" confirm password");
        JPasswordField cpassw = new JPasswordField(15);


        newpasswd.add(pass);
        newpasswd.add(passw);
        cpasswd.add(cpass);
        cpasswd.add(cpassw);


        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        JButton signb = new JButton("Sign up");
        signb.setBackground(Color.ORANGE);;
        signb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //database check here
                String customerPhone = normalizePhoneNumber(phoneNum.getText());
                String customerEmail = Email.getText().trim();
                if(!firstN.getText().isEmpty() && !lastn.getText().isEmpty() && !customerPhone.isEmpty() && !customerEmail.isEmpty() && !String.valueOf(passw.getPassword()).isEmpty() &&  !String.valueOf(cpassw.getPassword()).isEmpty()){
                    if (!isValidEmail(customerEmail)) {
                        JOptionPane.showMessageDialog(mP, "Please enter a valid email address.");
                        return;
                    }
                    String check = "select * from Customer where CustomerPhoneNum='"+customerPhone+"' and CustomerEmail='"+customerEmail+"'";
                    if (Db.sqlReturn(check)==-1 && String.valueOf(passw.getPassword()).compareTo(String.valueOf(cpassw.getPassword()))==0){
                        String date = yearBox.getSelectedItem() +"-"+ monthBox.getSelectedItem()+"-"+dayBox.getSelectedItem();
                        String input = "insert into Customer values('"+firstN.getText()+"','" + MiddleN.getText()+"','" + lastn.getText()+"','" + date+"','" +customerPhone+"','" +customerEmail+"',HASHBYTES('SHA2_512','"+String.valueOf(passw.getPassword())+"'))";
                        boolean ex= false;
                        try {
                            ex = Db.executeSql(input);
                        } catch (com.microsoft.sqlserver.jdbc.SQLServerException s) {
                            if(s.getMessage().compareTo("Conversion failed when converting date and/or time from character string.")==0){
                                JOptionPane.showMessageDialog(mP, "Invalid date, please input a valid date.");
                            }
                            if(s.getMessage().compareTo("Violation of UNIQUE KEY constraint 'UQ__Customer__3A0CE74CE4A81DD4'. Cannot insert duplicate key in object 'dbo.Customer'. The duplicate key value is ("+Email.getText()+").")==0 ){
                                JOptionPane.showMessageDialog(mP, "User email already registered, please changethe email or login");
                            }else if( s.getMessage().compareTo("Violation of UNIQUE KEY constraint 'UQ__Customer__27A270983B003192'. Cannot insert duplicate key in object 'dbo.Customer'. The duplicate key value is ("+phoneNum.getText()+").")==0){
                                JOptionPane.showMessageDialog(mP, "User phone number already registered, please changethe phone number or login");
                            }
                            System.out.println(s.getMessage());
                        }catch(Exception d){ 
                            System.out.println("error error error");
                        }
                        
                        String s = "select * from Customer where CustomerPhoneNum='"+customerPhone+"' and CustomerEmail='"+customerEmail+"'";
                        if(ex){
                            id=Db.returnIdSql(s, "CustomerId");
                            name = Db.returnNameSql(s, "CustomerFirstName");
                            name+= " " +Db.returnNameSql(s, "CustomerLastName");
                            Main.addCustomerPage();
                            mP.updateUI();
                            mL.show(mP, "CustomerMenu");
                        }else{
                            System.out.println("error");
                        }
                    }else if(Db.sqlReturn(check)==1){
                        JOptionPane.showMessageDialog(mP, "User already registered, please log in");
                    }else{
                        JOptionPane.showMessageDialog(mP, "Password Mismatch, please correct your password");
                    }
                }else{
                    JOptionPane.showMessageDialog(mP, "Please fill the entire form.");
                }
               
            }
        });
        submitPanel.add(signb);

        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        optionsPanel.setOpaque(false);
        JButton ic = new JButton("contractor");
        ic.setBackground(Color.BLACK);
        ic.setForeground(Color.WHITE);
        ic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //database check here 
             
        
                cl.show(signCard, "CSignUpPage");
            

               
            }
        });
        JButton signUp = new JButton("login");
        
        signUp.setBackground(Color.BLACK);
        signUp.setForeground(Color.WHITE);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               
                cl.show(signCard, "SignInPage");
            }
        });
        optionsPanel.add(ic);
        optionsPanel.add(signUp);


       
        signFields.add(newUserFirstname);
        signFields.add(newUserMiddlename);
        signFields.add(newUserLastname);
        signFields.add(datePanel);
        signFields.add(newUserEmail);
        signFields.add(newUserPhone);
        signFields.add(newpasswd);
        signFields.add(cpasswd);
        signFields.add(submitPanel);
        signFields.add(optionsPanel);



        signMainp.add(signTitle);
        signMainp.add(signFields);


        signCard.add(signMainp, "SignUpPage");
    }







    static void isignIn(JPanel signCard, CardLayout mL){

        JPanel signMainp = new JPanel();
        signMainp.setLayout(new BoxLayout(signMainp, BoxLayout.Y_AXIS));

    
        
        
        JPanel signTitle = new JPanel();
        signTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
       
        JLabel Title = new JLabel("Contractor");
        signTitle.add(Title);
       
    
       
        JPanel signFields = new JPanel();
        signFields.setLayout(new BoxLayout(signFields, BoxLayout.Y_AXIS));

        JPanel signUsername = new JPanel();
        signUsername.setOpaque(false);
        signUsername.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 30));
        
        JLabel user = new JLabel("Email");
        JTextField usern = new JTextField(15);



        signUsername.add(user);
        signUsername.add(usern);


        JPanel signpasswd = new JPanel();
        signpasswd.setOpaque(false);
        signpasswd.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JLabel pass = new JLabel("password");
        JPasswordField passw = new JPasswordField(15);


        signpasswd.add(pass);
        signpasswd.add(passw);


        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        JButton signb = createStyledActionButton("Sign in", new Color(255, 153, 0));
        signb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String email = usern.getText().trim();
                String password = String.valueOf(passw.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(mP, "Invalid email or password");
                    return;
                }

                String query = "select IndividualContractorId, IndividualContractorFirstName, IndividualContractorLastName from IndividualContractor where IndividualContractorEmail = ? and IndividualContractorPasswordHash = HASHBYTES('SHA2_512', CAST(? AS varchar(max)))";

                try (Connection con = DriverManager.getConnection(Db.connectionUrl);
                     PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, email);
                    ps.setString(2, password);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            id = rs.getInt("IndividualContractorId");
                            name = rs.getString("IndividualContractorFirstName") + " " + rs.getString("IndividualContractorLastName");
                            Main.addContractorPage();
                            mP.updateUI();
                            mL.show(mP, "ContractorMenu");
                        } else {
                            JOptionPane.showMessageDialog(mP, "Invalid email or password");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mP, "Invalid email or password");
                }
            }
        });
        submitPanel.add(signb);

        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        JButton ic = createStyledActionButton("Customer", new Color(37, 37, 37));
        ic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(signCard, "SignInPage");
                
            }
        });
        JButton signUp = createStyledActionButton("sign up", new Color(37, 37, 37));
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(signCard, "CSignUpPage");
               
            }
        });
        optionsPanel.add(ic);
        optionsPanel.add(signUp);

      
        signMainp.setOpaque(true);
        signMainp.setBackground(new Color(230, 235, 245));
        signMainp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 205), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        signMainp.add(signTitle);
        signFields.add(signUsername);
        signFields.add(signpasswd);
        signFields.add(submitPanel);
        signFields.add(optionsPanel);
        signMainp.add(signFields);
        signCard.add(signMainp,"CSignInPage");
        
       
    }

    private static String normalizePhoneNumber(String rawPhone) {
        if (rawPhone == null) {
            return "";
        }

        String trimmed = rawPhone.trim();
        String digits = trimmed.replaceAll("[^0-9]", "");

        if (trimmed.startsWith("+251") && trimmed.length() == 13) {
            return trimmed;
        }

        if (digits.length() == 9) {
            return "+251" + digits;
        }

        if (digits.length() == 12 && digits.startsWith("251")) {
            return "+" + digits;
        }

        if (digits.length() == 10 && digits.startsWith("0")) {
            return "+251" + digits.substring(1);
        }

        return trimmed;
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches(".*@.*\\..*");
    }

    private static JButton createStyledActionButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(background.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }


    static void isignUp(JPanel signCard,CardLayout mL) {
        JPanel signMainp = new JPanel();
        signMainp.setLayout(new BoxLayout(signMainp, BoxLayout.Y_AXIS));

       
        JPanel signTitle = new JPanel();
        signTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
       
        JLabel Title = new JLabel("Contractor");
        signTitle.add(Title);
       
    
       
        JPanel signFields = new JPanel();
        signFields.setLayout(new BoxLayout(signFields, BoxLayout.Y_AXIS));

       

        JPanel newUserFirstname = new JPanel();
        newUserFirstname.setOpaque(false);
        newUserFirstname.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        
        JLabel first = new JLabel("First name");
        JTextField firstN = new JTextField(15);

        newUserFirstname.add(first);
        newUserFirstname.add(firstN);





        JPanel newUserMiddlename = new JPanel();
        newUserMiddlename.setOpaque(false);
        newUserMiddlename.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 5));
        
        JLabel Middle = new JLabel("Middle name");
        JTextField MiddleN = new JTextField(15);


        newUserMiddlename.add(Middle);
        newUserMiddlename.add(MiddleN);

       

        JPanel newUserLastname = new JPanel();
        newUserLastname.setOpaque(false);
        newUserLastname.setLayout(new FlowLayout(FlowLayout.LEFT, 31, 5));
        
        JLabel last = new JLabel("Last name");
        JTextField lastn = new JTextField(15);


        newUserLastname.add(last);
        newUserLastname.add(lastn);


        JPanel datePanel = new JPanel();
        datePanel.setOpaque(false);
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        Date currentDate = new Date();
        String[] years = new String [83];
        int j=0;
        for(int i=currentDate.getYear()+1801;i<currentDate.getYear()+1883;i++){
            years[j]=String.valueOf(i);
            j++;
        }
        JComboBox<String> yearBox = new JComboBox<>(years);
        
        String[] months = new String[12];
        for(int i=0;i<=11;i++){
            months[i]=String.valueOf(i+1);
        }
        JComboBox<String> monthBox = new JComboBox<>(months);
        
        int days=31;
       
        
        String[] day = new String[days];
        for(int i=0;i<days;i++){
            day[i]=String.valueOf(i+1);
        }
        JComboBox<String> dayBox = new JComboBox<>(day);
        
        datePanel.add(new JLabel("Date of Birth"));
        datePanel.add(new JLabel("Day"));
        
        datePanel.add((dayBox));
        datePanel.add(new JLabel("Month"));
        datePanel.add((monthBox));
        datePanel.add(new JLabel("Year"));
        datePanel.add((yearBox));


        JPanel vehiclePanel = new JPanel();
        vehiclePanel.setOpaque(false);
        vehiclePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        String[] vc = {"No vehicle", "MotorCycle or Bicycle", "Sedan","Pick-up","Van or Mini-van"};
        JComboBox<String> vehicleBox = new JComboBox<>(vc);

        vehiclePanel.add(new JLabel("Vehicle Type: "));
        vehiclePanel.add(vehicleBox);



        JPanel newUserPhone = new JPanel();
        newUserPhone.setOpaque(false);
        newUserPhone.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        
        JLabel phone = new JLabel("Phone number");
        JTextField phoneNum = new JTextField(15);


        newUserPhone.add(phone);
        newUserPhone.add(phoneNum);


        JPanel newUserEmail = new JPanel();
        newUserEmail.setOpaque(false);
        newUserEmail.setLayout(new FlowLayout(FlowLayout.LEFT, 45, 5));
        
        JLabel email = new JLabel("Email");
        JTextField Email = new JTextField(15);


        newUserEmail.add(email);
        newUserEmail.add(Email);

       

        JPanel newpasswd = new JPanel();
        newpasswd.setOpaque(false);
        newpasswd.setLayout(new FlowLayout(FlowLayout.LEFT, 33, 5));
        JLabel pass = new JLabel("password");
        JPasswordField passw = new JPasswordField(15);
        JPanel cpasswd = new JPanel();
        cpasswd.setOpaque(false);
        cpasswd.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel cpass = new JLabel(" confirm password");
        JPasswordField cpassw = new JPasswordField(15);


        newpasswd.add(pass);
        newpasswd.add(passw);
        cpasswd.add(cpass);
        cpasswd.add(cpassw);


        JPanel submitPanel = new JPanel();
        submitPanel.setOpaque(false);
        JButton signb = new JButton("Sign up");
        signb.setBackground(Color.ORANGE);;
        signb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //database check here
                String contractorPhone = normalizePhoneNumber(phoneNum.getText());
                String contractorEmail = Email.getText().trim();
                if(!firstN.getText().isEmpty() && !lastn.getText().isEmpty() && !contractorPhone.isEmpty() && !contractorEmail.isEmpty() && !String.valueOf(passw.getPassword()).isEmpty() &&  !String.valueOf(cpassw.getPassword()).isEmpty()){
                    if (!isValidEmail(contractorEmail)) {
                        JOptionPane.showMessageDialog(mP, "Please enter a valid email address.");
                        return;
                    }
                    String check = "select * from IndividualContractor where IndividualContractorPhoneNum='"+contractorPhone+"' and IndividualContractorEmail='"+contractorEmail+"'";
                    if (Db.sqlReturn(check)==-1 && String.valueOf(passw.getPassword()).compareTo(String.valueOf(cpassw.getPassword()))==0){
                        String date = yearBox.getSelectedItem() +"-"+ monthBox.getSelectedItem()+"-"+dayBox.getSelectedItem();
                        int vc= vehicleBox.getSelectedIndex();
                        String input = "insert into IndividualContractor values('"+firstN.getText()+"','" + MiddleN.getText()+"','" + lastn.getText()+"','" + date+"','" +contractorPhone+"','" +vc+"','idle','" +contractorEmail+"',HASHBYTES('SHA2_512','"+String.valueOf(passw.getPassword())+"'))";
                        boolean ex=false;
                        try {
                            ex=Db.executeSql(input);
                        }catch(com.microsoft.sqlserver.jdbc.SQLServerException s){
                            if(s.getMessage().compareTo("Conversion failed when converting date and/or time from character string.")==0){
                                JOptionPane.showMessageDialog(mP, "Invalid date, please input a valid date.");
                            }
                            if(s.getMessage().compareTo("Violation of UNIQUE KEY constraint 'UQ__Individu__96FD6378E09B5733'. Cannot insert duplicate key in object 'dbo.IndividualContractor'. The duplicate key value is ("+Email.getText()+").")==0 ){
                                JOptionPane.showMessageDialog(mP, "User email already registered, please changethe email or login");
                            }else if( s.getMessage().compareTo("Violation of UNIQUE KEY constraint 'UQ__Individu__3778233E303895D6'. Cannot insert duplicate key in object 'dbo.IndividualContractor'. The duplicate key value is ("+phoneNum.getText()+").")==0 ||  s.getErrorCode() == 547 ){
                                JOptionPane.showMessageDialog(mP, "User phone number already registered, please changethe phone number or login");
                            }
                            System.out.println(s.getErrorCode());
                        } catch (Exception s) {
                            if(s.getMessage()=="Conversion failed when converting date and/or time from character string."){
                                JOptionPane.showMessageDialog(mP, "Invalid date, please input a valid date.");
                            }else{
                                System.out.println("error error error");
                            }
                        }
                       
                        String s = "select * from IndividualContractor where IndividualContractorPhoneNum='"+contractorPhone+"' and IndividualContractorEmail='"+contractorEmail+"'";
                        if(ex){
                            id=Db.returnIdSql(s, "IndividualContractorId");
                            name = Db.returnNameSql(s, "IndividualContractorFirstName");
                            name+= " " + Db.returnNameSql(s, "IndividualContractorLastName");
                            Main.addContractorPage();
                            mP.updateUI();
                            mL.show(mP, "ContractorMenu");
                        }else{
                            System.out.println("error");
                        }
                    }else if(Db.sqlReturn(check)==1){
                        System.out.println("sql check failed");
                    }else{
                        System.out.println("password fail");
                    }
                }
               
            }
        });
        submitPanel.add(signb);

        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        optionsPanel.setOpaque(false);
        JButton ic = new JButton("Customer");
        ic.setBackground(Color.BLACK);
        ic.setForeground(Color.WHITE);
        ic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //database check here 
                cl.show(signCard, "SignUpPage");
               
            }
        });
        JButton signUp = new JButton("login");
        
        signUp.setBackground(Color.BLACK);
        signUp.setForeground(Color.WHITE);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(signCard, "CSignInPage");
            }
        });
        optionsPanel.add(ic);
        optionsPanel.add(signUp);





      
        signMainp.add(signTitle);
        signFields.add(newUserFirstname);
        signFields.add(newUserMiddlename);
        signFields.add(newUserLastname);
        signFields.add(datePanel);
        signFields.add(newUserEmail);
        signFields.add(newUserPhone);
        signFields.add(vehiclePanel);
        signFields.add(newpasswd);
        signFields.add(cpasswd);
        signFields.add(submitPanel);
        signFields.add(optionsPanel);
        signMainp.add(signFields);
        signCard.add(signMainp, "CSignUpPage");
    }



    


}
