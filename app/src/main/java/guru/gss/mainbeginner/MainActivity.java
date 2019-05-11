package guru.gss.mainbeginner;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentNews.OnFragmentInteractionListener {

    private int mIdSelectedItem;
    private DrawerLayout mDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        setFragment(FragmentNews.newInstance("the-washington-post", "The Washington Post"), R.id.fl_fragment_conteiner);
        mIdSelectedItem = R.id.i_the_washington_post;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void openDrover() {
        mDrawer.openDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        int id = item.getItemId();

        if (id != mIdSelectedItem) {
            mIdSelectedItem = id;
            Fragment fragment;
            switch (id) {
                case R.id.i_the_washington_post:
                    fragment = FragmentNews.newInstance("the-washington-post", "The Washington Post");
                    break;
                case R.id.i_the_new_york_times:
                    fragment = FragmentNews.newInstance("the-new-york-times", "The New York Times");
                    break;
                case R.id.i_the_telegraph:
                    fragment = FragmentNews.newInstance("the-telegraph", "The Telegraph");
                    break;
                case R.id.i_cnn:
                    fragment = FragmentNews.newInstance("cnn", "CNN");
                    break;
                case R.id.i_time:
                    fragment = FragmentNews.newInstance("time", "Time");
                    break;
                case R.id.i_bbc_news:
                    fragment = FragmentNews.newInstance("bbc-news", "BBC News");
                    break;
                case R.id.i_associated_press:
                    fragment = FragmentNews.newInstance("associated-press", "Associated Press");
                    break;
                case R.id.i_independent:
                    fragment = FragmentNews.newInstance("independent", "Independent");
                    break;
                case R.id.i_reuters:
                    fragment = FragmentNews.newInstance("reuters", "Reuters");
                    break;
                default:
                    fragment = FragmentNews.newInstance("the-washington-post", "The Washington Post");
                    break;
            }
            setFragment(fragment, R.id.fl_fragment_conteiner);
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment, int layoutResIs) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.content_show, R.anim.content_hide);
        fragmentTransaction.replace(layoutResIs, fragment, tag);
        fragmentTransaction.commit();
    }
}