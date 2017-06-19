package com.fueled.fabulous.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.fueled.fabulous.Fabulous;
import com.fueled.fabulous.sample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Fabulous exampleOneMenu, exampleMenuTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupExampleOne();
        setupExampleTwo();
    }

    private void setupExampleTwo() {
        exampleMenuTwo = new Fabulous.Builder(this)
                .setFab(binding.exampleTwo)
                .setMenuId(R.menu.menu_sample)
                .setMenuPattern(new CirclePattern())
                .setFabTransition(new NinetyDegRotationTransition())
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        exampleOneMenu.closeMenu();
        Snackbar.make(binding.getRoot(), item.getTitle(), Snackbar.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void setupExampleOne() {
        exampleOneMenu = new Fabulous.Builder(this)
                .setFab(binding.exampleOne)
                .setFabOverlay(binding.overlay)
                .setMenuId(R.menu.menu_sample)
                .setMenuPattern(new LinearPattern())
                .build();
    }
}
