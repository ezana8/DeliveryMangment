package DeliveryMangment.src;


import DeliveryMangment.src.*;
import java.awt.*;
import javax.swing.*;

import java.sql.*;


public class Main {
    static JFrame mainFrame;
    static JPanel mainPanel;
    static JPanel CustomerMenu;
    static JPanel ContractorMenu;
    static CardLayout cl;
    public static void main(String[] args)throws SQLException {
        mainFrame = new JFrame("Package Delivery");
        cl = new CardLayout();
        mainFrame.setLayout(cl);
       
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1920,1080);
       
       
        mainPanel = new JPanel();
        mainPanel.setLayout(cl);
        
        JPanel SignIn = new JPanel();
        SignIn.setLayout(new BoxLayout(SignIn, BoxLayout.LINE_AXIS));

        CustomerMenu = new JPanel();
        ContractorMenu = new JPanel();
       
        SignInPage.main(mainFrame, cl, mainPanel, SignIn);
        mainPanel.add(SignIn,"SignIn");
       
       
      
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    static void refresh(){
        mainPanel.updateUI();
    }

    static void addCustomerPage(){
        CustomerMainMenu.main( CustomerMenu);
        mainPanel.add(CustomerMenu,"CustomerMenu");
        mainPanel.updateUI();
    }
    static void addContractorPage(){
        ContractorMainMenu.main(ContractorMenu);
        mainPanel.add(ContractorMenu,"ContractorMenu");
        mainPanel.updateUI();
    }
}
