package cs213.photoAlbum.model;
/**
 * @author Scott Calingasan
 * @author Seth Newman
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class Photo implements Serializable, Comparable<Photo>
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public String fileName; //filename of photo
	public String caption; //caption of photo
	public Calendar photoCreated; //time at which photo was created
	public List<Tags> photoTag; //tags given to photo
	public String pathName; //file Path
	/**
	 * @author Scott Calingasan
	 * 
	 * Constructor method
	 * 
	 * @param filename: Filename of Photo
	 * @param caption: Caption of photo
	 * @param pathName: pathname of photo
	 */
	public Photo(String filename, String caption, String pathName)
	{
		this.fileName = filename;
		this.caption = caption;
		this.photoCreated = Calendar.getInstance();
		this.photoCreated.set(Calendar.MILLISECOND,0);
		this.photoTag = new ArrayList<Tags>();
		this.pathName = pathName;
	}
	
	/**
	 * @author: Scott Calingasan
	 * 
	 * Gets a tag from a photo given a type
	 * 
	 * @return Tag object
	 */
	Tags getTag(String tagType)
	{
		
		for(int i = 0;i<this.photoTag.size();i++)
		{
			if(this.photoTag.get(i).equals(tagType))
			{
				return this.photoTag.get(i);
			}
		}
		return null;
	}
		
	/**
	 * @author Scott Calingasan
	 * 
	 * Prints out information about the photo, such as filename, caption, date created, and tags
	 * 
	 */
	public void getPhotoInfo()
	{
		System.out.println("Filename is: " + this.fileName);
		System.out.println("Caption: " + this.caption);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-hh:mm:ss");
		//Date date = new SimpleDateFormat("mm-dd-yyyy-hh:mm:ss").parse
		
		System.out.println("Date Created: " + df.format(this.photoCreated.getTime()));
		System.out.println("Tags: ");
		printAllTags();
	}
	
	/**
	 * 
	 * @author Scott Calingasan
	 * toString method
	 */
	public String toString()
	{
//		System.out.println(this.fileName);
		return this.fileName;
	}
	
	/**
	 * 
	 * @author Scott Calingasan
	 * Returns photoCreated of photo
	 * @return
	 */
	public Calendar getDateTime()
	{
		return this.photoCreated;
	}
	
	
	/**
	 * 
	 * @author Scott Calingasan
	 * compareTo function
	 */
	  public int compareTo(Photo o) 
	  {
		  if (getDateTime() == null || o.getDateTime() == null)
			  return 0;
		  
		  return getDateTime().compareTo(o.getDateTime());
		  
	  }
	
	  /**
	   * 
	 * @author Scott Calingasan
	   * Method to add tag to photo
	   * @param newTag: tag to add
	   */
	public void addTag(Tags newTag)
	{
		this.photoTag.add(newTag);
	}
	
	/**
	 * 
	 * @author Scott Calingasan
	 * Method to delete tag from photo
	 * @param newTag: tag to delete
	 */
	public void deleteTag(Tags newTag)
	{
//		printAllTags();
//		this.photoTag.remove(newTag);
		for(int i = 0; i<this.photoTag.size();i++)
		{
			Tags newPhotoTag = this.photoTag.get(i);
			if(newPhotoTag.type.equalsIgnoreCase(newTag.type) && newPhotoTag.value.equalsIgnoreCase(newTag.value))
			{
				System.out.println("Phototag is removed");
				this.photoTag.remove(i);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @author Scott Calingasan
	 * Method to print all tags
	 */
	public void printAllTags()
	{
		for(int i = 0;i<this.photoTag.size();i++)
		{
			Tags newPhotoTag = this.photoTag.get(i);
			newPhotoTag.toString();
			System.out.println(newPhotoTag);
		}
	}
	
	
	/**
	 * 
	 * @author Scott Calingasan
	 * Method to change
	 * @param newCaption
	 */
	public void changeCaption(String newCaption)
	{
		this.caption = newCaption;
	
	}
	/**
	 * @author Scott Calingasan
	 * Equals method
	 */
	public boolean equals(Object o)
	{
		if(o == null) { return false;}
		if(!(o instanceof Photo)) { return false;}
		Photo other = (Photo)o;
		return this.fileName == other.fileName && this.caption == other.caption && this.photoCreated == other.photoCreated && this.photoTag == other.photoTag;

	}
	
}
