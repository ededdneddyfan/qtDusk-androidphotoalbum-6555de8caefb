package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Scott Calingasan
 * @author Seth Newman
 */
public class Album implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String albumName; //name of album, name must be unique per user 
	public int numPhotos; //num photos in album
	public ArrayList<Photo> photos; //photos in album
	
	/**
	 * @author Scott Calingasan
	 * 
	 * Constructor for Album object
	 * 
	 * @param albumName: Name of the album
	 */
	public Album(String albumName)
	{
		this.albumName = albumName;
		this.numPhotos = 0;
		this.photos = new ArrayList<Photo>();
	}
	
	public String toString()
	{
		return this.albumName;
	}
	
	public boolean equals(Object o)
	{
		if(o == null) { return false;}
		if(!(o instanceof Album)) { return false;}
		Album other = (Album)o;
		return this.albumName == other.albumName && this.numPhotos == other.numPhotos && this.photos == other.photos;
	}
	
	/**
	 * @author Scott Calingasan
	 * 
	 * Method to list all the photos
	 * 
	 * @param targetAlbum: Album to list all the photos
	 */
	public void listPhotos()
	{
		if(this.photos.size() == 0)
		{
			System.out.println("No photos in album " + this.albumName);
			return;
		}
		System.out.println("Photos in album " + this.albumName + ": ");
		for(int i = 0; i<this.photos.size();i++)
		{
			Photo newPhoto = this.photos.get(i);
			newPhoto.toString();
		}
	}
	
	/**
	 * Method to add photo
	 * @param addedPhoto: Photo to add
	 */
	public void addPhotos(Photo addedPhoto)
	{
		this.photos.add(addedPhoto);
		this.numPhotos++;
	}
	
	/**
	 * Method to get the photo
	 * @param photo: photo to get from album
	 * @return
	 */
	public Photo getPhoto(String photo)
	{
		for(int i = 0; i<this.photos.size();i++){
			Photo newPhoto = this.photos.get(i);
			if(newPhoto.fileName.equalsIgnoreCase(photo))
			{
				return newPhoto;
			}
		}
		return null;
	}
	/**
	 * @author Scott Calingasan
	 * finds a photo in an album
	 * @param photoName photoname to find in given album
	 * @return
	 */
	public int findPhoto(String photoName)
	{
		int i = 0; 
		for(;i<this.photos.size();i++)
		{
			if(this.photos.get(i).fileName.equalsIgnoreCase(photoName))
			{
				return i;
			}
		}
		return -1;
	}
	/**
	 * @author Scott Calingasan
	 * 
	 * Method to remove a photo from a given album
	 * 
	 * @param photo: Photo to be removed from album
	 */
	public String removePhoto(String photo)
	{
		for(int i = 0; i<this.photos.size();i++)
		{
			Photo newPhoto = this.photos.get(i);
			if(newPhoto.fileName.equals(photo))
			{
				this.photos.remove(i);
				this.numPhotos--;
				return photo;
			}
		}
		return null;
	}
}
