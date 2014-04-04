package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Scott Calingasan
 * @author Seth Newman
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userID;
	public String userFullName;
	public List<Album> userAlbums;
	public String albumToLoad = ""; 
	/**
	 * @author Scott Calingasan
	 * 
	 * Constructor class for User
	 * 
	 * @param userID: User ID 
	 * @param userFullName: User's full name 
	 */
	public User(String userID, String userName)
	{
		this.userID = userID;
		this.userFullName = userName;
		this.userAlbums = new ArrayList<Album>();
	}
	/**
	 * Constructor class with no albums
	 */
	public User() {
		this.userID = "";
		this.userFullName = "";
		this.userAlbums = new ArrayList<Album>();
	}

	/**
	 * Method to add an album to user object
	 * @param addedAlbum: album object to add to user's current set of albums
	 */
	public void addAlbum(Album addedAlbum)
	{
		this.userAlbums.add(addedAlbum);
	}
	
	/**
	 * @author: Scott Calingasan
	 * 
	 * Method to delete album specified
	 * 
	 * @param targetAlbum: Album to be deleted
	 */
	public void deleteAlbum(String targetAlbum)
	{
		for(int i = 0; i<this.userAlbums.size();i++)
		{
			Album newAlbumTarg = this.userAlbums.get(i);
			if(newAlbumTarg.albumName.equalsIgnoreCase(targetAlbum))
			{
				this.userAlbums.remove(i);
				break;
			}
		}
	}
	/**
	 * 
	 * @author Scott Calingasan
	 * renames album
	 * @param targetAlbum: album to rename
	 * @param newName: new name of album
	 */
	public void renameAlbum(String targetAlbum, String newName)
	{
		for(int i = 0; i<this.userAlbums.size();i++)
		{
			Album newAlbumTarg = this.userAlbums.get(i);
			if(newAlbumTarg.albumName.equalsIgnoreCase(targetAlbum))
			{
				String oldAlbum = newAlbumTarg.albumName;
				this.userAlbums.remove(i);
				newAlbumTarg.albumName = newName;
				this.userAlbums.add(newAlbumTarg);
				System.out.println("Renamed user album " + oldAlbum + "to " + newAlbumTarg.albumName);
				break;
			}
		}
		
	}
	/**
	 * 
	 * @author Scott Calingasan
	 * Lists albums of user
	 */
	public void listAlbums()
	{
		if(this.userAlbums.size() == 0)
		{
			System.out.println("User has no albums");
			return;
		}
		System.out.println("User " + this.userFullName + "'s albums are: ");
		for(int i = 0; i<this.userAlbums.size();i++)
		{
			Album newAlbumTarg = this.userAlbums.get(i);
			System.out.println(newAlbumTarg.albumName);
		}	
	}
	/**
	 * 
	 * 
	 * @author Scott Calingasan
	 * @param targAlbum gets album given a String
	 * @return target album object
	 */
	public Album getAlbum(String targAlbum)
	{
		if(this.userAlbums.size() == 0)
		{
			System.out.println("No albums found");
			return null;
		}
		for(int i = 0; i<this.userAlbums.size();i++)
		{
			Album newAlbumTarg = this.userAlbums.get(i);
			if(newAlbumTarg.albumName.equalsIgnoreCase(targAlbum))
			{
				return newAlbumTarg;
			
			}
		}
		return null;
	
	}
	/**
	 * 
	 * @author Scott Calingasan
	 * Find album given a name of a photo
	 * @param photo to find album given a photo
	 * @return
	 */
	public Album findPhotoAlbum(String photo)
	{
		if(this.userAlbums.size() == 0)
		{
			System.out.println("No albums found");
			return null;
		}
		for(int i = 0; i<this.userAlbums.size();i++)
		{
			Album newAlbumTarg = this.userAlbums.get(i);
			if(newAlbumTarg.getPhoto(photo) == null)
			{
				System.out.println("Band-Aid");
			}
			else
				return newAlbumTarg;
		}
		return null;
	}
}
