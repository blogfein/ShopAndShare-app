package com.cianmcgovern.android.ShopAndShare.DisplayResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.cianmcgovern.android.ShopAndShare.EditItem;
import com.cianmcgovern.android.ShopAndShare.Item;
import com.cianmcgovern.android.ShopAndShare.R;
import com.cianmcgovern.android.ShopAndShare.Results;
import com.cianmcgovern.android.ShopAndShare.ShopAndShare;
import com.cianmcgovern.android.ShopAndShare.R.id;
import com.cianmcgovern.android.ShopAndShare.R.layout;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity to display the results from the native code execution
 * Displays two arrays with corresponding elements in two ListViews as specified in display_results.xml
 * 
 * @author Cian Mc Govern
 *
 */
public class ListCreator extends ListActivity{

    public void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        
        
        setContentView(R.layout.display_results);
        
        View v = getLayoutInflater().inflate(R.layout.display_results_buttons, null);
        this.getListView().addFooterView(v);

        ArrayList<Item> products = new ArrayList<Item>();

        Iterator i = Results.getInstance().getProducts().entrySet().iterator();

        while(i.hasNext()){
            Map.Entry x = (Map.Entry)i.next();
            products.add((Item)x.getValue());
        }
        
        ItemAdapter adap = new ItemAdapter(this,R.layout.display_results_row,products);
        setListAdapter(adap);
    }

    @Override
    public void onBackPressed(){
        Intent home = new Intent(this,ShopAndShare.class);
        startActivity(home);
        finish();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Item product = (Item) getListAdapter().getItem(position);
        Intent in = new Intent(this,EditItem.class);
        in.putExtra("Product", product.getProduct());
        startActivity(in);
        finish();
    }

    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.layout.results_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
        case R.id.exit:
            finish();
            return true;
        case R.id.save:
            try {
                Results.getInstance().saveCurrentResults();
                finish();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}