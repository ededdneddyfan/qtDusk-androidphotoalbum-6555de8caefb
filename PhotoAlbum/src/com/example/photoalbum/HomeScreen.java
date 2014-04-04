package com.example.photoalbum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cs213.photoAlbum.model.*;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeScreen extends Activity {

	//Buttons
	Button add, search;


	//User class
	User user;

	ListView albumList;

	ArrayAdapter<Album> aAdapter;


	/*
	 * 
	 * Check to see if external storage is available
	 */
	public boolean checkAvailable(){

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		return mExternalStorageAvailable&&mExternalStorageWriteable;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);




		/*
		 * Adds the saved state of user
		 */

		//		File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"cache" + File.separator);
		//		System.out.println(directory.getAbsolutePath());
		//	
		//		if(!directory.exists())
		//			directory.mkdirs();

		//	    File file = getFileStreamPath("1.data");
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



		albumList = (ListView)findViewById(R.id.listView);
		aAdapter = new ArrayAdapter<Album>(this, android.R.layout.simple_gallery_item, user.userAlbums);

		albumList.setAdapter(aAdapter);
		aAdapter.notifyDataSetChanged();



		add = (Button)findViewById(R.id.addButton);
		search = (Button)findViewById(R.id.searchButton);
		
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder alert = new AlertDialog.Builder(HomeScreen.this);
				alert.setTitle("Add Album");
				alert.setMessage("Type in name for new album");

				// Set an EditText view to get user input 
				final EditText input = new EditText(HomeScreen.this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  Album newAlbum = new Album(value);
				  user.addAlbum(newAlbum);
				  aAdapter.notifyDataSetChanged();
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				aAdapter.notifyDataSetChanged();

			}
		});
		search.setOnClickListener(new View.OnClickListener() {

			String tagType, tagValue;
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Builder alert = new AlertDialog.Builder(HomeScreen.this);
				alert.setTitle("Find Tags");
				alert.setMessage("Type in tag value");

				// Set an EditText view to get user input 
				final EditText input = new EditText(HomeScreen.this);
				alert.setView(input);
								
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					tagValue = input.getText().toString();
					System.out.println("TagType is " + tagType + "tagvalue is "+ tagValue);
					Album tagAlbum = new Album("newTagAlbum");
					
					for(int i = 0; i<user.userAlbums.size();i++){
						for(int x = 0;x<user.userAlbums.get(i).photos.size();x++){
							for(int z = 0;z<user.userAlbums.get(i).photos.get(x).photoTag.size();z++){
								if(user.userAlbums.get(i).photos.get(x).photoTag.get(z).type.equalsIgnoreCase(tagType)){
									String oldTagValue = user.userAlbums.get(i).photos.get(x).photoTag.get(z).value.toLowerCase();
									System.out.println("TagType is OKAY");
									if(oldTagValue.contains(tagValue.toLowerCase())){
										tagAlbum.addPhotos(user.userAlbums.get(i).photos.get(x));
										System.out.println("Added photo to new album");
										break;
									}
								}
							}
						}
					}
					
					user.addAlbum(tagAlbum);
					user.albumToLoad = tagAlbum.albumName;
					writeToSD();
					aAdapter.notifyDataSetChanged();
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
					  return;
				  }
				});

				alert.show();
				
				
				alert = new AlertDialog.Builder(HomeScreen.this);
				alert.setTitle("Find Tag");
				alert.setMessage("Type in tag type");

				// Set an EditText view to get user input 
				final EditText input2 = new EditText(HomeScreen.this);
				alert.setView(input2);
				
				
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					tagType = input2.getText().toString();
					
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
					  return;
				  }
				});

				alert.show();
				
			}
			
		});


		albumList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), AlbumScreen.class);
				user.albumToLoad = albumList.getAdapter().getItem(position).toString();
				
				writeToSD();
				startActivity(i);
			}
		
		});
		
		albumList.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
				menu.setHeaderTitle("Menu");
				menu.add(0, 1 ,0, "Delete this album");
				menu.add(0, 2, 0, "Rename this album");
			}

//

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
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

			//Delete Album
			case 1: {
				String albumToDelete = albumList.getAdapter().getItem(menuInfo.position).toString();
				user.deleteAlbum(albumToDelete);
				aAdapter.notifyDataSetChanged();
				return true;
			}
	
			//Rename Album
			case 2: {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("Rename Album Window");
				alert.setMessage("Rename album to different name");

				// Set an EditText view to get user input 
				final EditText input = new EditText(this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  user.renameAlbum(albumList.getAdapter().getItem(menuInfo.position).toString(), value);
				  aAdapter.notifyDataSetChanged();
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				return true;
			}
			
		}
		return super.onContextItemSelected(item);
	}



}
