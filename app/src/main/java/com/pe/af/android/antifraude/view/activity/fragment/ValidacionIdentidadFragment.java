package com.pe.af.android.antifraude.view.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.TipoDocumentoModel;
import com.pe.af.android.antifraude.view.activity.MenuActivity;
import com.pe.af.android.data.repository.UsuarioDataRepository;
import com.pe.af.android.domain.entity.request.IdentidadRequest;
import com.pe.af.android.domain.repository.UsuarioRepository;

import java.util.ArrayList;

public class ValidacionIdentidadFragment extends BaseFragment implements View.OnClickListener {

    private Context context;
    private UsuarioRepository usuarioRepository;
    private Spinner sp_tipo_documento;
    private EditText edt_nro_documento;
    private Button btn_continuar;
    private OnItemClickListener onItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_validacion_identidad, container, false);
        setHasOptionsMenu(true);

        context = ((MenuActivity) getActivity());
        usuarioRepository = new UsuarioDataRepository(context);

        ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.adm_pregunta_title));
        ((MenuActivity) getActivity()).getSupportActionBar().getThemedContext();


        sp_tipo_documento = (Spinner) view.findViewById(R.id.sp_tipo_documento);
        edt_nro_documento = (EditText) view.findViewById(R.id.edt_nro_documento);
        btn_continuar = (Button) view.findViewById(R.id.btn_continuar);

        btn_continuar.setOnClickListener(this);

        getViews();
        return view;
    }

    private void getViews() {
        // Construct the data source
        ArrayList<TipoDocumentoModel> list = new ArrayList<TipoDocumentoModel>();
        list.add(new TipoDocumentoModel("DNI","DNI"));
        list.add(new TipoDocumentoModel("CE", "Carnet Extranjería"));
        // Create the adapter to convert the array to views
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipo_documento.setAdapter(adapter);
    }

    public interface OnItemClickListener {
        void onClickValidacionIdentidad(IdentidadRequest identidadRequest);
    }

    @Override
    public void onClick(View v) {
        String mensaje = validar();
        if (mensaje.length() > 0) {
            showErrorFragment(mensaje);
            return;
        }
        IdentidadRequest identidadRequest = new IdentidadRequest();
        identidadRequest.setTipoDocumento(((TipoDocumentoModel) sp_tipo_documento.getSelectedItem()).getId());
        identidadRequest.setNroDocumento(edt_nro_documento.getText().toString());
        onItemClickListener.onClickValidacionIdentidad(identidadRequest);
    }


    private String validar() {
        String result = "";
        if (edt_nro_documento.getText().toString() == null ||
                edt_nro_documento.getText().toString() == "") {
            result = "El campo Nro de Documento no puede estar vacío.";
            return result;
        }
        if (edt_nro_documento.getText().toString().length() !=8 &&
                ((TipoDocumentoModel) sp_tipo_documento.getSelectedItem()).getId().equalsIgnoreCase("DNI")) {
            result = "El campo Nro de Documento es incorrecto.";
            return result;
        }
        if (edt_nro_documento.getText().toString().length() <9 &&
                ((TipoDocumentoModel) sp_tipo_documento.getSelectedItem()).getId().equalsIgnoreCase("CE")) {
            result = "El campo Nro de Documento es incorrecto.";
            return result;
        }
        return result;
    }

    //Override this function as below to set fragmentInterfacer
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onItemClickListener = (OnItemClickListener) context;
    }
}
