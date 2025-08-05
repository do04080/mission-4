package wiseSaying;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {

    private final Path dataPath = Path.of("db/wiseSaying/wiseSayings.json");
    private final Path lastIdPath = Path.of("db/wiseSaying/lastId.txt");

    public void saveList(List<WiseSaying> list) {
        JSONArray arr = new JSONArray();
        for (WiseSaying ws : list) {
            arr.put(ws.toJson());
        }

        try {
            Files.createDirectories(dataPath.getParent());
            Files.writeString(dataPath, arr.toString(2));
        } catch (IOException e) {
            throw new RuntimeException("명언 리스트 저장 실패", e);
        }
    }

    public List<WiseSaying> loadList() {
        List<WiseSaying> list = new ArrayList<>();

        if (!Files.exists(dataPath)) {
            return list;
        }

        try {
            String json = Files.readString(dataPath);
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                list.add(WiseSaying.fromJson(obj));
            }
        } catch (IOException e) {
            throw new RuntimeException("명언 리스트 불러오기 실패", e);
        }

        return list;
    }

    public void saveLastId(int id) {
        try {
            Files.createDirectories(lastIdPath.getParent());
            Files.writeString(lastIdPath, String.valueOf(id));
        } catch (IOException e) {
            throw new RuntimeException("마지막 ID 저장 실패", e);
        }
    }

    public int loadLastId() {
        if (!Files.exists(lastIdPath)) return 0;

        try {
            return Integer.parseInt(Files.readString(lastIdPath).trim());
        } catch (IOException e) {
            throw new RuntimeException("마지막 ID 불러오기 실패", e);
        }
    }

}
