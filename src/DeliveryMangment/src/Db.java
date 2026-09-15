package DeliveryMangment.src;

import java.sql.*;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerException;


public class Db {
  static String connectionUrl;
  static{
    connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=DeliveryApp;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
  }
    public static int sqlReturn(String st){
        
        int i =0;
       try (Connection con = DriverManager.getConnection(connectionUrl)) {
        System.out.println("Connection successful!");
        Statement statement = con.createStatement();
        ResultSet rs ;
        rs = statement.executeQuery(st);
      
        if (!rs.next() ) {    
           i=-1;
           System.out.println("nothing found");
        } else {
            i=1;
        }
        rs.close();
        statement.close();
        con.close(); 
      } catch (SQLException e) {
      e.printStackTrace();
      }
      return i;
    }


    public static int returnIdSql(String st,String Entity){

        
        int i =0;
       try (Connection con = DriverManager.getConnection(connectionUrl)) {
        System.out.println("Connection successful!");
        Statement statement = con.createStatement();
        ResultSet rs ;
        rs = statement.executeQuery(st);
      while(rs.next()){
        i=rs.getInt(Entity);
      }
       
        rs.close();
        statement.close();
        con.close(); 
      } catch (SQLException e) {
      e.printStackTrace();
      }
      return i;
    }


    public static String returnNameSql(String st,String Entity){

        
        String name="";
       try (Connection con = DriverManager.getConnection(connectionUrl)) {
        System.out.println("Connection successful!");
        Statement statement = con.createStatement();
        ResultSet rs ;
        rs = statement.executeQuery(st);
      while(rs.next()){
        name=rs.getString(Entity);
      }
       
        rs.close();
        statement.close();
        con.close(); 
      } catch (SQLException e) {
      e.printStackTrace();
      }
      return name;
    }


    public static boolean executeSql(String st) throws com.microsoft.sqlserver.jdbc.SQLServerException, Exception{
        
        
       try (Connection con = DriverManager.getConnection(connectionUrl)) {
        System.out.println("Connection successful!");
        Statement statement = con.createStatement();
        statement.execute(st);
        
        
        
        statement.close();
        con.close(); 
      } catch (com.microsoft.sqlserver.jdbc.SQLServerException f){
       throw f;
      }catch (SQLException e) {
      e.printStackTrace();
      System.out.println("execute error");
      return false;
      }
      return true;

    }


    public static ResultSet returnInfoSql(String st){
        
        ResultSet i=null;
       try (Connection con = DriverManager.getConnection(connectionUrl)) {
        System.out.println("Connection successful!");
        Statement statement = con.createStatement();
   
        i = statement.executeQuery(st);
        
      
        statement.close();
        con.close(); 
      } catch (SQLException e) {
      e.printStackTrace();
      }
      return i;
    }


