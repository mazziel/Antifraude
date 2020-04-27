package com.pe.af.android.antifraude.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.OpcionModel;
import com.pe.af.android.antifraude.model.PreguntaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PreguntaAdapter extends RecyclerView.Adapter<PreguntaAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<PreguntaModel> itemList;

    public PreguntaAdapter(List<PreguntaModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pregunta_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int i) {
        final PreguntaModel item = itemList.get(i);
        itemViewHolder.tv_nr_pregunta.setText(item.getNroPregunta().toString());
        itemViewHolder.tv_pregunta.setText(item.getPregunta());

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rv_sub_item.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.getAlternativas().size());

        // Create sub item view adapter
        final PreguntaSubItemAdapter subItemAdapter = new PreguntaSubItemAdapter(item.getAlternativas());
        subItemAdapter.setOnItemClickListener(new PreguntaSubItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, boolean state) {
                refrescarList(i, position, state);
                subItemAdapter.setPreguntaList(item.getAlternativas());
            }
        });

        itemViewHolder.rv_sub_item.setLayoutManager(layoutManager);
        itemViewHolder.rv_sub_item.setAdapter(subItemAdapter);
        itemViewHolder.rv_sub_item.setRecycledViewPool(viewPool);
    }

    private void refrescarList(int i, int position, boolean state) {
        for (OpcionModel opcionModel : itemList.get(i).getAlternativas()) {
            opcionModel.setEstado(!state);
        }
        itemList.get(i).getAlternativas().get(position).setEstado(state);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void validatePreguntaList(List<PreguntaModel> itemList) {
        if (itemList == null) {
            throw new IllegalArgumentException("The track list cannot be null");
        }
    }

    public void setPreguntaList(List<PreguntaModel> itemList) {
        this.validatePreguntaList(itemList);
        this.itemList = itemList;
        this.notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_nr_pregunta)
        TextView tv_nr_pregunta;
        @InjectView(R.id.tv_pregunta)
        TextView tv_pregunta;
        @InjectView(R.id.rv_sub_item)
        RecyclerView rv_sub_item;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
