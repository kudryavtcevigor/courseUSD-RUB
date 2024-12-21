import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class findRateMonth {


    public static void main(String[] args) throws Exception {
        System.out.println("Enter month and year format 'mm/yyyy', for example (10/2023)");
        Scanner scan = new Scanner(System.in);
        String period = scan.nextLine();

        int endMonth = 0;
        if (period.substring(0, 2).equals("04") || period.substring(0, 2).equals("06") || period.substring(0, 2).equals("09") || period.substring(0, 2).equals("11")) {
            endMonth = 30;
        } else if (period.substring(0, 2).equals("02")) {
            endMonth = 28;
        } else {
            endMonth = 31;
        }
        String numberPeriod;
        double max = 0, min = 100, ratDoub;
        for (int i = 1; i < endMonth; i++) {
            numberPeriod = "" + i;
            String datePeriod = numberPeriod.length() == 2 ? numberPeriod : "0" + numberPeriod;
            String originalStr = downloadWebPage("https://cbr.ru/scripts/XML_dynamic.asp?date_req1=" + datePeriod + "/" + period +
                    "&date_req2=" + datePeriod + "/" + period + "&VAL_NM_RQ=R01235");
            if (originalStr.contains("<Value>")) {
                String rate = originalStr.substring(originalStr.indexOf("<Value>") + 7, originalStr.indexOf("</Value>"));
                System.out.println("Date  : " + datePeriod + "/" + period + " USD/RUB  " + rate);
                String rateToDouble = rate.replace(",", ".");
                ratDoub = Double.parseDouble(rateToDouble);
                max = ratDoub > max ? ratDoub : max;
                min = ratDoub < min ? ratDoub : min;
            } else {
                System.out.println("Date  : " + datePeriod + "/" + period + " USD/RUB  weekend");
            }
        }
        System.out.println("Maximum course : " + max);
        System.out.println("Minimum course : " + min);
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

