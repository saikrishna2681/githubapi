package com.sailpoint.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

import javax.json.JsonObject;
import javax.mail.*;
import javax.mail.internet.*;

import org.json.JSONObject;



/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws IOException 
     */
	//String line;
	public static void main(String[] args) throws Exception {
		
		
		String[] commands = new String[] {"curl", "-X", "GET", "https://api.github.com/search/issues?q=repo:saikrishna2681/myapp+is:pr+created:>=2022-04-04"};
		Process process = Runtime.getRuntime().exec(commands);
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		String response = "";
		while((line = reader.readLine()) != null) {
			//System.out.println("Print Line : "+ line);
			response = response+line;
		}
		
		JSONObject jsonObject = new JSONObject(response);
		int count = (int) jsonObject.get("total_count");
		
		String name = "";
		
		for (int i = 0; i < count; i++) {
			name = name+jsonObject.getJSONArray("items").getJSONObject(i).getString("title")+"\n";
		}
		
		System.out.println("Tiltle Name : "+name );
		
        String to = "simple.sai220@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "simple.sai220@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("simple.sai220@gmail.com", "********");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("No of pull requests available : "+count );

            // Now set the actual message
            //System.out.println("Line ::::::::::: "+response);
            message.setText("Title of pull request : "+name);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
	}	
    
}
