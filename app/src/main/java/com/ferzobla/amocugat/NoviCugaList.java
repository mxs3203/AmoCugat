package com.ferzobla.amocugat;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
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
	String listName;
	ArrayList<EditText> pravila = new ArrayList<EditText>();
	ArrayList<TextView> cards = new ArrayList<TextView>();
	TableLayout mainTable;
	Button saveButton;
	public static boolean dbCreated = false;
	public AmoCugatDialogs acdDialogs;

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

		for(int i =1; i<= 14; i++)
		{
			TableRow tableRow = new TableRow(getActivity());
			EditText editText = new EditText(getActivity());
			TextView textView = new TextView(getActivity());

			editText.setTextColor(Color.BLACK);
            editText.setWidth(editTextWidth);
			textView.setTextColor(Color.BLACK);
            textView.setWidth(textViewWidth);
			if(i > 1) {
				textView.setText("Card " + i + ": ");
			}
			else if(i == 1){
				textView.setText("List Name: ");
			}
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


			for(int i =0; i<pravila.size(); i++)
			{
				if(getFromPravila(0).equalsIgnoreCase(""))
				{
					createDialog(1);
					hasError = true;
					break;
				}
				if(getFromPravila(i).equalsIgnoreCase(""))
				{
					createDialog(3);
					hasError = true;
					break;
				}

			}

			listName = getFromPravila(0);
			database = new DataBaseConnect();
			if (database.checkTableName(listName)){
				createDialog(2);
				hasError = true;
			}


			if(!hasError){

				for(int i =0; i<pravila.size(); i++)
				{
					database.insertCard(listName,i , pravila.get(i).getText().toString());
				}

				dbCreated = true;
				pravila.remove(0);
				getActivity().getSupportFragmentManager().popBackStack();
			}
		}
	};

	public String getFromPravila(int index){
		return pravila.get(index).getText().toString();
	}

	public void createDialog(int version)
	{
		acdDialogs.nclDialogs(version);
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

	public String getListName(){
		return listName;
	}
	
}
