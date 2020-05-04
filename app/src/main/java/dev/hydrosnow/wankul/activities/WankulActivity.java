package dev.hydrosnow.wankul.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

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

public class WankulActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private DrawerLayout drawer;
	private FrameLayout frame;
	private NavigationView navigationView;
	private int gravity;
	private String api_server;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
		
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
		final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		// ajouter un event listener
		navigationView.setNavigationItemSelectedListener(this);
		
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
			getLayoutInflater().inflate(R.layout.sub_home, frame);
			
		} else if (id == R.id.drawer_gallery) {
			frame.removeAllViews();
			
			final GridView grid = new GridView(this);
			grid.setNumColumns(2);
			frame.addView(grid);
			
			final GetFromageAsyncTask task = new GetFromageAsyncTask();
			task.execute(this, api_server, grid);
			
		} else if (id == R.id.drawer_contact) {
			frame.removeAllViews();
			getLayoutInflater().inflate(R.layout.sub_contact, frame);
			
		}
	}
	
	private static class GetFromageAsyncTask extends AsyncTask<Object, Void, GetFromageAsyncTask.Result> {
		@Override
		protected Result doInBackground(final Object... objects) {
			final WankulActivity context = (WankulActivity) objects[0];
			final String api_server = (String) objects[1];
			final GridView grid = (GridView) objects[2];
			
			try {
				final Fromage[] fromages = new FromageMV(api_server).get();
				return new Result(context, grid, fromages);
				
			} catch (final IOException | JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
			
		@Override
		protected void onPostExecute(final Result result) {
			if (result != null) {
				final FromageAdapter adapter = new FromageAdapter(result.context, R.layout.item_fromage, result.fromages);
				result.grid.setAdapter(adapter);
				System.out.println("[BIG-CHUNGUS] Finished query of " + result.fromages.length + " items.");
			}
		}
		
		private static class FromageAdapter extends ArrayAdapter<Fromage> {
			public FromageAdapter(@NonNull final Context context, final int resource, @NonNull final Fromage[] objects) {
				super(context, resource, objects);
			}
			
			@Override
			public View getView(final int position, final View convertView, final ViewGroup parent) {
				View view = convertView;
				if (convertView == null) {
					view = LayoutInflater.from(getContext()).inflate(R.layout.item_fromage, parent, false);
				}
				final Fromage fromage = getItem(position);
				final TextView textView = view.findViewById(R.id.fromage_name);
				final ImageView imageView = view.findViewById(R.id.fromage_thumbnail);
				
				final String nom = fromage.getNom();
				textView.setText(nom);
				
				final String url = fromage.getImg();
				Picasso.get().load(url).into(imageView);
				
				return view;
			}
		}
		
		private static class Result {
			private final WankulActivity context;
			private final GridView grid;
			private final Fromage[] fromages;
			
			private Result(final WankulActivity context, final GridView grid, final Fromage[] fromages) {
				this.context = context;
				this.grid = grid;
				this.fromages = fromages;
			}
		}
	}
}