package com.app.android.qxd0102.pluscinematv;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class TelaInicial extends Activity {

	private ImageButton imgButtonFilme;
	private ImageButton imgButtonSerie;
	private Intent intent;
	
	private Boolean isInternetPresent = false;
    private ConnectionDetector cd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_inicial);
		cd = new ConnectionDetector(getApplicationContext());
	}
	public void telaFilme(View view){
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {                    
            intent = new Intent(getApplicationContext(), Lista_Filmes.class);
			startActivity(intent);
			
        } else {
            showAlertDialog(TelaInicial.this, "Sem Conexão Com a Internet",
                    "O dispositivo não está conectado a internet !", false);
		}
	}
	
	public void telaSerie(View view){
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {                    
            intent = new Intent(getApplicationContext(), Lista_Series.class);
			startActivity(intent);
			
        } else {
            showAlertDialog(TelaInicial.this, "Sem Conexão Com a Internet",
                    "O dispositivo não está conectado a internet !", false);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	    alertDialog.setTitle(title);
	    alertDialog.setMessage(message);
	    alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
	    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int which) {
	        }
	    });
	        alertDialog.show();
	    }
}
