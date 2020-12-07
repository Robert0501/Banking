package prBanking;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import Client.Card;
import database.Database;
import thread.NewAccountEmailThread;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Random;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import javax.swing.SwingConstants;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public static JPanel loginPanel;
	public static JPanel registerPanel;
	public static JPanel forgotPasswordPanel;
	private static JPanel insertCodePanel;

	public static JTextField firstNameIn;
	public static JTextField lastNameIn;
	public static JTextField emailIn;
	public static JTextField countryIn;
	public static JTextField cityIn;
	public static JTextField AddressIn;
	public static JTextField forgotEmailIn;
	public static JTextField codeIn;
	public static JTextField loginEmailIn;

	public static JPasswordField pinIn;

	private JLabel registerBackground;
	private JLabel registerWelcomeLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel registerEmailLabel;
	private JLabel countryLabel;
	private JLabel cityLabel;
	private JLabel AddressLabel;
	private JLabel recoverPinLable;
	private JLabel loginLabel;
	private JLabel forgotPasswordLabel;
	private JLabel pinLabel;
	private JLabel emailLabel;
	private JLabel welcomeLabel;
	private JLabel loginBackground;
	private JLabel forgotPasswordBackground;
	private JLabel forgotPassWelcomeLabel;
	private JLabel forgotEmailLabel;
	private JLabel insertCodeWelcomeLabel;
	private JLabel codeLabel;
	private JLabel insertCodeLabel;
	private JLabel insertCodeBackground;

	private JButton registerButton;
	private JButton backButton;
	private JButton createAccountButton;
	private JButton loginButton;
	private JButton recoverButton;
	private JButton forgotPassBackButton;
	private JButton newPinButton;
	private JButton codeBackButton;

	public static String forgotPassUser;
	public static String forgotPinEmail;
	public static String cardNumber = String.valueOf(Card.genereazaNrCard());
	public static String clientName;
	public static String clientCardNumber;
	public static String clientEmail;
	public static double clientAmount;

	public static int pin = Card.generatePin();
	public static int newPin = Card.generatePin();
	public static int index;
	public static int code = generateCode();

	public static int sendNewAccountEmailThreadCounter = 0;
	public static int sendCodePinEmailThreadCounter = 0;
	public static int sendNewPinEmailThreadCounter = 0;
	public static int pinChangedThreadCounter = 0;
	public static NewAccountEmailThread sendNewAccountEmailThread[] = new NewAccountEmailThread[10];
	public static NewAccountEmailThread sendCodePinEmailThread[] = new NewAccountEmailThread[10];
	public static NewAccountEmailThread sendNewPinEmailThread[] = new NewAccountEmailThread[10];
	public static NewAccountEmailThread pinChangedThread[] = new NewAccountEmailThread[10];

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Login frame = new Login();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public Login() throws Exception {
		Database.connectDB();
		Database.createClientTable();
		Database.createTranzactiiTable();

		for (int i = 0; i < 10; i++) {
			sendNewAccountEmailThread[i] = new NewAccountEmailThread();
			sendCodePinEmailThread[i] = new NewAccountEmailThread();
			sendNewPinEmailThread[i] = new NewAccountEmailThread();
			pinChangedThread[i] = new NewAccountEmailThread();
		}

		contentPane();
		loginPanel();

		forgotPasswordPanel();
		forgotPinLabel();
		forgotEmailLabel();
		forgotPassWelcomeLabel();
		forgotEmailIn();
		recoverButton();
		forgotPassBackButton();
		forgotPasswordBackground();
		insertCodePanel();
		insertCodeWelcomeLabel();
		insertCodeLabel();
		codeLabel();
		newPinButton();
		codeBackButton();
		insertCodeBackground();

		registerPanel.setVisible(false);
		forgotPasswordPanel.setVisible(false);
		insertCodePanel.setVisible(false);

	}

	private void contentPane() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLocationByPlatform(true);
		setResizable(false);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Images/Icons/bankIcon.png")));
		setTitle("AmzBANK");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 150, 896, 584);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loginPanel() {
	}

	private void forgotPasswordPanel() {
	}

	private void forgotPinLabel() {
	}

	private void forgotEmailLabel() {
	}

	private void forgotPassWelcomeLabel() {
	}

	private void forgotEmailIn() {
	}

	private void recoverButton() {
	}

	private void forgotPassBackButton() {
	}

	private void forgotPasswordBackground() {
	}

	private void insertCodePanel() {
		loginPanel = new JPanel();
		loginPanel.setBounds(0, 0, 894, 559);
		contentPane.add(loginPanel);
		loginPanel.setLayout(null);
		loginButton = new JButton("Login");
		loginButton.setBackground(Color.WHITE);
		loginButton.setBounds(590, 420, 85, 23);
		loginPanel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (Database.checkForLoginIn(loginEmailIn.getText(), Integer.parseInt(pinIn.getText()))) {
						JOptionPane.showMessageDialog(loginPanel, "Login Succesfully");
						Database.returnClientName(loginEmailIn.getText());
						setVisible(false);
						new Main();

					} else {
						JOptionPane.showMessageDialog(loginPanel, "Check again you login data");
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(loginPanel, "Be carefull what you insert as login data");
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});
		
				loginLabel = new JLabel("Sign In");
				loginLabel.setForeground(Color.WHITE);
				loginLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
				loginLabel.setBounds(628, 244, 132, 29);
				loginPanel.add(loginLabel);
				forgotPasswordLabel = new JLabel("Forgot PIN");
				forgotPasswordLabel.setForeground(Color.WHITE);
				forgotPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
				forgotPasswordLabel.setBounds(654, 452, 106, 17);
				loginPanel.add(forgotPasswordLabel);
				forgotPasswordLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setForgotPasswordPanelVisible();
					}
				});
				registerButton = new JButton("Register");
				registerButton.setBorderPainted(false);
				registerButton.setForeground(Color.BLACK);
				registerButton.setBackground(Color.WHITE);
				registerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setRegisterPanelVisible();
					}
				});
				registerButton.setBounds(700, 420, 85, 23);
				loginPanel.add(registerButton);
				emailLabel = new JLabel("Email");
				emailLabel.setForeground(Color.WHITE);
				emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
				emailLabel.setBounds(495, 310, 74, 25);
				loginPanel.add(emailLabel);
				pinLabel = new JLabel("PIN");
				pinLabel.setForeground(Color.WHITE);
				pinLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
				pinLabel.setBackground(Color.WHITE);
				pinLabel.setBounds(500, 370, 69, 25);
				loginPanel.add(pinLabel);
				
						pinIn = new JPasswordField();
						pinIn.setColumns(10);
						pinIn.setBounds(564, 370, 240, 25);
						loginPanel.add(pinIn);
						
								pinIn.addKeyListener(new KeyAdapter() {
									@SuppressWarnings("deprecation")
									public void keyPressed(KeyEvent e) {
										if (e.getKeyCode() == KeyEvent.VK_ENTER) {
											try {
												if (Database.checkForLoginIn(loginEmailIn.getText(), Integer.parseInt(pinIn.getText()))) {
													JOptionPane.showMessageDialog(loginPanel, "Login Succesfully");
													Database.returnClientName(loginEmailIn.getText());
													setVisible(false);
													new Main();
						
												} else {
													JOptionPane.showMessageDialog(loginPanel, "Check again you login data");
												}
											} catch (NumberFormatException exp) {
												JOptionPane.showMessageDialog(loginPanel, "Be carefull what you insert as login data");
											} catch (SQLException exp) {
												exp.printStackTrace();
											}
										}
									}
								});
								
										welcomeLabel = new JLabel("Welcome to AmzBANK");
										welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
										welcomeLabel.setForeground(Color.WHITE);
										welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
										welcomeLabel.setBounds(10, 34, 874, 40);
										loginPanel.add(welcomeLabel);
										
												loginEmailIn = new JTextField();
												loginEmailIn.setColumns(10);
												loginEmailIn.setBounds(564, 310, 240, 25);
												loginPanel.add(loginEmailIn);
												loginBackground = new JLabel("");
												loginBackground.setIcon(new ImageIcon(Login.class.getResource("/Images/Backgrounds/bank_background.jpg")));
												loginBackground.setBounds(0, 0, 895, 559);
												loginPanel.add(loginBackground);
												registerPanel = new JPanel();
												registerPanel.setLayout(null);
												registerPanel.setBounds(0, 0, 895, 559);
												contentPane.add(registerPanel);
												backButton = new JButton("Back");
												backButton.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														setLoginPanelVisible();
													}
												});
												
														createAccountButton = new JButton("Create Account");
														createAccountButton.setBounds(290, 422, 130, 30);
														registerPanel.add(createAccountButton);
														createAccountButton.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent arg0) {
																if (firstNameIn.getText().equals("") || lastNameIn.getText().equals("") || emailIn.getText().equals("")
																		|| countryIn.getText().equals("") || cityIn.getText().equals("")
																		|| AddressIn.getText().equals(""))
																	JOptionPane.showMessageDialog(registerPanel,
																			"Please fill all the fields in order to create account");
																else {
																	try {
																		index = 1;
																		pin = Card.generatePin();
																		cardNumber = String.valueOf(Card.genereazaNrCard());
																		Database.checkForEmail(emailIn.getText());
																		firstNameIn.setText("");
																		lastNameIn.setText("");
																		emailIn.setText("");
																		countryIn.setText("");
																		cityIn.setText("");
																		AddressIn.setText("");
																	} catch (SQLException e) {
																		e.printStackTrace();
																	}
																}
															}
														});
														
																backButton.setBounds(150, 422, 130, 30);
																registerPanel.add(backButton);
																registerWelcomeLabel = new JLabel("Register Form");
																registerWelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
																registerWelcomeLabel.setForeground(SystemColor.activeCaptionText);
																registerWelcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
																registerWelcomeLabel.setBounds(10, 10, 875, 45);
																registerPanel.add(registerWelcomeLabel);
																
																		firstNameLabel = new JLabel("First Name");
																		firstNameLabel.setEnabled(false);
																		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																		firstNameLabel.setBounds(50, 160, 120, 23);
																		registerPanel.add(firstNameLabel);
																		
																				lastNameLabel = new JLabel("Last Name");
																				lastNameLabel.setEnabled(false);
																				lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																				lastNameLabel.setBounds(50, 200, 120, 23);
																				registerPanel.add(lastNameLabel);
																				
																						registerEmailLabel = new JLabel("Email");
																						registerEmailLabel.setEnabled(false);
																						registerEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																						registerEmailLabel.setBounds(50, 240, 102, 23);
																						registerPanel.add(registerEmailLabel);
																						
																								countryLabel = new JLabel("Country");
																								countryLabel.setEnabled(false);
																								countryLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																								countryLabel.setBounds(50, 280, 102, 23);
																								registerPanel.add(countryLabel);
																								
																										cityLabel = new JLabel("City");
																										cityLabel.setEnabled(false);
																										cityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																										cityLabel.setBounds(50, 320, 102, 23);
																										registerPanel.add(cityLabel);
																										
																												AddressLabel = new JLabel("Address");
																												AddressLabel.setEnabled(false);
																												AddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																												AddressLabel.setBounds(50, 360, 102, 23);
																												registerPanel.add(AddressLabel);
																												
																														firstNameIn = new JTextField();
																														firstNameIn.setBounds(180, 160, 240, 20);
																														registerPanel.add(firstNameIn);
																														firstNameIn.setColumns(10);
																														
																																lastNameIn = new JTextField();
																																lastNameIn.setColumns(10);
																																lastNameIn.setBounds(180, 200, 240, 20);
																																registerPanel.add(lastNameIn);
																																
																																		emailIn = new JTextField();
																																		emailIn.setColumns(10);
																																		emailIn.setBounds(180, 240, 240, 20);
																																		registerPanel.add(emailIn);
																																		
																																				countryIn = new JTextField();
																																				countryIn.setColumns(10);
																																				countryIn.setBounds(180, 280, 240, 20);
																																				registerPanel.add(countryIn);
																																				
																																						cityIn = new JTextField();
																																						cityIn.setColumns(10);
																																						cityIn.setBounds(180, 320, 240, 20);
																																						registerPanel.add(cityIn);
																																						
																																								AddressIn = new JTextField();
																																								AddressIn.setColumns(10);
																																								AddressIn.setBounds(180, 360, 240, 20);
																																								AddressIn.addKeyListener(new KeyAdapter() {
																																									public void keyPressed(KeyEvent e) {
																																										if (e.getKeyCode() == KeyEvent.VK_ENTER) {
																																											if (firstNameIn.getText().equals("") || lastNameIn.getText().equals("")
																																													|| emailIn.getText().equals("") || countryIn.getText().equals("")
																																													|| cityIn.getText().equals("") || AddressIn.getText().equals(""))
																																												JOptionPane.showMessageDialog(registerPanel,
																																														"Please fill all the fields in order to create account");
																																											else {
																																												try {
																																													index = 1;
																																													pin = Card.generatePin();
																																													cardNumber = String.valueOf(Card.genereazaNrCard());
																																													Database.checkForEmail(emailIn.getText());
																																													firstNameIn.setText("");
																																													lastNameIn.setText("");
																																													emailIn.setText("");
																																													countryIn.setText("");
																																													cityIn.setText("");
																																													AddressIn.setText("");
																																												} catch (SQLException exp) {
																																													exp.printStackTrace();
																																												}
																																											}

																																										}
																																									}
																																								});
																																								
																																										registerPanel.add(AddressIn);
																																										registerBackground = new JLabel("");
																																										registerBackground
																																												.setIcon(new ImageIcon(Login.class.getResource("/Images/Backgrounds/register_background.jpg")));
																																										registerBackground.setBounds(-11, -24, 901, 597);
																																										registerPanel.add(registerBackground);
																																										forgotPasswordPanel = new JPanel();
																																										forgotPasswordPanel.setBounds(0, 0, 895, 559);
																																										contentPane.add(forgotPasswordPanel);
																																										forgotPasswordPanel.setLayout(null);
																																										forgotPasswordPanel.setVisible(false);
																																										recoverPinLable = new JLabel("Recover PIN");
																																										recoverPinLable.setHorizontalAlignment(SwingConstants.CENTER);
																																										recoverPinLable.setFont(new Font("Tahoma", Font.BOLD, 24));
																																										recoverPinLable.setForeground(Color.WHITE);
																																										recoverPinLable.setBounds(564, 245, 240, 29);
																																										forgotPasswordPanel.add(recoverPinLable);
																																										forgotEmailLabel = new JLabel("Email");
																																										forgotEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																																										forgotEmailLabel.setForeground(Color.WHITE);
																																										forgotEmailLabel.setBounds(495, 310, 94, 25);
																																										forgotPasswordPanel.add(forgotEmailLabel);
																																										forgotPassWelcomeLabel = new JLabel("Welcome to AmzBANK");
																																										forgotPassWelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
																																										forgotPassWelcomeLabel.setForeground(Color.WHITE);
																																										forgotPassWelcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
																																										forgotPassWelcomeLabel.setBounds(10, 34, 875, 40);
																																										forgotPasswordPanel.add(forgotPassWelcomeLabel);
																																										forgotEmailIn = new JTextField();
																																										forgotEmailIn.setBounds(564, 310, 240, 25);
																																										forgotPasswordPanel.add(forgotEmailIn);
																																										forgotEmailIn.setColumns(10);
																																										forgotEmailIn.addKeyListener(new KeyAdapter() {
																																											public void keyPressed(KeyEvent exp) {
																																												if (exp.getKeyCode() == KeyEvent.VK_ENTER) {
																																													if (forgotEmailIn.getText().equals(""))
																																														JOptionPane.showMessageDialog(forgotPasswordPanel, "Please enter your email");
																																													else {
																																														try {
																																															index = 2;
																																															forgotPinEmail = forgotEmailIn.getText();
																																															code = generateCode();
																																															newPin = Card.generatePin();
																																															Database.checkForForgotPinEmail(forgotEmailIn.getText());
																																															forgotEmailIn.setText("");
																																														} catch (SQLException e) {
																																															e.printStackTrace();
																																														}
																																													}
																																												}
																																											}
																																										});
																																										recoverButton = new JButton("Recover");
																																										recoverButton.addActionListener(new ActionListener() {
																																											public void actionPerformed(ActionEvent arg0) {
																																												if (forgotEmailIn.getText().equals(""))
																																													JOptionPane.showMessageDialog(forgotPasswordPanel, "Please enter your email");
																																												else {
																																													try {
																																														index = 2;
																																														forgotPinEmail = forgotEmailIn.getText();
																																														code = generateCode();
																																														newPin = Card.generatePin();
																																														Database.checkForForgotPinEmail(forgotEmailIn.getText());
																																														forgotEmailIn.setText("");
																																													} catch (SQLException e) {
																																														e.printStackTrace();
																																													}
																																												}
																																											}
																																										});
																																										recoverButton.setBounds(715, 370, 89, 23);
																																										forgotPasswordPanel.add(recoverButton);
																																										forgotPassBackButton = new JButton("Back");
																																										forgotPassBackButton.addActionListener(new ActionListener() {
																																											public void actionPerformed(ActionEvent arg0) {
																																												setLoginPanelVisible();
																																											}
																																										});
																																										forgotPassBackButton.setBounds(564, 370, 89, 23);
																																										forgotPasswordPanel.add(forgotPassBackButton);
																																										forgotPasswordBackground = new JLabel("");
																																										forgotPasswordBackground
																																												.setIcon(new ImageIcon(Login.class.getResource("/Images/Backgrounds/bank_background.jpg")));
																																										forgotPasswordBackground.setBounds(0, 0, 895, 559);
																																										forgotPasswordPanel.add(forgotPasswordBackground);
																																										insertCodePanel = new JPanel();
																																										insertCodePanel.setBounds(0, 0, 895, 559);
																																										contentPane.add(insertCodePanel);
																																										insertCodePanel.setLayout(null);
																																										insertCodeWelcomeLabel = new JLabel("Welcome to AmzBank");
																																										insertCodeWelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
																																										insertCodeWelcomeLabel.setForeground(Color.WHITE);
																																										insertCodeWelcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
																																										insertCodeWelcomeLabel.setBounds(10, 34, 875, 40);
																																										insertCodePanel.add(insertCodeWelcomeLabel);
																																										insertCodeLabel = new JLabel("Insert Code");
																																										insertCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
																																										insertCodeLabel.setForeground(Color.WHITE);
																																										insertCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
																																										insertCodeLabel.setBounds(564, 245, 240, 29);
																																										insertCodePanel.add(insertCodeLabel);
																																										codeLabel = new JLabel("Code");
																																										codeLabel.setForeground(Color.WHITE);
																																										codeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
																																										codeLabel.setBounds(495, 310, 79, 25);
																																										insertCodePanel.add(codeLabel);
																																										
																																												codeIn = new JTextField();
																																												codeIn.setBounds(564, 310, 240, 25);
																																												insertCodePanel.add(codeIn);
																																												codeIn.setColumns(10);
																																												codeIn.addKeyListener(new KeyAdapter() {
																																													public void keyPressed(KeyEvent exp) {
																																														if (exp.getKeyCode() == KeyEvent.VK_ENTER) {
																																															if (code != Integer.parseInt(codeIn.getText())) {
																																																JOptionPane.showMessageDialog(insertCodePanel, "Check the code again");
																																															} else {
																																																index = 3;
																																																sendNewPinEmailThread[sendNewPinEmailThreadCounter].start();
																																																sendNewPinEmailThreadCounter++;
																																																JOptionPane.showMessageDialog(insertCodePanel,
																																																		"Your PIN has been changed, check your email in order to see it");
																																																Database.updatePin(newPin);
																																																codeIn.setText("");
																																																setLoginPanelVisible();
																																															}
																																														}
																																													}
																																												});
																																												newPinButton = new JButton("Send PIN");
																																												newPinButton.addActionListener(new ActionListener() {
																																													public void actionPerformed(ActionEvent arg0) {
																																														if (code != Integer.parseInt(codeIn.getText())) {
																																															JOptionPane.showMessageDialog(insertCodePanel, "Check the code again");
																																														} else {
																																															index = 3;
																																															sendNewPinEmailThread[sendNewPinEmailThreadCounter].start();
																																															sendNewPinEmailThreadCounter++;
																																															JOptionPane.showMessageDialog(insertCodePanel,
																																																	"Your PIN has been changed, check your email in order to see it");
																																															Database.updatePin(newPin);
																																															codeIn.setText("");
																																															setLoginPanelVisible();
																																														}
																																													}
																																												});
																																												newPinButton.setBounds(715, 370, 89, 23);
																																												insertCodePanel.add(newPinButton);
																																												codeBackButton = new JButton("Back");
																																												codeBackButton.setBounds(564, 370, 89, 23);
																																												insertCodePanel.add(codeBackButton);
																																												insertCodeBackground = new JLabel("");
																																												insertCodeBackground.setIcon(new ImageIcon(Login.class.getResource("/Images/Backgrounds/bank_background.jpg")));
																																												insertCodeBackground.setBounds(0, 0, 895, 559);
																																												insertCodePanel.add(insertCodeBackground);
	}

	private void insertCodeWelcomeLabel() {
	}

	private void insertCodeLabel() {
	}

	private void codeLabel() {
	}

	private void newPinButton() {
	}

	private void codeBackButton() {
	}

	private void insertCodeBackground() {
	}

	public static void setLoginPanelVisible() {
		loginPanel.setVisible(true);
		registerPanel.setVisible(false);
		forgotPasswordPanel.setVisible(false);
		insertCodePanel.setVisible(false);
	}

	private void setRegisterPanelVisible() {
		registerPanel.setVisible(true);
		loginPanel.setVisible(false);
		forgotPasswordPanel.setVisible(false);
		insertCodePanel.setVisible(false);
	}

	private void setForgotPasswordPanelVisible() {
		forgotPasswordPanel.setVisible(true);
		loginPanel.setVisible(false);
		registerPanel.setVisible(false);
		insertCodePanel.setVisible(false);
	}

	public static void setInsertCodePanelVisible() {
		forgotPasswordPanel.setVisible(false);
		loginPanel.setVisible(false);
		registerPanel.setVisible(false);
		insertCodePanel.setVisible(true);
	}

	private static int generateCode() {
		int code = 0;
		Random rand = new Random();
		int random;
		while (Card.nrDigits(code) != 6) {
			random = rand.nextInt() % 10;
			while (random < 0)
				random = rand.nextInt();
			code = code * 10 + random;
		}

		if (code < 0)
			code = -code;
		return code;
	}
}
