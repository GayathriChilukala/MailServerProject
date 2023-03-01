import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.mail.Flags.Flag;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.PasswordAuthentication;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.util.*;
class Atbash
{
    public static String encode(String plaintext)
    {
        String ciphertext = "";
        plaintext = removeUnwantedChars(plaintext.toLowerCase());
        for(char c : plaintext.toCharArray())
        {
            if(Character.isLetter(c))
            {
                ciphertext += (char) ('a' + ('z' - c));
            }
            else
            {
                ciphertext += c;
            }
        }
        return getSubStrings(ciphertext).trim();
    }
    public static String decode(String ciphertext)
    {
        String plaintext = "";
        ciphertext = removeUnwantedChars(ciphertext.toLowerCase());
        for(char c : ciphertext.toCharArray())
        {
            if(Character.isLetter(c))
            {
                plaintext += (char) ('z' + ('a' - c));
            }
            else
            {
                plaintext += c;
            }
        }
        return plaintext;
    }
    static String getSubStrings(String input)
    {
        String out = "";
        for(int i = 0; i < input.length(); i += 5)
        {
            if(i + 5 <= input.length())
            {
                out += (input.substring(i, i + 5) + " ");
            }
            else
            {
                out += (input.substring(i) + " ");
            }
        }
        return out;
    }
     static String removeUnwantedChars(String input)
    {
        String out = "";
        for(char c : input.toCharArray())
        {
            if(Character.isLetterOrDigit(c))
            {
                out += c;
            }
        }
        return out;
    }
}
class CRC
{
    static String div(String num1,String num2)
    {
     int pointer = num2.length();
     String result = num1.substring(0, pointer);
     String remainder = "";
     for(int i = 0; i < num2.length(); i++)
     {
      if(result.charAt(i) == num2.charAt(i))
       remainder += "0";
      else
       remainder += "1";
     }
     while(pointer < num1.length())
     {
      if(remainder.charAt(0) == '0')
      {
       remainder = remainder.substring(1, remainder.length());
       remainder = remainder + String.valueOf(num1.charAt(pointer));
       pointer++;
      }
      result = remainder;
      remainder = "";
      for(int i = 0; i < num2.length(); i++)
      {
       if(result.charAt(i) == num2.charAt(i))
        remainder += "0";
       else
        remainder += "1";
      }
     }
     return remainder.substring(1,remainder.length());
    }
}
class Mailer{     
    public static void send(String from,String password,String to,String sub,String msg,String fl){     
          Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");   
          props.put("mail.smtp.ssl.enable", "true");  
          Session session = Session.getInstance(props,    
           new javax.mail.Authenticator() {    
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication(from,password);  
           }    
          });       
          try {    
           MimeMessage message = new MimeMessage(session);    
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
           message.setSubject(sub);       
           BodyPart messageBodyPart1 = new MimeBodyPart();  
           messageBodyPart1.setText(msg);      
           MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
           String filename = fl; 
           DataSource source = new FileDataSource(filename);  
           messageBodyPart2.setDataHandler(new DataHandler(source));  
           messageBodyPart2.setFileName(filename);       
           Multipart multipart = new MimeMultipart();  
           multipart.addBodyPart(messageBodyPart1);  
           if (!fl.equals("no")) {
            multipart.addBodyPart(messageBodyPart2);}
           message.setContent(multipart );  
           Transport.send(message);    
           System.out.println("message sent successfully");    
          } catch (MessagingException e) {throw new RuntimeException(e);}    
             
    }  
}  
   
