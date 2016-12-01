package com.evercons.commons.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMService { 
 
	protected Log logger = LogFactory.getLog(getClass());
 
    public void sendMessage(ArrayList<String> receipients, String data)  { 
        if (receipients.size() > 0) { 
            try { 
                Sender sender = new Sender("AIzaSyAK8ISok1NWs8SLqDpeyT2Ym0wEuqqxobs"); 
 
                Message.Builder messageBuilder = new Message.Builder(); 
                messageBuilder.addData("type", "addfeed"); 
                messageBuilder.addData("data", data); 
                Message message = messageBuilder.build(); 
 
                MulticastResult multicastResult = sender.send(message, receipients, 5); 
 
                if (multicastResult.getCanonicalIds() != 0 || multicastResult.getFailure() != 0) { 
 
                    List<Result> results = multicastResult.getResults(); 
                    for (int i = 0; i < results.size(); i++) { 
                        String canonicalRegId = results.get(i).getCanonicalRegistrationId(); 
                        String error = results.get(i).getErrorCodeName(); 
                        logger.debug("sendMessage result :: " + canonicalRegId + " : " + error);
                    } 
                } 
            } catch (Exception e) {
            	e.printStackTrace();
            } 
        } 
    } 
}