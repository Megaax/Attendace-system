/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance.system;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;


public class FileReader {
    
    private File file=new File("C:\\Users\\antoz\\Desktop\\students.txt");
    StringTokenizer tokenizer;
    String filecontent="";
    DATABASE my_db=new DATABASE();
    FileReader()
    {
        JFileChooser j = new JFileChooser();
        int r = j.showSaveDialog(null);
        String file_name=j.getSelectedFile().getAbsolutePath();
        File file=new File(file_name);
        DB_Update();
    }
    private void DB_Update()
    {
        String name = null;
        String email=null;
        String password=null;
        int i=1;
    try
    {
        Scanner scan=new Scanner(file);
        while(scan.hasNextLine())
        {
        filecontent=filecontent.concat(scan.nextLine() + "\n");
        }
        tokenizer= new StringTokenizer(filecontent , "///");
        while(tokenizer.hasMoreTokens())
        {
             if(i==1 && tokenizer.hasMoreTokens())
             {
             name=tokenizer.nextToken().trim();
             i++;
             }
             if(i==2 && tokenizer.hasMoreTokens())
             {
             email=tokenizer.nextToken().trim();
             i++;
             }
             if(i==3 && tokenizer.hasMoreTokens())
             {
                password=tokenizer.nextToken().trim();
                i=1;
             }
             System.out.println(name+email+password);
             my_db.Insert(email, password,"student");
        }
        System.out.println("Done Insertion!");
 
    }
    catch(FileNotFoundException e)
    {
    System.out.println("FileNotFound!");
    }
    
    }
   
}
