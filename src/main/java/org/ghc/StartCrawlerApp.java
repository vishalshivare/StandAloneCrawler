package org.ghc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

public class StartCrawlerApp {
	private static final Logger LOGGER = Logger.getLogger(StartCrawlerApp.class);
	private JFrame jFrame;
	private JTextField rootDirTextField;
	private JButton findButton;
	private JButton replaceButton;
	private JButton resetButton;

	File backupFolder;

	public StartCrawlerApp() {
		backupFolder = new File("./backup");
		backupFolder.mkdir();
		initialize();
	}

	public static void main(String[] args) {
		LOGGER.info("Entering method main()::StartCrawler");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartCrawlerApp startCrawler = new StartCrawlerApp();
					startCrawler.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		LOGGER.info("Exiting method main()::StartCrawler");

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LOGGER.info("Entering method initialize()::StartCrawler");
		jFrame = new JFrame();
		jFrame.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 15));
		jFrame.getContentPane().setForeground(UIManager.getColor("Button.foreground"));
		jFrame.setBounds(50, 60, 580, 400);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);
		jFrame.setTitle("Group Health");

		JLabel groupHealthLabel = new JLabel("Crawler Utility TOOL");
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
					} catch (IOException exc) {
						LOGGER.error("Exception occured on browseButton click due to = " + exc.getMessage(), exc);

					}
				} else {
					LOGGER.info(
							"Browse Button is clicked, But choosed root file path is not valid rootDirectory or a file or  should not have write permission");
					JOptionPane.showMessageDialog(null,
							"choosed root file path is not valid RootDirectory or a file or  should not have write permission");
				}

			}
		});
		broserButton.setBounds(454, 78, 89, 28);

		findButton = new JButton("Find");
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceButton.setEnabled(false);
				LOGGER.info("Entering On click event of Find button's actionPerformed():: StartCrawler");

				if (rootDirTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the rootDirectory");

				} else {

					FindAndReplaceThreadPool threadPool = new FindAndReplaceThreadPool();
					threadPool.startCrawlingInRootDir(rootDirTextField.getText().trim());
				}

				replaceButton.setEnabled(true);
				LOGGER.info("Exiting On click event of Find button's actionPerformed():: StartCrawler");

			}
		});
		findButton.setBounds(176, 323, 89, 28);
		replaceButton = new JButton("Find/Replace");
		replaceButton.setBounds(280, 323, 130, 28);
		replaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Entering  Onclick event of Find/Replace button's actionPerformed():: StartCrawler");
				findButton.setEnabled(false);

				if (rootDirTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the rootDirectory");

				} else {

					FindAndReplaceThreadPool threadPool = new FindAndReplaceThreadPool();
					threadPool.startCrawlingInRootDir(rootDirTextField.getText().trim());
				}

				findButton.setEnabled(true);
				LOGGER.info("Exiting  Onclick event of Find/Replace button's actionPerformed():: StartCrawler");

			}
		});

		resetButton = new JButton("Reset");
		resetButton.setForeground(Color.BLACK);
		resetButton.setBounds(432, 322, 107, 29);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Entering  Onclick event of Reset button's actionPerformed():: StartCrawler");

				rootDirTextField.setText("");
				LOGGER.info("Exiting  Onclick event of Reset button's actionPerformed():: StartCrawler");

			}
		});

		jFrame.getContentPane().add(groupHealthLabel);
		jFrame.getContentPane().add(rootDirLabel);
		jFrame.getContentPane().add(rootDirTextField);
		jFrame.getContentPane().add(broserButton);

		jFrame.getContentPane().add(findButton);
		jFrame.getContentPane().add(replaceButton);
		jFrame.getContentPane().add(resetButton);

		LOGGER.info("Exiting method initialize()::StartCrawler");

	}

}
