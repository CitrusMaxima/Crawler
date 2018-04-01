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

            //提取主演姓名
            Pattern pattern5 = Pattern.compile("主演</span>.+<span class=\"pl\">类型:");
            Matcher matcher5 = pattern5.matcher(mainInfo);
            while (matcher5.find()) {
                Pattern actorPattern = Pattern.compile("[\\u4e00-\\u9fa5a-zA-Z·]+</a>");
                Matcher actorMatcher = actorPattern.matcher(matcher5.group());
                String actors = "";
                while (actorMatcher.find()) {
                    actors += actorMatcher.group().substring(0,actorMatcher.group().length()-4) + " ";
                }
                movieInfo.append("主演：" + actors + "\n");
            }

            //提取电影类型
            Pattern pattern6 = Pattern.compile("v:genre\">[\\u4e00-\\u9fa5]+");
            Matcher matcher6 = pattern6.matcher(mainInfo);
            String genre = "";
            while (matcher6.find()) {
                genre += matcher6.group().substring(9,matcher6.group().length()) + " ";
            }
            movieInfo.append("类型：" + genre + "\n");

            //提取官方网站网址
            Pattern pattern7 = Pattern.compile("官方网站:</span> <a href=\".*?\" ");
            Matcher matcher7 = pattern7.matcher(mainInfo);
            while (matcher7.find()) {
                String URL = matcher7.group().substring(22,matcher7.group().length()-2);
                movieInfo.append("官方网站：" + URL + "\n");
            }

            //提取制片国家/地区
            Pattern pattern8 = Pattern.compile("制片国家/地区:</span> [\\u4e00-\\u9fa5]+");
            Matcher matcher8 = pattern8.matcher(mainInfo);
            while (matcher8.find()) {
                String country = matcher8.group().substring(16);
                movieInfo.append("制片国家/地区：" + country + "\n");
            }

            //提取语言
            Pattern pattern9 = Pattern.compile("语言:</span> [\\u4e00-\\u9fa5]+");
            Matcher matcher9 = pattern9.matcher(mainInfo);
            while (matcher9.find()) {
                String language = matcher9.group().substring(11);
                movieInfo.append("语言：" + language + "\n");
            }

            //提取上映日期
            Pattern pattern10 = Pattern.compile("v:initialReleaseDate\".*片长:");
            Matcher matcher10 = pattern10.matcher(mainInfo);
            while (matcher10.find()) {
                String dateStr = matcher10.group();
                Pattern datePattern = Pattern.compile(">[\\u4e00-\\u9fa50-9-()]+</span>");
                Matcher dateMatcher = datePattern.matcher(dateStr);
                String date = "";
                while (dateMatcher.find()) {
                    date += dateMatcher.group().substring(1,dateMatcher.group().length()-7) + " ";
                }
                movieInfo.append("上映日期：" + date + "\n");
            }

            //提取片长
            
        }
        movieInfo.append(mainInfo);
        return movieInfo.toString();
    }

}
