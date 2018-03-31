package crawler;

import crawler.ConnectionUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    public String regexMain() {
        String url="https://movie.douban.com/subject/4920389/?from=playing_poster";
        String result = ConnectionUtil.Connect(url);
        return getMovieInfo(result);
    }

    private String getMovieInfo(String targetStr) {

        StringBuilder movieInfo=new StringBuilder();

        return movieInfo.toString();
    }

}
