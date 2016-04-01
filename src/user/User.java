package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.*;
import database.DataBase;
import message.Message;
import post.Post;


/**
 * @author Sean Maloney
 * 
 */

/**
 * Creates users and saves them to database.
 */

public class User {


	/** The user name. */
	private String username;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	/** Karma for links*/
	private int linkKarma;
	
	/** Karma for posts*/
	private int postKarma;

	private Date dateCreated;
	/**
	 * Instantiates a new user.
	 *
	 * @param email: user email
	 * @param password: user password
	 * @param username: user name
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public User(String email, String password, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

		this.email = email;
		this.password = password;
		this.username = username;
		this.dateCreated = new Date();
		
		System.out.println("=====Generated New User=====");

	}

	/**
	 * Instantiates a new user.
	 *
	 * @param id: user id
	 * @throws SQLException 
	 */
	public User(String id){
		DataBase dataBase = new DataBase();
		Connection dataBaseConn = null;
		try {
			dataBaseConn = dataBase.getConnection();


			String command ="SELECT * FROM User WHERE userID='"+id+"'";

			ResultSet rs = dataBase.getDataBaseInfo(dataBaseConn, command);
			while(rs.next()){
				this.userID = rs.getString("userID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.email = rs.getString("email");
				this.password = rs.getString("password");
				this.userName = rs.getString("userName");
				this.bio = rs.getString("bio").replace("'", "''");
				this.spamCount = Integer.parseInt(rs.getString("spamCount"));

				Message newMessage = new Message();
				this.messageCount = newMessage.getNewMessageCountByUser(this);
				Post newPost = new Post();
				this.postCount = newPost.getPostCountByUser(this);
			}

			System.out.println("=====USER CREATED FROM DATABASE=====");

		} catch (Exception e) {
			System.out.println("=====ERROR GETTING USER FROM DATABASE=====");
			e.printStackTrace();
		}finally{
			try {
				dataBaseConn.close();
			} catch (SQLException e) {
				System.out.println("CANNOT CLOSE MYSQL CONNECTION");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Blank Constructor
	 */
	public User(){

	}



	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getemail(){
		return this.email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getpassword(){
		return this.password;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getusername(){
		return this.username;
	}




	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setemail(String email){

		this.email = email;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setpassword(String password){
		this.password = password;
	}



	/**
	 * Save new user to database.
	 *
	 * @throws SQLException the SQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void saveNewUserToDatabase() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DataBase dataBase = new DataBase();

		Connection dataBaseConn = dataBase.getConnection();

		String command ="INSERT INTO "
				+ "`electro956_db`.`User` (`userID`,`firstName`,`lastName`,`email`,`password`,`userName`,`bio`)"
				+ "VALUES"
				+ "('"+this.userID+"','"+this.firstName+"','"+this.lastName+"','"+this.email+"','"+this.password+"','"+this.userName+"','"+this.bio+"');";

		String commandUserMap = "INSERT INTO `electro956_db`.`UserMap` (`userName`, `userID`) VALUES ('"+this.userName+"', '"+this.userID+"');";

		dataBase.executeUpdate(dataBaseConn, command);
		dataBase.executeUpdate(dataBaseConn, commandUserMap);


		System.out.println("=====USER SENT TO DATABASE=====");

	}

	/**
	 * Update user in database.
	 *
	 * @throws SQLException the SQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void update() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DataBase dataBase = new DataBase();

		Connection dataBaseConn = dataBase.getConnection();

		String command ="UPDATE User"
				+ " SET firstName='"+this.firstName+"', lastName='"+this.lastName+"', email='"+this.email+"', password='"+this.password+"', userName='"+this.userName+"', bio='"+this.bio+"', spamCount='"+this.spamCount+"'"
				+  "WHERE userID='"+this.userID+"';";

		String commandUserMap = "UPDATE UserMap SET userName='"+this.userName+"' WHERE userID='"+this.userID+"';";

		dataBase.executeUpdate(dataBaseConn, command);
		dataBase.executeUpdate(dataBaseConn, commandUserMap);


		System.out.println("=====USER UPDATED IN DATABASE=====");

	}

	/**
	 * Gets the user info.
	 *
	 * @return the user info
	 */
	public String getUserInfo() {

		Map<String,String> userMap = new HashMap<String, String>();

	
		userMap.put("email", this.email);
		userMap.put("username", this.username);

		JSONObject json = new JSONObject(userMap);

		System.out.println("=====USER CONVERTED TO JSON=====");

		return json.toString();


	}

	/**
	 * Delete user in database.
	 *
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void deleteUser() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

		DataBase dataBase = new DataBase();

		Connection dataBaseConn = dataBase.getConnection();

		String command ="DELETE FROM `electro956_db`.`User` WHERE userID='"+this.userID+"';";

		dataBase.executeUpdate(dataBaseConn, command);
		System.out.println("=====USER UPDATED IN DATABASE=====");


	}



	/**
	 * Id generator.
	 *
	 * @return the string
	 */
	private String idGenerator() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * Hash password (SHA-256 with Salt).
	 *
	 * @param hashString the hash string
	 * @return the string
	 */
	private String hashPassword(String hashString){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");

			String text = hashString;

			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();

			System.out.println("=====PASSWORD HASHED=====");

			return String.format("%064x", new java.math.BigInteger(1, digest));

		} catch (Exception e) {
			System.out.println("=====HASH FAILED=====");
			e.printStackTrace();
		}

		return null;


	}

	/**
	 * Fetches userID from database.
	 *
	 * @param userName
	 * @return userID
	 */public static String getUserID(String userName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		 String userIDReturn=null;

		 DataBase dataBase = new DataBase();

		 Connection dataBaseConn = dataBase.getConnection();
		 try{
			 String command ="SELECT * FROM `electro956_db`.`UserMap` WHERE userName='"+userName+"';";

			 ResultSet rs = dataBase.getDataBaseInfo(dataBaseConn, command);

			 while(rs.next()){
				 userIDReturn = rs.getString("userID");
			 }

		 }finally{
			 try {
				 dataBaseConn.close();

			 } catch (SQLException e) {
				 System.out.println("CANNOT CLOSE MYSQL CONNECTION");
				 e.printStackTrace();
			 }
		 }

		 System.out.println("=====USERID FETCHED FROM DATABASE=====");
		 return userIDReturn;

	 }

	

	 /**
	  * Verify password.
	  *
	  * @param password the password
	  * @return true, if successful
	  */
	 public boolean verifyPassword(String password){
		 String testString = hashPassword(password+this.userID);

		 if(testString.equals(this.password)){
			 return true;
		 }
		 return false;
	 }

}
