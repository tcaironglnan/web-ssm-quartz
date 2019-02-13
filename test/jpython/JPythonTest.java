package jpython;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author FaceFeel
 * @Created 2019-01-21 21:21
 */
public class JPythonTest {

    public static void main(String[] args) {
        String[] arguments = new String[]{"python", "D:\\test.py", "huzhiwei", "25"};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            int re = process.waitFor();
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}