package com.example.a1.cardforstudying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.a1.cardforstudying.fragment.CardsFragment;
import com.example.a1.cardforstudying.fragment.DictionariesListFragment;
import com.example.a1.cardforstudying.fragment.EmptyDictionary;
import com.example.a1.cardforstudying.fragment.LeftButtonFragment;
import com.example.a1.cardforstudying.fragment.PhraseFragment;
import com.example.a1.cardforstudying.fragment.TabsFragment;
import com.example.a1.cardforstudying.fragment.TestFragment;
import com.example.a1.cardforstudying.model.DictionaryLab;
import com.example.a1.cardforstudying.model.PhraseLab;
import com.example.a1.cardforstudying.model.Word;
import com.example.a1.cardforstudying.model.WordLab;

import static com.example.a1.cardforstudying.XMLHelper.createFirstDictionary;

public class CardsForStudying extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    public FragmentManager fm = getSupportFragmentManager();
    public Fragment fragment = fm.findFragmentById(R.id.fragment_container);//создание фрагмента
    private Fragment  leftButtonFragment = fm.findFragmentById(R.id.left_btn_fragment_container);

    GestureDetectorCompat mDetector;
    DrawerLayout drawer;

    public int index = 0;
    public int phraseIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_for_studuing);

        initAppBar();
        firstStart();

        if (fragment == null) {
            leftButtonFragment = new LeftButtonFragment();
            startFragment();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_drawer_dictionaries:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.nav_drawer_language:
                //отркыть список слов в стесте
                makeToast("В разарботке");
                break;
            case R.id.nav_about_developers:
                Uri address = Uri.parse("https://hh.ru/applicant/resumes/view?resume=06285ed3ff0593ce870039ed1f344d53553939");
                Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlinkIntent);
                break;
        }

        // закрываем NavigationView, параметр определяет анимацию закрытия
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);

        Log.d(TAG, "Touch!");//фиксирование касания
        Log.d(TAG, "X:" + event.getX() + " Y:" + event.getY());//получение координат касания
        return super.onTouchEvent(event);
    }


    public void startFragment() {
        fragment = new CardsFragment();

        fm.beginTransaction()
                .replace(R.id.fragment_container, checkDataLab(fragment))
                .commit();
        fm.beginTransaction()
                .replace(R.id.left_btn_fragment_container, leftButtonFragment)
                .commit();
    }

    public void startFragment(Fragment nameFragment) {
        fragment = nameFragment;

        fm.beginTransaction()
                .replace(R.id.fragment_container, checkDataLab(fragment))
                .commit();
    }

    public void startFragmentWithParameter(Fragment nameFragment, int dictionaryID, int elementID) {
        fm.beginTransaction().hide(leftButtonFragment).commit();

        fragment = nameFragment;
        Bundle bundleWord = new Bundle();
        bundleWord.putString("_dictionaryID", String.valueOf(dictionaryID));
        bundleWord.putString("_elementID", String.valueOf(elementID));
        fragment.setArguments(bundleWord);

        startFragment(fragment);
    }

    public Fragment checkDataLab(Fragment nameFragment) {
        int count = 0;
        if (WordLab.get(this).getWords().isEmpty() && (nameFragment.toString().contains("CardsFragment") || nameFragment.toString().contains("TestFragment"))) {
            EmptyDictionary.mMessage = R.string.empty_dictionary;
            return new EmptyDictionary();
        }

        if (PhraseLab.get(this).getPhrases().isEmpty() && nameFragment.toString().contains("PhraseFragment")) {
            EmptyDictionary.mMessage = R.string.err_empty_phrase_dictionary;
            return new EmptyDictionary();
        }

        for (Word each : WordLab.get(this).getWords()) {
            if (each.isInTest()) {
                count++;
            }
        }

        if (count < 4 && nameFragment.toString().contains("TestFragment")) {
            EmptyDictionary.mMessage = R.string.fewer_than_four_on_dictionary;
            return new EmptyDictionary();
        }

        return nameFragment;
    }

    public String getActiveFragmentName() {
        return fragment.getClass().getSimpleName();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume() called");

        if (fragment != null) {
            switch (getActiveFragmentName()) {
                case "CardsFragment":
                    startFragment(new CardsFragment());
                    break;
                case "PhraseFragment":
                    startFragment(new PhraseFragment());
                    break;
                case "TestFragment":
                    startFragment(new TestFragment());
                    break;
                default:
                    startFragment();
                    break;
            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop() called");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        WordLab.get(this).close();
        PhraseLab.get(this).close();
        DictionaryLab.get(this).close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //обработка нажатия в Navigation Drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        switch (getActiveFragmentName()) {
            case "WordEditFragment":
                startFragment(new CardsFragment());
                fm.beginTransaction().show(leftButtonFragment).commit();
                initAppBar();
                return;
            case "PhraseEditFragment":
                startFragment(new PhraseFragment());
                fm.beginTransaction().show(leftButtonFragment).commit();
                initAppBar();
                return;
            default:
                super.onBackPressed();
        }
        super.onBackPressed();
    }

    private void makeToast(int string_id) {
        Toast toast = Toast.makeText(this, string_id, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void makeToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void initAppBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Добавляем слушателя на drawer.
        drawer.addDrawerListener(toggle);

        // Cинхронизируем menu с drawer.
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Добавляем слушатель нажатий на пункт списка
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void firstStart() {
        SharedPreferences sPref = getSharedPreferences(getString(R.string.first_start_file_name), MODE_PRIVATE);
        boolean firstStart = Boolean.valueOf(sPref.getString(getString(R.string.first_start_file_name), "true"));
        if (!firstStart) {
            return;
        }
        createFirstDictionary(this);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(getString(R.string.first_start_file_name), "false");
        ed.commit();
    }
}
