package giacomo.cignoni.testandroid.testrecycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PokeViewHolder>{

    public static class PokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView pokeName;
        TextView pokeIngr;
        ImageView pokeImg;
        ImageButton starButton;
        Button orderButton;
        OnCardListener onCardListener;

        public PokeViewHolder(View itemView, OnCardListener onCardListener) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            pokeName = (TextView) itemView.findViewById(R.id.poke_name);
            pokeIngr = (TextView) itemView.findViewById(R.id.poke_ingr);
            pokeImg = (ImageView) itemView.findViewById(R.id.poke_img);
            starButton = (ImageButton) itemView.findViewById(R.id.buttonStar);
            orderButton = (Button) itemView.findViewById(R.id.button);

            this.onCardListener = onCardListener;
            starButton.setOnClickListener(this);
            orderButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(v.getId()==R.id.buttonStar){
                ImageButton ib = (ImageButton) v;
                onCardListener.onFavoriteClick(position, ib);
            }
            else if(v.getId()==R.id.button){
                Button b = (Button) v;
                onCardListener.onOrderClick(position, b);
            }

        }
    }

    private List<Poke> pokeList;
    private ArrayList<Boolean> orderedPokes;
    private ArrayList<Boolean> favoritePokes;
    private OnCardListener onCardListener;



    RVAdapter(List<Poke> pokeList, OnCardListener onCardListener, ArrayList<Boolean> favoritePokes, ArrayList<Boolean> orderedPokes){
        this.pokeList = pokeList;
        this.onCardListener = onCardListener;
        this.favoritePokes = favoritePokes;
        this.orderedPokes = orderedPokes;

    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    @Override
    public PokeViewHolder onCreateViewHolder(ViewGroup vg, int i) {
        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.rv_item, vg, false);
        PokeViewHolder pvh = new PokeViewHolder(v, onCardListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PokeViewHolder pokeViewHolder, int i) {
        pokeViewHolder.pokeName.setText(pokeList.get(i).getName());
        pokeViewHolder.pokeIngr.setText(pokeList.get(i).getIngredients());
        pokeViewHolder.pokeImg.setImageResource(pokeList.get(i).getPhotoId());

        if(orderedPokes.get(i)){
            pokeViewHolder.orderButton.setEnabled(false);
            pokeViewHolder.orderButton.setText(R.string.btn_disabled);
            pokeViewHolder.orderButton.setBackgroundColor(ContextCompat.getColor(pokeViewHolder.orderButton.getContext(),
                    R.color.material_on_background_disabled));
        }

        if(favoritePokes.get(i)){
            pokeViewHolder.starButton.setImageResource(R.drawable.ic_baseline_star_24);

        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnCardListener{
        void onOrderClick(int position, Button b);
        void onFavoriteClick(int position, ImageButton ib);
    }

}
