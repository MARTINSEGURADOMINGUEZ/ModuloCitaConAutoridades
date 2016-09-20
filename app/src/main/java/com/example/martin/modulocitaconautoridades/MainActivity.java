package com.example.martin.modulocitaconautoridades;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends Activity {

    private Spinner spn1,spn2,spn3,spn4;
    private EditText edt1;
    private Button btn1;

    private ArrayList codigo1  = new ArrayList();
    private ArrayList nombre1 = new ArrayList();

    private ArrayList codigo2  = new ArrayList();
    private ArrayList nombre2 = new ArrayList();

    private ArrayList codigo3  = new ArrayList();
    private ArrayList nombre3 = new ArrayList();

    private ArrayList codigo4  = new ArrayList();
    private ArrayList nombre4 = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spn1 = (Spinner) findViewById(R.id.spinner);
        spn2 = (Spinner) findViewById(R.id.spinner2);
        spn3 = (Spinner) findViewById(R.id.spinner3);
        spn4 = (Spinner) findViewById(R.id.spinner4);

        btn1 = (Button)findViewById(R.id.button);

        CargarFacultades();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }

   public void CargarFacultades(){

                codigo1.clear();
                nombre1.clear();

                AsyncHttpClient client = new AsyncHttpClient();

                client.get("http://examenfinal2016.esy.es/ModuloCafeteriaSimple/ModuloAutoridadesPart1.php", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        if(statusCode==200){

                            try {

                                JSONArray jsonArray = new JSONArray(new String(responseBody));

                                    for(int i=0; i<jsonArray.length(); i++){

                                        codigo1.add(jsonArray.getJSONObject(i).getString("Codigo"));
                                        nombre1.add(jsonArray.getJSONObject(i).getString("Facultad"));

                                    }

                                spn1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nombre1));
                                spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        //SIEMPRE REVISAR LOS CASTEOS DE VARIABLES , PARA EVITAR ERRORES...

                                        String codigocad = String.valueOf(codigo1.get(position));

                                        int codigo = Integer.parseInt(codigocad);

                                        switch (position){

                                            case 0:{
                                                    FiltrarCarrera(codigo);
                                                break;}
                                            case 1:{
                                                FiltrarCarrera(codigo);
                                                break;}
                                            case 2:{
                                                FiltrarCarrera(codigo);
                                                break;}
                                            case 3:{
                                                FiltrarCarrera(codigo);
                                                break;}

                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Toast.makeText(getApplicationContext()," SUPER ERRORR!!!",Toast.LENGTH_LONG).show();

                    }
                });

   }

    public void FiltrarCarrera(int codiFacu){

            nombre2.clear();
            codigo2.clear();

            String codigofacu = String.valueOf(codiFacu);

            AsyncHttpClient client = new AsyncHttpClient();

            String url = "http://examenfinal2016.esy.es/ModuloCafeteriaSimple/ModuloAutoridadesPart2.php";

            RequestParams requestParams = new RequestParams();
            requestParams.add("codigofacu", codigofacu );

            RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    if(statusCode==200){

                        try {

                            JSONArray jsonArray = new JSONArray(new String(responseBody));

                            for(int j=0; j<jsonArray.length();j++){

                                codigo2.add(jsonArray.getJSONObject(j).getString("Codigo"));
                                nombre2.add(jsonArray.getJSONObject(j).getString("Carrera"));

                            }

                            spn2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nombre2));
                            spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    String codigocad = String.valueOf(codigo2.get(position));

                                    int codigo = Integer.parseInt(codigocad);

                                    switch (position){

                                        case 0:{
                                            FiltrarAutoridades(codigo);
                                            break;}
                                        case 1:{
                                            FiltrarAutoridades(codigo);
                                            break;}
                                        case 2:{
                                            FiltrarAutoridades(codigo);
                                            break;}
                                        case 3:{
                                            FiltrarAutoridades(codigo);
                                            break;}

                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

    }

    public void FiltrarAutoridades(int codigoCarrera){

        nombre3.clear();
        codigo3.clear();

        String codigocarre = String.valueOf(codigoCarrera);

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://examenfinal2016.esy.es/ModuloCafeteriaSimple/ModuloAutoridadesPart3.php";

        RequestParams requestParams = new RequestParams();
        requestParams.add("codigocarre", codigocarre );

        RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    for(int k=0; k<jsonArray.length();k++){

                        codigo3.add(jsonArray.getJSONObject(k).getString("Codigo"));
                        nombre3.add(jsonArray.getJSONObject(k).getString("Nombre"));

                    }

                    spn3.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nombre3));
                    spn3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String codigocad = String.valueOf(codigo3.get(position));

                            int codigo = Integer.parseInt(codigocad);

                            switch (position){

                                case 0:{

                                    FiltrarDisponibilidad(codigo);

                                    break;}
                                case 1:{

                                    break;}
                                case 2:{

                                    break;}
                                case 3:{

                                    break;}

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public void FiltrarDisponibilidad(int codigoAutoridad){

        nombre3.clear();
        codigo3.clear();

        String codigoautori = String.valueOf(codigoAutoridad);

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://examenfinal2016.esy.es/ModuloCafeteriaSimple/ModuloAutoridadesPart4.php";

        RequestParams requestParams = new RequestParams();
        requestParams.add("codigoautori", codigoautori );

        RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    for(int l=0; l<jsonArray.length();l++){

                        codigo4.add(jsonArray.getJSONObject(l).getString("DispoID"));
                        nombre4.add(jsonArray.getJSONObject(l).getString("Disponibilidad"));

                    }

                    spn4.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nombre4));
                    spn4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String codigocad = String.valueOf(codigo4.get(position));

                            int codigo = Integer.parseInt(codigocad);

                            switch (position){

                                case 0:{

                                    FiltrarDisponibilidad(codigo);

                                    break;}
                                case 1:{

                                    break;}
                                case 2:{

                                    break;}
                                case 3:{

                                    break;}

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
