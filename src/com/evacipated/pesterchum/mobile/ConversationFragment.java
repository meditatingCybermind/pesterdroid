package com.evacipated.pesterchum.mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
		final View view = inflater.inflate(R.layout.fragment_conversation, container, false);
		
		Button sendbtn = (Button) view.findViewById(R.id.button_send);
		sendbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendMessage(view);
			}
		});
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		float size = Float.valueOf((prefs.getString("chat_fontsize", "11"))).floatValue();
		
		EditText text_conversation = (EditText) getView().findViewById(R.id.editText_conversation);
		text_conversation.setTextSize(size);
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	public void sendMessage(View view) {
    	EditText text_message = (EditText) view.findViewById(R.id.editText_message);
    	String message = text_message.getText().toString();
    	text_message.setText(null);
    	if (message.length() == 0)
    		return;
    	PesterText text_conversation = (PesterText) view.findViewById(R.id.editText_conversation);
    	
    	text_conversation.addMessage(message);
    	
    	text_conversation.setSelection(text_conversation.end());
    }
}
