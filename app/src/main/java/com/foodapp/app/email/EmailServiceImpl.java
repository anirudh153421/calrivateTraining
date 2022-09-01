package com.foodapp.app.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.FoodOrderDao;
import com.foodapp.app.dto.Admin;
import com.foodapp.app.dto.FoodOrder;
import com.foodapp.app.exception.EmailNotSentException;
import com.foodapp.app.util.AES;
import com.foodapp.app.util.ResponseStructure;

@Service
public class EmailServiceImpl {
	
	@Value("${spring.mail.username}") private String sender;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	FoodOrderDao foodOrderDao;
	@Autowired
	EmailDetails details;
	public ResponseEntity<ResponseStructure<EmailDetails>> sendSimpleMail(int id)
    {
 
        // Try block to check for exceptions
        try {
 
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                = new SimpleMailMessage();
            
            FoodOrder foodOrder=foodOrderDao.findFoodOrderById(id).get();
            long amount=foodOrder.getBillingAmount();
            long gst=(amount*18)/100;
            long total_Bill=amount+gst;
            String body="Order Details"+"\n"+"<--------------------------->"+"\n"+"Food Order ID: "+foodOrder.getId()+"\n"+"Customer Name: "+foodOrder.getCustomerName()+"\n"+"Customer Email ID: "+foodOrder.getCustomerEmail()+"\n"+"Total Items: "+foodOrder.getQuantity()+"\n"+" price: "+amount+"\n"+"Taxes: "+gst+"\n"+"Total Price: "+total_Bill;                                
            details.setRecipient(foodOrder.getCustomerEmail());
            details.setSubject("Billing Receipt");
            details.setMsgBody(body);
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
 
            // Sending the mail
            javaMailSender.send(mailMessage);
            
            
            ResponseStructure<EmailDetails> structure = new ResponseStructure<>();
            structure.setMessage("Email Sent Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(details);
            return new ResponseEntity<>(structure, HttpStatus.OK);
            
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            throw new EmailNotSentException();
        }
    }

}
