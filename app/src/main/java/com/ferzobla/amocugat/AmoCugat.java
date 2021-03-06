package com.ferzobla.amocugat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class AmoCugat extends Activity
{
	public static TextView karta;
	public static View mainView;
	public static Map<String, String> pravila;
	public ArrayList<String> timeout;
	public RelativeLayout relativeLayout;
	static GraphicsView g;
	public int playerTurn=0;
	public int gameTurn = 1;
	public final double chanceOfRepeat = 0.025;
	public String listName;
	

	@Override
	public void onCreate(Bundle bundle) 
	{
		super.onCreate(bundle);
		this.setContentView(R.layout.amocugat);
		Intent i = getIntent();
		if(i.hasExtra("listName")){
			listName = i.getStringExtra("listName");
		}
		new GetCards().execute((Object[]) null);
		
		
		karta = (TextView)findViewById(R.id.textView1);
		g = new GraphicsView(this);
		relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
		relativeLayout.addView(g);
		
		timeout = new ArrayList<String>();

	}
	
	public void drawPravilo(View v)
	{
		String key;
		String pravilo;
		int randInt = 0;

		Random rand = new Random();

		if(gameTurn == 1) {
			randInt = rand.nextInt(13);
			key = Integer.toString(randInt);
			pravilo = pravila.get(key);
			timeout.add(pravilo);
		}
		else {
			if(gameTurn >=4){
				timeout.remove(0);
			}

			do{
				randInt = rand.nextInt(13);
				key = Integer.toString(randInt);
				pravilo = pravila.get(key);

			}while(timeout.contains(pravilo) && (Math.random() >= chanceOfRepeat));

			if(!timeout.contains(pravilo)){
				timeout.add(pravilo);
			}
		}
		gameTurn++;
		String whoDrink = getPlayer();
		karta.setText(whoDrink + ":  "+pravilo);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
		karta.setAnimation(anim);
		g.stop();
	
	       final Handler threadHandler = new Handler();
	       new Thread() 
	       {
	               @Override
	               public void run() 
	               {
	                       threadHandler.postDelayed(new Runnable() 
	                       {
	                               public void run() 
	                               {
	                            	   g.start();
	                               }
	                       }, 3000);
	               }
	       }.start();
		
	}

	public String getPlayer()
	{
		String playerName = MainActivity.players.get(playerTurn).getText().toString();
		playerTurn++;
		if(MainActivity.players.size() == playerTurn)
		{
			playerTurn =0;
		}
		return playerName;
	}
	

	private class GetCards extends AsyncTask<Object, Object, Cursor>
	{

		@Override
		protected Cursor doInBackground(Object... params) 
		{
			NoviCugaList.database.open();

			pravila = NoviCugaList.database.getAllValues(listName);

			for (Entry<String, String> entry : pravila.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				System.out.println(key + "   "+value);
				// do what you have to do here
				// In your case, an other loop.
			}
			return NoviCugaList.database.getAllCards(listName);
		}

		@Override
		protected void onPostExecute(Cursor result)
		{
			// adapter.changeCursor(result);
			NoviCugaList.database.close();
		}
	}

	private static class GraphicsView extends View 
	{
		private static String AnimatedTxt="";
		
		private Animation rotateAnim;

		public GraphicsView(Context context) 
		{
			super(context);
			
		}
		
		public void start()
		{
			startAnimation(rotateAnim);
		}
		
		public void stop()
		{
			clearAnimation();
		}
		
		private void createAnim(Canvas canvas) 
		{

			try
			{
				AnimatedTxt = "";
				for (Entry<String, String> entry : pravila.entrySet())
				{
					String key = entry.getKey();
					String value = entry.getValue();
					
					AnimatedTxt += " "+value;
				}
					rotateAnim = new RotateAnimation(0, 360, canvas.getWidth() / 2, canvas.getHeight() / 2);
					rotateAnim.setRepeatMode(Animation.REVERSE);
					rotateAnim.setRepeatCount(Animation.INFINITE);
					rotateAnim.setDuration(10000L);
					rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		
					startAnimation(rotateAnim);
				}
			catch(Exception e)
			{
				e.printStackTrace();
				Toast.makeText(this.getContext(), "Some studpid error occured! No Alchocol for you today!", Toast.LENGTH_LONG).show();
			}
			
		}
		@Override
		protected void onDraw(Canvas canvas) 
		{
			super.onDraw(canvas);
			
			// creates the animation the first time
			if (rotateAnim == null)
			{
				createAnim(canvas);
			}

			Path circle = new Path();

			int centerX = canvas.getWidth() / 2;
			int centerY = canvas.getHeight() / 2;
			int r = Math.min(centerX, centerY);
			
			circle.addCircle(centerX, centerY, r, Direction.CW);
			Paint paint = new Paint();
			paint.setColor(Color.rgb(0, 0, 0));
			paint.setTextSize(50);
			paint.setAntiAlias(true);

			canvas.drawTextOnPath(AnimatedTxt, circle, 0, 30, paint);
			
		}
	}
	
	

}
