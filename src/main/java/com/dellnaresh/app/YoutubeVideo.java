package com.dellnaresh.app;

import com.dellnaresh.exception.DownloadError;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nareshm on 14/06/2015.
 */
public class YoutubeVideo {
    public static final String SCRIPT = "script";
    private String youtubeUrl;
    private Document youtubeDocument;
    public static final String DEFAULT_SEARCH = "\"url_encoded_fmt_stream_map\":(.+?),";

    public YoutubeVideo(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
    public String getSearchPattern(String input){
        return input+":(.+?),";
    }

    public void fetchHtmlContent() {
        try {
            if (youtubeDocument == null) {
                youtubeDocument = Jsoup.connect(youtubeUrl).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URL findDefaultURL() throws MalformedURLException, UnsupportedEncodingException {
        return findURL(DEFAULT_SEARCH);
    }

    private URL findURL(String defaultSearch) throws MalformedURLException, UnsupportedEncodingException {
        String decodedValue = URLDecoder.decode(findValue(defaultSearch), "UTF-8");
        String[] urlStrings = decodedValue.split("url=");
        return new URL(urlStrings[1]);
    }
    public String getTitle(){
        fetchHtmlContent();
        return youtubeDocument.select("title").first().html();
    }
    public String findValue(String searchTerm) throws DownloadError {
        fetchHtmlContent();
        boolean found = false;
        int i = 0;
        String urlString = null;
        while (!found) {
            Element script =null;
            try {
                script=youtubeDocument.select(SCRIPT).get(i); // Get the script part
            }catch (Exception ex){
                throw new DownloadError("Error during download");
            }
            i++;
            Pattern p = Pattern.compile(searchTerm); // Regex for the value of the key
            Matcher m = p.matcher(script.html()); // you have to use html here and NOT text! Text will drop the 'key' part
            while (m.find()) {
                urlString = m.group(1);
                System.out.println("Found Message is:" + urlString);
                found = true;
            }
        }

        return urlString;
    }

}
