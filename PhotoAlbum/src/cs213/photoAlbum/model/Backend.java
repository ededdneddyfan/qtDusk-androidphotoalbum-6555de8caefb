package cs213.photoAlbum.model;

import java.io.*;
import java.util.List;

/**
 * 
 * 
 *
 */


public class Backend {
	public static final String dirName = "data";

	

	/**
	 * @author Scott Calingasan
	 * 
	 * Reads user from filename into memory
	 * 
	 * @param userStorage: List used to add user to
	 * @param userID: target userID to be read into file
	 * @param filename: name of file to be used to read in users and information
	 * @return new List<User> with added user into List.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	
	
	public static User readUser(String userID) throws ClassNotFoundException, IOException, FileNotFoundException
	{
    	File dir = new File(dirName);
    	if(!dir.exists())
    	{
    		dir.mkdir();
    	}
    	
		File newFile = new File(dirName + File.separator + userID + ".data");
    	
    	
    	
		FileInputStream fis = new FileInputStream(dirName + File.separator + userID + ".data");
		ObjectInputStream ois = new ObjectInputStream(fis);
		User newUser = (User)ois.readObject();
		return newUser;
	}
	
	
	/**
	 * @author Scott Calingasan
	 * 
	 * Writes a user from storage into memory
	 * 
	 * @param userStorage: List used to get user information to write to memory
	 * @param userID: target userID to be read into file
	 * @param filename: Name of file to write user information into
	 * @return 
	 * @throws IOException 
	 */
	
	
	public static int writeUser(User userWritten) throws IOException
	{
		
        try {
        	
        	String userID = userWritten.userID;
        	File newFile = new File(dirName + File.separator + userID + ".data");
        	System.out.println("Created new file " + newFile.getPath());
        	
        	if(!newFile.exists())
        	{
        		newFile.createNewFile();
        		System.out.println("Created new file " + newFile.getPath());
        	}
        	
        	File dir = new File(dirName);
        	if(!dir.exists())
        	{
        		dir.mkdir();
        	}
        	
    		FileOutputStream fos = new FileOutputStream(dirName + File.separator + userID + ".data");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userWritten);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		}
        return 0;
	}
	
	
	/**
	 * @author Scott Calingasan
	 * 
	 * Deletes a user given a userID from the filename provided
	 * 
	 * @param userStorage: List of users to delete userID
	 * @param userID: ID of user to be deleted
	 * @param filename: File used to delete user information
	 */
	public static void deleteUser(String userID)
	{
		File file = new File(dirName + File.separator + userID + ".data");
    	File dir = new File(dirName);
    	if(!dir.exists())
    	{
    		dir.mkdir();
    	}
		
		if(file.delete())
		{
			System.out.println(file.getName() + " is deleted" );
		}
		
		
		else
		{
			System.out.println("Failed to delete file");
		}
		
	}
	/**
	 * @author Scott Calingasan
	 * 
	 * Prints out all users given a list of users stored in memory
	 * 
	 * @param userStorage: List of users to print out information
	 * 
	 */
	public static void getUsers()
	{
		String user;
		File folder = new File(dirName);
		File[] userList = folder.listFiles();
		
		
		for(int i = 0;i < userList.length; i++)
		{
			if(userList[i].isFile())
			{
				user = userList[i].getName();
				user = user.substring(0, user.length()-4);
				System.out.println(user);
			}
			
		}
	}
	
}
