

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class InsertCoffeeServlet
 */
@WebServlet("/InsertCoffeeServlet")
public class InsertCoffeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertCoffeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 //宣告網站出現後的表呈現的樣子是html形式
		response.setContentType("text/html;charset=UTF-8");
		//標準字串輸出的方法，設定一個寫資料的變數，讓他可以把文字傳到網頁上
	        PrintWriter out = response.getWriter();
	        try {
	      /*    out.println("<!DOCTYPE html>");
	            out.println("<html>");
	            out.println("<head>");
	            out.println("<title>Servlet InsertCoffee</title>");            
	            out.println("</head>");
	            out.println("<body>");  
	      */
	        	out.println("<h1>執行結果</h1>");  
	            String coffee=request.getParameter("coffee");
	            String sale=request.getParameter("sale");
	            String total=request.getParameter("total");         
	            String supplier=request.getParameter("supplier");
	            String price=request.getParameter("price");         
	            try {
	                InsertCoffee(coffee,sale,total,supplier,price);
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	                out.println("新增失敗");
	                return;
	            }
	            out.println("新增完成");
	            /*out.println("</body>");
	            out.println("</html>");*/
	        } finally {            
	            out.close();
	        }

	}
		public int InsertCoffee(String coffee,String sale,String total,String supplier,String price)
		throws SQLException
		{
			String url="jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei";
			String user="root";
			String password="1234";
		Connection conn=null;
		PreparedStatement insert=null;
		String inserStatement="insert into classicmodels.COFFEES( COF_NAME , SUP_ID , PRICE , SALES ,TOTAL)" +
		        " values (?,?,?,?,?)";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			insert=conn.prepareStatement(inserStatement);
			insert.setString(1,coffee);
			insert.setInt(2, Integer.parseInt(supplier));           
	        insert.setDouble(3, Double.parseDouble(price));
	        insert.setInt(4, Integer.parseInt(sale));
	        insert.setInt(5, Integer.parseInt(total));
	        int r=insert.executeUpdate();
	        conn.commit();
	        return r;}
	        catch(Exception e ) {
	        	  System.out.println("SQL Error:"+e.getMessage());
			        if (conn != null) {
			            try {
			                System.err.print("Transaction is being rolled back");
			                conn.rollback();
			            } catch(SQLException ex) {
			                System.out.println(ex.getMessage());
			            }
			        }
			    } finally {
			    	conn.setAutoCommit(true);
			    	if (insert != null) {
			            insert.close();
			        }
			        if (insert != null) {
			            insert.close();
			        }
			        
			    }
			    return 0;
	}}
