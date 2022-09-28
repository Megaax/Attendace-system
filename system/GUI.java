
package attendance.system;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI implements ActionListener{
     public DATABASE my_db=new DATABASE();
    /////////////////////
    private static JFrame f;
    
    private static JLabel user_label;
    private static JTextField userText;
    
    private static JLabel pwLabel;
    private static JPasswordField pwText;
    
    private static JButton login_button;
    private static JButton instructor_button;
    
    private static JLabel success;
    public EmailChecker checker=new EmailChecker();
    ///////////////////
    public void _init_login()
    {
        
        //Create the frame

        f= new JFrame();
        f.setTitle("Login Panel");
        f.setSize(400,260);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(123 , 50 , 250));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // image icon for the frame
        ImageIcon img= new ImageIcon("C:\\Users\\antoz\\Desktop\\icon1.png");
        f.setIconImage(img.getImage());
        
     //Create the panel
        JPanel panel=new JPanel();
        f.add(panel);
        panel.setLayout(null);
        panel.setBackground(new Color(0x006633));
        
        
        
        user_label = new JLabel("Email :");
        user_label.setBounds(10,20,80,25);
        user_label.setForeground(new Color(255,150,50));
        user_label.setSize(150,25);
        user_label.setFont(new Font(Font.DIALOG,Font.BOLD , 17));
        ImageIcon img2=new ImageIcon("C:\\Users\\antoz\\Desktop\\icon2.png");
        user_label.setIcon(img2);
        panel.add(user_label);
        
        userText=new JTextField(20);
        userText.setBounds(150,20,165,25);
        panel.add(userText);
        
        
        pwLabel = new JLabel("Password :");
        pwLabel.setBounds(10,50 ,80,25);
        pwLabel.setSize(150,25);
        pwLabel.setForeground(new Color(255,165,0));
        pwLabel.setFont(new Font(Font.DIALOG,Font.BOLD , 17));
        pwLabel.setIcon(img2);
        panel.add(pwLabel);
        
        pwText=new JPasswordField();
        pwText.setBounds(150,50,165,25);
        
        panel.add(pwText);
        
        success=new JLabel("");
        success.setBounds(10,100,300,25);
        success.setForeground(new Color(255,165,0));
        panel.add(success); 
        
        
        login_button=new JButton("Login");
        login_button.setBounds(150,90,80,25);
        login_button.setSize(170,40);
        login_button.setFont(new Font(Font.DIALOG, Font.BOLD , 25));
        login_button.setForeground(new Color(0x006633));
        login_button.setFocusable(false);
        ImageIcon img3 = new ImageIcon("C:\\Users\\antoz\\Desktop\\icon3.png");
        login_button.setIcon(img3);
        
        panel.add(login_button); 
        
        instructor_button= new JButton("Instructor?");
        instructor_button.setBounds(150,150,80,25);
        instructor_button.setSize(170,40);
        instructor_button.setFont(new Font(Font.DIALOG , Font.PLAIN , 25));
        instructor_button.setForeground(new Color(0x006633));
        instructor_button.setFocusable(false);
        panel.add(instructor_button);
        f.setVisible(true);
        
    }
    
    public void _login_button()
    {
    
    
    login_button.addActionListener(this);
    instructor_button.addActionListener(this);
    
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //TO CLOSE WINDO USE FRAME.DISPOSE()
        if(e.getSource()==login_button)
        {
        String email=userText.getText();
        String password=pwText.getText();
        if(checker.verify_email(email) && checker.verify_pass(password))
        {
            
        if(my_db.search_student(email, password)==true)
        {
          success.setText("Login Success!");
          f.dispose();
          new AttendanceGUI().setVisible(true);
        }
        else
        {
           success.setText("User Not Recognized!!");
        }
        
        }
        else
        {
        success.setText("Invaled Info!");
        }
        }
        else if(e.getSource()==instructor_button)
        {
         f.dispose();
         new InstructorLoginGUI(); 
        
        }
    }
    
    GUI()
    {
    _init_login();
    _login_button();
    }
    }