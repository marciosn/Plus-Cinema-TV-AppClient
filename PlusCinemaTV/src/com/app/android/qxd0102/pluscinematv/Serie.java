package com.app.android.qxd0102.pluscinematv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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

import com.app.android.qxd0102.pluscinematv.loadimages.ImageLoader;
import com.google.gson.Gson;

public class Serie extends Activity {
	private RatingBar ratingBar2;
	private TextView txtRatingValue2;
	private TextView txtnameSerie, txtanoSerie, txtgenero, txtqueroAssitir, txtnotaMedia, txttrailer, txtsinopse;
	private Button btnSubmit2;
	private String url;
	private String nameSerie, anoSerie, gerero, nota, quantidadeVotos, QueroAssistir, trailer, sinopse;
	private Intent intent;
	private ImageButton imgButtonTraillerSerie;
	private ImageButton imgButtonGosteiSerie;
	private ImageButton imgButtonNotaSerie;
	
	String urlConexao = "http://138.91.114.49:8080/JSON-SERVER07-11/";
	ArrayAdapter<String> adp;
	String response, out;
	ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
	float resultado;
	Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serie);
		
		txtnameSerie = (TextView) findViewById(R.id.nomeSerie);
		txtanoSerie = (TextView) findViewById(R.id.textView1);
		txtgenero = (TextView) findViewById(R.id.textView2);
		txtnotaMedia = (TextView) findViewById(R.id.textView3);
		txtqueroAssitir = (TextView) findViewById(R.id.textView4);
		txtsinopse = (TextView) findViewById(R.id.textView5);
		
		Intent i = getIntent();
		url = i.getExtras().getString("url");
		nameSerie = i.getExtras().getString("nameSerie");
		anoSerie = i.getExtras().getString("anoSerie");
		gerero = i.getExtras().getString("genero");
		nota = i.getExtras().getString("nota");
		QueroAssistir = i.getExtras().getString("queroAssistir");
		sinopse = i.getExtras().getString("sinopse");
		trailer = i.getExtras().getString("trailer");
		
		txtnameSerie.setText(nameSerie);
		txtanoSerie.setText("Ano: "+"("+anoSerie+")");
		txtgenero.setText("Genero: "+gerero);
		txtnotaMedia.setText("Média: "+nota);
		txtqueroAssitir.setText("Gostei: "+QueroAssistir);
		txtsinopse.setText(sinopse);
		int loader = R.drawable.loader;
        ImageView image = (ImageView) findViewById(R.id.imageserie);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        //imgLoader.DisplayImage(image_url, loader, image);
        imgLoader.DisplayImage(url, loader, image);
        
        addListenerOnRatingBar();
		//addListenerOnButton();
        addButtonListenerTraillerSerie();
        addButtonListenerGosteiSerie();
        addButtonListenerNotaSerie();
	}

	public void addButtonListenerNotaSerie() {
		imgButtonNotaSerie = (ImageButton) findViewById(R.id.imageButtonNotaSerie);
		imgButtonNotaSerie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				envia(view);	
			}
		});
		
	}

	public void addButtonListenerGosteiSerie() {
		imgButtonGosteiSerie = (ImageButton) findViewById(R.id.imageButtonGosteiSerie);
		imgButtonGosteiSerie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				queroAssistir(view);				
			}
		});
	}

	public void addButtonListenerTraillerSerie() {
		imgButtonTraillerSerie = (ImageButton) findViewById(R.id.imageButtonTraillerSerie);
		imgButtonTraillerSerie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				trailer(view);
			}
		});
		
	}

	public void addListenerOnRatingBar() {

		ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
		txtRatingValue2 = (TextView) findViewById(R.id.txtRatingValue2);

		//if rating is changed,
		//display the current rating value in the result (textview) automatically
		ratingBar2.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				resultado = rating * 2;
				txtRatingValue2.setText("Nota: "+String.valueOf(resultado));

			}
		});
	}
	class EnviarNota extends AsyncTask<String, Void, String> {
		WebService webService = new WebService(urlConexao);
		@Override
		protected String doInBackground(String... urls) {
			Message message = new Message();
			String n = String.valueOf(resultado);
			String parametro = n + " " + nameSerie;
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
	public void verImagem(View view){
		intent = new Intent(getApplicationContext(), VerImagem.class);
		intent.putExtra("url", url);
    	startActivity(intent);
	}
	public String converteParaString(File f){
        String fileJSONString = gson.toJson(f);
        Log.d("Gson", "evento JSON String" + fileJSONString);	
    	return  fileJSONString;
    }
    public File converteParaObjeto(String s){
    	Gson gson = new Gson();
    	File f = gson.fromJson(s, File.class);
    	return f;
    }
    class QueroAssistir extends AsyncTask<String, Void, String> {
		WebService webService = new WebService(urlConexao);
		@Override
		protected String doInBackground(String... urls) {
			Message message = new Message();
			String n = String.valueOf(resultado);
			String parametro = nameSerie;
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
		Intent intent = new Intent(Serie.this, Trailer.class);
		String idVideo = pegaId(trailer);
		intent.putExtra("idVideo", idVideo);
		startActivity(intent);
	}
	
	public String pegaId(String url){
		Uri uri = Uri.parse(url);
		uri = Uri.parse(uri.getQueryParameter("v"));
		String id = uri.toString();
		//Toast.makeText(Serie.this, id, Toast.LENGTH_SHORT).show();
		return id;
	}
}
