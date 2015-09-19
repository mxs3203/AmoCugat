package com.ferzobla.amocugat;

import java.util.ArrayList;

import com.ferzobla.amocugat.NoviCugaList.OnFragmentInteractionListener;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;



public class MainActivity extends ActionBarActivity implements OnFragmentInteractionListener
{
	public static ArrayList<EditText> players = new ArrayList<EditText>();
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        
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
    
    public void createDialog()
	 {
    		ImageView image = new ImageView(this);
    		image.setImageResource(R.drawable.why);
    	
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setView(image);
	        builder.setMessage("Moras Prvo napraviti pravila!")
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
    
    public void play(View v)
	{
    	
		if(NoviCugaList.dbCreated)
		{
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 
		        builder.setMessage("How many want to get drunk tonight?");
		        final EditText editText = new EditText(this);
		        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		        editText.setWidth(250);
				editText.setTextColor(Color.BLACK);
				builder.setView(editText);
		        
		               builder.setPositiveButton("That many!", new DialogInterface.OnClickListener() 
		               {
		                   public void onClick(DialogInterface dialog, int id) 
		                   {
		                      if(editText.getText().toString().equalsIgnoreCase(""))
		                      {
		                    	  Toast.makeText(getApplication(), "Do you want to get drunk or not?", Toast.LENGTH_LONG).show();
		                          
		                      }
		                      else
		                      {
		                    	  dialog.dismiss(); 
		                    	  setPlayers(Integer.parseInt(editText.getText().toString()));
		                    	 
		                      }
		                   }
		               });
		        AlertDialog bla = builder.create();
		        bla.show();
			

               
		
		}
		else
		{
			createDialog();
		}
    	
		
		
		
	}
    
    public void setPlayers(int num)
    {
    	
    	
    	players.clear();
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 
        builder.setMessage("Name the drunken fellows!");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        for(int i =0; i<num; i++)
        {
        	
        	EditText player = new EditText(this);
        	layout.addView(player);
        	players.add(player);
        }
        
        builder.setView(layout);
        
        
           builder.setPositiveButton("\'Amo Cugat vise!", new DialogInterface.OnClickListener() 
           {
               public void onClick(DialogInterface dialog, int id) 
               {
            	   
            	  boolean allClear = true;
                  for(EditText e : players)
                  {
                	  System.out.println(e.getText().toString());
                	  if(e.getText().toString().equalsIgnoreCase(""))
                	  {
                		  allClear = false;
                		  break;
                	  }
                	  else
                	  {
                		 allClear = true;  
                	  }
                  }
                  if(allClear)
                  {
                	  dialog.dismiss();
                	  _startActivity();
                  }
                  else
                  {
                	  Toast.makeText(getApplication(), "Name allovit!", Toast.LENGTH_LONG).show();
                  }
                  
                  
               }
           });
        AlertDialog bla = builder.create();
        bla.show();
	
    }
    
    public void _startActivity()
    {
    	 Intent i = new Intent(this , AmoCugat.class);
         startActivity(i);
    }
   
    
    public void noviCugaList(View v)
    {
    	 getSupportFragmentManager().beginTransaction().replace(R.id.container, 
    			 new NoviCugaList()).addToBackStack(null).commit();
    }


	@Override
	public void onFragmentInteraction() {
		// TODO Auto-generated method stub
		
	}
    
}
