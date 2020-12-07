package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import Client.Tranzactie;
import prBanking.Login;
import prBanking.Main;

public class Database {
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;
	static String CreateSql = null;

	public static void connectDB() {

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/amzbank", "postgres",
					"Laurentiu02");

			if (connection == null) {
				JOptionPane.showMessageDialog(Login.loginPanel, "Something went wrong.Please try again");
				System.exit(0);
			}
		} catch (Exception e) {
		}
	}

	public static void createClientTable() {
		try {
			statement = connection.createStatement();
			CreateSql = "Create Table Clients(FirstName varchar , LastName varchar, Email varchar , Country varchar, City varchar, Address varchar, CardNumber varchar, Pin int, Amount real)";
			statement.executeUpdate(CreateSql);
		} catch (PSQLException e) {
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void createTranzactiiTable() {
		try {
			statement = connection.createStatement();
			CreateSql = "Create Table Tranzactii(emailClient varchar , data date , ora time, tiptranzactie varchar, amount real);";
			statement.executeUpdate(CreateSql);
		} catch (PSQLException e) {
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private static void insertElementsIntoDB(String firstName, String lastName, String email, String country,
			String city, String Address, String cardNumber, int pin) {
		try {
			String querry = "insert into Clients values('" + firstName + "','" + lastName + "','" + email + "','"
					+ country + "','" + city + "','" + Address + "','" + cardNumber + "', " + pin + ",0.0);";
			statement = connection.createStatement();
			statement.executeUpdate(querry);
			JOptionPane.showMessageDialog(Login.registerPanel,
					"Account created successfully. Please check your email for login data");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Login.registerPanel, "An error has occured , please try again");
		}
	}

	public static void insertElementsIntoTranzactii(String email, LocalDate date, String time, String tipTranzactie,
			double amount) {

		try {
			String querry = "INSERT INTO Tranzactii values('" + email + "', '" + date + "', '" + time + "','"
					+ tipTranzactie + "'," + amount + ");";
			statement = connection.createStatement();
			statement.executeUpdate(querry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void countTransactionsNumber(String email) {
		try {
			Main.transactionsNumber = 0;
			String querry = "SELECT * FROM Tranzactii WHERE emailclient = '" + email + "';";
			statement = connection.createStatement();
			rs = statement.executeQuery(querry);
			while (rs.next()) {
				Main.transactionsNumber++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getTransactionsData(String email) {
		try {
			String querry = "SELECT * FROM Tranzactii WHERE emailClient = '" + email + "';";
			statement = connection.createStatement();
			rs = statement.executeQuery(querry);
			Main.i = 0;
			while (rs.next()) {
				Main.dataTranzactie = rs.getString("data");
				Main.oraTranzactie = rs.getString("ora");
				Main.tipTranzactie = rs.getString("tiptranzactie");
				Main.sumTranzactie = rs.getString("amount");
				Main.tranzactii[Main.i] = new Tranzactie(Main.dataTranzactie, Main.oraTranzactie, Main.tipTranzactie,
						Main.sumTranzactie);
				Main.i++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void returnForgotPassName(String email) {
		try {
			String querry = "SELECT FirstName, LastName FROM clients WHERE email = '" + email + "';";
			statement = connection.createStatement();
			rs = statement.executeQuery(querry);
			while (rs.next()) {
				Login.forgotPassUser = (rs.getString("FirstName") + ", " + rs.getString("LastName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Login.sendNewAccountEmailThread[Login.sendNewAccountEmailThreadCounter].start();
		Login.sendNewAccountEmailThreadCounter++;
	}

	public static void updatePin(int pin) {
		try {
			String querry = "update clients set pin = " + pin + "where email = '" + Login.forgotPinEmail + "';";
			statement = connection.createStatement();
			rs = statement.executeQuery(querry);
		} catch (PSQLException e) {

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void returnClientName(String email) throws SQLException {

		String querry = "SELECT FirstName, LastName, CardNumber, Email, Amount FROM clients WHERE email = '" + email
				+ "';";
		statement = connection.createStatement();
		rs = statement.executeQuery(querry);
		while (rs.next()) {
			Login.clientName = (rs.getString("FirstName") + " " + rs.getString("LastName"));
			Login.clientCardNumber = rs.getString("CardNumber");
			Login.clientAmount = rs.getDouble("Amount");
			Login.clientEmail = rs.getString("email");
		}
	}

	public static boolean checkForLoginIn(String email, int pin) throws SQLException {
		String querry = "SELECT * FROM Clients WHERE email = '" + email + "' AND pin = " + pin + ";";
		statement = connection.createStatement();
		rs = statement.executeQuery(querry);
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public static void checkForForgotPinEmail(String email) throws SQLException {
		String querry = "SELECT * FROM Clients WHERE email = '" + email + "';";
		statement = connection.createStatement();
		rs = statement.executeQuery(querry);
		if (rs.next()) {
			returnForgotPassName(Login.forgotEmailIn.getText());
			JOptionPane.showMessageDialog(Login.forgotPasswordPanel, "Please check your email");
			Login.setInsertCodePanelVisible();
		} else {
			JOptionPane.showMessageDialog(Login.forgotPasswordPanel, "We couldn't find your email");
		}
	}

	public static void changePin(String email, int pin) {
		String querry = "UPDATE Clients SET pin = " + pin + "WHERE Email ='" + email + "';";
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(querry);
		} catch (SQLException e) {
		}

	}

	public static void checkForEmail(String email) throws SQLException {
		String querry = "SELECT * FROM Clients WHERE email = '" + email + "';";
		statement = connection.createStatement();
		rs = statement.executeQuery(querry);
		if (rs.next()) {
			JOptionPane.showMessageDialog(Login.registerPanel, "There is already an account with this email");
		} else {
			Login.sendCodePinEmailThread[Login.sendCodePinEmailThreadCounter].start();
			Login.sendCodePinEmailThreadCounter++;
			insertElementsIntoDB(Login.firstNameIn.getText(), Login.lastNameIn.getText(), Login.emailIn.getText(),
					Login.countryIn.getText(), Login.cityIn.getText(), Login.AddressIn.getText(), Login.cardNumber,
					Login.pin);

			Login.setLoginPanelVisible();
		}
	}

	public static double getAmount(String email) throws SQLException {
		double sum = 0;
		String querry = "SELECT Amount FROM Clients WHERE email = '" + email + "';";
		statement = connection.createStatement();
		rs = statement.executeQuery(querry);
		if (rs.next()) {
			sum = rs.getDouble("Amount");
		}
		return sum;
	}

	public static void updateAmount(String email, double sum) {
		String querry = "update clients set amount = " + sum + "where email = '" + email + "';";
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(querry);
		} catch (SQLException e) {
		}

	}

}
