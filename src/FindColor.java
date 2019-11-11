import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

class FindColor {
    JsonObject jsonObject;
    static Set<String> out = new HashSet<>();

    FindColor(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        String st = new String(encoded, StandardCharsets.UTF_8);
        JsonParser parser = new JsonParser();
        jsonObject = parser.parse(st).getAsJsonObject();
    }

    Set<String> find(JsonObject json) {
        for (String key : json.keySet()) {
            if (json.get(key) instanceof JsonObject) {
                find((JsonObject) json.get(key));
            } else if (json.get(key) instanceof JsonArray && json.getAsJsonArray(key).size() == 4 && json.getAsJsonArray(key).get(3).toString().equals("1")) {
                System.out.println(json.getAsJsonArray(key));
                out.add(json.getAsJsonArray(key).toString());
            } else if (json.get(key) instanceof JsonArray) {
                for (JsonElement e : (JsonArray) json.get(key)) {
                    if (e instanceof JsonObject) {
                        find((JsonObject) e);
                    }
                }
            }
        }
    return out;
    }
}
