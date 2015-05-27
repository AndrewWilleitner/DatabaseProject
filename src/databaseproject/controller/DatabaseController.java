package databaseproject.controller;

import java.sql.*;

import javax.swing.*;

import data.model.QueryInfo;

public class DatabaseController
{
	/**
	 * Declaration section
	 */
	private String connectionString;
	private Connection databaseConnection;
	private DatabaseAppController baseController;
	private String currentQuery;
	private long queryTime;
	
	/**
	 * This sets up the connection to the database
	 * @param baseController links back to app controller.
	 */
	public DatabaseController(DatabaseAppController baseController)
	{
		connectionString = "jdbc:mysql://Localhost/global_game_database?user=root";
		this.baseController = baseController;
		queryTime = 0;
		checkDriver();
		setupConnection();
	}
	
	/**
	 * This sets the string to connect to the server
	 * @param pathToDBServer path to the database.
	 * @param databaseName database name.
	 * @param userName your user name.
	 * @param password your password.
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName,
			String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString = pathToDBServer;
		connectionString = "/" + databaseName;
		connectionString = "?user=" + userName;
		connectionString = "&password=" + password;
	}
	
	public String getQuery()
	{
		return currentQuery;
	}
	
	public void setQuery(String query)
	{
		this.currentQuery = query;
	}
	
	/**
	 * checks the driver.
	 */
	
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	/**
	 * This closes the connection to the database.
	 */
	
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * This sets up the connection to the database
	 */
	
	public void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * This puts the database into a string array.
	 * @return the column names of the database
	 */
	
	public String [] getMetaDataTitles()
	{
		String [] columns;
		currentQuery = "SELECT * FROM `INNODB_SYS_COLUMNS`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData answerData = answers.getMetaData();
			
			columns = new String [answerData.getColumnCount()];
			
			for(int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column+1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String [] {"empty"};
			displayErrors(currentException);
		}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		
		return columns;
	}
	
	/**
	 * This is a more generic version of getMetaDataTitles.
	 * The method only needs the databases name.
	 */
	
	public String [] getDatabaseColumnNames(String tableName)
	{
		String [] columns;
		currentQuery = "SELECT * FROM `" + tableName + "`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData answerData = answers.getMetaData();
			
			columns = new String [answerData.getColumnCount()];
			
			for(int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column+1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String [] {"empty"};
			displayErrors(currentException);
		}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		
		return columns;
	}
	
	/**
	 * Checks the data member query for potential violation of database structure/content removal.
	 * the spaces check if it is it's own word.
	 * @return True if the query could remove data
	 */
	private boolean checkQueryForDataViolation()
	{
		if(currentQuery.toUpperCase().contains(" DROP ")
				|| currentQuery.toUpperCase().contains(" TRUNCATE ")
				|| currentQuery.toUpperCase().contains(" SET ")
				|| currentQuery.toUpperCase().contains(" ALTER "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Generic version of the select query method that will work with any database that is in the connetionString value.
	 * @param query The database query.
	 * @return The query.
	 */
	public String [][] selectQueryResults(String query)
	{
		String [][] results;
		this.currentQuery = query;
		
		try
		{
			if(checkQueryForDataViolation())
			{
				throw new SQLException("There was an attempt at a data violation", 
						" you don't get to mess with dis data!", 
						Integer.MIN_VALUE);
			}
			
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			int columnCount = answers.getMetaData().getColumnCount();
			
			answers.last();
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			results = new String [numberOfRows][columnCount];
			
			while(answers.next())
			{
				for(int col = 0; col < columnCount; col++)
				{
					results[answers.getRow()-1][col] = answers.getString(col + 1);
				}
			}
			
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentException)
		{
			results = new String [][] { {"The query was unsuccessful."},
										{"You shoud use a better String."},
										{currentException.getMessage()}
									  };
			displayErrors(currentException);
		}
		
		return results;
	}
	
	/**
	 * This is the results from the database.
	 * @return the database results.
	 */
	public String [][] realResults()
	{
		String [][] results;
		currentQuery = "SELECT * FROM `INNODB_SYS_COLUMNS`";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(currentQuery);
			int columnCount = answers.getMetaData().getColumnCount();
			
			answers.last();
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			results = new String [numberOfRows][columnCount];
			
			while(answers.next())
			{
				for(int col = 0; col < columnCount; col++)
				{
					results[answers.getRow()-1][col] = answers.getString(col + 1);
				}
			}
			
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentException)
		{
			results = new String [][] {{"empty"}};
			displayErrors(currentException);
		}
		
		return results;
	}
	
	/**
	 * This drops the database. It also checks if you can drop the database.
	 * @return results
	 */
	
	public String [][] testResults()
	{
		String [][] results;
		currentQuery = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(currentQuery);
			
			answers.last();
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			results = new String [numberOfRows][1];
			
			while(answers.next())
			{
				results[answers.getRow()-1][0] = answers.getString(1);
			}
			
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentException)
		{
			results = new String [][] {{"empty"}};
			displayErrors(currentException);
		}
		
		return results;
	}
	
	public String displayTables()
	{
		String tableNames = "";
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			while(answers.next())
			{
				tableNames += answers.getString(1) + "\n";
			}
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return tableNames;
	}
	
	public int insertSample()
	{
		int rowsAffected = -1;
		currentQuery = "INSERT INTO `global_game_database`.`cities` (`username`,`password`,`email`) VALUES (`Me`,`cake`,`guesswho@yahoo.com`);";
		
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(currentQuery);
			insertStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return rowsAffected;
	}
	
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), "Exception: " + currentException.getMessage());
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getSQLState());
		}
	}
	
	private boolean checkForStructureViolation()
	{
		if(currentQuery.toUpperCase().contains(" DATABASE "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void dropStatement(String query)
	{
		currentQuery = query;
		String results;
		try
		{
			if(checkForStructureViolation())
			{
				throw new SQLException("you is no allowed to dropping db's",
						"duh", Integer.MIN_VALUE);
			}
			
			if(currentQuery.toUpperCase().contains(" INDEX "))
			{
				results = "The index was ";
			}
			else
			{
				results = "The table was ";
			}
			
			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);
			
			dropStatement.close();
			
			if(affected == 0)
			{
				results += "dropped";
			}
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch(SQLException dropError)
		{
			displayErrors(dropError);
		}
	}
}
