package wiseSaying;

import java.util.Scanner;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;
    private final Scanner sc;

    public WiseSayingController(Scanner sc, WiseSayingService ws) {
        this.sc = sc;
        this.wiseSayingService=ws;
    }

    public void actionStart(){
        if(!wiseSayingService.findAll().isEmpty()) actionList();
    }
    public void actionWrite(){
        System.out.print("명언 : ");
        String content=sc.nextLine();
        System.out.print("작가 : ");
        String author=sc.nextLine();
        WiseSaying ws = wiseSayingService.write(content, author);
        System.out.println(ws.id + "번 명언이 등록되었습니다.");
    }

    public void actionList(String command) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying ws : wiseSayingService.findAll()) {
            System.out.printf("%d / %s / %s\n", ws.getId(), ws.getAuthor(), ws.getContent());
        }
    }

    public void actionDelete(String command){
        int id = Integer.parseInt(command.split("=")[1]);
        boolean success = wiseSayingService.removeById(id);
        if (success) System.out.println(id + "번 명언이 삭제되었습니다.");
        else System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

    public void actionModify(String command){
        int id = Integer.parseInt(command.split("=")[1]);
        WiseSaying ws = wiseSayingService.findById(id);
        if (ws == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + ws.getContent());
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.println("작가(기존) : " + ws.getAuthor());
        System.out.print("작가 : ");
        String author = sc.nextLine();

        wiseSayingService.modify(id, content, author);
        System.out.println(id + "번 명언이 수정되었습니다.");
    }
    public void actionBuild(){
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