public class SendMailSSL extends Authenticator {    
 public static void main(String[] args) {     
    JFrame f=new JFrame("Login");
    JLabel us1=new JLabel("Mail Id");
    JTextField mail1=new JTextField(8);
    JLabel us2=new JLabel("Password");
    JPasswordField pass = new JPasswordField(8);
    JButton login=new JButton("login");
    login.setBackground(Color.BLUE);
    login.setForeground(Color.WHITE);
    Color c = new Color(51,204,255);
    Authenticator auth=new Authenticator();
    JTextArea lblInvalid=new JTextArea("login status");
    login.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){  
            try{
            JFrame f7=new JFrame(" ");
            JButton b1=new JButton("COMPOSE MAIL");
            b1.setBackground(Color.BLUE);
            b1.setForeground(Color.WHITE);
            JButton b3=new JButton("ACCESS MAIL");
            b3.setBackground(Color.BLUE);
            b3.setForeground(Color.WHITE);
            Properties props = new Properties();
            JButton b5=new JButton("CRC");
            b5.setBackground(Color.BLUE);
            b5.setForeground(Color.WHITE);
            JButton bd=new JButton("Decrpt");
            bd.setBackground(Color.BLUE);
            bd.setForeground(Color.WHITE);
            props.load(new FileInputStream(new File("D:\\smtp.properties")));
            
            try{
                Session session = Session.getDefaultInstance(props, null);
                Store store = session.getStore("imaps");
                store.connect("imap.gmail.com", mail1.getText(), new String(pass.getPassword()));
                lblInvalid.setText("Correct information!");
                f7.add(b1);
                f7.add(b3);
                f7.add(b5);
                f7.add(bd);
                f7.setLayout(new FlowLayout());
                f7.setVisible(true);
                f7.setSize(400,400); 
                f7.getContentPane().setBackground(c);
                store.close();
                Thread.sleep(1000);
             }
             catch(javax.mail.AuthenticationFailedException k)
             {
                lblInvalid.setText("Invalid MailId or password!");
                 System.out.println("Sorry your credentials are incorrect");
             }
    b5.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){  
        try{
      Scanner myObj = new Scanner(System.in);
      JFrame f4=new JFrame("CRC Check");
      JTextField tf10=new JTextField(8);
      JLabel l10=new JLabel("Enter Generator:");
      JTextField tf3=new JTextField(8);
      JLabel l3=new JLabel("Enter Data:");
      JButton ch=new JButton("Transmit");
      ch.setBackground(Color.BLUE);
      ch.setForeground(Color.WHITE);
      f4.add(l10);
      f4.add(tf10);
      f4.add(l3);
      f4.add(tf3);
        f4.add(ch);
        f4.setLayout(new FlowLayout());
        f4.getContentPane().setBackground(c);
        f4.setVisible(true);
        f4.setSize(400,400);
       ch.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){
            try{
            String input=tf3.getText();
            String gen = tf10.getText();
            String data =input;
            String code = data;
            while(code.length() < (data.length() + gen.length() - 1))
            code = code + "0";
            code = data + CRC.div(code,gen);
            System.out.println("The transmitted Code Word is: " + code);
            try{
                int t=0;
            JFrame f14=new JFrame("check"); 
            JLabel l14=new JLabel("Enter the received Code Word");
            JTextField tf4 = new JTextField(8);
            JButton bv=new JButton("verify");
            JTextArea t14 = new JTextArea("Message Status");
            bv.setBackground(Color.BLUE);
            bv.setForeground(Color.WHITE);
            String input2=tf4.getText();
            String rec = input2;
            bv.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                    String input2=tf4.getText();
                    String rec = input2;
            if(Integer.parseInt(CRC.div(rec,gen)) == 0)
              t14.setText("The received code word contains no errors");
            else
             t14.setText("The received code word contains errors");}});
             f14.add(l14);
             f14.add(tf4);
             f14.add(bv);
             f14.add(t14);
             f14.setLayout(new FlowLayout());
             f14.getContentPane().setBackground(c);
             f14.setVisible(true);
             f14.setSize(400,400);
            }catch(Exception ll){}
        }catch(Exception j){}

         }}); 
        }
        catch(Exception r){}
    }});
       b1.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){  
        try{ 
            JFrame f2=new JFrame("Send Mail");
            JLabel l1=new JLabel("To mail");
            JTextField t1 = new JTextField(8);      
            JLabel l2=new JLabel("Subject");
            JTextField t2 = new JTextField(8);
            JLabel l3=new JLabel("message");
            JTextField t3= new JTextField(8);
            JLabel l17=new JLabel("File path(Don't want to attach type no)");
            JTextField t17= new JTextField(8);
            JButton b2=new JButton("send");
            JButton ba=new JButton("encrypt");
            ba.setBackground(Color.BLUE);
            ba.setForeground(Color.WHITE);
            b2.setBackground(Color.BLUE);
            b2.setForeground(Color.WHITE);
            JTextArea ta=new JTextArea("Message Status",10,30);
            f2.add(l1);
            f2.add(t1);
            f2.add(l2);
            f2.add(t2);
            f2.add(l3);
            f2.add(t3);
            f2.add(l17);
            f2.add(t17);
            f2.add(b2);
            f2.add(ba);
            f2.add(ta);
            f2.getContentPane().setBackground(c);
            f2.setLayout(new FlowLayout());
            f2.setVisible(true);
            f2.setSize(400,400);
            b2.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
    Mailer.send(mail1.getText(),new String(pass.getPassword()),t1.getText(),t2.getText(),t3.getText(),t17.getText()); 
    ta.setText("message sent successfully...");}});
    ba.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){
            String j=t2.getText();
            String out=Atbash.encode(j);
Mailer.send(mail1.getText(),new String(pass.getPassword()),t1.getText(),out,t3.getText(),t17.getText()); 
ta.setText("message sent successfully...");}});
}

