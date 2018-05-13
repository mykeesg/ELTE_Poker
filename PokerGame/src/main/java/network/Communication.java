/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import com.google.gson.Gson;

/**
 *
 * @author iron2414
 */
public class Communication {

    public static String getSocketString(Object obj) {

        Gson gson = new Gson();
 
        return obj.getClass().getSimpleName() + gson.toJson(obj);

    }

}