    public static ArrayList<ArrayList<String>> sqlPendingPackagesRead(String st){
        ResultSet s=null;
        
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connection !");
            Statement statement = con.createStatement();
      
            s = statement.executeQuery(st);
     
         
                 while(s.next()){
   
                    String  pid,pil,pfd;
                   
                    pid= String.valueOf(s.getInt("PackageId"));
                    pil= s.getString("PackageInitialLocation");
                    pfd= s.getString("PackageFinalDestination");
                    ArrayList<String> arr = new ArrayList<>();
                  
                    arr.add(pid);
                    arr.add(pil);
                    arr.add(pfd);
                    b.add(arr);
                    //tm.addRow(arr);
                }
            
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static ArrayList<ArrayList<String>> sqlPackagesRead(String st){
        ResultSet s=null;
        
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connection !");
            Statement statement = con.createStatement();
      
            s = statement.executeQuery(st);
     
         
                 while(s.next()){
   
                    String  cpn,pid,pil,pfd;
                   
                    pid= String.valueOf(s.getInt("PackageId"));
                    pil= s.getString("PackageInitialLocation");
                    pfd= s.getString("PackageFinalDestination");
                    cpn = s.getString("CustomerPhoneNum");
                    ArrayList<String> arr = new ArrayList<>();
                  
                    arr.add(pid);
                    arr.add(pil);
                    arr.add(pfd);
                    arr.add(cpn);
                    b.add(arr);
                    //tm.addRow(arr);
                }
            
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }
    public static ArrayList<ArrayList<String>> sqlPackagesHistory(String st){
        ResultSet s=null;
        
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connection !");
            Statement statement = con.createStatement();
      
            s = statement.executeQuery(st);
     
         
                 while(s.next()){
   
                    String  ts,pid,pil,pfd;
                   
                    pid= String.valueOf(s.getInt("PackageId"));
                    ts= String.valueOf(s.getInt("TransactionStatus"));
                    pil= s.getString("PackageInitialLocation");
                    pfd= s.getString("PackageFinalDestination");
                 
                    ArrayList<String> arr = new ArrayList<>();
                  
                    arr.add(pid);
                    arr.add(pil);
                    arr.add(pfd);
                    arr.add(ts);
                    b.add(arr);
                    //tm.addRow(arr);
                }
            
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static ArrayList<ArrayList<String>> sqlPackagesHistoryRead(String st){
        ResultSet s=null;
        
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(connectionUrl)) {
          System.out.println("Connection !");
          Statement statement = con.createStatement();
    
          s = statement.executeQuery(st);

        
          while(s.next()){
            

            
            
            String  cid,pid,pil,pfd,icfn,icln;
            
            pid= String.valueOf(s.getInt("PackageId"));
            cid = String.valueOf(s.getInt("CustomerId"));
            icfn = s.getString("IndividualContractorFirstName");
            icln = s.getString("IndividualContractorLastName");
            pil= s.getString("PackageInitialLocation");
            pfd= s.getString("PackageFinalDestination");
            
            ArrayList<String> arr = new ArrayList<>();
          
            arr.add(pid);
            arr.add(cid);
            arr.add(icfn);
            arr.add(icln);
            arr.add(pil);
            arr.add(pfd);
            b.add(arr);
            //tm.addRow(arr);
        }
          
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }
    public static void createTransaction(String id){
      
      
     try (Connection con = DriverManager.getConnection(connectionUrl)) {
      System.out.println("Connection successful!");
      Statement statement = con.createStatement();
      statement.execute("Update Package set PackageShipped=1 where PackageId="+id);
      ResultSet p=statement.executeQuery("select PackageId,CustomerId,PackagePrice from Package where PackageId="+id);
      p.next();
      int cid,pid,pm;
      cid=p.getInt("CustomerId");
      pid=p.getInt("PackageId");
      pm=p.getInt("PackagePrice");

      statement.execute("Insert into Transactions values ("+pm+",0,"+cid+","+ContractorMainMenu.c.id+","+pid+")");
    
      
      statement.close();
      con.close(); 
    } catch (SQLException e) {
    e.printStackTrace();
    System.out.println("execute error");

    }
  }

  public static void packageDetailsql(int id,String[] details){
    
      
     try (Connection con = DriverManager.getConnection(connectionUrl)) {
      System.out.println("Connection successful!");
      Statement statement = con.createStatement();

      ResultSet p=statement.executeQuery("select * from Package inner join Transactions on Transactions.PackageId=Package.PackageId and Transactions.TransactionStatus=0 and Transactions.IndividualContractorId="+id);
      p.next();
      String pil,pfd,pw,pd,pv,pid,pm;
      details[0]=String.valueOf(p.getInt("PackageId"));
      details[1]=p.getString("PackageInitialLocation");
      details[2]=p.getString("PackageFinalDestination");
      details[3]=String.valueOf(p.getInt("PackageVolume"));
      details[4]=String.valueOf(p.getInt("PackageWeight"));
      details[5]=String.valueOf(p.getString("PackageDescription"));
      details[6]=String.valueOf(p.getInt("PackagePrice"));
  

    
      
      statement.close();
      con.close(); 
    } catch (SQLException e) {
    e.printStackTrace();
    System.out.println("execute error");

    }
   

  }

}
