package wiseSaying;

import java.util.Scanner;

public class App {

    Scanner sc = new Scanner(System.in);
    WiseSayingRepository repository = new WiseSayingRepository();
    WiseSayingService service = new WiseSayingService(repository);
    WiseSayingController controller = new WiseSayingController(sc,service);

    public void run(){
        System.out.println("==명언 앱==");
        System.out.println("-----------");
        controller.actionStart();
        while (true) {
            System.out.print("명령)");
            String command=sc.nextLine();
            if(command.equals("등록")) {
                controller.actionWrite();
            } else if(command.startsWith("목록")){
                controller.actionList(command);
            } else if (command.startsWith("삭제?id=")) {
                controller.actionDelete(command);
            }else if (command.startsWith("수정?id=")) {
                controller.actionModify(command);
            } else if (command.equals("빌드")) {
                controller.actionBuild();
            } else if(command.equals("종료")) break;
        }
    }
}
