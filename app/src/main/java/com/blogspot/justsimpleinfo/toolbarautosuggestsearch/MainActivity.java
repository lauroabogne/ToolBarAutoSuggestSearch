package com.blogspot.justsimpleinfo.toolbarautosuggestsearch;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * inflate
         */
        getMenuInflater().inflate(R.menu.main_menu,menu);
        /**
         * set menu
         */
        mMenu = menu;

        /**
         * get action view
         */
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

        /**
         * set text change listener
         */
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                Log.e("the query",query);
                loadSuggestion(query);

                return true;

            }

        });

        return super.onCreateOptionsMenu(menu);
    }


    private void loadSuggestion(String query) {


        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final SearchView search = (SearchView) mMenu.findItem(R.id.search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));


        /**
         * create temp cursor
         */
        String[] columns = new String[] { "_id", "item", "description" };

        MatrixCursor matrixCursor= new MatrixCursor(columns);
        startManagingCursor(matrixCursor);

        matrixCursor.addRow(new Object[] { 1, "Item A1", "test" });
        matrixCursor.addRow(new Object[] { 2, "Item A2", "test" });
        matrixCursor.addRow(new Object[] { 3, "Item A3", "test" });


        search.setSuggestionsAdapter(new ExampleAdapter(this, matrixCursor));



    }

    /**
     * Custom cursor adapter
     */
    public class ExampleAdapter extends CursorAdapter {

        private TextView textView;

        public ExampleAdapter(Context context, Cursor cursor) {

            super(context, cursor, false);


        }



        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            /**
             * set text
             */

            textView.setText(cursor.getString(cursor.getColumnIndex("item"))); // Example column index

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            /**
             * inflate layout
             */
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout view = (LinearLayout) inflater.inflate(R.layout.search_auto_suggest_item, parent, false);

            textView = (TextView) view.findViewById(R.id.textView);

            return view;

        }

    }

}
