package crawler;

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

        //提取出电影信息
        Pattern mainPattern = Pattern.compile("div id=\"content\">\\w*.+\\s*<div id=\"celebrities");
        Matcher mainMatcher = mainPattern.matcher(targetStr);
        String mainInfo = "";
        while (mainMatcher.find()) {
            mainInfo = mainMatcher.group();

            //提取电影标题
            Pattern pattern1 = Pattern.compile("v:itemreviewed\">[\\u4e00-\\u9fa5a-zA-Z0-9 ]+");
            Matcher matcher1 = pattern1.matcher(mainInfo);
            while (matcher1.find()) {
                String title = matcher1.group().substring(16);
                movieInfo.append(title+"\n");
            }

            //提取海报图片链接
            Pattern pattern2 = Pattern.compile("img src=\"[a-zA-z]+://[^\\s]* title=\"点击看更多海报");
            Matcher matcher2 = pattern2.matcher(mainInfo);
            while (matcher2.find()) {
                String imgURL = matcher2.group().substring(9,matcher2.group().length()-16);
                movieInfo.append("海报链接：" + imgURL + "\n");
            }

            //提取导演姓名
            Pattern pattrern3 = Pattern.compile("v:directedBy\">[\\u4e00-\\u9fa5a-zA-Z·]+");
            Matcher matcher3 = pattrern3.matcher(mainInfo);
            while (matcher3.find()) {
                String director = matcher3.group().substring(14);
                movieInfo.append("导演：" + director + "\n");
            }

            //提取编剧姓名
            Pattern pattern4 = Pattern.compile("编剧</span>.+span class=\"actor\"");
            Matcher matcher4 = pattern4.matcher(mainInfo);
            while (matcher4.find()) {
                Pattern attrsPattern = Pattern.compile("[\\u4e00-\\u9fa5a-zA-Z·]+</a>");
                Matcher attrsMatcher = attrsPattern.matcher(matcher4.group());
                String attrs = "";
                while (attrsMatcher.find()) {
                    attrs += attrsMatcher.group().substring(0,attrsMatcher.group().length()-4) + " ";
                }
                movieInfo.append("编剧：" + attrs + "\n");
            }

            
        }
        //movieInfo.append(mainInfo);
        return movieInfo.toString();
    }

}
