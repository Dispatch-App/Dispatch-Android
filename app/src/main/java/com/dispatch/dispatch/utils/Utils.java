package com.dispatch.dispatch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Daniel on 8/8/2015.
 */
public class Utils {

    public static String readFullySync(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder response = new StringBuilder();
        String temp;

        while((temp = bufferedReader.readLine()) != null) {
            response.append(temp);
        }

        return response.toString();
    }
}
