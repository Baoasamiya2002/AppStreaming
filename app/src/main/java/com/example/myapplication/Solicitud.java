package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class Solicitud {
    private final RequestQueue queue;
    private final String url;
    public static final int MY_DEFAULT_TIMEOUT = 15000;

    public Solicitud(Context context){
        this.queue = VolleySingleton.getInstance(context).getRequestQueue();
        url = context.getResources().getString(R.string.url);
    }

    public void solicitudGet(String ruta, final ResultadoListener listener)
    {
        String rutaCompleta = url;
        if(ruta != null)
            rutaCompleta += ruta;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, rutaCompleta, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(" Eror: ", ""+ error.networkResponse.statusCode);
                        }
                    }
                });
        queue.add(request);
    }

    public void solicitudPost(String ruta, JSONObject jsonObject, final ResultadoListener listener)
    {
        String rutaCompleta = url;
        if(ruta != null)
            rutaCompleta += ruta;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, rutaCompleta, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(" Eror: ", ""+ error.networkResponse.statusCode);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void solicitudArrayPost(String ruta, JSONArray jsonArray, final ResultadoListener listener)
    {
        String rutaCompleta = url;
        if(ruta != null)
            rutaCompleta += ruta;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, rutaCompleta, jsonArray,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        listener.getArrayResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(" Eror: ", ""+ error.networkResponse.statusCode);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void solicitudArrayGet(String ruta, final ResultadoListener listener)
    {
        String rutaCompleta = url;
        if(ruta != null)
            rutaCompleta += ruta;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, rutaCompleta, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        listener.getArrayResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(" Eror: ", ""+ error.networkResponse.statusCode);
                        }
                    }
                });
        queue.add(request);
    }

    public void solicitudPatch(String ruta, JSONObject jsonObject, final ResultadoListener listener)
    {
        String rutaCompleta = url;
        if(ruta != null)
            rutaCompleta += ruta;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, rutaCompleta, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(" Eror: ", ""+ error.networkResponse.statusCode);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void solicitudDelete(String ruta, final ResultadoListener listener)
    {
        String rutaCompleta = url;
        if(ruta != null)
            rutaCompleta += ruta;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, rutaCompleta, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        listener.getResult(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(" Eror: ", ""+ error.networkResponse.statusCode);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
