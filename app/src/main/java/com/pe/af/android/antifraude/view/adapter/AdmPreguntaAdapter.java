package com.pe.af.android.antifraude.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.AdmPreguntaModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdmPreguntaAdapter extends RecyclerView.Adapter<AdmPreguntaAdapter.AdmPreguntaViewHolder> {

    private List<AdmPreguntaModel> admPreguntaModelList;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onAdmPreguntaItemClicked(List<AdmPreguntaModel> admPreguntaModels);
    }

    public AdmPreguntaAdapter(Context context, List<AdmPreguntaModel> admPreguntaModelList) {
        this.validateAdmPreguntaList(admPreguntaModelList);
        this.admPreguntaModelList = admPreguntaModelList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ;
    }

    @Override
    public AdmPreguntaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.adm_pregunta_item, parent, false);
        AdmPreguntaViewHolder viewHolder = new AdmPreguntaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdmPreguntaViewHolder holder, int position) {
        final AdmPreguntaModel admPreguntaModel = this.admPreguntaModelList.get(position);
        holder.ckbPregunta.setChecked(admPreguntaModel.isSeleccionada());
        holder.tvPregunta.setText(admPreguntaModel.getPregunta());
        holder.ckbPregunta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(onItemClickListener != null){
                    onItemClickListener.onAdmPreguntaItemClicked(admPreguntaModelList);
                }
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
        if (this.admPreguntaModelList != null && !this.admPreguntaModelList.isEmpty()) {
            count = this.admPreguntaModelList.size();
        }
        return count;
    }

    private void validateAdmPreguntaList(List<AdmPreguntaModel> admPreguntaModelList) {
        if (admPreguntaModelList == null) {
            throw new IllegalArgumentException("The track list cannot be null");
        }
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setAdmPreguntaList(List<AdmPreguntaModel> admPreguntaModelList){
        this.validateAdmPreguntaList(admPreguntaModelList);
        this.admPreguntaModelList = admPreguntaModelList;
        this.notifyDataSetChanged();

    }

    static class AdmPreguntaViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ckbPregunta)
        CheckBox ckbPregunta;

        @InjectView(R.id.tvPregunta)
        TextView tvPregunta;

        public AdmPreguntaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