catch (Exception n){}}

    });

bd.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){ 

        Properties props = new Properties();

        try {
            JFrame f3=new JFrame("Access Mail");
            JTextArea ta2=new JTextArea("Message Status",10,30);
            int i=1;
             // Prepare a Properties object which holds server settings such as host, port, protocol
            props.load(new FileInputStream(new File("D:\\smtp.properties")));
            //Create a session to initiate a working session with the server.
            Session session = Session.getDefaultInstance(props, null);
            StringBuffer sb=new StringBuffer();
            //Obtain a store from the session by a specific protocol 
            Store store = session.getStore("imaps");
             //Connect to the store using a credential
            store.connect("smtp.gmail.com", mail1.getText(),new String( pass.getPassword()));
            //Gets inbox folder from the store.
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            sb.append("Total Messages:- " + messageCount+"\n");
            Message[] messages = inbox.getMessages();
            String ati=messages[messageCount-i].getSubject();
            String ato=Atbash.decode(ati);
            sb.append("Mail Subject:- " +ato+"\n");
               while(i<=10)
               {
               sb.append("Mail Subject:- " + messages[messageCount-(i+1)].getSubject()+"\n");
               i++;
               }
               ta2.setText(sb.toString());
                //Close the Folder.
            inbox.close(true);
             //Close the store.
            store.close();
            f3.add(ta2);
            f3.setLayout(new FlowLayout());
            f3.getContentPane().setBackground(c);
            f3.setVisible(true);
            f3.setSize(400,400);
 
        } catch (Exception y) {
            y.printStackTrace();
        }
    }
});
b3.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){ 

            Properties props = new Properties();
 
            try {
                JFrame f3=new JFrame("Access Mail");
                JTextArea ta2=new JTextArea("Message Status",10,30);
                int i=1;
                props.load(new FileInputStream(new File("D:\\smtp.properties")));
                Session session = Session.getDefaultInstance(props, null);
                StringBuffer sb=new StringBuffer();
                Store store = session.getStore("imaps");
                store.connect("smtp.gmail.com", mail1.getText(),new String( pass.getPassword()));
                Folder inbox = store.getFolder("inbox");
                inbox.open(Folder.READ_ONLY);
                int messageCount = inbox.getMessageCount();
                sb.append("Total Messages:- " + messageCount+"\n");
                Message[] messages = inbox.getMessages();
                   while(i<=10)
                   {
                   sb.append("Mail Subject:- " + messages[messageCount-i].getSubject()+"\n");
                   i++;
                   }
                   ta2.setText(sb.toString());
                    //Close the Folder.
                inbox.close(true);
                 //Close the store.
                store.close();
                f3.add(ta2);
                f3.setLayout(new FlowLayout());
                f3.getContentPane().setBackground(c);
                f3.setVisible(true);
                f3.setSize(400,400);
     
            } catch (Exception y) {
                y.printStackTrace();
            }
        }
    });
           }catch(Exception t){}       }});
    f.add(us1);
    f.add(mail1);
    f.add(us2);
    f.add(pass);
    f.add(login);
    f.add(lblInvalid);
    f.setLayout(new FlowLayout());
    f.setVisible(true);
    f.setSize(400,400); 
    f.getContentPane().setBackground(c);
       
 }    
}


