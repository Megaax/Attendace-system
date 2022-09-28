package attendance.system;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.*;
import java.text.Format;  
import java.text.SimpleDateFormat;
public class DATABASE{
   String url,username,password;
    public Connection MyConnection;
    public  Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    DATABASE(){
       this.url="jdbc:mysql://localhost:3306/attendance system?zeroDateTimeBehavior=convertToNull";
       this.username="root";
       this.password="";
       this.connect();
   }
   public void connect(){
       try{
           MyConnection=DriverManager.getConnection(this.url,this.username,this.password) ;
           System.out.println("connected");
       }
       catch(SQLException x){
            System.out.println("can't connect" + x );
        } 
       
   }
   /*****************ATTEND*********************/
    boolean attend(String STemail, String subject){
        try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                   myStatement.executeUpdate("INSERT INTO takes(students_email,course_section_name,status) VALUES('"+STemail+"','"+subject+"','"+1+"');");
                   return true;
                } 
                catch(SQLException x){
                    System.out.println("can't INSERT as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
    }
   
    String attendanceProcessing(String STEmail){
        int groupSec=0;
        String str="";
        Calendar cal = Calendar.getInstance();  
        Format f = new SimpleDateFormat("EEEE");  
        String todayDate = f.format(new Date());
        System.out.println( "todaaaay "+todayDate);
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int currentTime=Integer.valueOf(sdf.format(date));
        System.out.println( "time "+currentTime);
        try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                    ResultSet myresult =myStatement.executeQuery("SELECT group_no_ID FROM students WHERE email='"+STEmail+"'");
                    while(myresult.next())
                    {
                      groupSec=myresult.getInt("group_no_ID");
                      System.out.println(groupSec );
                    }
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                }
        try{     
              Statement myStatement= MyConnection.createStatement();
                try{
                    ResultSet myresult =myStatement.executeQuery("SELECT * FROM group_has_course_section WHERE group_no_ID='"+groupSec+"' AND day = '"+todayDate+"'" );
                    if(myresult.next())
                    {   
                      if (myresult.getInt("hour") <= currentTime && myresult.getInt("hour")+2 > currentTime &&myresult.getString("day").compareTo(todayDate)==0 )
                      {
                          str =myresult.getString("course_section_name");
                     
                      }
                    }
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                }
        return str;
    }
   /*************************************Search student for login ************************************/
    public boolean search_student (String email , String password ){
        try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                    ResultSet myresult =myStatement.executeQuery("SELECT * FROM students WHERE email='"+email+"' AND password='"+password+"'");
                    while(myresult.next())
                    {
                      
                       System.out.println("found : "+myresult.getString("email"));
                       return true;
                    }
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
        return false;
    } 
    /*************************************Search instructor for login ************************************/
    public boolean search_instructor(String email , String password ){
        try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                    ResultSet myresult =myStatement.executeQuery("SELECT * FROM instructor WHERE email='"+email+"' AND password='"+password+"'");
                    while(myresult.next())
                    {
                      
                       System.out.println("found : "+myresult.getString("email"));
                       return true;
                    }
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
        return false;
    }
    /*************************************Search instructor groups for publishing course time and day ************************************/
    public  ArrayList<Integer> search_instructorgroups(String email ){
        ArrayList<Integer> Grouparr=new ArrayList<>();
        try{
                Statement myStatement= MyConnection.createStatement();
                try{
                    
                    ResultSet myresult =myStatement.executeQuery("SELECT group_no_ID FROM instructor_has_group_no WHERE instructor_email='"+email+"'");
                    while(myresult.next())
                    {
                     Grouparr.add(myresult.getInt("group_no_ID"));              
                    }
                    return Grouparr;
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                    return Grouparr;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return Grouparr;
                }
        
    }
    /*************************************get all course ************************************/
    public  ArrayList<String> getCourses (){
        ArrayList<String> coursearr=new ArrayList<>();
        try{
                Statement myStatement= MyConnection.createStatement();
                try{
                    
                    ResultSet myresult =myStatement.executeQuery("SELECT * FROM course_section  ");
                    while(myresult.next())
                    {
                     coursearr.add(myresult.getString("name"));              
                    }
                    return coursearr;
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                    return coursearr;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return coursearr;
                }
    }
    /**************************************insert student data for file of student*************************************************/
    public boolean insertST_data(String email , String password ,int Group){
         try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                   myStatement.executeUpdate("INSERT INTO students(email,password,group_no_ID) VALUES('"+email+"','"+password+"','"+Group+"');");
                   return true ;
                } 
                catch(SQLException x){
                    System.out.println("can't INSERT as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
    }
    public int get_attendance(String email, String course){
        int counter=0;
        try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                    ResultSet myresult =myStatement.executeQuery("SELECT status FROM takes WHERE students_email='"+email+"' AND course_section_name='"+course+"'");
                    while(myresult.next())
                    {
                       if(myresult.getInt("status")==1){
                           counter++;
                       }
                    }
                    return counter;
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                    return counter;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return counter;
                }
    }
    /**************************************insert instructor data for file of instructor *************************************************/
    public boolean insertINST_data(String email , String password){// insert Student data into table
         try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                   myStatement.executeUpdate("INSERT INTO instructor(email,password) VALUES('"+email+"','"+password+"');");
                   return true ;
                } 
                catch(SQLException x){
                    System.out.println("can't INSERT as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
    }
    /**************************************insert instructor groups data*************************************************/
    public boolean insert_INSTgroups(String instructor , int group){// insert Student data into table
         try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                   myStatement.executeUpdate("INSERT INTO instructor_has_group_no(group_no_ID,instructor_email) VALUES('"+group+"','"+instructor+"');");
                   return true;
                } 
                catch(SQLException x){
                    System.out.println("can't INSERT as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
    }
    /**************************************insert data of publishing course day and time *************************************************/
    public boolean insert_groupCourse( int group, String course, String day, int hour){// insert Student data into table
         try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                   myStatement.executeUpdate("INSERT INTO  group_has_course_section(course_section_name,group_no_ID,day,hour) VALUES('"+course+"','"+group+"','"+day+"','"+hour+"');");
                   return true;
                }
                catch(SQLException x){
                    System.out.println("can't INSERT as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
	
    }
    public boolean Search( String email, String pw , String table)
    {
        String m;
        try {
            String searchQuery="SELECT * FROM "+table+" WHERE email='"+email+"' AND password='"+pw+"'";
            pst = con.prepareStatement(searchQuery);
            rs=pst.executeQuery();
            
        } catch (SQLException ex) 
        {
            System.out.println(ex);
        }
        try {
            while(rs.next())
            {
               m=rs.getString("email");
               System.out.println("found : "+m);
               return true;
            }
        } catch (SQLException ex)
        {
            System.out.println(ex);
            }
            return false;
        }
    public void Insert(String email , String pw , String table)
    {
             String insertQuery="INSERT INTO "+table+" ( email , pw) VALUES ('"+email+"','"+pw+"')";
        try {
            pst = con.prepareStatement(insertQuery);
                 int updated_rows = pst.executeUpdate();
            System.out.println("Insertion Done Succefully!  Update Rows : " + updated_rows);
        } catch (SQLException ex)
        {
                Logger.getLogger(DATABASE.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
     public boolean email_search (String email){
        try{     
                Statement myStatement= MyConnection.createStatement();
                try{
                    ResultSet myresult =myStatement.executeQuery("SELECT * FROM students WHERE email='"+email+"'");
                    while(myresult.next())
                    {
                      
                       System.out.println("found : "+myresult.getString("email"));
                       return true;
                    }
                }
                catch(SQLException x){
                    System.out.println("can't create result as "+x);
                    return false;
                 }
            }
        catch(SQLException x){
                System.out.println("can't create statement as "+x);
                return false;
                }
        return false;
    } 
    
}