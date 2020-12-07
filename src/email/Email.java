package email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import prBanking.Login;
import prBanking.Main;

public class Email {
	public static void sendMail(String recepient, int number) throws Exception {
		System.out.println("Preparing to send email");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		String myAccountEmail = "amzarobert05012000@gmail.com";
		String myPassowrd = "laurentiu02";

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, myPassowrd);
			}
		});
		session.setDebug(true);
		switch (number) {
		case 1:
			Message newAccountMessage = newAccountEmail(session, myAccountEmail, recepient);
			Transport.send(newAccountMessage);
			System.out.println("Message sent");
			break;
		case 2:
			Message forgotPinMessage = forgotPinEmail(session, myAccountEmail, recepient);
			Transport.send(forgotPinMessage);
			System.out.println("Message sent");
			break;
		case 3:
			Message newPinMessage = newPinEmail(session, myAccountEmail, recepient);
			Transport.send(newPinMessage);
			System.out.println("Message sent");
		case 4:
			Message pinChangedMessage = pinChangedEmail(session, myAccountEmail, recepient);
			Transport.send(pinChangedMessage);
			System.out.println("Message sent");
			break;
		default:
			break;
		}

	}

	private static Message newAccountEmail(Session session, String myAccountEmail, String recepient) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("AmzBank");
			message.setText("Welcome to AmzBank, " + Login.firstNameIn.getText() + " " + Login.lastNameIn.getText()
					+ "!\n\nYour login data are here:\nCard number: " + Login.cardNumber + " \nPIN: " + Login.pin
					+ "  \n\n\nWe strongly recommand to change your PIN!\n\n\nThanks for using AmzBank!\nHave a nice day!!");
			return message;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Message forgotPinEmail(Session session, String myAccountEmail, String recepient) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("AmzBank");
			message.setText("Hello " + Login.forgotPassUser
					+ "\n\nWe are sorry to hear that you forgot your PIN.\nWe will generate a new one for you but first you have to introduce the following code in the specified field.\nCode: "
					+ Login.code
					+ "\nAfter that you will recieve another email with the new PIN.\n\nThanks for using AmzBank!\nHave a nice day!");
			return message;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Message newPinEmail(Session session, String myAccountEmail, String recepient) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("AmzBank");
			message.setText("Hello " + Login.forgotPassUser + "!\n\nWe have generated you a new PIN: " + Login.newPin
					+ "\nPlease change it to one you will remember!\n\nThanks for using AmzBank!\nHave a nice day!");
			return message;

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private static Message pinChangedEmail(Session session, String myAccountEmail, String recepient) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("AmzBank");
			message.setText("Hello, " + Login.clientName + "!\n\nYou have changed your PIN to: "
					+ Main.newPinIn.getText()
					+ "\nIf this wasn't you please change your email password and card pin as fast as you can!\n\nThanks for using AmzBank!\nHave a nice day!");
			return message;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
