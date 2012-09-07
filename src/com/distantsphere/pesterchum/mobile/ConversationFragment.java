package com.distantsphere.pesterchum.mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragment;


public class ConversationFragment extends SherlockFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
	    	super.onActivityCreated(savedInstanceState);

		}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Log.d("Conversation", "onCreateView");
		View view = inflater.inflate(R.layout.fragment_conversation, container, false);
		
		//EditText edittext = (EditText) view.findViewById(R.id.editText_message);
		//edittext.requestFocus();
		
		return view;
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
	/*public void setText(String item) {
		TextView view = (TextView) getView().findViewById(R.id.detailsText);
		view.setText(item);
	}*/
}
