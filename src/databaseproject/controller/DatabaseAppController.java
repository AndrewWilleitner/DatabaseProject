package databaseproject.controller;

import databaseproject.view.DatabaseFrame;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import data.model.QueryInfo;

public class DatabaseAppController
{
	private DatabaseFrame appFrame;
	private DatabaseController dataController;
	private ArrayList<QueryInfo> queryList;
	
	/**
	 * This sets up the values to the class's variables
	 */
	public DatabaseAppController()
	{
		dataController = new DatabaseController(this);
		queryList = new ArrayList<QueryInfo>();
		appFrame = new DatabaseFrame(this);
	}
	
	public DatabaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	public DatabaseController getDataController()
	{
		return dataController;
	}
	
	public ArrayList<QueryInfo> getQueryList()
	{
		return queryList;
	}
	
	public void start()
	{
		
	}
	
	/**
	 * This loads the query from a file
	 */
	public void loadTimingInformation()
	{
		try
		{
			File loadFile = new File("asdasda.save");
			if(loadFile.exists())
			{
				queryList.clear();
				Scanner textScanner = new Scanner(loadFile);
				while(textScanner.hasNext())
				{
					String query = textScanner.nextLine();
					long queryTime = Long.parseLong(textScanner.nextLine());
					queryList.add(new QueryInfo(query, queryTime));
				}
				textScanner.close();
				JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + " QueryInfo objects were loaded");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects loaded");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
	
	/**
	 * This loads the query from a file
	 */
	public void saveTimingInformation()
	{
		try
		{
			File saveFile = new File("asdasda.save");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				for (QueryInfo current : queryList)
				{
					writer.println(current.getQuery());
					writer.println(current.getQueryTime());
				}
				writer.close();
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects saved");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
}
