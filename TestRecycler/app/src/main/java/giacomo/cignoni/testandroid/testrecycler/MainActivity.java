package giacomo.cignoni.testandroid.testrecycler;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RVAdapter.OnCardListener {

    private CoordinatorLayout coordinatorLayout;
    private List<Poke> pokeList;
    private ArrayList<Boolean> orderedPokes;
    private ArrayList<Boolean> favoritePokes;
    private static String ORD_BUNDLE_CODE = "ord_pokes_code";
    private static String FAV_BUNDLE_CODE = "fav_pokes_code";



    private void initializeData(boolean[] favoritesArr, boolean[] orderedArr){
        pokeList = new ArrayList<>();
        pokeList.add(new Poke(getString(R.string.mango_name), getString(R.string.mango_ingr), R.drawable.mango_poke));
        pokeList.add(new Poke(getString(R.string.tuna_name), getString(R.string.tuna_ingr), R.drawable.tuna_poke));
        pokeList.add(new Poke(getString(R.string.chicken_name), getString(R.string.chicken_ingr), R.drawable.chicken_poke));
        pokeList.add(new Poke(getString(R.string.vegan_name), getString(R.string.vegan_ingr), R.drawable.vegan_poke));

        orderedPokes = new ArrayList<>();
        favoritePokes = new ArrayList<>();


        if(favoritesArr!=null && favoritesArr.length==pokeList.size()) {
            for (int i = 0; i < favoritesArr.length; i++) {
                favoritePokes.add(favoritesArr[i]);
            }
        }
        else{
            for(Poke p :pokeList){
                favoritePokes.add(false);
            }
        }

        if(orderedArr!=null && orderedArr.length==pokeList.size()) {
            for (int i = 0; i < orderedArr.length; i++) {
                orderedPokes.add(orderedArr[i]);
            }
        }
        else{
            for(Poke p :pokeList){
                orderedPokes.add(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean[] favoritesArr = null;
        boolean[] orderedArr = null;
        if(savedInstanceState!=null){
            favoritesArr = savedInstanceState.getBooleanArray(FAV_BUNDLE_CODE);
            orderedArr = savedInstanceState.getBooleanArray(ORD_BUNDLE_CODE);
        }
        initializeData(favoritesArr, orderedArr);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);



        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(pokeList, this, favoritePokes, orderedPokes);
        rv.setAdapter(adapter);

        coordinatorLayout = this.findViewById(R.id.coordinatorLayout);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        boolean[] favoritesArr = new boolean[favoritePokes.size()];
        for(int i=0; i<favoritePokes.size(); i++) favoritesArr[i] = favoritePokes.get(i);
        boolean[] orderedArr = new boolean[orderedPokes.size()];
        for(int i=0; i<orderedPokes.size(); i++) orderedArr[i] = orderedPokes.get(i);

        outState.putBooleanArray(FAV_BUNDLE_CODE, favoritesArr);
        outState.putBooleanArray(ORD_BUNDLE_CODE, orderedArr);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onOrderClick(int position, Button b) {

        b.setEnabled(false);
        b.setText(R.string.btn_disabled);
        b.setBackgroundColor(ContextCompat.getColor(b.getContext(), R.color.material_on_background_disabled));
        orderedPokes.set(position, true);

        Poke p = pokeList.get(position);


        Snackbar sb = Snackbar.make(coordinatorLayout, getString(R.string.snackbar_text)+
                " "+p.getName(), BaseTransientBottomBar.LENGTH_LONG);

        sb.setAction(R.string.snackbar_undo, v -> {
            orderedPokes.set(position, false);

            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = this.getTheme();
            theme.resolveAttribute(R.attr.colorSecondary, typedValue, true);
            @ColorInt int originalColor = typedValue.data;
            b.setBackgroundColor(originalColor);
            b.setEnabled(true);
            b.setText(R.string.btn_text);

        });

        sb.show();
    }

    @Override
    public void onFavoriteClick(int position, ImageButton ib) {
        if(favoritePokes.get(position)){
            //remove star
            ib.setImageResource(R.drawable.ic_baseline_star_border_24);
            favoritePokes.set(position, false);
        }
        else{
            ib.setImageResource(R.drawable.ic_baseline_star_24);
            favoritePokes.set(position, true);
        }
    }
}
