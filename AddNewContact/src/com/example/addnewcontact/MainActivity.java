package com.example.addnewcontact;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	final static String LOG_TAG = "PocketMagic";
	// -
	TextView tv1;
	EditText et1, et2, et3, et4, et5, et6, et7;
	Button b1;
	// -
	final static int idb1 = Menu.FIRST + 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// Hide titlebar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		ScrollView vscroll = new ScrollView(this);
		vscroll.setFillViewport(false);
		// panel in scroll: add all controls/ objects to this layout
		LinearLayout m_panel = new LinearLayout(this);
		m_panel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		m_panel.setOrientation(LinearLayout.VERTICAL);
		m_panel.setPadding(10, 10, 10, 10);
		vscroll.addView(m_panel);

		// Create some controls
		tv1 = new TextView(this);
		tv1.setText("Add a contact");
		m_panel.addView(tv1);

		et1 = new EditText(this);
		et1.setHint("Enter Firstname");
		et1.setText("Undertaker");
		m_panel.addView(et1);
		et2 = new EditText(this);
		et2.setHint("Enter Lastname");
		et2.setText("Tendulkar");
		m_panel.addView(et2);
		et3 = new EditText(this);
		et3.setHint("Enter Phone");
		et3.setText("001002003");
		m_panel.addView(et3);
		et4 = new EditText(this);
		et4.setHint("Enter Mobile");
		et4.setText("101002005");
		m_panel.addView(et4);
		et5 = new EditText(this);
		et5.setHint("Enter Company");
		et5.setText("Pocketmagic");
		m_panel.addView(et5);
		et6 = new EditText(this);
		et6.setHint("Enter Email");
		et6.setText("radu.motisan@gmail.com");
		m_panel.addView(et6);
		et7 = new EditText(this);
		et7.setHint("Enter Website");
		et7.setText("http://www.pocketmagic.net");
		m_panel.addView(et7);
		b1 = new Button(this);
		b1.setId(idb1);
		b1.setOnClickListener((OnClickListener) this);
		b1.setText("Add Contact!");
		m_panel.addView(b1);

		setContentView(vscroll);
	}

	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		int id = arg0.getId();
		if (id == idb1)
		{
			SaveContact();

		}
	}

	public void SaveContact()
	{
		// Get text
		String szFirstname = et1.getText().toString(), szLastname = et2.getText().toString(), szPhone = et3.getText().toString(), szMobile = et4.getText()
				.toString(), szCompany = et5.getText().toString(), szEmail = et6.getText().toString(), szWeb = et7.getText().toString();

		// Create a new contact entry!
		String szFullname = szFirstname + " " + szLastname;

		if (contactExists(this, szMobile))
		{
			Toast.makeText(getBaseContext(), "Contact Already Exists!", Toast.LENGTH_SHORT).show();
		}
		else
		{

			ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
			int rawContactInsertIndex = ops.size();

			ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(RawContacts.ACCOUNT_TYPE, null)
					.withValue(RawContacts.ACCOUNT_NAME, null).build());
			// INSERT NAME
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, szFullname) // Name
																											// of
																											// the
																											// person
					.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, szLastname) // Name
																										// of
																										// the
																										// person
					.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, szFirstname) // Name
																										// of
																										// the
																										// person
					.build());
			// INSERT PHONE
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, szPhone) // Number of
																						// the
																						// person
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK).build()); //
			// INSERT MOBILE
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, szMobile) // Number of
																						// the
																						// person
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); //

			// INSERT FAX
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "12344")
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK).build()); //

			// INSERT EMAIL
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA, szEmail)
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK).build()); //
			// INSERT WEBSITE
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Website.URL, szWeb) //
					.withValue(ContactsContract.CommonDataKinds.Website.TYPE, ContactsContract.CommonDataKinds.Website.TYPE_WORK).build()); //
			// SAVE CONTACT IN BCR Structure
			Uri newContactUri = null;
			// PUSH EVERYTHING TO CONTACTS
			try
			{
				ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
				if (res != null && res[0] != null)
				{
					newContactUri = res[0].uri;
					Log.d(LOG_TAG, "URI added contact:" + newContactUri);
				}
				else
					Log.e(LOG_TAG, "Contact not added.");
			}
			catch (RemoteException e)
			{
				// error
				Log.e(LOG_TAG, "Error (1) adding contact.");
				newContactUri = null;
			}
			catch (OperationApplicationException e)
			{
				// error
				Log.e(LOG_TAG, "Error (2) adding contact.");
				newContactUri = null;
			}
			Log.d(LOG_TAG, "Contact added to system contacts.");

			if (newContactUri == null)
			{
				Log.e(LOG_TAG, "Error creating contact");
			}

			Toast.makeText(getBaseContext(), "Contact Added!", Toast.LENGTH_SHORT).show();
		}

	}

	public static boolean contactExists(Context context, String displayName)
	{
		if (!TextUtils.isEmpty(displayName))
		{
			// Checking if the name already exists in contacts then
			// dont add the contact.
			Uri uri = ContactsContract.Data.CONTENT_URI;
			String[] projection = new String[] { PhoneLookup._ID };
			String selection = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME + " = ?";
			String[] selectionArguments = { displayName };
			Cursor cur = context.getContentResolver().query(uri, projection, selection, selectionArguments, null);

			// Uri lookupUri =
			// Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
			// Uri.encode(displayName));
			// String[] mNameProjection = {
			// ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };
			// Cursor cur =
			// context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
			// mNameProjection, null, null, null);
			try
			{
				if (cur.moveToFirst())
				{
					return true;
				}
			}
			finally
			{
				if (cur != null)
					cur.close();
			}
			return false;
		}
		else
		{
			return false;
		}
	}

	public boolean contactExists(Activity _activity, String number)
	{
		if (number != null)
		{
			Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
			String[] mPhoneNumberProjection = { PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME };
			Cursor cur = _activity.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
			try
			{
				if (cur.moveToFirst())
				{
					return true;
				}
			}
			finally
			{
				if (cur != null)
					cur.close();
			}
			return false;
		}
		else
		{
			return false;
		}
	}
}