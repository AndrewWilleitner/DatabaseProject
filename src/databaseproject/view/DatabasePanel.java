package databaseproject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import databaseproject.controller.DatabaseAppController;
import databaseproject.controller.DatabaseController;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabasePanel extends JPanel
{

	private DatabaseAppController baseController;
	private SpringLayout baseLayout;
	private JButton queryButton;
	private JScrollPane displayPane;
	private JTextArea displayArea;
	private JTable tableData;
	private JPasswordField samplePassword;
	
	/**
	 * This sets up the GUI window.
	 * @param baseController
	 */
	public DatabasePanel(DatabaseAppController baseController)
	{
		
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		queryButton = new JButton("Click here to test query");
		displayArea = new JTextArea(10,30);
		samplePassword = new JPasswordField(null, 20);
		
		displayPane = new JScrollPane(displayArea);
		
		setupDisplayPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	/**
	 * This sets up the Display Pane
	 */
	private void setupDisplayPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
		displayArea.setBackground(Color.PINK);
	}
	
	/**
	 * This sets up a table
	 */
	private void setupTable()
	{
		tableData = new JTable(new DefaultTableModel(baseController.getDataController().realResults(), baseController.getDataController().getMetaDataTitles()));
		displayPane = new JScrollPane(tableData);
	}
	
	/**
	 * this sets up the password panel
	 */
	private void setupPanel()
	{
		setBackground(Color.GREEN);
		this.setSize(1000,1000);
		this.setLayout(baseLayout);
		this.add(displayPane);
		this.add(queryButton);
		this.add(samplePassword);
		samplePassword.setEchoChar('☃');
		samplePassword.setFont(new Font("Serif", Font.BOLD, 32));
		samplePassword.setForeground(Color.CYAN);
		//☃,♧,❄,♫
	}
	
	/**
	 * this sets up the Layout.
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.EAST, queryButton, -303, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, queryButton, -6, SpringLayout.NORTH, displayPane);
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 78, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, displayPane, -254, SpringLayout.EAST, this);
	}
	
	/**
	 * This sets up the buttons in the GUI
	 */
	private void setupListeners()
	{
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String [] temp = baseController.getDataController().getMetaDataTitles();
				for(String current : temp)
				{
					displayArea.setText(displayArea.getText() + "Column: " + current +"\n");
				}
			}
		});
	}
}
