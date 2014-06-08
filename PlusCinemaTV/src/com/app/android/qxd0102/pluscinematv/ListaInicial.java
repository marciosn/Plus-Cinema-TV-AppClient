package com.app.android.qxd0102.pluscinematv;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaInicial extends ListActivity {
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] telas = getResources().getStringArray(R.array.itens);
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, telas));
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	  switch (position) {
			case 0:
				intent = new Intent(getApplicationContext(), Lista_Filmes.class);
	        	  startActivity(intent);
				break;
			case 1:
				intent = new Intent(getApplicationContext(), Lista_Series.class);
				startActivity(intent);
				break;
			case 2:
				
				break;
			default:
				break;
			}
          }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_inicial, menu);
		return true;
	}

}
