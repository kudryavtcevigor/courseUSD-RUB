import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test {
    public static void main(String[] args) throws IOException {
        String adress = "https://cbr.ru/scripts/XML_dynamic.asp?date_req1=03/11/2023&date_req2=03/11/2023&VAL_NM_RQ=R01235";
        String value = downloadWebPage(adress);
        String value1 = value.substring(value.indexOf("<Value>") + 7, value.indexOf("</Value>"));
        System.out.println(value1);
    }

    private static String downloadWebPage(String url) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection = new URL(url).openConnection();
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
