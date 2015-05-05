package com.actionbardemo.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

public class ProgressDialogFragment extends DialogFragment
{
	private ProgressDialog progressDialog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(getString(R.string.app_loading));
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);

		/**
		 * Disable the back button
		 */
		OnKeyListener keyListener = new OnKeyListener()
		{

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
			{

				if (keyCode == KeyEvent.KEYCODE_BACK)
				{
					return true;
				}
				return false;
			}

		};
		progressDialog.setOnKeyListener(keyListener);
		return progressDialog;
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		progressDialog.dismiss();
		super.onDismiss(dialog);
	}
}
