package com.app.android.qxd0102.pluscinematv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.qxd0102.model.FilmePojo;
import com.app.android.qxd0102.pluscinematv.loadimages.ImageLoader;
import com.google.gson.Gson;


public class Filme extends Activity {
	private RatingBar ratingBar;
	private TextView txtRatingValue, txtnameFilme, txtanoFilme, txtgeneroFilme, txtnota, txtqueroAssistir, txtsinopse, txttrailer;
	private Button btnSubmit;
	
	private String url;
	private String nameFilme, anoFilme, genero, nota, queroAssistir, sinopse, trailer;
	
	private Intent intent;
	
	private ImageButton imgButtonGostei;
	private ImageButton imgButtonTrailler;
	private ImageButton imgButtonNota;
	
	String urlConexao = "http://138.91.114.49:8080/JSON-SERVER07-11/";
	
	ArrayAdapter<String> adp;
	String response, out;
	ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
	float resultado;
	Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filme);
		
		txtnameFilme = (TextView) findViewById(R.id.nomefilme);
		txtanoFilme = (TextView) findViewById(R.id.textView1);
		txtnota = (TextView) findViewById(R.id.textView2);
		txtgeneroFilme = (TextView) findViewById(R.id.textView3);
		txtqueroAssistir = (TextView) findViewById(R.id.textView4);
		txtsinopse = (TextView) findViewById(R.id.textView6);
		
		Intent i = getIntent();
		url = i.getExtras().getString("url");
		nameFilme = i.getExtras().getString("nameFilme");
		anoFilme = i.getExtras().getString("anoFilme");
		genero = i.getExtras().getString("genero");
		nota = i.getExtras().getString("nota");
		queroAssistir = i.getExtras().getString("queroAssistir");
		trailer = i.getExtras().getString("trailer");
		sinopse = i.getExtras().getString("sinopse");
		
		txtnameFilme.setText(nameFilme);
		txtanoFilme.setText("Ano: "+"("+anoFilme+")");
		txtgeneroFilme.setText("Genero: "+genero);
		txtnota.setText("Média: "+nota);
		txtqueroAssistir.setText("Gostei: "+queroAssistir);
		txtsinopse.setText(sinopse);
        
		int loader = R.drawable.loader;
        ImageView image = (ImageView) findViewById(R.id.imagefilme);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        imgLoader.DisplayImage(url, loader, image);
        
        addListenerOnRatingBar();
        addButtonListenerGostei();
        addButtonListenerTrailler();
        addButtonListenerNota();
	}
	
	public void addButtonListenerNota() {
		imgButtonNota = (ImageButton) findViewById(R.id.imageButtonNotas);
		imgButtonNota.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				envia(view);				
			}
		});
		
	}

	public void addButtonListenerTrailler() {
		imgButtonTrailler = (ImageButton) findViewById(R.id.imageButtonTrailler);
		imgButtonTrailler.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				trailer(view);
				
			}
		});
	}

	public void addButtonListenerGostei() {
		imgButtonGostei = (ImageButton) findViewById(R.id.imageButtonGostei);
		imgButtonGostei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				queroAssistir(view);		
			}
		});
		
	}
	public void addListenerOnRatingBar() {

		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				resultado = rating * 2;

				txtRatingValue.setText("Nota: "+String.valueOf(resultado));

			}
		});
	}
	
	public void verImagem(View view){
		intent = new Intent(getApplicationContext(), VerImagem.class);
		intent.putExtra("url", url);
    	startActivity(intent);
	}
	
	class EnviarNota extends AsyncTask<String, Void, String> {
		WebService webService = new WebService(urlConexao);
		@Override
		protected String doInBackground(String... urls) {
			Message message = new Message();
			String n = String.valueOf(resultado);
			String parametro = n + " " + nameFilme;
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("var", parametro);
	        String response = webService.webGet("", params);
	        try {
	        	if(response == null){
	        		return null;
	        	}
				JSONObject objeto = new JSONObject(response);
				out = objeto.get("message").toString();
				
			} catch (Exception e1) {
				e1.printStackTrace(); 
			}
			return out;
		}
		@Override
		protected void onPostExecute(String result) {
			
		}
	}
	
	public void envia(View view){
		new EnviarNota().execute(urlConexao);
	}
	
	class QueroAssistir extends AsyncTask<String, Void, String> {
		WebService webService = new WebService(urlConexao);
		@Override
		protected String doInBackground(String... urls) {
			Message message = new Message();
			String n = String.valueOf(resultado);
			String parametro = nameFilme;
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("var", parametro);
	        String response = webService.webGet("", params);
	        try {
	        	if(response == null){
	        		return null;
	        	}
				JSONObject objeto = new JSONObject(response);
				out = objeto.get("message").toString();
				
			} catch (Exception e1) {
				e1.printStackTrace(); 
			}
			return out;
		}
		@Override
		protected void onPostExecute(String result) {
			
		}
	}
	
	public void queroAssistir(View view){
		new QueroAssistir().execute(urlConexao);
	}
	
	public void trailer(View view){
		Intent intent = new Intent(Filme.this, Trailer.class);
		String idVideo = pegaId(trailer);
		intent.putExtra("idVideo", idVideo);
		startActivity(intent);
	}
	
	public String pegaId(String url){
		Uri uri = Uri.parse(url);
		uri = Uri.parse(uri.getQueryParameter("v"));
		String id = uri.toString();
		//Toast.makeText(Filme.this, id, Toast.LENGTH_SHORT).show();
		return id;
	}
}
