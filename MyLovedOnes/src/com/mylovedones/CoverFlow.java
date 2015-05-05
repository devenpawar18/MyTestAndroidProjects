//package com.android;
//
//import java.io.FileInputStream;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.LinearGradient;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.AvoidXfermode.Mode;
//import android.graphics.Bitmap.Config;
//import android.graphics.Shader.TileMode;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;

package com.mylovedones;

import java.io.FileInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CoverFlow extends Activity {

	/**
	 * @author devpawar
	 */

	/** Called when the activity is first created. */

	private TextView name;
	private TextView nickName;
	private TextView residence;
	private TextView company;
	private TextView phone;
	private ImageView birthdate;
	private Button gallery;
	CFlow coverFlow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flow);

		coverFlow = new CFlow(this);
		name = (TextView) findViewById(R.id.name);
		nickName = (TextView) findViewById(R.id.nickName);
		residence = (TextView) findViewById(R.id.residence);
		company = (TextView) findViewById(R.id.company);
		phone = (TextView) findViewById(R.id.phone);
		birthdate = (ImageView) findViewById(R.id.birthday);
		gallery = (Button) findViewById(R.id.gallery);
		coverFlow = (CFlow) findViewById(R.id.cf);
		coverFlow.setAdapter(new ImageAdapter(this));
		name.setText("Khushboo Ghumare");
		nickName.setText("Nick Name : khush");
		residence.setText("Residence : Kalyan");
		company.setText("Company : Infosys");
		final String s1 = "9768732217";
		phone.setText(Html.fromHtml("Contact No : " + "<u>" + s1 + "</u>"));
		phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent out = new Intent();
				out.setAction(Intent.ACTION_DIAL);
				out.setData(Uri.parse("tel:" + Uri.encode(s1)));
				startActivity(out);

			}

		});
		birthdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "16th November", 3000)
						.show();

			}

		});
		gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						CoverFlow.this);
				dialog.setTitle("Do u want to visit Khushboo's Gallery ?");
				// dialog.setMessage(findUsListItem.getSubTitle());
				dialog.setPositiveButton("Yes",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(CoverFlow.this,
										GalleryExample.class);
								int[] Imgid = { R.drawable.khush10,
										R.drawable.khush9, R.drawable.khush11,
										R.drawable.khush6, R.drawable.khush4,
										R.drawable.khush14, R.drawable.khush12,
										R.drawable.khush7, R.drawable.khush8,
										R.drawable.khush1, R.drawable.khush2,
										R.drawable.khush5, R.drawable.khush3,
										R.drawable.khush13, R.drawable.khush15 };
								intent.putExtra("khush", Imgid);
								startActivity(intent);
							}
						});
				dialog.setNegativeButton("No",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				dialog.show();

			}
		});

		ImageAdapter coverImageAdapter = new ImageAdapter(this);

		// coverImageAdapter.createReflectedImages();

		coverFlow.setAdapter(coverImageAdapter);
		coverFlow.setSpacing(-55);
		coverFlow.setSelection(0, true);
		coverFlow.setAnimationDuration(1000);

		coverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					name.setText("Khushboo Ghumare");
					nickName.setText("Nick Name : khush");
					residence.setText("Residence : Kalyan");
					company.setText("Company : Infosys");
					final String s1 = "9768732217";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s1
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s1)));
							startActivity(out);

						}
					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"16th November", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Khushboo's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.khush10,
													R.drawable.khush9,
													R.drawable.khush11,
													R.drawable.khush6,
													R.drawable.khush4,
													R.drawable.khush14,
													R.drawable.khush12,
													R.drawable.khush7,
													R.drawable.khush8,
													R.drawable.khush1,
													R.drawable.khush2,
													R.drawable.khush5,
													R.drawable.khush3,
													R.drawable.khush13,
													R.drawable.khush15 };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 1:
					name.setText("Deven Pawar");
					nickName.setText("Nick Name : dabbangg");
					residence.setText("Residence : Parel");
					company.setText("Company : CapGemini");
					final String s2 = "9930486071";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s2
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s2)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"2nd August", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Deven's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.icon,
													R.drawable.icon,
													R.drawable.icon,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 2:
					name.setText("Gaurang Manjrekar");
					nickName.setText("Nick Name : gau");
					residence.setText("Residence : Kurla");
					company.setText("Company : Cognizant");
					final String s3 = "7667104619";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s3
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s3)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"22nd September", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Gaurang's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.gau1,
													R.drawable.gau2,
													R.drawable.gau3,
													R.drawable.gau4,
													R.drawable.gau5,
													R.drawable.gau6,
													R.drawable.gau7 };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 3:
					name.setText("Pooja Mahajan");
					nickName.setText("Nick Name : poo");
					residence.setText("Residence : Dombivali");
					company.setText("Company : Infrasoft Technologies");
					final String s4 = "8082442730";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s4
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s4)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"24th June", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Pooja's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.poo,
													R.drawable.poo1,
													R.drawable.poo,
													R.drawable.poo1,
													R.drawable.poo,
													R.drawable.poo1,
													R.drawable.poo };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 4:
					name.setText("Abhay Chaubey");
					nickName.setText("Nick Name : bhaiyaa");
					residence.setText("Residence : Badlapur");
					company.setText("Company : Atos Origin");
					final String s5 = "9503275361";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s5
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s5)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"12th January", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Abhay's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.ab,
													R.drawable.ab1,
													R.drawable.ab2,
													R.drawable.ab,
													R.drawable.ab1,
													R.drawable.ab2,
													R.drawable.ab };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 5:
					name.setText("Jaya Krishnan");
					nickName.setText("Nick Name : jk");
					residence.setText("Residence : Dombovali");
					company.setText("Company : Infosys");
					final String s6 = "9543684823";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s6
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s6)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"19th November", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Jaya's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.jaya,
													R.drawable.jaya,
													R.drawable.jaya,
													R.drawable.jaya,
													R.drawable.jaya,
													R.drawable.jaya,
													R.drawable.jaya };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 6:
					name.setText("Sachin Kumbharkar");
					nickName.setText("Nick Name : tana");
					residence.setText("Residence : Kopar Khairane");
					company.setText("Company : Trans Asia");
					final String s7 = "9773549967";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s7
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s7)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"26th November", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Sachin's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.tanaji,
													R.drawable.tana1,
													R.drawable.tanaji,
													R.drawable.tana1,
													R.drawable.tanaji,
													R.drawable.tana1,
													R.drawable.tanaji };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 7:
					name.setText("Yogini Shinde");
					nickName.setText("Nick Name : yo");
					residence.setText("Residence : Nerul");
					company.setText("Company : L & T Infotech");
					final String s8 = "7411685658";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s8
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s8)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"8th August", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Yogini's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.yo,
													R.drawable.yo1,
													R.drawable.yo2,
													R.drawable.yo3,
													R.drawable.yo4,
													R.drawable.yo1,
													R.drawable.yo2 };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 8:
					name.setText("Siddharth Pujare");
					nickName.setText("Nick Name : kaka");
					residence.setText("Residence : Thane");
					company.setText("Company : Precious Alloys");
					final String s9 = "9773550448";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s9
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s9)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"28th February", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Siddharth's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.sidd,
													R.drawable.sidd1,
													R.drawable.sidd,
													R.drawable.sidd1,
													R.drawable.sidd1,
													R.drawable.sidd1,
													R.drawable.sidd1 };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 9:
					name.setText("Suraj Wagh");
					nickName.setText("Nick Name : suu");
					residence.setText("Residence : Nerul");
					company.setText("Company : Rheal Software Pvt Ltd");
					final String s10 = "9773554506";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s10
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s10)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"6th September", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Suraj's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.su2,
													R.drawable.su3,
													R.drawable.suraj,
													R.drawable.su2,
													R.drawable.su3,
													R.drawable.suraj,
													R.drawable.su2 };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 10:
					name.setText("Pooja Mukhne");
					nickName.setText("Nick Name : pooji");
					residence.setText("Residence : Thane");
					company.setText("Company : ");
					final String s11 = "9870478296";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s11
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s11)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"10th July", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Pooja's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;
				case 11:
					name.setText("Venkatram Selvraj");
					nickName.setText("Nick Name : venky");
					residence.setText("Residence : GTB Nagar");
					company.setText("Company : Infosys");
					final String s12 = "9579727710";
					phone.setText(Html.fromHtml("Contact No : " + "<u>" + s12
							+ "</u>"));
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent out = new Intent();
							out.setAction(Intent.ACTION_DIAL);
							out.setData(Uri.parse("tel:" + Uri.encode(s12)));
							startActivity(out);

						}

					});
					birthdate.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),
									"13th July", 3000).show();

						}

					});
					gallery.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									CoverFlow.this);
							dialog.setTitle("Do u want to visit Venky's Gallery ?");
							// dialog.setMessage(findUsListItem.getSubTitle());
							dialog.setPositiveButton(
									"Yes",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													CoverFlow.this,
													GalleryExample.class);
											int[] Imgid = { R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev,
													R.drawable.dev };
											intent.putExtra("khush", Imgid);
											startActivity(intent);
										}
									});
							dialog.setNegativeButton(
									"No",
									new android.content.DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
							dialog.show();

						}
					});

					break;

				default:
					break;
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;

		private FileInputStream fis;

		private Integer[] mImageIds = { R.drawable.khush, R.drawable.dev,
				R.drawable.gau, R.drawable.poo, R.drawable.ab, R.drawable.jaya,
				R.drawable.tanaji, R.drawable.yo, R.drawable.sidd,
				R.drawable.suraj, R.drawable.pooja, R.drawable.venky };

		private ImageView[] mImages;

		public ImageAdapter(Context c) {
			mContext = c;
			mImages = new ImageView[mImageIds.length];
		}

		public Bitmap createReflectedImages(Integer integer) {
			// The gap we want between the reflection and the original image
			final int reflectionGap = 0;
			Bitmap bitmapWithReflection = null;

			// int index = 0;
			int imageId = integer;
			Bitmap originalImage = BitmapFactory.decodeResource(getResources(),
					imageId);
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();

			// This will not scale but will flip on the Y axis
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);

			// Create a Bitmap with the flip matrix applied to it.
			// We only want the bottom half of the image
			Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
					height / 2, width, height / 2, matrix, false);

			// Create a new bitmap with same width but taller to fit
			// reflection
			bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 2), Config.ARGB_8888);

			// Create a new Canvas with the bitmap that's big enough for
			// the image plus gap plus reflection
			Canvas canvas = new Canvas(bitmapWithReflection);
			// Draw in the original image
			canvas.drawBitmap(originalImage, 0, 0, null);
			// Draw in the gap
			Paint deafaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap,
					deafaultPaint);
			// Draw in the reflection
			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

			// Create a shader that is a linear gradient that covers the
			// reflection
			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0,
					originalImage.getHeight(), 0,
					bitmapWithReflection.getHeight() + reflectionGap,
					0x70ffffff, 0x00ffffff, TileMode.CLAMP);
			// Set the paint to use this shader (linear gradient)
			paint.setShader(shader);
			// Set the Transfer mode to be porter duff and destination in
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			// Draw a rectangle using the paint with our linear gradient
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			// ImageView imageView = new ImageView(mContext);
			// imageView.setImageBitmap(bitmapWithReflection);
			// imageView.setLayoutParams(new CFlow.LayoutParams(120, 180));
			// imageView.setScaleType(ScaleType.MATRIX);
			// mImages[index++] = imageView;

			return bitmapWithReflection;
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			// Use this code if you want to load from resources
			ImageView i = new ImageView(mContext);
			Bitmap img = createReflectedImages(mImageIds[position]);
			i.setImageBitmap(img);
			i.setLayoutParams(new CFlow.LayoutParams(130, 130));
			i.setScaleType(ImageView.ScaleType.FIT_XY);

			// Make sure we set anti-aliasing otherwise we get jaggies
			BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
			drawable.setAntiAlias(true);
			return i;

			// return mImages[position];
		}

		/**
		 * Returns the size (0.0f to 1.0f) of the views depending on the
		 * 'offset' to the center.
		 */
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}

	}
}