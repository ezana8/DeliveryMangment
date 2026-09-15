public class InspectCustomer {
  public static void main(String[] args) throws Exception {
    String conn = "jdbc:sqlserver://localhost:1433;databaseName=DeliveryApp;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    try (java.sql.Connection con = java.sql.DriverManager.getConnection(conn);
         java.sql.Statement st = con.createStatement();
         java.sql.ResultSet rs = st.executeQuery("SELECT TOP 20 CustomerId, CustomerFirstName, CustomerLastName, CustomerPhoneNum, CustomerEmail, CustomerPasswordHash FROM dbo.Customer")) {
      java.sql.ResultSetMetaData md = rs.getMetaData();
      int cols = md.getColumnCount();
      for (int i=1; i<=cols; i++) System.out.print(md.getColumnName(i) + "\t");
      System.out.println();
      while (rs.next()) {
        for (int i=1; i<=cols; i++) {
          Object v = rs.getObject(i);
          if (v == null) System.out.print("NULL\t");
          else if (v instanceof byte[]) System.out.print(((byte[])v).length + " bytes\t");
          else System.out.print(v + "\t");
        }
        System.out.println();
      }
    }
  }
}
