package com.app.android.qxd0102.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.android.qxd0102.pluscinematv.WebService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class ServicoTest extends Service {
	public List<Worker> threads = new ArrayList<Worker>();
	String urlConexao = "http://138.91.114.49:8080/JSON-SERVER07-11/";
	ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
	private String response, out;

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		Log.i("Script", "onCreate()");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.i("TesteService", "onStartCommand()");
		
		Worker w = new Worker();
		w.start();
		threads.add(w);
		
		return(super.onStartCommand(intent, flags, startId));
	}
	
	
	class Worker extends Thread{
		int delay = 5000;
		int intervalo = 60000;
		TimerTask timertask;
		Handler handler = new Handler();
		Timer timer = new Timer();
		
		public void run(){
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					try {
						WebService webService = new WebService(urlConexao);
						Map<String, String> params = new HashMap();
					    String parametros = "Filme";
					    params.put("var", parametros);
					    response = webService.webGet("", params);
			        	JSONObject objeto;
						objeto = new JSONObject(response);
						out = objeto.get("message").toString();
						Log.i("Script", out);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, delay, intervalo);
	}
}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
