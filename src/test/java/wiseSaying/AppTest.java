package wiseSaying;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AppTest {
    public static void clear() {
        new File("db/wiseSaying/wiseSayings.json").delete();
        new File("db/wiseSaying/lastId.txt").delete();
    }

    public static String run(String input) {

        InputStream in = new ByteArrayInputStream(input.strip().getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out, true, StandardCharsets.UTF_8);
        System.setOut(printStream);

        new App().run();

        return out.toString(StandardCharsets.UTF_8);
    }
}
