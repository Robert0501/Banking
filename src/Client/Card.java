package Client;

import java.util.Random;

public class Card {
	private String cardNumber;
	private int pin;
	private double amount;

	public Card(String cardNumber, int pin) {
		super();
		this.cardNumber = cardNumber;
		this.pin = pin;
		this.amount = 0;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	private static int nrDigits(int nr) {
		int counter = 0;
		int temp = nr;

		while (temp != 0) {
			temp /= 10;
			counter++;
		}

		return counter;
	}

	public static int nrDigits(long nr) {
		int counter = 0;
		long temp = nr;

		while (temp != 0) {
			temp /= 10;
			counter++;
		}

		return counter;
	}

	public static int generatePin() {
		int nr = 0;
		Random rand = new Random();
		int random = rand.nextInt() % 10;
		while (random == 0 || random < 0) {
			random = rand.nextInt() % 10;
			nr = random;
		}
		while (nrDigits(nr) != 4) {
			random = rand.nextInt() % 10;
			if (random < 0)
				random = -random;
			nr = 10 * nr + random;
		}
		if (checkPin(nr)) {
			return nr;
		}

		return generatePin();
	}

	private static boolean checkPin(int pin) {
		int numbers[] = { 0, 0, 0, 0 };

		for (int i = 0; i < 4; i++) {
			numbers[i] = pin % 10;
			pin /= 10;
		}

		for (int i = 0; i < 3; i++) {
			if (numbers[i] == numbers[i + 1] || numbers[i] == numbers[i + 1] + 1 || numbers[i] == numbers[i + 1] - 1
					|| numbers[i] == numbers[i + 1] + 2 || numbers[i] == numbers[i + 1] - 2) {
				return false;
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = i + 1; j < 4; j++) {
				if (numbers[i] == 0 && numbers[j] == 0) {
					return false;
				}
			}
		}

		if (numbers[0] == numbers[2] && numbers[1] == numbers[3])
			return false;

		return true;
	}

	private static boolean checkCardValability(String card) {
		boolean ok = false;
		long nr = Long.parseLong(card);
		long lastDigit = 0;
		long sumaPar = 0;
		long sumaImpar = 0;

		while (nr != 0) {
			lastDigit = nr % 10;
			sumaImpar += lastDigit;
			nr /= 100;
		}

		nr = Long.parseLong(card) / 10;

		while (nr != 0) {
			lastDigit = nr % 10;
			lastDigit *= 2;
			if (lastDigit * 2 >= 10) {
				sumaPar += (lastDigit % 10);
				lastDigit /= 10;
				sumaPar += (lastDigit % 10);
			} else {
				sumaPar += (lastDigit);
			}
			nr /= 100;
		}
		if ((sumaPar + sumaImpar) % 10 == 0) {
			ok = true;
		}
		return ok;
	}

	private static String generateNrCard() {
		Random rand = new Random();
		long number = 4;
		long nr;

		number = 4;
		while (nrDigits(number) != 16) {
			nr = rand.nextInt() % 9;
			while (nr < 0)
				nr = rand.nextInt() % 9;
			number = number * 10 + nr;
		}
		if (number < 0)
			number = -number;

		return String.valueOf(number);

	}

	public static long genereazaNrCard() {
		long nrCard = 0;
		nrCard = Long.parseLong(generateNrCard());
		while (!checkCardValability(String.valueOf(nrCard))) {
			nrCard = Long.parseLong(generateNrCard());
		}
		return nrCard;
	}

}
