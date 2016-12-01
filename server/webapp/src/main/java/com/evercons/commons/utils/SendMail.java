package com.evercons.commons.utils;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendMail {

	private Log logger = LogFactory.getLog(getClass());

	private static SendMail mInstance_;
	private String mailId = null;
	private String pwd = null;
	private Properties mailServerConfig = new Properties();

	private SendMail() {
		InputStream iStream = null;
		try {
			iStream = getClass().getClassLoader().getResourceAsStream("evercons.properties");
			if (iStream != null) {
				Properties serverProps = new Properties();
				serverProps.load(iStream);
				mailId = serverProps.getProperty("mail.smtp.username");
				pwd = serverProps.getProperty("mail.smtp.password");
				mailServerConfig.put("mail.smtp.auth", serverProps.getProperty("mail.smtp.auth"));
				mailServerConfig.put("mail.smtp.starttls.enable", serverProps.getProperty("mail.smtp.starttls.enable"));
				mailServerConfig.put("mail.smtp.host", serverProps.getProperty("mail.smtp.host"));
				mailServerConfig.put("mail.smtp.port", serverProps.getProperty("mail.smtp.port"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(iStream);
		}
	}

	public synchronized static SendMail getInstance() {
		if (mInstance_ == null)
			mInstance_ = new SendMail();

		return mInstance_;
	}

	private Session getSession() {
		return Session.getInstance(mailServerConfig, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailId, pwd);
			}
		});
	}

	public void sendMail(String[] toAddress, String subject, String messageText) {
		try {
			String toAddressStr = StringUtils.join(toAddress, ",");
			Message message = new MimeMessage(getSession());
			message.setFrom(new InternetAddress(mailId));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddressStr));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);

			logger.debug("Mail sent to " + toAddressStr + "::" + subject);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
