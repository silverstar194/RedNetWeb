package post;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import database.DataBase;
import message.Message;
import user.User;


/**
 * @author Sean Maloney
 * 
 */

/**
 * Creates posts from Users and sends them to the database.
 */
public class Post {

	
	/** The title. */
	private String title;
	
	/** The content. */
	private String content;
	
	
	/** The time posted. */
	private long time;
	
	/** The username of poster. */
	private User username;
	
	/** The post id. */
	private String postID;

	/**The subreddit*/
	private String subreddit;
	
	/**
	 * Instantiates a new post.
	 *
	 * @param title: the title
	 * @param content: the content
	 * @param userID: the user id
	 * @param endTime: the expiration time
	 */
	public Post(String title, String content, User username){
		
		//for mysql storage
		if (content.contains("'") || title.contains("'")) 
		{
			content = content.replace("'", "''");
			title = title.replace("'", "''");
		}
		
		this.username = username;
		this.title = title;
		this.content = content;
		this.postID = idGenerator();
		this.time=System.currentTimeMillis();


	}

	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	//getters
	public String getTitle(){
		return this.title;
	}
	

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent(){
		return this.content;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public long getTime(){
		return this.time;
	}


	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public User getUser(){
		return this.username;
	}

	/**
	 * Gets the post id.
	 *
	 * @return the post id
	 */
	public String getPostId(){
		return this.postID;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	//setters
	public void setTitle(String title){
		if ( title.contains("'")) 
		{
			title = title.replace("'", "''");
		}
		this.title = title;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content){
		if (content.contains("'")) 
		{
			content = content.replace("'", "''");
		}
		
		this.content = content;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(long time){
		this.time = time;
	}


	
	/**
	 * Sets the post to deleted.
	 *
	 * @param endTme the new end time
	 */
	public void delete(){
		//implement
	}
	
	/**
	 * Save new post to database.
	 *
	 * @throws SQLException the SQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void saveNewPostToDatabase() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DataBase dataBase = new DataBase();

		Connection dataBaseConn = dataBase.getConnection();

		String command ="INSERT INTO "
				+ "`electro956_db`.`Post` (`postID`,`title`,`content`,`time`,`userID`,`endtime`, `deleted`) "
				+ "VALUES"
				+ " ('"+this.postID+"','"+this.title+"','"+this.content+"','"+this.time+"','"+this.userID+"','"+this.endTime+"','"+this.deleted+"');";

		dataBase.executeUpdate(dataBaseConn, command);
		System.out.println("=====POST SENT TO DATABASE=====");

	}

	/**
	 * Update  post in database.
	 *
	 * @throws SQLException the SQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void update() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DataBase dataBase = new DataBase();

		Connection dataBaseConn = dataBase.getConnection();

		String command ="UPDATE Post"
				+ " SET postID='"+this.postID+"', title='"+this.title+"', "
				+ "content='"+this.content+"', time='"+this.time+"', userID='"+this.userID+"', endTime='"+this.endTime+"', deleted='"+this.deleted+"', spamCount='"+this.spamCount+"', spam='"+this.spam+"'"
				+  " WHERE postID='"+this.postID+"';";

		dataBase.executeUpdate(dataBaseConn, command);
		System.out.println("=====POST UPDATED IN DATABASE=====");



	}


	/**
	 * Delete post from database.
	 *
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public void deletePost() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

		DataBase dataBase = new DataBase();

		Connection dataBaseConn = dataBase.getConnection();

		String command ="UPDATE Post"
				+ " SET deleted='true' WHERE postID='"+this.postID+"';";
		this.deleted = true;

		dataBase.executeUpdate(dataBaseConn, command);
		System.out.println("=====POST DELETED FROM DATABASE=====");



	}



	/**
	 * Gets the post info.
	 *
	 * @return the post info
	 */
	public String getPostInfo() {
		DataBase dataBase = new DataBase();

		Map<String,String> userMap = new HashMap<String, String>();
		String titleNew = null;
		String contentNew = null;
		if(this.title.contains("''") || this.content.contains("''")){
			titleNew = this.title.replace("''", "'");
			contentNew = this.content.replace("''", "'");
		}else{
			titleNew = this.title;
			contentNew = this.content;
		}


		userMap.put("postID", this.postID);
		userMap.put("title", titleNew);
		userMap.put("content", contentNew);
		userMap.put("time", ""+this.time);
		userMap.put("username".getUserName(), this.username);
		
		JSONObject json = new JSONObject(userMap);

		System.out.println("=====USER CONVERTED TO JSON=====");

		return json.toString();

	}
	
	/**
	 * Gets the posts by user.
	 *
	 * @param user
	 * @return the message by user
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Post> getPostByUserID(User user) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		DataBase dataBase = new DataBase();
		Connection dataBaseConn = null;
		ArrayList<Post> posts = new ArrayList<Post>();
		try{
		dataBaseConn = dataBase.getConnection();

		String command ="SELECT * FROM Post WHERE userID='"+user.getUserID()+"'";

		ResultSet rs = dataBase.getDataBaseInfo(dataBaseConn, command);

		while(rs.next()){
			Post newPost = new Post(rs.getString("title"), rs.getString("content"), rs.getString("userID"),
					rs.getString("postID"), Long.parseLong(rs.getString("time")), Long.parseLong(rs.getString("endTime")), Boolean.parseBoolean(rs.getString("deleted")), Integer.parseInt(rs.getString("spamCount")), Boolean.parseBoolean(rs.getString("spam")));

			if(true){ //later add a max distance here so a user in NY doesn't get something in CA
						//also will flex area based on num. in DB
			posts.add(newPost);
			}
			
		}
		}finally{
			dataBaseConn.close();
		}
		
		return posts;
	}
	

}
