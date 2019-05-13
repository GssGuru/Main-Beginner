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

/*
ENG: The main activity with the Navigation menu and container for the fragments. Using the menu, we will switch fragments
RU: Главное активити с Навигационным меню и контейнером для фрагментов. С помощью меню будем переключать фрагменти
*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentNewsFeed.OnFragmentInteractionListener {

    /*
    ENG: Prepare elements of the Navigation menu
    RU: Подготовить элементы Навигационного меню
    */
    private int mIdSelectedItem;
    private DrawerLayout mDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        /*
        ENG: Find and work with the Navigation menu
        RU: Находим и работаем с Navigation menu
        */
        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        setFragment(FragmentNewsFeed.newInstance("the-washington-post", "The Washington Post"), R.id.fl_fragment_conteiner);
        mIdSelectedItem = R.id.i_the_washington_post;
    }

    /*
    ENG: Override the Backspace button to check if the Navigation menu is open.
    RU: Переопределить кнопку Backspace для проверки не открыто ли Navigation menu
    */
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    ENG: Interface method from Fragment NewsFeed for opening the Navigation menu
    RU: Метод интерфейса из Fragment NewsFeed для открытия Navigation menu
    */
    @Override
    public void openDrover() {
        mDrawer.openDrawer(GravityCompat.START);
    }

    /*
    ENG: Navigation menu. item click handler
    RU: Navigation menu. обработчик click по позициям
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id != mIdSelectedItem) {
            mIdSelectedItem = id;
            Fragment fragment;
            switch (id) {
                case R.id.i_the_washington_post:
                    fragment = FragmentNewsFeed.newInstance("the-washington-post", "The Washington Post");
                    break;
                case R.id.i_the_new_york_times:
                    fragment = FragmentNewsFeed.newInstance("the-new-york-times", "The New York Times");
                    break;
                case R.id.i_the_telegraph:
                    fragment = FragmentNewsFeed.newInstance("the-telegraph", "The Telegraph");
                    break;
                case R.id.i_cnn:
                    fragment = FragmentNewsFeed.newInstance("cnn", "CNN");
                    break;
                case R.id.i_time:
                    fragment = FragmentNewsFeed.newInstance("time", "Time");
                    break;
                case R.id.i_bbc_news:
                    fragment = FragmentNewsFeed.newInstance("bbc-news", "BBC News");
                    break;
                case R.id.i_associated_press:
                    fragment = FragmentNewsFeed.newInstance("associated-press", "Associated Press");
                    break;
                case R.id.i_independent:
                    fragment = FragmentNewsFeed.newInstance("independent", "Independent");
                    break;
                case R.id.i_reuters:
                    fragment = FragmentNewsFeed.newInstance("reuters", "Reuters");
                    break;
                default:
                    fragment = FragmentNewsFeed.newInstance("the-washington-post", "The Washington Post");
                    break;
            }
            setFragment(fragment, R.id.fl_fragment_conteiner);
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    ENG: The method of switching fragments between themselves
    RU: Метод переключения фрагментов между собой
    */
    private void setFragment(Fragment fragment, int layoutResIs) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.content_show, R.anim.content_hide);
        fragmentTransaction.replace(layoutResIs, fragment, tag);
        fragmentTransaction.commit();
    }
}