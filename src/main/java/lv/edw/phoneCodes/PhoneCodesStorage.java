package lv.edw.phoneCodes;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Log
public class PhoneCodesStorage implements ApplicationRunner {

    private Map<Long, String> phoneCodesMap = new HashMap<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Started retrieving information from " + Utils.WIKI_PHONE_CODES_URL);
        Document doc = Jsoup.connect(Utils.WIKI_PHONE_CODES_URL).get();
        Elements allTableRows = doc.body().select(".wikitable.sortable").select("tbody").select("tr");
        allTableRows.forEach(row -> {
            Elements columns = row.select("td");
            if (columns.size() == 4) { //there is rows with less than 4 columns
                Elements codeColumns = columns.get(1).select("a[href]");//second column contains countryCode
                String phoneCodes = codeColumns.text();
                if (StringUtils.isNotEmpty(phoneCodes)) {
                    String[] codes = getSeparatedCodes(phoneCodes);
                    String countryName = columns.get(0).text();//country name stored in first column
                    addCodesToMap(codes, countryName);
                }
            }
        });
        log.info("Retrieved " + phoneCodesMap.size() + " codes");
    }

    private String[] getSeparatedCodes(String phoneCodes) {
        String x = Utils.removeWikiSpecificCharacters(phoneCodes);
        return x.split("\\+");
    }

    private void addCodesToMap(String[] codes, String countryName) {
        Arrays.stream(codes).forEach(code -> {
            if (StringUtils.isNotEmpty(code)) {
                phoneCodesMap.put(Long.valueOf(code), countryName);
            }
        });
    }

    public String getCountryNameByCode(Long code) {
        return phoneCodesMap.get(code);
    }

}
