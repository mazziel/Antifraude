package com.pe.af.android.antifraude.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.OpcionModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class PreguntaSubItemAdapter extends RecyclerView.Adapter<PreguntaSubItemAdapter.SubItemViewHolder> {

    private List<OpcionModel> subItemList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, boolean state);
    }

    PreguntaSubItemAdapter(List<OpcionModel> subItemList) {
        this.validateSubList(subItemList);
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pregunta_sub_item, viewGroup, false);
        SubItemViewHolder viewHolder = new SubItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder holder, final int position) {
        final OpcionModel opcion = this.subItemList.get(position);
        holder.tv_opcion.setText(opcion.getOpcion());
        holder.rb_opcion.setChecked(opcion.isEstado());
        holder.rb_opcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(position, true);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (this.subItemList != null && !this.subItemList.isEmpty()) {
            count = this.subItemList.size();
        }
        return count;
    }

    private void validateSubList(List<OpcionModel> subItemList) {
        if (subItemList == null) {
            throw new IllegalArgumentException("The track list cannot be null");
        }
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPreguntaList(List<OpcionModel> subItemList) {
        this.validateSubList(subItemList);
        this.subItemList = subItemList;
        this.notifyDataSetChanged();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rb_opcion)
        RadioButton rb_opcion;

        @InjectView(R.id.tv_opcion)
        TextView tv_opcion;

        SubItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
