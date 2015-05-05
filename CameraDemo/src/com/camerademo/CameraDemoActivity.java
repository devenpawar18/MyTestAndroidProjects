package com.camerademo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraDemoActivity extends Activity
{
	private ImageView capturedImage;
	private Button takePicture;
	private Intent mIntent;
	private Uri outputFileUri;

	public static HashMap<Integer, Bitmap> imageMap = new HashMap<Integer, Bitmap>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String root = Environment.getExternalStorageDirectory().getAbsolutePath();

		// File.separator + "testdir" + File.separator + "subdir"
		File dir = new File(root + "/maindir/subdir");
		if (!dir.exists())
		{
			dir.mkdirs();
		}

		capturedImage = (ImageView) findViewById(R.id.image);
		takePicture = (Button) findViewById(R.id.take_picture);

		// Bitmap bitmap = retriveFile("test");
		// if (bitmap == null)
		// ;
		// else
		// capturedImage.setImageBitmap(bitmap);

		takePicture.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				String root = Environment.getExternalStorageDirectory().getAbsolutePath();
				File dir = new File(root + "/maindir/subdir");
				File file = new File(dir, "test.jpg");
				if (file.exists())
					file.delete();
				else
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				outputFileUri = Uri.fromFile(file);
				mIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				startActivityForResult(mIntent, 10);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		String path = "";
		if (resultCode == RESULT_OK)
		{
			if (requestCode == 10)
			{
				// Ideally We should handle the URI sent by Android system that we receive from
				// intent
				// But on Nexus S and HTC desire the intent or data are null. Hence providing my my
				// URI and using it to retrieve the picture.
				path = outputFileUri.toString();
				try
				{
					Bitmap bmp = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(Uri.parse(path)));

					capturedImage.setImageBitmap(bmp);

				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}

			}
			else
			{
				Drawable drawable = getResources().getDrawable(R.drawable.splashscreen);
				capturedImage.setImageBitmap(((BitmapDrawable) drawable).getBitmap());
			}
		}

	}

	@Override
	protected void onStop()
	{
		// File root = Environment.getExternalStorageDirectory();
		// File thumbsDirectory = new File(root.getAbsolutePath() + "/maindir/subdir");
		// try
		// {
		// delete(thumbsDirectory);
		// }
		// catch (IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		File root = Environment.getExternalStorageDirectory();
		File thumbsDirectory = new File(root.getAbsolutePath() + "/maindir/subdir");
		try
		{
			delete(thumbsDirectory);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroy();
	}

	public static void storeFile(Bitmap bitmapImage, String filename)
	{
		OutputStream outStream = null;
		String root = Environment.getExternalStorageDirectory().getAbsolutePath();

		File dir = new File(root + File.separator + "testImageDir" + File.separator);
		if (!dir.exists())
		{
			dir.mkdirs();
		}

		File imageFile = new File(dir, filename + ".jpg");
		if (imageFile.exists() == true)
		{
			imageFile.delete();
		}

		try
		{
			imageFile.createNewFile();
			outStream = new FileOutputStream(imageFile);
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			// bitmapImage.recycle();
			outStream.flush();
			outStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static Bitmap retriveFile(String filename)
	{
		Bitmap bMap = null;
		String root = Environment.getExternalStorageDirectory().getAbsolutePath();
		File dir = new File(root + File.separator + "testImageDir" + File.separator);

		String fName = filename + ".jpg";

		File file = new File(dir, fName);
		Log.d("temp exists : ", "file ecists  " + file.exists());
		if (file.exists() == true)
		{
			bMap = BitmapFactory.decodeFile(file.getAbsolutePath());

		}
		return bMap;

	}

	public static void delete(File file) throws IOException
	{
		if (file.isDirectory())
		{
			// directory is empty, then delete it
			if (file.list().length == 0)
			{
				file.delete();
			}
			else
			{
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files)
				{
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0)
				{
					file.delete();
				}
			}
		}
		else
		{
			// if file, then delete it
			file.delete();
		}
	}
}