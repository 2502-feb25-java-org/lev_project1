package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import org.apache.log4j.Logger;

import com.revature.servlets.LoginServlet;

public class DBConnection {
	private static Logger log = Logger.getLogger(DBConnection.class);

	public static Connection getConnection() {	
		//Properties prop = new Properties();
		//String path = "/src/main/resources/database.properties";
		Connection connection = null;

		try {
			//log.info(System.getProperty("user.dir"));
			//prop.load(new FileReader(path));
			
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			String server = "jdbc:sqlserver://localhost:1433";
			String database = "ERS";
			String username = "DESKTOP-2Q7B3SA\\Lev";
			String password = "";
			String other = "integratedSecurity=true";
			String url = server + ";database=" + database + ";username=" + username + ";password=" + 
																	password + ";" + other + ";";
			
			log.info("TRYING TO CONNECT TO: " + url);
			Class.forName(driver);
			connection = DriverManager.getConnection(url);
			log.info("CONNECTED TO SQLEXPRESS.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connection;
	}
}
