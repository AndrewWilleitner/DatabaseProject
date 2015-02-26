package databaseproject.controller;

import databaseproject.view.DatabaseFrame;

public class DatabaseAppController
{
	private DatabaseFrame appFrame;
	private DatabaseController dataController;
	
	public DatabaseAppController()
	{
		dataController = new DatabaseController(this);
		appFrame = new DatabaseFrame(this);
	}
	
	public DatabaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	public void start()
	{
		
	}
}
