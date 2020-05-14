package com.oF2pks.applicationsinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity implements MainCallbacks {

    private boolean mIsDualPane;
    public static final String EXTRA_PACKAGE_NAME = "package_name";
    public static String packageList;
    public static String permName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appinfos_activity_main);
        packageList=getIntent().getStringExtra(EXTRA_PACKAGE_NAME);
        permName=getIntent().getStringExtra("perm_name");
        if  (permName==null) permName="Onboard.packages";

        mIsDualPane = findViewById(R.id.item_detail_container) != null;

        //Show an art when no fragment is showed, we make sure no detail fragment is present.
        if (mIsDualPane && getFragmentManager().findFragmentByTag(DetailFragment.FRAGMENT_TAG) == null) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.icon_art_appinfos);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ((FrameLayout) findViewById(R.id.item_detail_container)).addView(imageView);
        }
    }

    @Override
    public void onItemSelected(String packageName) {
        if (mIsDualPane) {
            //Hide art when a fragment is showed.
            ((FrameLayout) findViewById(R.id.item_detail_container)).removeAllViews();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.item_detail_container, DetailFragment.getInstance(packageName), DetailFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.EXTRA_PACKAGE_NAME, packageName);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appinfos_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about)
                    .setView(getLayoutInflater().inflate(R.layout.appinfos_about_dialog_message, null))
                    .setNegativeButton(android.R.string.ok, null)
                    .setIcon(R.drawable.ic_launcher_appinfos)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
