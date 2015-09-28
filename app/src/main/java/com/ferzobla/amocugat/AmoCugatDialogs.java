package com.ferzobla.amocugat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Filipe on 9/23/2015.
 */
public class AmoCugatDialogs extends MainActivity{

    public void nclDialogs(int version) {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.jesus);
        String msg = "";
        if (version == 1) {
            msg = getResources().getString(R.string.dajIme);
        }
        else if(version == 2){
            msg = getResources().getString(R.string.imeExists);
        }
        else if(version == 3){
            msg = getResources().getString(R.string.dajPravila);
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public String[] listChoice()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] choice = new String[1];
        builder.setMessage(this.getResources().getString(R.string.choice));
        String load = this.getResources().getString(R.string.loadList);
        builder.setPositiveButton(load, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                choice[0] = "Load List";
            }
        });
        String newList = this.getResources().getString(R.string.createCugat);
        builder.setNegativeButton(newList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                choice[0] = "Create List";
            }
        });
        AlertDialog bla = builder.create();
        bla.show();

        return choice;

    }

    public void setPlayerNum(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(this.getResources().getString(R.string.askNumPlayers));
        final EditText numOfPlayers = new EditText(this);
        numOfPlayers.setInputType(InputType.TYPE_CLASS_NUMBER);
        String ok = this.getResources().getString(R.string.ok);
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog bla = builder.create();
        bla.show();
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

}
