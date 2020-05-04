package dev.hydrosnow.wankul.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import dev.hydrosnow.wankul.R;
import dev.hydrosnow.wankul.viewmodel.Fromage;
import dev.hydrosnow.wankul.viewmodel.FromageMV;

public class Wankul extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private final Wankul self = this;
	
	private DrawerLayout drawer;
	private FrameLayout frame;
	private NavigationView navigationView;
	private int gravity;
	private String api_server;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wankul);
		
		// récupérer nos variables
		drawer = findViewById(R.id.drawer_layout);
		frame = findViewById(R.id.frame_layout);
		navigationView = findViewById(R.id.nav_view);
		gravity = navigationView.getForegroundGravity();
		api_server = getString(R.string.api_server);
		
		// utiliser notre toolbar
		final Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		// ajouter un bouton à la toolbar
		final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(self, drawer, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		// ajouter un event listener
		navigationView.setNavigationItemSelectedListener(self);
		
		// lancer sur l'accueil
		navigationView.setCheckedItem(R.id.drawer_home);
		changeFrame(R.id.drawer_home);
	}
	
	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(gravity)) {
			drawer.closeDrawer(gravity);
		} else {
			drawer.openDrawer(gravity);
		}
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
		final int id = menuItem.getItemId();
		changeFrame(id);
		drawer.closeDrawer(gravity);
		return true;
	}
	
	public void changeFrame(final int id) {
		if (id == R.id.drawer_home) {
			frame.removeAllViews();
			getLayoutInflater().inflate(R.layout.wankul_home, frame);
			
		} else if (id == R.id.drawer_gallery) {
			frame.removeAllViews();
			
			final GridView grid = new GridView(self);
			grid.setNumColumns(2);
			frame.addView(grid);
			
			final AsyncTask<Void, Void, Fromage[]> task = new AsyncTask<Void, Void, Fromage[]>() {
				@Override
				protected Fromage[] doInBackground(Void... voids) {
					try {
						final Fromage[] fromages = new FromageMV(api_server).get();
						return fromages;
					} catch (IOException | JSONException e) {
						e.printStackTrace();
						return null;
					}
				}
				
				@Override
				protected void onPostExecute(final Fromage[] fromages) {
					final ArrayAdapter<Fromage> adapter = new ArrayAdapter<Fromage>(self, android.R.layout.simple_list_item_1, fromages);
					grid.setAdapter(adapter);
					System.out.println("[BIG-CHUNGUS] Finished query of " + fromages.length + " items.");
				}
			};
			task.execute();
			
			
		} else if (id == R.id.drawer_contact) {
			frame.removeAllViews();
			getLayoutInflater().inflate(R.layout.wankul_contact, frame);
			
		}
	}
}