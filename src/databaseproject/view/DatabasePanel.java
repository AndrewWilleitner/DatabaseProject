package databaseproject.view;

import javax.swing.*;

import databaseproject.controller.DatabaseAppController;
import databaseproject.controller.DatabaseController;

import java.awt.Color;

public class DatabasePanel extends JPanel
{

	private DatabaseAppController baseController;
	
	
	public DatabasePanel(DatabaseAppController baseController)
	{
		
		this.baseController = baseController;
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupPanel()
	{
		setBackground(Color.GREEN);
	}
	
	private void setupLayout()
	{
		
	}
	
	private void setupListeners()
	{
		
	}
}
