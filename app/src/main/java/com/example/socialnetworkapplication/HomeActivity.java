package com.example.socialnetworkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, RequestFriendFragment.OnFragmentInteractionListener,EventFragment.OnFragmentInteractionListener, NotificationFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener{

    private TextView mTxtEmail;
    private TextView mTxtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        toolbar.setTitle(R.string.home_toolbar_title);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationView navigationView = findViewById(R.id.navigationview);
                navigationView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initViewPager() {
        TabLayout tabLayout = findViewById(R.id.taplayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_supervisor_account_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_event_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_notification_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_black_24dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init() {
//        mTxtEmail = findViewById(R.id.username);
//        mTxtName=  findViewById(R.id.password);
//        Intent intent = getIntent();
//        mTxtName.setText(intent.getStringExtra("email"));
//        mTxtEmail.setText(intent.getStringExtra("name"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_search:
                Toast.makeText(getApplicationContext(), "Seach", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item_reflect:
                Toast.makeText(getApplicationContext(), "Seach", Toast.LENGTH_LONG).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
