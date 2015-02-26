package databaseproject.view;

import javax.swing.*;

import databaseproject.controller.DatabaseAppController;
import databaseproject.controller.DatabaseController;

public class DatabaseFrame extends JFrame
{
	private DatabasePanel appPanel;
	
	public DatabaseFrame(DatabaseAppController baseController)
	{
		appPanel = new DatabasePanel(baseController);
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setSize(1000,1000);
		this.setContentPane(appPanel);
		this.setVisible(true);
	}
	
}
