package org.ghc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class StartCrawler {

	private JFrame jFrame;
	private JTextField rootDirTextField;
	private JTextField searchTextField;
	private JTextField replaceTextField;
	private JTextField showInformationInTextFild;
	JButton findButton;
	JButton replaceButton;
	JButton resetButton;
	JComboBox filePatterComboBox;
	File backupFolder;

	static {

		try {
			// this is the logger file location
			FileOutputStream fileOutputStream = new FileOutputStream("./loggerFile.txt", true);

			PrintStream printStream = new PrintStream(fileOutputStream);
			System.setOut(printStream);

		} catch (Exception e) {
			throw new RuntimeException(
					"Config file is not loaded successfully,make sure the config file should be in src folder");
		}
	}

	public StartCrawler() {
		backupFolder = new File("./backup");
		backupFolder.mkdir();
		initialize();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartCrawler startCrawler = new StartCrawler();
					startCrawler.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jFrame = new JFrame();
		jFrame.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 15));
		jFrame.getContentPane().setForeground(UIManager.getColor("Button.foreground"));
		jFrame.setBounds(100, 100, 650, 650);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);
		jFrame.setTitle("Group Health");

		JLabel groupHealthLabel = new JLabel("GROUP HEALTH FIND AND REPLACE TOOL");
		groupHealthLabel.setFont(new Font("Californian FB", Font.BOLD, 25));
		groupHealthLabel.setForeground(SystemColor.textHighlight);
		groupHealthLabel.setBounds(36, 12, 507, 38);

		Font font = new Font("Times New Roman", Font.BOLD, 20);

		JLabel rootDirLabel = new JLabel("Root Directory");
		rootDirLabel.setFont(font);
		rootDirLabel.setBounds(14, 75, 139, 28);

		rootDirTextField = new JTextField();
		rootDirTextField.setEditable(false);
		rootDirTextField.setBounds(176, 78, 250, 28);
		rootDirTextField.setColumns(10);

		JButton broserButton = new JButton("Browse");
		broserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				f.showOpenDialog(null);
				File rootDir = f.getSelectedFile();
				if (rootDir != null && rootDir.exists() && rootDir.isDirectory() && rootDir.canWrite()) {
					try {
						rootDirTextField.setText(rootDir.getCanonicalPath());
					} catch (IOException e1) {

					}
				} else {
					JOptionPane.showMessageDialog(null,
							"choosed root file path is not valid RootDirectory or a file or  should not have write permission");
				}

			}
		});
		broserButton.setBounds(454, 78, 89, 28);

		JLabel searchLabel = new JLabel("Search Text");
		searchLabel.setFont(font);
		searchLabel.setBounds(14, 130, 119, 28);
		searchTextField = new JTextField();
		searchTextField.setColumns(15);
		searchTextField.setBounds(176, 130, 250, 28);

		JLabel replaceLabel = new JLabel("Replace Text");
		replaceLabel.setFont(font);
		replaceLabel.setBounds(14, 184, 156, 29);

		replaceTextField = new JTextField();
		replaceTextField.setColumns(15);
		replaceTextField.setBounds(176, 187, 250, 28);

		JLabel filePatternLabel = new JLabel("File Ptn Ext");
		filePatternLabel.setFont(font);
		filePatternLabel.setBounds(14, 248, 156, 29);

		filePatterComboBox = new JComboBox();
		filePatterComboBox.setFont(new Font("Vijaya", Font.PLAIN, 15));
		filePatterComboBox.setToolTipText("Select");
		filePatterComboBox.setForeground(UIManager.getColor("TextField.foreground"));
		filePatterComboBox.setModel(new DefaultComboBoxModel(new String[] { "txt", "doc", "html/jhtml", "css", "js",
				"xml", "xsl", "pdf", "html/css/js/jhtml/xsl/xml" }));
		filePatterComboBox.setSelectedIndex(0);
		filePatterComboBox.setBounds(176, 249, 250, 32);

		findButton = new JButton("Find");
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceButton.setEnabled(false);

				if (rootDirTextField.getText().isEmpty() || searchTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the searchText as well as rootDirectory");

				} else {
					String selectedItem = filePatterComboBox.getSelectedItem().toString();
					if (selectedItem.equalsIgnoreCase("pdf") || selectedItem.equalsIgnoreCase("doc")) {
						JOptionPane.showMessageDialog(null,
								"Find or replace features of pdf or doc file is not added yet, comming soon.........");
					} else {
						TextBasedFileUtility fileUtility = new TextBasedFileUtility();
						try {
							fileUtility.processAllFiles(rootDirTextField.getText().trim(),
									searchTextField.getText().trim(), null, backupFolder, selectedItem);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}
				replaceButton.setEnabled(true);

			}
		});
		findButton.setBounds(176, 323, 89, 28);

		replaceButton = new JButton("Find/Replace");
		replaceButton.setBounds(280, 323, 130, 28);
		replaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findButton.setEnabled(false);

				if (rootDirTextField.getText().isEmpty() || searchTextField.getText().isEmpty()
						|| replaceTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter searchText, replaceText and rootDirectory");
				} else {
					String selectedItem = filePatterComboBox.getSelectedItem().toString();
					if (selectedItem.equalsIgnoreCase("pdf") || selectedItem.equalsIgnoreCase("doc")) {
						JOptionPane.showMessageDialog(null,
								"Find or replace features of pdf or doc file is not added yet, comming soon.........");
					} else {

						TextBasedFileUtility fileUtility = new TextBasedFileUtility();
						try {
							fileUtility.processAllFiles(rootDirTextField.getText().trim(),
									searchTextField.getText().trim(), replaceTextField.getText().trim(), backupFolder,
									selectedItem);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}
				findButton.setEnabled(true);

			}
		});

		resetButton = new JButton("Reset");
		resetButton.setForeground(Color.BLACK);
		resetButton.setBounds(432, 322, 107, 29);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTextField.setText("");
				replaceTextField.setText("");
				rootDirTextField.setText("");

			}
		});

		showInformationInTextFild = new JTextField();
		showInformationInTextFild.setForeground(UIManager.getColor("Button.foreground"));
		showInformationInTextFild.setColumns(10);
		showInformationInTextFild.setBounds(176, 362, 367, 188);

		jFrame.getContentPane().add(groupHealthLabel);
		jFrame.getContentPane().add(rootDirLabel);
		jFrame.getContentPane().add(rootDirTextField);
		jFrame.getContentPane().add(broserButton);

		jFrame.getContentPane().add(searchLabel);
		jFrame.getContentPane().add(searchTextField);
		jFrame.getContentPane().add(replaceLabel);
		jFrame.getContentPane().add(replaceTextField);
		jFrame.getContentPane().add(filePatternLabel);
		jFrame.getContentPane().add(filePatterComboBox);
		jFrame.getContentPane().add(findButton);
		jFrame.getContentPane().add(replaceButton);
		jFrame.getContentPane().add(resetButton);
		jFrame.getContentPane().add(showInformationInTextFild);

	}

}
