package wiseSaying;

import org.json.JSONObject;

public class WiseSaying {
    public String content;
    public String author;
    public int id;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("content", content);
        obj.put("author", author);
        return obj;
    }
    public static WiseSaying fromJson(JSONObject obj) {
        int id = obj.getInt("id");
        String content = obj.getString("content");
        String author = obj.getString("author");
        return new WiseSaying(id, content, author);
    }
}