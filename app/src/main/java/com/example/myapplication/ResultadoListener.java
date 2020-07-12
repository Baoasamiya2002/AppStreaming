package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ResultadoListener {
    void getResult(JSONObject respuesta);
    void getArrayResult(JSONArray respuesta);
}
