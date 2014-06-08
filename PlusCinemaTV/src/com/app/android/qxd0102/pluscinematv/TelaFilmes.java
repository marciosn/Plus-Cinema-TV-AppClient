package com.app.android.qxd0102.pluscinematv;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.app.android.qxd0102.model.FilmePojo;
import com.app.android.qxd0102.pluscinematv.loadimages.AdapterGridViewFilmes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class TelaFilmes extends Activity implements OnItemClickListener{

	private ArrayList<FilmePojo> filmes = new ArrayList<FilmePojo>();
	AdapterGridViewFilmes adapterGridFilmes;
	String urlConexao = "http://138.91.114.49:8080/JSON-SERVER07-11/";
	private Intent intent;
	private String out = null;
	private GridView gridView;
	private Gson gson = new Gson();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_filmes);
		
		new ListaServidor().execute(urlConexao);
		GridView gridview = (GridView) findViewById(R.id.gridViewFilmes);
		gridview.setOnItemClickListener(this);
        adapterGridFilmes = new AdapterGridViewFilmes(this, filmes);
	}
	
	private class ListaServidor extends AsyncTask<String, Void, String> {
		WebService webService = new WebService(urlConexao);
		@Override
		protected String doInBackground(String... urls) {
			Message message = new Message();
			String filme = "ListaDeFilmes";
	        Map params = new HashMap();
	        params.put("var", filme);
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
			Log.i("onPostExecute", out);
			for(FilmePojo filme : PegaLista(out)){
				filmes.add(filme);
				gridView.setAdapter(adapterGridFilmes);
		        gridView.setCacheColorHint(Color.TRANSPARENT);
			}
		}
	}
	
	public List<FilmePojo> PegaLista(String r){
    	Type colletionType = new TypeToken<Collection<FilmePojo>>(){}.getType();
        Collection<FilmePojo> filmes = null;
        filmes = gson.fromJson(r, colletionType);
        return  (List<FilmePojo>) filmes;
	}
	
	public String converteParaString(FilmePojo filme){
		String filmeJSONString = gson.toJson(filme);
		Log.d("Gson", "evento JSON String" + filmeJSONString);	
		return  filmeJSONString;
	}
	
	public FilmePojo converteParaObjeto(String s){
		Gson gson = new Gson();
		FilmePojo filme = gson.fromJson(s, FilmePojo.class);
		return filme;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		intent = new Intent(TelaFilmes.this, Filme.class);
		FilmePojo filme = (FilmePojo) parent.getItemAtPosition(position);

		intent.putExtra("url", filme.getUrl());
		intent.putExtra("nameFilme", filme.getNome());
		intent.putExtra("anoFilme", filme.getAno());
		intent.putExtra("genero", filme.getGenero());
		intent.putExtra("nota", filme.getNotaMedia());
		
		String queroAssitir = String.valueOf(filme.getQueroAssistir());
		intent.putExtra("queroAssistir", queroAssitir);
		intent.putExtra("sinopse", filme.getSinopse());
		intent.putExtra("trailer", filme.getTrailer());
    	startActivity(intent);
		
	}

	

}
