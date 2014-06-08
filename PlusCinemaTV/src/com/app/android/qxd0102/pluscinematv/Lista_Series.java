package com.app.android.qxd0102.pluscinematv;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.app.android.qxd0102.model.FilmePojo;
import com.app.android.qxd0102.model.FilmesPojo;
import com.app.android.qxd0102.pluscinematv.loadimages.AdapterFilme;
import com.app.android.qxd0102.pluscinematv.loadimages.AdapterListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Lista_Series extends Activity implements OnItemClickListener{
		String urlConexao = "http://138.91.114.49:8080/JSON-SERVER07-11/";
		Gson gson = new Gson();
		private ArrayList<FilmesPojo> series = new ArrayList<FilmesPojo>();
		private AdapterListView adapterListView;
		private ListView listView;
		private Intent intent;
		private String out;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_de_series);
		
		new ListaServidor().execute(urlConexao);
		listView = (ListView) findViewById(R.id.listaSerie);
		listView.setOnItemClickListener(this);
		adapterListView = new AdapterListView(this, series);
	}

	private class ListaServidor extends AsyncTask<String, Void, String> {
		WebService webService = new WebService(urlConexao);
		@Override
		protected String doInBackground(String... urls) {
			String serie = "ListaDeSeries";
			Message message = new Message();
	        Map params = new HashMap();
	        params.put("var", serie);
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
			for(FilmesPojo serie : PegaLista(out)){
				series.add(serie);
		        listView.setAdapter(adapterListView);
		        listView.setCacheColorHint(Color.TRANSPARENT);
			}
		}
	}
	public List<FilmesPojo> PegaLista(String r){
    	Type colletionType = new TypeToken<Collection<FilmesPojo>>(){}.getType();
        Collection<FilmesPojo> series = null;
        series = gson.fromJson(r, colletionType);
	return  (List<FilmesPojo>) series;
	}
	public String converteParaString(FilmesPojo serie){
		String serieJSONString = gson.toJson(serie);
		Log.d("Gson", "evento JSON String" + serieJSONString);	
	return  serieJSONString;
}
	public FilmesPojo converteParaObjeto(String s){
		Gson gson = new Gson();
		FilmesPojo serie = gson.fromJson(s, FilmesPojo.class);
	return serie;
}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		intent = new Intent(Lista_Series.this, Serie.class);
		FilmesPojo serie = (FilmesPojo) parent.getItemAtPosition(position);
		
		intent.putExtra("url", serie.getUrl());
		intent.putExtra("nameSerie", serie.getNome());
		intent.putExtra("anoSerie", serie.getAno());
		intent.putExtra("genero", serie.getGenero());
		intent.putExtra("nota", serie.getNotaMedia());
		
		String queroAssistir = String.valueOf(serie.getQueroAssistir());
		intent.putExtra("queroAssistir", queroAssistir);
		intent.putExtra("sinopse", serie.getSinopse());
		intent.putExtra("trailer", serie.getTrailer());
    	startActivity(intent);
	}

}
