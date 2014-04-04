package cs213.photoAlbum.model;

import java.io.Serializable;

public class Tags implements Serializable{
		public String type; //location of where photo was taken
		public String value;
		private static final long serialVersionUID = 1L;
		
		/**
		 * @author: Scott Calingasan
		 * @param type: type of tag
		 * @param value: value of the type
		 */
		public Tags(String type, String value)
		{
			this.type = type;
			this.value = value;
		}
	/**
	 * 
	 * @author Scott Calingasan
	 * equals method
	 */
		public boolean equals(Object o)
		{
			if(o == null) { return false;}
			if(!(o instanceof Tags)) { return false;}
			Tags other = (Tags)o;
			return this.type == other.type && this.value == other.value;
		}
		
		/**
		 * 
		 * 
		 * @author Scott Calingasan
		 *
		 * toString method
		 */
		public String toString()
		{
			return this.type + ":" + this.value;
		}
	
}
