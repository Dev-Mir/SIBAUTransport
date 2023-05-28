package javaapplication1;
import java.sql.*;
import java.util.Scanner;


/**
 *
 * @author Mir
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
   Scanner sc = new Scanner(System.in);
	
	static final String databasePrefix ="sibatransport";
	static final String user = "root";
	static final String hostName ="localhost:3306";
	static final String url = "jdbc:mysql://"+hostName+"/"+databasePrefix;
	static final String password = "mirhussain123";

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
        
        int isBookcounter = 0;
        int p_id ;


	public void Connection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

        public void addPoint_and_Schedules(){
        
            System.out.println("Enter point ID");
            int p_id = sc.nextInt();
            
            System.out.println("Enter point capacity");
            int capacity = sc.nextInt();
            
            int available_seats = capacity;
            
            int booked_seats = (capacity-available_seats);  
            
            String origin = "IBA";
            
            System.out.println("Enter point's destinations eg: military road, bandar road");
            sc.nextLine() ;
            String destination = sc.nextLine();
            
            System.out.println("Enter point's arrival time eg: HH:MM:SS");
            String a_time = sc.next();
            
            System.out.println("Enter point departure time eg: eg: HH:MM:SS");
            String d_time = sc.next();
            
            
            
             try{
            
            
            PreparedStatement stat = connection.prepareStatement("INSERT INTO points (p_id, capacity, booked_seats, available_seats, origin, destination, a_time, d_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            // set the parameters in the prepared statement
            stat.setInt(1, p_id);
            stat.setInt(2, capacity);
            stat.setInt(3, booked_seats);
            stat.setInt(4, available_seats);
            stat.setString(5, origin);
            stat.setString(6, destination);
            stat.setString(7, a_time);
            stat.setString(8, d_time);
            
            

            // execute the statement
            int rowsInserted = stat.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("point and its schedules added successfully.\n");
                
                System.out.print("Do you want to do more operation? (y/n): ");
                String c = sc.next();
        
        if(c.contains("y") || c.contains("Y")){
        
            System.out.print("\t1. Add a point and its schedules details    \n");
            System.out.print("\t2. See points schedules  \n");
            System.out.print("\t3. Exit  \n");
            
             
             System.out.print("Enter Your Choince: ");
             int cc = sc.nextInt() ;
            
            if(cc==1){
            
                addPoint_and_Schedules();
            }
            
            if(cc==2){
            
                seePointsschedulesasmanager();
                
            }
            
            if(cc==3){
            
                System.exit(0);
            }
            
            
            
        }
        
        else{                System.exit(0);
}
                
               // addSchedules(p_id, origin, destination, a_time, d_time);
           
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred, Enter valid details");
            System.exit(0);

            ex.printStackTrace();
             
        }
    }
        
	public void signup() {
            
            System.out.print("Enter user name: ");
            String name = sc.next();
            System.out.print("Enter password: ");
            String password = sc.next();

            
        try{
            
            
            PreparedStatement stat = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");

            // set the parameters in the prepared statement
            stat.setString(1, name);
            stat.setString(2, password);

            // execute the statement
            int rowsInserted = stat.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Sign up successfully.");
                System.exit(0);
           
            }
        } catch (SQLException ex) {
            System.out.println("\nusername already exist.\n");
            System.exit(0);
            
            ex.printStackTrace();
             
        }
            
        }

        static String name; 
        
        public void signin(String password){
        
            try{
            PreparedStatement stat = connection.prepareStatement("select * from users where name = ? and password = ?");

            // set the parameters in the prepared statement
            stat.setString(1, name);
            stat.setString(2, password);

            // execute the statement
            ResultSet rs = stat.executeQuery();
            
             // if a row exists, the user is authenticated
        if (rs.next()) {
             System.out.println("Welcome " + rs.getString("name") + "!");
             
             System.out.print("\t1. Book a seat    \n");
             System.out.print("\t2. Unbook a seat  \n");
             System.out.print("\t3. See the points schedules  \n");
            
             System.out.print("\t4. Exit : \n");
//             system.exit();

             System.out.print("Enter Your Choince: ");
             int choice = sc.nextInt() ;
             System.out.println(choice);
              switch(choice){
              
                  case 1:
                      bookseat();
                      break;
                      
                  case 2:
                      unbookseat();
                      break;
                      
                  case 3:
                      seePointsschedulesasuser();
                      break;
                      
                  case 4:
                      System.exit(0);
                      break;
                      
                  default:
			System.out.println("not a valid choice...");
			break ;
                      
              }
             
             
             
        } else {
            System.out.println("Invalid user name or password.");
            System.exit(0);
        }            
            
       } catch (SQLException ex) {
        System.out.println("An error occurred while authenticating the user.");
        ex.printStackTrace();
    }
        
         }
        
        public void signinmanager(int admin_id, String password){
        
            try{
            PreparedStatement stat = connection.prepareStatement("select * from admins where admin_id = ? and password = ?");

            // set the parameters in the prepared statement
            stat.setInt(1, admin_id);
            stat.setString(2, password);

            // execute the statement
            ResultSet rs = stat.executeQuery();
            
             // if a row exists, the user is authenticated
        if (rs.next()) {
             System.out.println("\nWelcome Admin" );
            
             int choice = welcommanager();
             System.out.println(choice);
              switch(choice){
              
                   
                                    
                    case 1:
                           addPoint_and_Schedules();
                           break;
                                        
                                        
                    case 2:
                           seePointsschedulesasmanager();
                           break;
                                
                                        
                    case 3:
                           break;
                         
                      
                  default:
			System.out.println("not a valid choice...");
			break ;
                      
              }
             
             
             
        } else {
            System.out.println("Invalid user name or password.");
            System.exit(0);
        }            
            
       } catch (SQLException ex) {
        System.out.println("An error occurred while authenticating the user.");
        ex.printStackTrace();
    }
        
         }
       
        public void bookseat(){
            
            
        if(isBookcounter == 0){
            
                     isBookcounter =+ 1;
            
          try{
       
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM points");
            
//            resultSet = statement.executeQuery("SELECT capacity, booked_seats, available_seats FROM points");
            
            while (resultSet.next()) {
                
            int p_id = resultSet.getInt("p_id");
            int capacity = resultSet.getInt("capacity");
            int booked_seats = resultSet.getInt("booked_seats");
            int available_seats = resultSet.getInt("available_seats");
            String origin = resultSet.getString("origin");
            String destination = resultSet.getString("destination");
//            Time a_time = resultSet.getString("a_time");
            String a_time = resultSet.getString("a_time");
            String d_time = resultSet.getString("d_time");
            
            
            
            
            System.out.println("\nPoint id : " + p_id);
            System.out.println("Capacity : " + capacity);
            System.out.println("Booked seats : " + booked_seats);
            System.out.println("Available seats : " + available_seats);
            System.out.println("Origin : " + origin);
            System.out.println("Destination : " + destination);
            System.out.println("Arrival time : " + a_time);
            System.out.println("Departure time : " + d_time);
            
            System.out.println("-------------------------");
            }
            
            
            confirm_booking();
            
           
               
         
            
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
            System.exit(0);
            ex.printStackTrace();
             
        }  
        
            }
             
             
         else{
             System.out.println("You have already booked a seat");
             System.exit(0);
             }
   }
                
        public void confirm_booking(){
            
          try{
                 
            PreparedStatement stat = connection.prepareStatement("SELECT isBooked FROM users where name = ? ");
            stat.setString(1, name);
            resultSet = stat.executeQuery();
            
            if(resultSet.next()){
                boolean isbooked = resultSet.getBoolean(1) ;
                
                if(isbooked){
                
                        System.out.println("You booked already");
                        System.exit(0);
                }
            }
            
            stat = connection.prepareStatement("Update users set isBooked = 1 where name = ? ");
            stat.setString(1, name);
            stat.executeUpdate();
            
          
//          
            System.out.println("Enter point id to book a seat : ");
            p_id = sc.nextInt();
            stat = connection.prepareStatement("SELECT capacity, booked_seats FROM points where p_id = ? ");
            stat.setInt(1, p_id);
            resultSet = stat.executeQuery();
            if(resultSet.next()){
                int cap = resultSet.getInt(1) ;
                int b = resultSet.getInt(2) ;
                
                if(cap==b){
                
                    System.out.println("No seats Available for this point");
                    System.exit(0);
                }
                
            }
            
            PreparedStatement stat1 = connection.prepareStatement("Update points set booked_seats = booked_seats + 1, available_seats = available_seats - 1 where p_id = ? ");
            
            stat1.setInt(1, p_id);
            System.out.println("\nYou have booked a seat successfully\nKindly reach at point before 10 mintunes \n");
            stat1.executeUpdate();
            
            
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
//            System.exit(0);
            ex.printStackTrace();
             
        }
           
        }
        
        public void unbookseat(){
            
            
            try{
                
                
            PreparedStatement stat = connection.prepareStatement("Update points set booked_seats = booked_seats - 1, available_seats = available_seats + 1 where p_id = ? ");
            
            stat.setInt(1, p_id);
            stat.executeUpdate();
            
      
            stat = connection.prepareStatement("Update users set isBooked = 0 where name = ? ");
            stat.setString(1, name);
            stat.executeUpdate();
            
            
            System.out.println("\nYou have unbooked your a seat successfully \n");
            
            // execute the statement
//            int rowsUpdated = stat.executeUpdate();
            
           
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
            System.exit(0);
            ex.printStackTrace();
             
        }
        }            
        
        public void seePointsschedulesasmanager(){
             
              try{
            
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM points");
            
            while (resultSet.next()) {
                
            int p_id = resultSet.getInt("p_id");
            int capacity = resultSet.getInt("capacity");
            int booked_seats = resultSet.getInt("booked_seats");
            int available_seats = resultSet.getInt("available_seats");
            String origin = resultSet.getString("origin");
            String destination = resultSet.getString("destination");
            String a_time = resultSet.getString("a_time");
            String d_time = resultSet.getString("d_time");
            
            
            System.out.println("\nPoint id : " + p_id);
            System.out.println("Capacity : " + capacity);
            System.out.println("Booked seats : " + booked_seats);
            System.out.println("Available seats : " + available_seats);
            System.out.println("Origin : " + origin);
            System.out.println("Destination : " + destination);
            System.out.println("Arrival time : " + a_time);
            System.out.println("Departure time : " + d_time);
            
            System.out.println("-------------------------");
         }
            
        }  catch (SQLException ex) {
            System.out.println("An error occurred while inserting the getting points schedules.");
            ex.printStackTrace();
             
        } 
               
        System.out.print("Do you want to do more operation? (y/n): ");
        String c = sc.next();
        
        if(c.contains("y") || c.contains("Y")){
        
            int cc = welcommanager();
            
            if(cc==1){
            
                addPoint_and_Schedules();
            }
            
            if(cc==2){
            
                seePointsschedulesasmanager();
                
            }
            
            if(cc==3){
            
                System.exit(0);
            }
        }
    }
         
        public void seePointsschedulesasuser(){
             
          try{
            
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM points");
            
            while (resultSet.next()) {
                
            int p_id = resultSet.getInt("p_id");
            int capacity = resultSet.getInt("capacity");
            int booked_seats = resultSet.getInt("booked_seats");
            int available_seats = resultSet.getInt("available_seats");
            String origin = resultSet.getString("origin");
            String destination = resultSet.getString("destination");
            String a_time = resultSet.getString("a_time");
            String d_time = resultSet.getString("d_time");
            
            
            System.out.println("\nPoint id : " + p_id);
            System.out.println("Capacity : " + capacity);
            System.out.println("Booked seats : " + booked_seats);
            System.out.println("Available seats : " + available_seats);
            System.out.println("Origin : " + origin);
            System.out.println("Destination : " + destination);
            System.out.println("Arrival time : " + a_time);
            System.out.println("Departure time : " + d_time);
            
            System.out.println("-------------------------");
         }
            
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
             
        } 
               
        System.out.print("Do you want to do more operation? (y/n): ");
        String c = sc.next();
        
        if(c.contains("y") || c.contains("Y")){
        
            System.out.print("\t1. Book a seat    \n");
            System.out.print("\t2. Unbook a seat  \n");
            System.out.print("\t3. See the points schedules  \n");
            
             System.out.print("\t4. Exit : \n");
//             system.exit();

             System.out.print("Enter Your Choince: ");
             int cc = sc.nextInt() ;
            
            if(cc==1){
            
                bookseat();
            }
            
            if(cc==2){
            
                unbookseat();
                
            }
            
            if(cc==3){
            
                seePointsschedulesasuser();
            }
            
            
            if(cc==4){
            
                System.exit(0);
            }
        }
             
         }
         
        public int welcommanager(){
         
             System.out.println("\n-------- Welcome to SIBAU Transport System  as Admin ----------");
             System.out.println("1. Add a point and its schedules details");
             System.out.println("2. See points schedules");
             System.out.println("3. Exit");
             System.out.print("Enter Your Choince: ");
             int choice = sc.nextInt();
             
             return choice;
                                
         }
        
	public static void main(String[] args) {
		JavaApplication1 AppObj = new JavaApplication1();
		AppObj.Connection();
                
                boolean Isuser = false;
                Scanner sc = new Scanner(System.in) ;
			
                
                        System.out.println("\t1. Manager");
			System.out.println("\t2. User ");
                        System.out.print("Enter Your Choince: ");
			int c = sc.nextInt();
                
                        switch(c){
                            
                            case 1:
                                
                               
                                System.out.print("Enter id: ");
                                int admin_id = sc.nextInt();
                                System.out.print("Enter password: ");
                                String mpassword = sc.next();
                                AppObj.signinmanager( admin_id , mpassword);
                                
                               
                                break;
                                
                                
                            case 2:
                                        Isuser = true;
                                        while(Isuser){

                                        System.out.println("\n-------- Welcome to SIBAU Transport System as student/faculty ----------");
                                        System.out.println("\t1. Sign up: ");
                                        System.out.println("\t2. Sign in: ");


                                        System.out.print("Enter Your Choince: ");
                                        int choice = sc.nextInt() ;
                                        String namesignin = null;
                                        String passwordsignin = null;

                                        switch(choice){
                                                case 1:


                                                        AppObj.signup();
                                                        break ;

                                                case 2:
                                                        System.out.print("Enter user name: ");
                                                        namesignin = sc.next();
                                                        name = namesignin ;
                                                        System.out.print("Enter password: ");
                                                        passwordsignin = sc.next();
                                                        AppObj.signin(passwordsignin);
                                                        break ;

                                                default:
                                                        System.out.println("not a valid choice...");
                                                        break ;
                                        }
                                        System.out.print("Do you want to do more operation? (y/n): ");
                                       // sc.nextLine() ;
                                        String choice1 = sc.next(); 
                                        if(choice1.contains("y") || choice1.contains("Y")){
                                                AppObj.signin(passwordsignin);
                                                break;

                                        }
                                        else 
                                            break ;
                                        }
                                
                        break;
                     
                    default:
                            System.out.println("not a valid choice...");
                            break ;            
                                
                }
        }
 }
