/**
 * 
 */
package com.arbetsformedlingen.activity;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.ContactDetailEntity;
import com.arbetsformedlingen.entity.ContactPersonEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;
import com.arbetsformedlingen.network.HttpConnectionCallback;

/**
 * @author aramesan
 * 
 */
public abstract class GlobalDetailActivity extends Activity implements
		HttpConnectionCallback {
	private static final String SHARE_URL = "http://www.arbetsformedlingen.se/4.1799db4911df80d2fa9800024.html?id=";
	protected WebView webview;
	protected boolean isFavroite;
	protected Object entity;
	public String mLocale = "sv";
	static String mTmpLocale;
	private Dialog dialog;
	private AlertDialog listDialog;
	private ArrayList<String> languageList;
	private boolean isShowLanguageDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detaillayout);

		webview = (WebView) findViewById(R.id.contentWebView);
		webview.setWebViewClient(new AdvancedWebClient());

		languageList = new ArrayList<String>();
		languageList.add((String) getResources().getText(
				R.string.swedishLanguage));
		languageList.add((String) getResources().getText(
				R.string.arabicLanguage));
		languageList.add((String) getResources().getText(
				R.string.englishLanguage));
		languageList.add((String) getResources().getText(
				R.string.frenchLanguage));
		languageList.add((String) getResources().getText(
				R.string.russianLanguage));
		languageList.add((String) getResources().getText(
				R.string.spanishLanguage));

	}

	private void showDialog(String title, ArrayList<String> listArr,
			OnItemClickListener onItemClickListener) {
		final AlertDialog.Builder dialogBuilder;
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View dialogLayout = inflater.inflate(R.layout.list_dialog, null);
		ListView itemList = (ListView) dialogLayout
				.findViewById(R.id.dialog_list);

		itemList.setAdapter(new DialogAdapter(getApplicationContext(), listArr));
		itemList.setOnItemClickListener(onItemClickListener);
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setView(dialogLayout);
		dialogBuilder.setTitle(null);
		listDialog = dialogBuilder.create();
		listDialog.setTitle(title);
		listDialog.setInverseBackgroundForced(true);
		listDialog.show();
	}

	private class DialogAdapter extends BaseAdapter {

		private ArrayList<String> listArr;
		private Context context;

		public DialogAdapter(Context context, ArrayList<String> listArr) {
			this.context = context;
			this.listArr = listArr;
		}

		@Override
		public int getCount() {
			return listArr.size();
		}

		@Override
		public Object getItem(int position) {
			return listArr.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String item = listArr.get(position);
			String[] codeLangArr = item.split("#");
			View view = null;
			try {
				TextView textView = null;
				if (convertView == null) {
					view = (View) LayoutInflater.from(context).inflate(
							R.layout.dialog_item_row, parent, false);
				} else {
					view = convertView;
				}
				textView = (TextView) view.findViewById(R.id.dialog_text);
				view.setTag(codeLangArr[1]);
				textView.setTypeface(Typeface.createFromAsset(
						GlobalDetailActivity.this.getAssets(),
						"fonts/DejaVuSans.ttf"));
				textView.setText(codeLangArr[0]);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("DialogAdapter",
						"getView :: " + "Exception :: " + e.toString());
			}
			return view;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.addfavorite:
			if (entity instanceof JobDetailEntity) {
				GlobalBaseActivity.tracker.trackEvent("Favorite", "Job",
						"Android", 9);

				getContentResolver().insert(
						JobDetailEntity.JOBDETAIL_CONTENT_URI,
						((JobDetailEntity) entity).getJobDetailValue());
				isFavroite = true;
			}

			break;
		case R.id.mail:
			String to = "kundtjanst@arbetsformedlingen.se",
			subject = "",
			body = "";

			if (entity instanceof JobDetailEntity) {
				GlobalBaseActivity.tracker.trackEvent("JobShared", "mail",
						"Android", 4);
				JobDetailEntity tempEntity = (JobDetailEntity) entity;
				to = tempEntity.getEmail();
				subject = tempEntity.getTitle();
				body = "";

			} else if (entity instanceof ContactDetailEntity) {
				ContactDetailEntity tempEntity = (ContactDetailEntity) entity;
				to = tempEntity.getEmailAddress();
				subject = tempEntity.getOfficeName();
			}

			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/html");
			intent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { to });
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
			if (body != null)
				intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
			else
				intent.putExtra(Intent.EXTRA_TEXT, "");
			Log.d("GlobalDetailActivity", "onOptionsItemSelected :: "
					+ "to :: " + to);
			startActivity(Intent.createChooser(intent,
					"Select email application."));
			break;
		case R.id.contact:
			if (entity instanceof JobDetailEntity) {
				JobDetailEntity tempEntity = (JobDetailEntity) entity;
				if (tempEntity.contactPersonsList != null
						&& tempEntity.contactPersonsList.size() > 0) {
					ArrayList<String> numbers = new ArrayList<String>();
					for (ContactPersonEntity person : tempEntity.contactPersonsList) {
						if (!TextUtils.isEmpty(person.getPhoneNumber()))
							numbers.add(person.getName() + "#"
									+ person.getPhoneNumber());
					}
					if (numbers.size() > 0)
					{
						isShowLanguageDialog=false;
						showDialog(
								(String) getResources().getText(
										R.string.contact), numbers,
								phnumberItemClickListner);
					}
					else
						Toast.makeText(this,
								GlobalBaseActivity.NO_PHONE_NUMBERS_FOUND, 500)
								.show();
				} else {
					Toast.makeText(this,
							GlobalBaseActivity.NO_PHONE_NUMBERS_FOUND, 500)
							.show();
				}
			} else if (entity instanceof ContactDetailEntity) {
				ContactDetailEntity tempEntity = (ContactDetailEntity) entity;
				Intent newIntent = new Intent(
						android.content.Intent.ACTION_CALL);
				newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				newIntent
						.setData(Uri.parse("tel:" + tempEntity.getTelNumber()));
				startActivity(newIntent);
			}

			break;
		case R.id.geoPoint:
			String location = "";
			intent = new Intent(this, GlobalMapActivity.class);
			if (entity instanceof JobDetailEntity) {
				JobDetailEntity tempEntity = (JobDetailEntity) entity;
				location = tempEntity.getVisitorTown() + ", "
						+ tempEntity.getVisitorAddress();
			} else if (entity instanceof ContactDetailEntity) {
				ContactDetailEntity tempEntity = (ContactDetailEntity) entity;
				location = tempEntity.getAddress() + ", "
						+ tempEntity.getPlace();
			}
			Log.d("GlobalDetailActivity", "onOptionsItemSelected :: "
					+ "location :: " + location);
			intent.putExtra("location", location);
			startActivity(intent);
			break;
		case R.id.share:
			dialog = new Dialog(GlobalDetailActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog);
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			double width = (dm.widthPixels * 0.80);
			int widthI = (int) width;
			Log.d("GlobalDetailActivity", "onOptionsItemSelected :: "
					+ "widthI :: " + widthI);
			dialog.getWindow().setLayout(widthI, LayoutParams.WRAP_CONTENT);
			dialog.show();

			TextView dialogText = (TextView) dialog
					.findViewById(R.id.dialog_title_text);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				dialogText.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			dialogText.setText(R.string.share);

			Button twitterButton = (Button) dialog.findViewById(R.id.twitter);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				twitterButton.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			twitterButton.setText(R.string.twitter);
			twitterButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent settingIntent = new Intent(
							GlobalDetailActivity.this, SharingActivity.class);
					if (entity instanceof JobDetailEntity) {
						settingIntent.putExtra("Type", (String) getResources()
								.getText(R.string.twitter));
						settingIntent.putExtra("Message",
								((JobDetailEntity) entity).getTitle());
						settingIntent.putExtra("URL", SHARE_URL
								+ ((JobDetailEntity) entity).getJobId());
					}
					startActivity(settingIntent);
					dialog.dismiss();

				}

			});

			Button facebookButton = (Button) dialog.findViewById(R.id.facebook);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				facebookButton.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			facebookButton.setText(R.string.facebook);
			facebookButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent settingIntent = new Intent(
							GlobalDetailActivity.this, SharingActivity.class);
					if (entity instanceof JobDetailEntity) {
						settingIntent.putExtra("Type", (String) getResources()
								.getText(R.string.facebook));
						settingIntent.putExtra("Message",
								((JobDetailEntity) entity).getTitle());
						settingIntent.putExtra("URL", SHARE_URL
								+ ((JobDetailEntity) entity).getJobId());
					}
					startActivity(settingIntent);
					dialog.dismiss();
				}

			});

			Button linkedInButton = (Button) dialog.findViewById(R.id.linkedin);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				linkedInButton.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			linkedInButton.setText(R.string.linkedIn);
			linkedInButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent settingIntent = new Intent(
							GlobalDetailActivity.this, SharingActivity.class);
					if (entity instanceof JobDetailEntity) {
						settingIntent.putExtra("Type", (String) getResources()
								.getText(R.string.linkedIn));
						settingIntent.putExtra("Message",
								((JobDetailEntity) entity).getTitle());
						settingIntent.putExtra("URL", SHARE_URL
								+ ((JobDetailEntity) entity).getJobId());
					}
					startActivity(settingIntent);
					dialog.dismiss();
				}

			});

			Button browserButton = (Button) dialog.findViewById(R.id.browser);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				browserButton.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			browserButton.setText(R.string.open_In_Browser);
			browserButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = SHARE_URL
							+ ((JobDetailEntity) entity).getJobId();
					Log.d("GlobalDetailActivity", "onClick :: " + "url :: "
							+ url);
					if (entity instanceof JobDetailEntity
							&& !TextUtils.isEmpty(url)) {
						if (url.indexOf("http://") == -1) {
							url = "http://" + url;
						}
						Log.d("GlobalDetailActivity.onOptionsItemSelected",
								"onClick :: " + "url :: " + url);
						Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(url));
						startActivity(newIntent);
					}
					dialog.dismiss();
				}

			});

			Button mailButton = (Button) dialog.findViewById(R.id.mail);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				mailButton.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			mailButton.setText(R.string.mail);
			mailButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (entity instanceof JobDetailEntity) {
						JobDetailEntity tempEntity = (JobDetailEntity) entity;
						Intent newIntent = new Intent(Intent.ACTION_SEND);
						String to = "";
						Log.d("GlobalDetailActivity", "onClick :: " + "to :: "
								+ to);
						String subject = TextUtils.isEmpty(tempEntity
								.getTitle()) ? "" : tempEntity.getTitle();
						String body = "<a href='" + SHARE_URL
								+ ((JobDetailEntity) entity).getJobId() + "'>"
								+ SHARE_URL
								+ ((JobDetailEntity) entity).getJobId()
								+ "</a>";
						newIntent.setType("text/html");
						newIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
								new String[] { to });
						newIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
						if (body != null)
							newIntent.putExtra(Intent.EXTRA_TEXT,
									Html.fromHtml(body));
						else
							newIntent.putExtra(Intent.EXTRA_TEXT, "");
						startActivity(Intent.createChooser(newIntent,
								"Select email application."));
					}
					dialog.dismiss();
				}

			});

			Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
			if (GlobalBaseActivity.currentLocale != null
					&& GlobalBaseActivity.currentLocale.equals("ar")) {
				cancelButton.setTypeface(Typeface.createFromAsset(
						this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			cancelButton.setText(R.string.cancel);
			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}

			});
			break;
		case R.id.browser:
			String translateText = (String) getResources().getText(
					R.string.translate);
			isShowLanguageDialog=true;
			showDialog(translateText, languageList, langItemClickListner);
			break;

		case R.id.delete:
			if (entity instanceof JobDetailEntity) {
				int delCount = getContentResolver().delete(
						JobDetailEntity.JOBDETAIL_CONTENT_URI,
						JobDetailEntity.ID_COLUMN + "='"
								+ ((JobDetailEntity) entity).getJobId() + "'",
						null);
				Log.d("GlobalWebView", "getContentResolver() delete :: "
						+ "delCount :: " + delCount);
				finish();
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public OnItemClickListener langItemClickListner = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			String localecode = (String) view.getTag();
			JobDetailEntity jobDetailEntity = (JobDetailEntity) entity;

			String[] params = new String[] {localecode, jobDetailEntity.getTitle(),
					jobDetailEntity.getJobText(),
					jobDetailEntity.getWorkDuration(),
					jobDetailEntity.getMisc(), jobDetailEntity.getPayroll() };

			mTmpLocale = localecode;
			AppController controller = new AppController(GlobalDetailActivity.this);
			controller.getMicrosoftTranslateJobDetail(params);
			if (listDialog != null)
				listDialog.dismiss();
		}
	};

	public OnItemClickListener phnumberItemClickListner = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			String num = (String) view.getTag();
			num = num.replace(" ", "");
			num = num.replace("-", "");
			Log.d("GlobalWebView", "phnumberItemClickListner :: " + "num :: "
					+ num);
			Intent newIntent = new Intent(android.content.Intent.ACTION_CALL);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			newIntent.setData(Uri.parse("tel:" + num));
			startActivity(newIntent);
			if (listDialog != null)
				listDialog.dismiss();
		}

	};

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detailmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private class AdvancedWebClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d("GlobalWebView.InsideWebViewClient",
					"shouldOverrideUrlLoading :: " + "url :: " + url);
			int indexOfColon = url.indexOf(":");
			if (indexOfColon <= 0)
				return false;
			String commandString = url.substring(0, indexOfColon);
			if (commandString.equals("mailto")) {
				HashMap<String, String> params = new HashMap<String, String>();
				String[] arr = URLDecoder.decode(url).split("\\?");
				String to = arr[0].replaceFirst("mailto:", "");
				String bcc = (String) params.get("bcc");
				String subject = (String) params.get("subject");
				String body = (String) params.get("body");

				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/html");
				i.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { to });
				if (bcc != null && !bcc.equals(""))
					i.putExtra(Intent.EXTRA_CC, new String[] { bcc });
				i.putExtra(Intent.EXTRA_SUBJECT, subject);
				if (body != null)
					i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
				else
					i.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(i,
						"Select email application."));

			}
			if (commandString.equals("tel")) {
				Intent callIntent = new Intent(Intent.ACTION_CALL,
						Uri.parse(url));
				startActivity(callIntent);

			}
			if (commandString.equals("http")) {
				Log.d("hyperlink url :", url);
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(myIntent);
			}
			return true;
		}
	}

}
