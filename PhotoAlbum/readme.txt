Android PhotoAlbum

To accurately test, data/data/com.example.photoalbum must have read/write permission to write to sdcard and mnt/sdcard/Pictures must have read/write permission. All photos to look for must be saved in mnt/sdcard/Pictures.

HomeScreen:
On the home screen there is a list of albums. On click they will open to display photos in the album. On long press a user can delete or rename the album chosen. "Search Tags" button will prompt the user to enter a tag type and tag value. It will then create a new album with all the photos found called "newTagAlbum." On cancel or back pressed nothing will happen. "Add Album" button prompts the user to input an album name to create a new album. On cancel or back pressed nothing will happen. On the home screen pause the user information stored is written to the data/data/com.example.photoalbum/files/1.data file.




AlbumScreen:
On the album screen the list of photos in the album opened will be displayed. If there are no photos the default android image will be displayed. The image is shown at top center of the screen, with a manual slideshow feature present in the back and forward buttons at the left and right of the screen. The "Add Photo" button allows a user to add a photo from the mnt/sdcard/Pictures directory. On back pressed nothing happens. The gallery at the bottom of the screen is scrollable. It is composed of the thumbnails of all the photos in the album. When you click on the thumbnail the image is displayed. Long clicking a photo displays a menu to remove the photo, add a tag for the photo, display the photo tags, move photo, and delete tag.

OnLongPress Photo Options:
Remove photo removes the photo from the album.
Add tag prompts the user to input tag and value, which is added to the photo.
Display Tags displays the tags of the file.
Move photo prompts the user to input an album to move the photo to. If the photo does not exist, a toast indicating that the album specified is invalid opens.
Delete tag prompts the user to enter a tag type and tag value to remove a tag from the photo.

