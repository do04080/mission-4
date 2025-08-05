package wiseSaying;


import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;
    private List<WiseSaying> list;
    private int lastId;

    public WiseSayingService(WiseSayingRepository repository) {
        this.wiseSayingRepository = repository;
        this.list = repository.loadList();
        this.lastId = repository.loadLastId();
    }

    public WiseSaying write(String content, String author) {
        lastId++;
        WiseSaying ws = new WiseSaying(lastId, content, author);
        list.add(ws);

        wiseSayingRepository.saveList(list);
        wiseSayingRepository.saveLastId(lastId);

        return ws;
    }

    public List<WiseSaying> findAll() {
        return list;
    }

    public boolean removeById(int id) {
        boolean removed = list.removeIf(ws -> ws.getId() == id);
        if (removed) wiseSayingRepository.saveList(list);
        return removed;
    }
    public WiseSaying findById(int id){
        for (WiseSaying ws : list){
            if(ws.getId()==id)return ws;
        }
        return null;
    }

    public boolean modify(int id, String newContent, String newAuthor) {
        for (WiseSaying ws : list) {
            if (ws.getId() == id) {
                ws.content = newContent;
                ws.author = newAuthor;
                wiseSayingRepository.saveList(list);
                return true;
            }
        }
        return false;
    }
    public void build() {
        wiseSayingRepository.saveList(list);
        wiseSayingRepository.saveLastId(lastId);
    }
}
