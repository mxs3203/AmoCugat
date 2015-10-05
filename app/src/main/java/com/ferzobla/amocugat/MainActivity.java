package com.ferzobla.amocugat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends ActionBarActivity implements NoviCugaList.OnFragmentInteractionListener
{
	public static ArrayList<EditText> players = new ArrayList<EditText>();
	public static NoviCugaList ncl;
    public static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        c = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        public PlaceholderFragment() 
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void amoCugat(View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AmoCugatDialogs.listChoice(c);
            }
        });
        try{
            wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        String choice = AmoCugatDialogs.getChoice();
        if(choice != null){
            System.out.println("not null!!!");
            if(choice.equalsIgnoreCase("Create List")){
                ncl = new NoviCugaList();
                getSupportFragmentManager().beginTransaction().add(R.id.container,
                        ncl).addToBackStack(null).commit();
            }
            else{

            }
        }
        else{
            Toast.makeText(this, "Something went wrong, try again.", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    public void _startActivity() {
		Intent i = new Intent(this, AmoCugat.class);
        i.putExtra("listName",ncl.getListName());
		startActivity(i);
	}

	@Override
	public void onFragmentInteraction() {
		// TODO Auto-generated method stub
		System.out.println("here");
	}
    
}
