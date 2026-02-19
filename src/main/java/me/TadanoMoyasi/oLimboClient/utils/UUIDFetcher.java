package me.TadanoMoyasi.oLimboClient.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UUIDFetcher {

    public static UUID getUUIDFromName(String name) {
        try {
            URL url = new URI("https://api.mojang.com/users/profiles/minecraft/" + name).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
            String id = json.get("id").getAsString();

            return formatUUID(id);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static UUID formatUUID(String id) {
        String formatted = id.replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{12})",
            "$1-$2-$3-$4-$5"
        );
        return UUID.fromString(formatted);
    }
}