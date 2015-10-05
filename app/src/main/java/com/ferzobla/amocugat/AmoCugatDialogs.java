package com.ferzobla.amocugat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Filipe on 9/23/2015.
 */
public class AmoCugatDialogs{

    static String gChoice;

    public static void nclDialogs(Context c, int version) {
        ImageView image = new ImageView(c.getApplicationContext());
        image.setImageResource(R.drawable.jesus);
        String msg = "";
        if (version == 1) {
            msg = c.getResources().getString(R.string.dajIme);
        }
        else if(version == 2){
            msg = c.getResources().getString(R.string.imeExists);
        }
        else if(version == 3){
            msg = c.getResources().getString(R.string.dajPravila);
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(c.getApplicationContext());
        builder.setView(image);
        builder.setMessage(msg)
                .setPositiveButton("Kuzim...", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    public static void listChoice(Context c)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(c.getResources().getString(R.string.choice));
        String load = c.getResources().getString(R.string.loadList);
        builder.setPositiveButton(load, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setChoice("Load List");
                dialog.dismiss();
                notify();
            }
        });
        String newList = c.getResources().getString(R.string.createCugat);
        builder.setNegativeButton(newList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setChoice("Create List");
                dialog.dismiss();
                notify();
            }
        });

        builder.create();
        builder.show();
    }

    public static void setChoice(String choice){
        gChoice = choice;
    }

    public static String getChoice(){
        return gChoice;
    }

    public static void setPlayerNum(final Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c.getApplicationContext());

        builder.setMessage(c.getResources().getString(R.string.askNumPlayers));
        final EditText numOfPlayers = new EditText(c.getApplicationContext());
        numOfPlayers.setInputType(InputType.TYPE_CLASS_NUMBER);
        String ok = c.getResources().getString(R.string.ok);
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!numOfPlayers.getText().toString().equals("")){
                    int num = Integer.parseInt(numOfPlayers.getText().toString());
                    dialog.dismiss();
                    setPlayers(c, num);
                }
            }
        });
        AlertDialog bla = builder.create();
        bla.show();
    }

    public static void setPlayers(final Context c, int num)
    {
        MainActivity.players.clear();
        AlertDialog.Builder builder = new AlertDialog.Builder(c.getApplicationContext());

        builder.setMessage("Name the drunken fellows!");
        LinearLayout layout = new LinearLayout(c.getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        for(int i =0; i<num; i++)
        {

            EditText player = new EditText(c.getApplicationContext());
            layout.addView(player);
            MainActivity.players.add(player);
        }

        builder.setView(layout);


        builder.setPositiveButton("\'Amo Cugat vise!", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

                boolean allClear = true;
                for(EditText e : MainActivity.players)
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
                }
                else
                {
                    Toast.makeText(c.getApplicationContext(), "Name allovit!", Toast.LENGTH_LONG).show();
                }


            }
        });
        AlertDialog bla = builder.create();
        bla.show();

    }

}
