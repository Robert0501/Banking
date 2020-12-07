package thread;

import email.Email;
import prBanking.Login;

public class NewAccountEmailThread extends Thread {
	public void run() {
		try {
			switch (Login.index) {
			case 1:
				Email.sendMail(Login.emailIn.getText(), Login.index);
				break;
			case 2:
				Email.sendMail(Login.forgotEmailIn.getText(), Login.index);
				break;
			case 3:
				Email.sendMail(Login.forgotPinEmail, Login.index);
				break;

			case 4:
				Email.sendMail(Login.clientEmail, Login.index);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
