/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviosya.review.domain;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/QueueReview"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ReviewMessageBean implements MessageListener {
        static Logger log =  Logger.getLogger("FILE");
    public ReviewMessageBean() {
        
    }
    
    @Override
    public void onMessage(Message message) {
           try {
               
            TextMessage txt = (TextMessage) message;
            String msg = txt.getText();
           
            log.info("Valido contra natlang");

         } catch (JMSException ex) {
            log.error("ERROR:"  + ex.getMessage());
        }
    }
    
}
