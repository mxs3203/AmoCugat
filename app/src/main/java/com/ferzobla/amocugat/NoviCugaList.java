package com.ferzobla.amocugat;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class NoviCugaList extends Fragment
{
	private OnFragmentInteractionListener fragmentListener; 
	View mainView;
	CursorAdapter adapter;
	ListView list;
	public static DataBaseConnect database;
	String deckName;
	ArrayList<EditText> pravila = new ArrayList<EditText>();
	ArrayList<TextView> cards = new ArrayList<TextView>();
	TableLayout mainTable;
	Button saveButton;
	public static boolean dbCreated = false;

	public NoviCugaList()
	{
		
	}
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public View onCreateView(LayoutInflater s, ViewGroup g, Bundle b)
	{
		mainView = s.inflate(R.layout.layout, g,false);
		
		mainTable = (TableLayout)mainView.findViewById(R.id.TableLayout1);
		saveButton = (Button)mainView.findViewById(R.id.button1);
		saveButton.setOnClickListener(listener);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int textViewWidth = (int)(width * 0.2);
        int editTextWidth = (int)(width * 0.75);

		for(int i =1; i<= 13; i++)
		{
			TableRow tableRow = new TableRow(getActivity());
			EditText editText = new EditText(getActivity());
			TextView textView = new TextView(getActivity());

			editText.setTextColor(Color.BLACK);
            editText.setWidth(editTextWidth);
			textView.setTextColor(Color.BLACK);
            textView.setWidth(textViewWidth);
			textView.setText("Card " + i + ": ");

			tableRow.addView(textView);
			tableRow.addView(editText);
			mainTable.addView(tableRow);
			pravila.add(editText);
			cards.add(textView);
		}
		
		
		return mainView;
		
	}
	
	View.OnClickListener listener = new View.OnClickListener()
	{
		
		@Override
		public void onClick(View v) 
		{
			boolean hasError = false;
			// TODO Auto-generated method stub
			database = new DataBaseConnect(getActivity());
			database.deleteAll();
			
			for(int i =0; i<pravila.size(); i++)
			{
				if(pravila.get(i).getText().toString().equalsIgnoreCase(""))
				{
					createDialog();
					hasError = true;
					break;
				}
				else
				{
					hasError = false;
					database.insertCard(i , pravila.get(i).getText().toString());
				}
			}
			
			if(!hasError){
				dbCreated = true;
				getActivity().getSupportFragmentManager().popBackStack();
			}
		}
	};
	
	 public void createDialog()
	 {
		 	ImageView image = new ImageView(getActivity());
 			image.setImageResource(R.drawable.jesus);
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setView(image);
	        builder.setMessage("Glupane! Nemoze cugat bez svih pravila!")
	               .setPositiveButton("Kuzim...", new DialogInterface.OnClickListener() 
	               {
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                      dialog.dismiss(); 
	                   }
	               })
	               .setNegativeButton("Stvarno kuzim...", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                	   dialog.dismiss(); 
	                   }
	               });
	        AlertDialog bla = builder.create();
	        bla.show();
	       
	    }

	
	public interface OnFragmentInteractionListener
	{
		public void onFragmentInteraction();
	}
	@Override
	public void onResume()
	{
		super.onResume();
	}


	
}
