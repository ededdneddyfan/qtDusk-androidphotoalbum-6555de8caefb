package com.example.photoalbum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tags;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.*;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class AlbumScreen extends Activity {

	//TODO Rename Bundle ?
	//TODO documentation - explain how everything works in readme
	
	Button button;
	Button backButton;
	Button forwardButton;
	ImageView image;
	int currentGalPosition;
	GalleryAdapter galleryAdapter;
	ArrayList<Photo> GalleryFileList;
	List<File> pics;
	ArrayAdapter<File> picsAdapter;
    @SuppressWarnings("deprecation")
	Gallery gallery;
    TextView tags;
    User user;
    String tagType="", tagValue="";
    
	public class GalleryAdapter extends BaseAdapter {

	    // ArrayList<String> GalleryFileList;
	     Context context;
	     
	     GalleryAdapter(Context cont, ArrayList<Photo> photoList){
	      context = cont;
	      GalleryFileList = photoList;
	  }
	     
	  @Override
	  public int getCount() {
	   return GalleryFileList.size();
	  }

	  @Override
	  public Object getItem(int position) {
	   return GalleryFileList.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
	   // TODO Auto-generated method stub
	   return position;
	  }
	  
	  @SuppressWarnings("deprecation")
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		   Bitmap bm = resizePic(GalleryFileList.get(position).pathName, 200, 200);
		   LinearLayout layout = new LinearLayout(context);
		   layout.setLayoutParams(new Gallery.LayoutParams(250, 250));
		   layout.setGravity(Gravity.CENTER);

		   ImageView imageView = new ImageView(context);
		   imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
		   imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		   imageView.setImageBitmap(bm);
		   
		   layout.addView(imageView);
		   return layout;

		  }
		  
		  public void add(Photo newitem){
		   GalleryFileList.add(newitem);
		  }

		 }
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_screen);
		File file = new File(getFilesDir()+ File.separator + "1.data");

		System.out.println(file.getAbsolutePath());

		if(!file.exists()){
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
				System.out.println("Created new file " + file.getPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user = new User("1", "user");
		}
		else{
			try {

				System.out.println("Reading in user from file");

				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream is = new ObjectInputStream(fis);
				user = (User) is.readObject();
				is.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IllegalArgumentException e){
				e.printStackTrace();
			}

		}
		
		
		String SDDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
		gallery = (Gallery)findViewById(R.id.gallery);
		galleryAdapter = new GalleryAdapter(this, user.getAlbum(user.albumToLoad).photos);
		//We need to load in all of the possible photos that we can add into a list from the sd/Pictures directory
		//for when you're adding photos
		final String galleryLocation = SDDirectory + "/Pictures/";
		pics = getListFiles(new File(galleryLocation));
		picsAdapter = new ArrayAdapter<File>(this, android.R.layout.simple_gallery_item, pics);
		button = (Button)findViewById(R.id.addPhotoButton);
		backButton = (Button)findViewById(R.id.backSlideshowButton);
		forwardButton = (Button)findViewById(R.id.forwardSlideshowButton);
		
		Toast.makeText(getBaseContext(), galleryLocation, Toast.LENGTH_LONG).show();
		
		gallery.setAdapter(galleryAdapter);
		image = (ImageView)findViewById(R.id.imageView1);
		image.setImageResource(R.drawable.ic_launcher);
		
		//Clicking on a photo, which displays it in full.
		OnItemClickListener pictureClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Bitmap bm = resizePic(GalleryFileList.get(position).pathName, 400, 400);
				currentGalPosition = position;
				image = (ImageView)findViewById(R.id.imageView1);
				image.setImageBitmap(bm);
				Toast.makeText(getBaseContext(), GalleryFileList.get(position).toString(), Toast.LENGTH_LONG).show();
			}
		};
		gallery.setOnItemClickListener(pictureClickedHandler);
		
		
		gallery.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
				menu.setHeaderTitle("Menu");
				menu.add(0, 0 ,0, "Remove photo");
				menu.add(0, 1, 0, "Add tag");
				menu.add(0, 2, 0, "Display Tags");
				menu.add(0, 3, 0, "Move Photo");
				menu.add(0,4,0, "Delete Tag");
			}


		});
		
		//Add photo, also prompt user for photo tags!
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder addedPic = new AlertDialog.Builder(AlbumScreen.this);
				addedPic.setTitle("Choose a photo to add to this album");
				addedPic.setAdapter(picsAdapter, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Bitmap bm = BitmapFactory.decodeFile(pics.get(which).toString());

						image = (ImageView)findViewById(R.id.imageView1);
						image.setImageBitmap(bm);
						Photo newPhoto = new Photo(pics.get(which).toString(),pics.get(which).toString(),pics.get(which).toString());
						user.getAlbum(user.albumToLoad).addPhotos(newPhoto);
						Toast.makeText(getBaseContext(), galleryLocation, Toast.LENGTH_LONG).show();
						galleryAdapter.notifyDataSetChanged();
						writeToSD();
					}
				});
				AlertDialog alert = addedPic.create();
				alert.show();
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (currentGalPosition == 0)
				{
					currentGalPosition = GalleryFileList.size()- 1;
					Bitmap bm = resizePic(GalleryFileList.get(currentGalPosition).pathName, 400, 400);
					gallery.setSelection(currentGalPosition);
					image = (ImageView)findViewById(R.id.imageView1);
					image.setImageBitmap(bm);
				}
				else
				{
					Bitmap bm = resizePic(GalleryFileList.get(currentGalPosition-1).pathName, 400, 400);
					currentGalPosition--;
					gallery.setSelection(currentGalPosition);
					image = (ImageView)findViewById(R.id.imageView1);
					image.setImageBitmap(bm);
				}
			}
			
		});
		
		forwardButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (currentGalPosition == GalleryFileList.size()-1)
				{
					currentGalPosition = 0;
					Bitmap bm = resizePic(GalleryFileList.get(currentGalPosition).pathName, 400, 400);
					gallery.setSelection(currentGalPosition);
					image = (ImageView)findViewById(R.id.imageView1);
					image.setImageBitmap(bm);
				}
				else
				{
					Bitmap bm = resizePic(GalleryFileList.get(currentGalPosition+1).pathName, 400, 400);
					currentGalPosition++;
					gallery.setSelection(currentGalPosition);
					image = (ImageView)findViewById(R.id.imageView1);
					image.setImageBitmap(bm);
				}
			}
			
		});
	}
	
	public List<File> getListFiles(File parentDir) {
	    ArrayList<File> inFiles = new ArrayList<File>();
	    File[] files = parentDir.listFiles();
	    for (File file : files) {
	    	if (file.isDirectory()) {
	            inFiles.addAll(getListFiles(file));
	        } else {
	            if(file.getName().endsWith(".bmp") || file.getName().endsWith(".png") || file.getName().endsWith(".jpg")){
	                inFiles.add(file);
	            }
	        }
	        }
	    return inFiles;
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }
	
	public Bitmap resizePic(String path, int width, int height)
	{
		Bitmap bm = null;
	     
	     // First decode with inJustDecodeBounds=true to check dimensions
	     final BitmapFactory.Options options = new BitmapFactory.Options();
	     options.inJustDecodeBounds = true;
	     BitmapFactory.decodeFile(path, options);
	     
	     // Calculate inSampleSize
	     options.inSampleSize = calculateInSampleSize(options, width, height);
	     
	     // Decode bitmap with inSampleSize set
	     options.inJustDecodeBounds = false;
	     bm = BitmapFactory.decodeFile(path, options); 
	     
	     return bm;  
	}
	    
	public int calculateInSampleSize(
	      
	     BitmapFactory.Options options, int reqWidth, int reqHeight) {
	     // Raw height and width of image
	     final int height = options.outHeight;
	     final int width = options.outWidth;
	     int inSampleSize = 1;
	        
	     if (height > reqHeight || width > reqWidth) {
	      if (width > height) {
	       inSampleSize = Math.round((float)height / (float)reqHeight);   
	      } else {
	       inSampleSize = Math.round((float)width / (float)reqWidth);   
	      }   
	     }
	     
	     return inSampleSize;   
	    }
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		writeToSD();
		finish();
	}

	public void writeToSD(){
		try {

			File file = new File(getFilesDir()+ File.separator + "1.data");

			//External storage
			FileOutputStream fos = new FileOutputStream(file, false);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(user);
			os.flush();
			os.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		return;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		final AdapterContextMenuInfo menuInfo =(AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId()){
			//Remove Photo
			case 0:{
				String photoToDelete = galleryAdapter.getItem(menuInfo.position).toString();
				user.getAlbum(user.albumToLoad).removePhoto(photoToDelete);
//				for(int i = 0;i<user.getAlbum(user.albumToLoad).photos.size();i++){
//					galleryAdapter.add(user.getAlbum(user.albumToLoad).photos.get(i));
//				}
				gallery.setAdapter(null);
				gallery.setAdapter(galleryAdapter);
				galleryAdapter.notifyDataSetChanged();
				break;
			}
			
			//Add Tag
			case 1:{
				Builder alert = new AlertDialog.Builder(AlbumScreen.this);
				alert.setTitle("Add Tag");
				alert.setMessage("Type in value for new Tag");

				// Set an EditText view to get user input 
				final EditText input = new EditText(AlbumScreen.this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  tagValue = input.getText().toString();
				  	
				  	if(tagType.equalsIgnoreCase("Person") || tagType.equalsIgnoreCase("location")){
				  		Tags newTag = new Tags(tagType, tagValue);
				  		user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString()).addTag(newTag);
					}
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				
				
				
				alert.setTitle("Add Tag");
				alert.setMessage("Type in type for new tag");

				// Set an EditText view to get user input 
				final EditText input2 = new EditText(AlbumScreen.this);
				alert.setView(input2);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  tagType = input2.getText().toString();
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				break;
				
			}
			
			//Display Tags
			case 2:{
				Builder alert = new AlertDialog.Builder(AlbumScreen.this);

				alert.setTitle("Display Tag");
				alert.setMessage("Display Tags");
				TextView newText = new TextView(this);
				String textString = "";
				
				Photo newPhoto = user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString());
				for(int i = 0;i<newPhoto.photoTag.size();i++){
			
					textString = textString + "\n" + 
				user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString()).photoTag.get(i).type + ":"+ 
				user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString()).photoTag.get(i).value;
//					Toast.makeText(getBaseContext(), textString, Toast.LENGTH_LONG).show();
				}
				newText.setText(textString);
				
				alert.setView(newText);
				
				
				/*ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  }
				});*/

				alert.show();
				galleryAdapter.notifyDataSetChanged();
				break;
			}
			
			
			//Move Photo
			case 3:{
				Builder alert = new AlertDialog.Builder(AlbumScreen.this);
				alert.setTitle("Add Album");
				alert.setMessage("Type in name for new album");

				// Set an EditText view to get user input 
				final EditText input = new EditText(AlbumScreen.this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  
				  if(user.getAlbum(value) != null){
					  Photo newPhoto = user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString());
					  user.getAlbum(value).addPhotos(newPhoto);
					  user.getAlbum(user.albumToLoad).removePhoto(galleryAdapter.getItem(menuInfo.position).toString());
					  galleryAdapter.notifyDataSetChanged();

				  }
				  else{
						Toast.makeText(getBaseContext(), "Invalid album", Toast.LENGTH_LONG).show();
				  }
				}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				galleryAdapter.notifyDataSetChanged();

			}
			//Delete Tags
			case 4:{
				
				Builder alert = new AlertDialog.Builder(AlbumScreen.this);
				alert.setTitle("Remove Tag");
				alert.setMessage("Type in tag value to remove");

				// Set an EditText view to get user input 
				final EditText input = new EditText(AlbumScreen.this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  tagValue = input.getText().toString();
				  System.out.println("TagType to be removed is "+ tagType + " TagValue to be removed is "+ tagValue);
				  if(tagType.equalsIgnoreCase("location") || tagType.equalsIgnoreCase("person")){
					  System.out.println("the if statement works");
//					  Photo newPhoto = user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString());
					  Tags removeTag = new Tags(tagType, tagValue);
					  user.getAlbum(user.albumToLoad).getPhoto(galleryAdapter.getItem(menuInfo.position).toString()).deleteTag(removeTag);

				  }
				  else{
						Toast.makeText(getBaseContext(), "Invalid tag", Toast.LENGTH_LONG).show();
				  }
				}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});
				alert.show();
				
				
				alert.setTitle("Remove Tag");
				alert.setMessage("Type in type for tag to remove");

				// Set an EditText view to get user input 
				final EditText input2 = new EditText(AlbumScreen.this);
				alert.setView(input2);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  tagType = input2.getText().toString();
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				break;
			}
			
		}
		tagType = ""; tagValue = "";
		return super.onContextItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		writeToSD();
		Intent i = new Intent(getApplicationContext(), HomeScreen.class);
		startActivity(i);
	}
	
	
}
