package prBanking;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;

import Client.Card;
import Client.Tranzactie;
import database.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmAmzbank;
	private JPanel welcomePanel;
	private JPanel cardPanel;
	private JPanel optionsPanel;
	private JPanel changePinPanel;
	private JPanel addMoneyPanel;
	private JPanel withdrawMoneyPanel;
	private JPanel previousTransactionsPanel;
	private JPanel printReceiptPanel;

	private JLabel welcomeLabel;
	private JLabel numarCardLabel;
	private JLabel numeCardLabel;
	private JLabel amountLabel;
	private JLabel numeDeCititLabel;
	private JLabel numarCardDeCititLabel;
	private JLabel sumaDeCititLabel;
	private JLabel visaSign;
	private JLabel addMoneyButton;
	private JLabel withdrawMoneyButton;
	private JLabel previousTransactionsButton;
	private JLabel printReceiptButton;
	private JLabel logoutButton;
	private JLabel changePinButton;
	private JLabel changePinLabel;
	private JLabel actualPinLabel;
	private JLabel newPinLabel;
	private JLabel retypeNewPinLabel;
	private JLabel addMoneyLabel;
	private JLabel addMoneyAmountLabel;
	private JLabel newAmountLabel;
	private JLabel withdrawMoneyLabel;
	private JLabel withdrawMoneyAmountLabel;
	private JLabel newWithdrawAmountLabel;
	private JLabel saveToLabel;
	private JLabel printReceiptLabel;
	private JLabel previousTransactionLabel;

	private JLabel label = new JLabel();

	public static String dataTranzactie;
	public static String oraTranzactie;
	public static String tipTranzactie;
	public static String sumTranzactie;

	public static JPasswordField newPinIn;
	private JPasswordField actualPinIn;
	private JPasswordField retypeNewPinIn;
	private JTextField amountIn;
	private JTextField withdrawAmountIn;
	private JTextField browseIn;

	private JButton newPinButton;
	private JButton addButton;
	private JButton withdrawButton;
	private JButton browseButton;
	private JButton printButton;

	private JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private File file = new File("");
	private FileWriter myWriter;

	public static int transactionsNumber = 0;
	public static int counter = 0;
	public static int i;

	public static JLabel transactions[];

	private String suma = "";
	private String diferenta = "";
	private double sum = 0;
	private double dif = 0;
	private boolean fail = false;

	public static LocalDate date;
	public static String time;
	DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static int lineBound = 135;
	public static Tranzactie tranzactii[];
	public static boolean tranzactiiPressed = false;

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void browse(JLabel label) {
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showSaveDialog(null);
		label.setText(fileChooser.getSelectedFile().getAbsolutePath());
	}

	private void initialize() {

		frmAmzbank = new JFrame();
		frmAmzbank.setIconImage(
				Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/Images/Icons/bankIcon.png")));
		frmAmzbank.setTitle("AmzBANK");
		frmAmzbank.setResizable(false);
		frmAmzbank.setVisible(true);
		frmAmzbank.setBounds(550, 100, 750, 890);
		frmAmzbank.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAmzbank.getContentPane().setLayout(null);
		frmAmzbank.setLocationRelativeTo(null);

		printReceiptPanel = new JPanel();
		printReceiptPanel.setBackground(Color.GRAY);
		printReceiptPanel.setBounds(305, 80, 430, 790);
		frmAmzbank.getContentPane().add(printReceiptPanel);
		printReceiptPanel.setLayout(null);

		printReceiptLabel = new JLabel("Print Receipt");
		printReceiptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		printReceiptLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		printReceiptLabel.setBounds(0, 200, 430, 35);
		printReceiptPanel.add(printReceiptLabel);

		saveToLabel = new JLabel("Save To");
		saveToLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		saveToLabel.setBounds(30, 347, 100, 20);
		printReceiptPanel.add(saveToLabel);

		browseIn = new JTextField();
		browseIn.setBounds(125, 345, 200, 25);
		printReceiptPanel.add(browseIn);
		browseIn.setColumns(10);
		previousTransactionsPanel = new JPanel();
		previousTransactionsPanel.setBackground(Color.GRAY);
		previousTransactionsPanel.setBounds(305, 80, 430, 790);
		frmAmzbank.getContentPane().add(previousTransactionsPanel);

		previousTransactionsPanel.setLayout(null);

		previousTransactionLabel = new JLabel("Previous Transactions");
		previousTransactionLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		previousTransactionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		previousTransactionLabel.setBounds(0, 50, 430, 35);
		previousTransactionsPanel.add(previousTransactionLabel);

		browseButton = new JButton("Browse");
		browseButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				browse(label);
				browseIn.setText(label.getText());
			}
		});
		browseButton.setBounds(330, 345, 90, 25);
		printReceiptPanel.add(browseButton);

		printButton = new JButton("Print");
		printButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				file = new File(browseIn.getText() + "\\receipt.txt");
				try {
					myWriter = new FileWriter(file);
					Database.countTransactionsNumber(Login.clientEmail);
					try {
						myWriter.write("Name: " + Login.clientName + " \nActual amount:  "
								+ Database.getAmount(Login.clientEmail) + "€\nBank: AmzBank" + "\n\n\n");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					for (int i = 0; i < transactions.length; i++) {
						transactions[i]
								.setText(i + 1 + ") " + tranzactii[transactions.length - i - 1].getTipTranzactie() + " "
										+ tranzactii[transactions.length - i - 1].getSumTranzactie() + ", Date: "
										+ tranzactii[transactions.length - i - 1].getDataTranzactie() + ", Hour:"
										+ tranzactii[transactions.length - i - 1].getOraTranzactie());
						myWriter.write(transactions[i].getText() + "\n\t\t\t***\n");

					}
					Desktop desktop = Desktop.getDesktop();
					desktop.open(file);
					myWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		printButton.setBounds(165, 430, 90, 25);
		printReceiptPanel.add(printButton);

		addMoneyPanel = new JPanel();
		addMoneyPanel.setBackground(Color.GRAY);
		addMoneyPanel.setBounds(305, 80, 430, 790);
		frmAmzbank.getContentPane().add(addMoneyPanel);
		addMoneyPanel.setLayout(null);

		withdrawMoneyPanel = new JPanel();
		withdrawMoneyPanel.setBackground(Color.GRAY);
		withdrawMoneyPanel.setBounds(305, 80, 430, 790);
		frmAmzbank.getContentPane().add(withdrawMoneyPanel);
		withdrawMoneyPanel.setLayout(null);

		addMoneyLabel = new JLabel("Add Money");
		addMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addMoneyLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		addMoneyLabel.setBounds(0, 200, 430, 35);
		addMoneyPanel.add(addMoneyLabel);

		withdrawMoneyLabel = new JLabel("Withdraw Money");
		withdrawMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		withdrawMoneyLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		withdrawMoneyLabel.setBounds(0, 200, 430, 35);
		withdrawMoneyPanel.add(withdrawMoneyLabel);

		addMoneyAmountLabel = new JLabel("Amount");
		addMoneyAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addMoneyAmountLabel.setBounds(35, 350, 100, 20);
		addMoneyPanel.add(addMoneyAmountLabel);

		withdrawMoneyAmountLabel = new JLabel("Amount");
		withdrawMoneyAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		withdrawMoneyAmountLabel.setBounds(35, 350, 100, 20);
		withdrawMoneyPanel.add(withdrawMoneyAmountLabel);

		amountIn = new JTextField();
		amountIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				try {
					sum = Database.getAmount(Login.clientEmail);
					if (fail) {
						amountIn.setText("");
						fail = false;
					}
					switch (e.getKeyCode()) {
					case 10:
						break;
					case 48:
						suma = suma + '0';
						break;
					case 49:
						suma = suma + '1';
						break;
					case 50:
						suma = suma + '2';
						break;
					case 51:
						suma = suma + '3';
						break;
					case 52:
						suma = suma + '4';
						break;
					case 53:
						suma = suma + '5';
						break;
					case 54:
						suma = suma + '6';
						break;
					case 55:
						suma = suma + '7';
						break;
					case 56:
						suma = suma + '8';
						break;
					case 57:
						suma = suma + '9';
						break;
					case 96:
						suma = suma + '0';
						break;
					case 97:
						suma = suma + '1';
						break;
					case 98:
						suma = suma + '2';
						break;
					case 99:
						suma = suma + '3';
						break;
					case 100:
						suma = suma + '4';
						break;
					case 101:
						suma = suma + '5';
						break;
					case 102:
						suma = suma + '6';
						break;
					case 103:
						suma = suma + '7';
						break;
					case 104:
						suma = suma + '8';
						break;
					case 105:
						suma = suma + '9';
						break;
					case 46:
						suma = suma + '.';
						break;
					default:
						suma = "";
						fail = true;
						break;
					}

					if (!suma.equals(""))
						sum = sum + Double.parseDouble(suma);

					newAmountLabel.setText("New Amount: " + String.valueOf(sum) + "€");

				} catch (SQLException e1) {
				}

				if (fail) {
					amountIn.setText("");
					fail = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				switch (e.getKeyCode()) {
				case 10:
					try {
						double sum = Database.getAmount(Login.clientEmail) + Double.parseDouble(amountIn.getText());
						Database.updateAmount(Login.clientEmail, sum);
						sumaDeCititLabel.setText(String.valueOf(sum));
						JOptionPane.showMessageDialog(null, "Your money has beed added");
						date = LocalDate.now();
						time = LocalTime.now().format(format);
						Database.insertElementsIntoTranzactii(Login.clientEmail, date, time, "Added:",
								Double.parseDouble(amountIn.getText()));
						amountIn.setText("");
						suma = "";
					} catch (SQLException exp) {
					}
					break;
				case 48:
					break;
				case 49:
					break;
				case 50:
					break;
				case 51:
					break;
				case 52:
					break;
				case 53:
					break;
				case 54:
					break;
				case 55:
					break;
				case 56:
					break;
				case 57:
					break;
				case 96:
					break;
				case 97:
					break;
				case 98:
					break;
				case 99:
					break;
				case 100:
					break;
				case 101:
					break;
				case 102:
					break;
				case 103:
					break;
				case 104:
					break;
				case 105:
					break;
				case 46:
					break;
				default:
					amountIn.setText("");
					break;
				}
			}
		});
		amountIn.setBounds(130, 345, 200, 25);
		addMoneyPanel.add(amountIn);
		amountIn.setColumns(10);

		withdrawAmountIn = new JTextField();

		withdrawAmountIn.setBounds(130, 345, 200, 25);
		withdrawAmountIn.setColumns(10);
		withdrawMoneyPanel.add(withdrawAmountIn);
		withdrawAmountIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				try {
					dif = Database.getAmount(Login.clientEmail);
					if (fail) {
						withdrawAmountIn.setText("");
						fail = false;
					}
					switch (e.getKeyCode()) {
					case 10:
						break;
					case 48:
						diferenta = diferenta + '0';
						break;
					case 49:
						diferenta = diferenta + '1';
						break;
					case 50:
						diferenta = diferenta + '2';
						break;
					case 51:
						diferenta = diferenta + '3';
						break;
					case 52:
						diferenta = diferenta + '4';
						break;
					case 53:
						diferenta = diferenta + '5';
						break;
					case 54:
						diferenta = diferenta + '6';
						break;
					case 55:
						diferenta = diferenta + '7';
						break;
					case 56:
						diferenta = diferenta + '8';
						break;
					case 57:
						diferenta = diferenta + '9';
						break;
					case 96:
						diferenta = diferenta + '0';
						break;
					case 97:
						diferenta = diferenta + '1';
						break;
					case 98:
						diferenta = diferenta + '2';
						break;
					case 99:
						diferenta = diferenta + '3';
						break;
					case 100:
						diferenta = diferenta + '4';
						break;
					case 101:
						diferenta = diferenta + '5';
						break;
					case 102:
						diferenta = diferenta + '6';
						break;
					case 103:
						diferenta = diferenta + '7';
						break;
					case 104:
						diferenta = diferenta + '8';
						break;
					case 105:
						diferenta = diferenta + '9';
						break;
					case 46:
						diferenta = diferenta + '.';
						break;
					default:
						diferenta = "";
						fail = true;
						break;
					}

					if (!diferenta.equals(""))
						dif = dif - Double.parseDouble(diferenta);

					newWithdrawAmountLabel.setText("New Amount: " + String.valueOf(dif) + "€");

				} catch (SQLException e1) {
				}

				if (fail) {
					withdrawAmountIn.setText("");
					fail = true;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

				switch (e.getKeyCode()) {
				case 10:
					try {
						double dif = Database.getAmount(Login.clientEmail)
								- Double.parseDouble(withdrawAmountIn.getText());
						Database.updateAmount(Login.clientEmail, dif);
						sumaDeCititLabel.setText(String.valueOf(dif));
						JOptionPane.showMessageDialog(null, "Your have succesfully withdrawed you money");
						date = LocalDate.now();
						time = LocalTime.now().format(format);
						Database.insertElementsIntoTranzactii(Login.clientEmail, date, time, "Withdrawed:",
								Double.parseDouble(withdrawAmountIn.getText()));
						withdrawAmountIn.setText("");
						diferenta = "";
					} catch (SQLException exp) {
					}
					break;
				case 48:
					break;
				case 49:
					break;
				case 50:
					break;
				case 51:
					break;
				case 52:
					break;
				case 53:
					break;
				case 54:
					break;
				case 55:
					break;
				case 56:
					break;
				case 57:
					break;
				case 96:
					break;
				case 97:
					break;
				case 98:
					break;
				case 99:
					break;
				case 100:
					break;
				case 101:
					break;
				case 102:
					break;
				case 103:
					break;
				case 104:
					break;
				case 105:
					break;
				case 46:
					break;
				default:
					withdrawAmountIn.setText("");
					break;
				}
			}

		});

		newAmountLabel = new JLabel("New Amount: ");
		newAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		newAmountLabel.setBounds(0, 393, 430, 20);
		addMoneyPanel.add(newAmountLabel);

		try {
			newWithdrawAmountLabel = new JLabel("New Amount: " + Database.getAmount(Login.clientEmail) + "€");
		} catch (SQLException e1) {
		}
		newWithdrawAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newWithdrawAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		newWithdrawAmountLabel.setBounds(0, 393, 430, 20);
		withdrawMoneyPanel.add(newWithdrawAmountLabel);

		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					double sum = Database.getAmount(Login.clientEmail) + Double.parseDouble(amountIn.getText());
					Database.updateAmount(Login.clientEmail, sum);
					sumaDeCititLabel.setText(String.valueOf(sum));
					JOptionPane.showMessageDialog(null, "Your money has beed added");
					date = LocalDate.now();
					time = LocalTime.now().format(format);
					Database.insertElementsIntoTranzactii(Login.clientEmail, date, time, "Added:",
							Double.parseDouble(amountIn.getText()));
					amountIn.setText("");
					suma = "";
				} catch (SQLException e) {
				}
			}
		});
		try {
			newAmountLabel.setText("New Amount: " + Database.getAmount(Login.clientEmail) + "€");
		} catch (SQLException e2) {
		}
		addButton.setBounds(172, 436, 89, 23);
		addMoneyPanel.add(addButton);

		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					double dif = Database.getAmount(Login.clientEmail) - Double.parseDouble(withdrawAmountIn.getText());
					Database.updateAmount(Login.clientEmail, dif);
					sumaDeCititLabel.setText(String.valueOf(dif));
					JOptionPane.showMessageDialog(null, "Your have succesfully withdrawed you money");
					date = LocalDate.now();
					time = LocalTime.now().format(format);
					Database.insertElementsIntoTranzactii(Login.clientEmail, date, time, "Withdrawed:",
							Double.parseDouble(withdrawAmountIn.getText()));
					withdrawAmountIn.setText("");
					diferenta = "";
				} catch (SQLException e) {
				}
			}
		});

		withdrawButton.setBounds(172, 436, 89, 23);
		withdrawMoneyPanel.add(withdrawButton);

		welcomePanel = new JPanel();
		welcomePanel.setBackground(Color.BLACK);
		welcomePanel.setBounds(0, 0, 734, 80);
		frmAmzbank.getContentPane().add(welcomePanel);
		welcomePanel.setLayout(null);

		welcomeLabel = new JLabel("Welcome " + Login.clientName + "!");
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setBackground(Color.WHITE);
		welcomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		welcomeLabel.setBounds(140, 20, 550, 30);
		welcomePanel.add(welcomeLabel);

		cardPanel = new JPanel();
		cardPanel.setBackground(Color.DARK_GRAY);
		cardPanel.setBounds(0, 80, 305, 110);
		frmAmzbank.getContentPane().add(cardPanel);
		cardPanel.setLayout(null);

		numarCardLabel = new JLabel("Card No:");
		numarCardLabel.setForeground(Color.WHITE);
		numarCardLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		numarCardLabel.setBounds(10, 35, 65, 20);
		cardPanel.add(numarCardLabel);

		numeCardLabel = new JLabel("Name:");
		numeCardLabel.setForeground(Color.WHITE);
		numeCardLabel.setBackground(Color.WHITE);
		numeCardLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		numeCardLabel.setBounds(10, 10, 65, 20);
		cardPanel.add(numeCardLabel);

		amountLabel = new JLabel("Amount: ");
		amountLabel.setForeground(Color.WHITE);
		amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		amountLabel.setBounds(10, 60, 65, 20);
		cardPanel.add(amountLabel);

		numeDeCititLabel = new JLabel("");
		numeDeCititLabel.setText(Login.clientName);
		numeDeCititLabel.setForeground(Color.WHITE);
		numeDeCititLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		numeDeCititLabel.setBounds(75, 10, 200, 20);
		cardPanel.add(numeDeCititLabel);

		numarCardDeCititLabel = new JLabel("");
		numarCardDeCititLabel.setText(Login.clientCardNumber);
		numarCardDeCititLabel.setForeground(Color.WHITE);
		numarCardDeCititLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		numarCardDeCititLabel.setBounds(75, 35, 200, 20);
		cardPanel.add(numarCardDeCititLabel);

		sumaDeCititLabel = new JLabel("");
		sumaDeCititLabel.setText(String.valueOf(Login.clientAmount) + "€");
		sumaDeCititLabel.setBackground(Color.DARK_GRAY);
		sumaDeCititLabel.setForeground(Color.WHITE);
		sumaDeCititLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sumaDeCititLabel.setBounds(75, 60, 200, 20);
		cardPanel.add(sumaDeCititLabel);

		visaSign = new JLabel("");
		visaSign.setIcon(new ImageIcon(Main.class.getResource("/Images/Images/visa-sign.jpg")));
		visaSign.setBounds(245, 66, 50, 36);
		cardPanel.add(visaSign);

		optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.BLACK);
		optionsPanel.setBounds(0, 190, 305, 680);
		frmAmzbank.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);

		addMoneyButton = new JLabel("Add Money");
		addMoneyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setAddMoneyPanelVisibleTrue();
				tranzactiiPressed = false;
				try {
					newAmountLabel.setText("New Amount: " + Database.getAmount(Login.clientEmail) + "€");
					amountIn.setText("");
					suma = "";
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		addMoneyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMoneyButton.setBackground(Color.WHITE);
		addMoneyButton.setBorder(new LineBorder(Color.WHITE, 3));
		addMoneyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addMoneyButton.setForeground(Color.WHITE);
		addMoneyButton.setHorizontalAlignment(SwingConstants.CENTER);
		addMoneyButton.setBounds(0, 0, 305, 110);
		optionsPanel.add(addMoneyButton);

		withdrawMoneyButton = new JLabel("Withdraw Money");
		withdrawMoneyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setWithdrawMoneyPanelVisibleTrue();
				tranzactiiPressed = false;
				try {
					withdrawAmountIn.setText("");
					diferenta = "";
					newWithdrawAmountLabel.setText("New Amount: " + Database.getAmount(Login.clientEmail) + "€");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		withdrawMoneyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		withdrawMoneyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		withdrawMoneyButton.setHorizontalAlignment(SwingConstants.CENTER);
		withdrawMoneyButton.setForeground(Color.WHITE);
		withdrawMoneyButton.setBorder(new LineBorder(Color.WHITE, 3));
		withdrawMoneyButton.setBounds(0, 110, 305, 110);
		optionsPanel.add(withdrawMoneyButton);

		Database.countTransactionsNumber(Login.clientEmail);
		Main.transactions = new JLabel[Main.transactionsNumber];
		Main.tranzactii = new Tranzactie[Main.transactionsNumber];
		for (i = 0; i < transactions.length; i++) {
			transactions[i] = new JLabel();
			previousTransactionsPanel.add(transactions[i]);
			transactions[i].setBounds(25, lineBound, 500, 25);
			transactions[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
			lineBound += 40;
		}

		Database.getTransactionsData(Login.clientEmail);
		for (int i = 0; i < transactions.length; i++) {
			transactions[i].setText(i + 1 + ") " + tranzactii[transactions.length - i - 1].getTipTranzactie() + " "
					+ tranzactii[transactions.length - i - 1].getSumTranzactie() + ", Date: "
					+ tranzactii[transactions.length - i - 1].getDataTranzactie() + ", Hour:"
					+ tranzactii[transactions.length - i - 1].getOraTranzactie());
		}

		previousTransactionsButton = new JLabel("Previous Transactions");
		previousTransactionsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					for (int i = 0; i < transactions.length; i++)
						previousTransactionsPanel.remove(transactions[i]);
				} catch (NullPointerException exp) {

				}
				setPreviousTransactionsVisibleTrue();
				lineBound = 135;
				Database.countTransactionsNumber(Login.clientEmail);
				Main.transactions = new JLabel[Main.transactionsNumber];
				Main.tranzactii = new Tranzactie[Main.transactionsNumber];
				for (i = 0; i < transactions.length; i++) {
					transactions[i] = new JLabel();
					previousTransactionsPanel.add(transactions[i]);
					transactions[i].setBounds(25, lineBound, 500, 25);
					transactions[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
					lineBound += 40;
				}

				Database.getTransactionsData(Login.clientEmail);
				for (int i = 0; i < transactions.length; i++) {
					transactions[i].setText(i + 1 + ") " + tranzactii[transactions.length - i - 1].getTipTranzactie()
							+ " " + tranzactii[transactions.length - i - 1].getSumTranzactie() + ", Date: "
							+ tranzactii[transactions.length - i - 1].getDataTranzactie() + ", Hour:"
							+ tranzactii[transactions.length - i - 1].getOraTranzactie());

				}
			}

		});
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		previousTransactionsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		previousTransactionsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		previousTransactionsButton.setHorizontalAlignment(SwingConstants.CENTER);
		previousTransactionsButton.setForeground(Color.WHITE);
		previousTransactionsButton.setBorder(new LineBorder(Color.WHITE, 3));
		previousTransactionsButton.setBounds(0, 220, 305, 110);
		optionsPanel.add(previousTransactionsButton);

		printReceiptButton = new JLabel("Print Receipt");
		printReceiptButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setPrintReceiptVisibleTrue();
			}
		});
		printReceiptButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		printReceiptButton.setHorizontalAlignment(SwingConstants.CENTER);
		printReceiptButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		printReceiptButton.setForeground(Color.WHITE);
		printReceiptButton.setBorder(new LineBorder(Color.WHITE, 3));
		printReceiptButton.setBounds(0, 330, 305, 110);
		optionsPanel.add(printReceiptButton);

		logoutButton = new JLabel("Logout");
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				frmAmzbank.setVisible(false);
				try {
					new Login();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		logoutButton.setForeground(Color.WHITE);
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		logoutButton.setHorizontalAlignment(SwingConstants.CENTER);
		logoutButton.setBorder(new LineBorder(Color.WHITE, 3));
		logoutButton.setBounds(0, 550, 305, 110);
		optionsPanel.add(logoutButton);

		changePinButton = new JLabel("Change PIN");
		changePinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setChangePinPanelVisibleTrue();
				tranzactiiPressed = false;
			}
		});
		changePinButton.setBorder(new LineBorder(Color.WHITE, 3));
		changePinButton.setHorizontalAlignment(SwingConstants.CENTER);
		changePinButton.setForeground(Color.WHITE);
		changePinButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		changePinButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		changePinButton.setBounds(0, 440, 305, 110);
		optionsPanel.add(changePinButton);

		changePinPanel = new JPanel();
		changePinPanel.setBackground(Color.GRAY);
		changePinPanel.setBounds(305, 80, 430, 790);
		frmAmzbank.getContentPane().add(changePinPanel);
		changePinPanel.setLayout(null);

		changePinLabel = new JLabel("Change PIN");
		changePinLabel.setHorizontalAlignment(SwingConstants.CENTER);
		changePinLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		changePinLabel.setBounds(0, 200, 430, 35);
		changePinPanel.add(changePinLabel);

		actualPinLabel = new JLabel("Actual PIN");
		actualPinLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		actualPinLabel.setBounds(50, 370, 100, 20);
		changePinPanel.add(actualPinLabel);

		newPinLabel = new JLabel("New PIN");
		newPinLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newPinLabel.setBounds(50, 420, 100, 20);
		changePinPanel.add(newPinLabel);

		retypeNewPinLabel = new JLabel("Retype New PIN");
		retypeNewPinLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		retypeNewPinLabel.setBounds(50, 470, 150, 20);
		changePinPanel.add(retypeNewPinLabel);

		actualPinIn = new JPasswordField();
		actualPinIn.setBounds(205, 365, 200, 25);
		changePinPanel.add(actualPinIn);
		actualPinIn.setColumns(10);

		newPinIn = new JPasswordField();
		newPinIn.setColumns(10);
		newPinIn.setBounds(205, 415, 200, 25);
		changePinPanel.add(newPinIn);

		retypeNewPinIn = new JPasswordField();
		retypeNewPinIn.setColumns(10);
		retypeNewPinIn.setBounds(205, 465, 200, 25);
		changePinPanel.add(retypeNewPinIn);

		retypeNewPinIn.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			public void keyPressed(KeyEvent exp) {
				if (exp.getKeyCode() == KeyEvent.VK_ENTER)
					try {
						if (Database.checkForLoginIn(Login.clientEmail, Integer.valueOf(actualPinIn.getText()))
								&& newPinIn.getText().equals(retypeNewPinIn.getText())
								&& Card.nrDigits(Integer.parseInt(newPinIn.getText())) == 4
								&& Card.nrDigits(Integer.parseInt(retypeNewPinIn.getText())) == 4) {
							Database.changePin(Login.clientEmail, Integer.parseInt(newPinIn.getText()));
							JOptionPane.showMessageDialog(null, "Pin Changed Succesfully");
							changePinPanel.setVisible(false);
							Login.index = 4;
							Login.pinChangedThread[Login.pinChangedThreadCounter].start();
							Login.pinChangedThreadCounter++;
							actualPinIn.setText("");
							newPinIn.setText("");
							retypeNewPinIn.setText("");
							setPreviousTransactionsVisibleTrue();
						} else if (!Database.checkForLoginIn(Login.clientEmail,
								Integer.valueOf(actualPinIn.getText()))) {
							JOptionPane.showMessageDialog(null, "This is not your actually PIN");
						} else if (Card.nrDigits(Integer.parseInt(newPinIn.getText())) == 4
								&& Card.nrDigits(Integer.parseInt(retypeNewPinIn.getText())) == 4) {
							JOptionPane.showMessageDialog(null,
									"Retype New Pin and New Pin must have the same PIN number");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "PIN must be a number of 4 digits");
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}

		});

		newPinButton = new JButton("Change PIN");
		newPinButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (Database.checkForLoginIn(Login.clientEmail, Integer.valueOf(actualPinIn.getText()))
							&& newPinIn.getText().equals(retypeNewPinIn.getText())
							&& Card.nrDigits(Integer.parseInt(newPinIn.getText())) == 4
							&& Card.nrDigits(Integer.parseInt(retypeNewPinIn.getText())) == 4) {
						Database.changePin(Login.clientEmail, Integer.parseInt(newPinIn.getText()));
						JOptionPane.showMessageDialog(null, "Pin Changed Succesfully");
						changePinPanel.setVisible(false);
						Login.index = 4;
						Login.pinChangedThread[Login.pinChangedThreadCounter].start();
						Login.pinChangedThreadCounter++;
						actualPinIn.setText("");
						newPinIn.setText("");
						retypeNewPinIn.setText("");
						setPreviousTransactionsVisibleTrue();
					} else if (!Database.checkForLoginIn(Login.clientEmail, Integer.valueOf(actualPinIn.getText()))) {
						JOptionPane.showMessageDialog(null, "This is not your actually PIN");
					} else if (Card.nrDigits(Integer.parseInt(newPinIn.getText())) != 4
							|| Card.nrDigits(Integer.parseInt(retypeNewPinIn.getText())) != 4) {
						JOptionPane.showMessageDialog(null, "Retype New Pin and New Pin must have 4 digits");
					} else if (!newPinIn.getText().equals(retypeNewPinIn.getText())) {
						JOptionPane.showMessageDialog(null, "Retype New Pin and New Pin must be the same");
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "PIN must be a number of 4 digits");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		});
		newPinButton.setBounds(150, 530, 120, 25);
		changePinPanel.add(newPinButton);

		changePinPanel.setVisible(false);
		addMoneyPanel.setVisible(false);
		withdrawMoneyPanel.setVisible(false);
		printReceiptPanel.setVisible(false);
	}

	public void setChangePinPanelVisibleTrue() {
		changePinPanel.setVisible(true);
		addMoneyPanel.setVisible(false);
		withdrawMoneyPanel.setVisible(false);
		previousTransactionsPanel.setVisible(false);
		printReceiptPanel.setVisible(false);
	}

	public void setAddMoneyPanelVisibleTrue() {
		changePinPanel.setVisible(false);
		addMoneyPanel.setVisible(true);
		withdrawMoneyPanel.setVisible(false);
		previousTransactionsPanel.setVisible(false);
		printReceiptPanel.setVisible(false);
	}

	public void setWithdrawMoneyPanelVisibleTrue() {
		changePinPanel.setVisible(false);
		addMoneyPanel.setVisible(false);
		withdrawMoneyPanel.setVisible(true);
		previousTransactionsPanel.setVisible(false);
		printReceiptPanel.setVisible(false);
	}

	public void setPreviousTransactionsVisibleTrue() {
		changePinPanel.setVisible(false);
		addMoneyPanel.setVisible(false);
		withdrawMoneyPanel.setVisible(false);
		previousTransactionsPanel.setVisible(true);
		printReceiptPanel.setVisible(false);
	}

	public void setPrintReceiptVisibleTrue() {
		changePinPanel.setVisible(false);
		addMoneyPanel.setVisible(false);
		withdrawMoneyPanel.setVisible(false);
		previousTransactionsPanel.setVisible(false);
		printReceiptPanel.setVisible(true);
	}
}
