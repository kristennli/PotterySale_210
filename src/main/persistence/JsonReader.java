package persistence;

import model.Sale;
import model.SaleItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Sale from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Sale from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Sale read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSale(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses sale from JSON object and returns it
    private Sale parseSale(JSONObject jsonObject) {
        Sale s = new Sale();
        addSaleItems(s, jsonObject);
        return s;
    }

    // MODIFIES: s
    // EFFECTS: parses items from JSON object and adds them to Sale
    private void addSaleItems(Sale s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextSaleItem = (JSONObject) json;
            addItem(s, nextSaleItem);
        }
    }

    // MODIFIES: s
    // EFFECTS: parses SaleItem from JSON object and adds it to sale
    private void addItem(Sale s, JSONObject jsonObject) {
        String itemPrice = jsonObject.getString("itemPrice");
        String itemID = jsonObject.getString("itemID");
        String itemDescription = jsonObject.getString("itemDescription");
        SaleItem si = new SaleItem(itemID, itemPrice, itemDescription);
        s.addItem(si);
    }
}

