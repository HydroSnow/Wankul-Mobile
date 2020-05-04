package dev.hydrosnow.wankul.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import dev.hydrosnow.wankul.R;

public class Wankul extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private DrawerLayout drawer;
	private FrameLayout frame;
	private NavigationView navigationView;
	private int gravity;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wankul);
		
		// récupérer nos variables
		drawer = findViewById(R.id.drawer_layout);
		frame = findViewById(R.id.frame_layout);
		navigationView = findViewById(R.id.nav_view);
		final Toolbar toolbar = findViewById(R.id.toolbar);
		
		// utiliser notre toolbar
		setSupportActionBar(toolbar);
		
		// ajouter un bouton à la toolbar
		final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		// ajouter un event listener
		gravity = navigationView.getForegroundGravity();
		navigationView.setNavigationItemSelectedListener(this);
		
		navigationView.setCheckedItem(R.id.drawer_home);
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
		
		if (id == R.id.drawer_home) {
			frame.removeAllViews();
			getLayoutInflater().inflate(R.layout.wankul_home, frame);
			
		} else if (id == R.id.drawer_gallery) {
			frame.removeAllViews();
			
		} else if (id == R.id.drawer_settings) {
			frame.removeAllViews();
			
		}
		
		drawer.closeDrawer(gravity);
		return true;
	}
}