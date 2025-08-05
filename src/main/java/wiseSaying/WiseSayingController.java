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

        if(!wiseSayingService.findAll("all","").isEmpty()) actionList("목록?page=1&keywordType=all");

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
        String[] cmd= command.split("\\?");
        int page=1;
        String keywordType="all";
        String keyword="";
        if(cmd.length>1){
            for(String cmd2:cmd[1].split("&")){
                if(cmd2.startsWith("page")){
                    page=Integer.parseInt(cmd2.split("=")[1]);
                }
                if(cmd2.startsWith("keywordType")){
                    keywordType=cmd2.split("=")[1];
                    System.out.println("검색타입 : " + keywordType);
                }
                if(cmd2.startsWith("keyword")){
                    keyword=cmd2.split("=")[1];
                    System.out.println("검색어 : " + keyword);
                }
            }
        }
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        List<WiseSaying>list=wiseSayingService.findAll(keywordType, keyword);
        if(page==0) page=1;
        int toindex=Math.min(page*5,list.size());
        int fromIndex = toindex - 5;
        if (fromIndex < 0) fromIndex = 0;
        for (int i=fromIndex; i<toindex;i++) {
            System.out.printf("%d / %s / %s\n", list.get(i).getId(), list.get(i).getAuthor(),list.get(i).getContent());
        }
        System.out.print("페이지 : ");
        for(int i=1;i<=list.size()/5;i++){
            if(i==page) System.out.print("[" + i + "]");
            else System.out.print(i + "");
            if(i<=list.size()/5-1) System.out.print(" / ");
        }
        System.out.println();
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
