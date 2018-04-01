package crawler;

/**
 * Created by CitrusMaxima on 2018/3/31.
 */
public class Main {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        String url="https://movie.douban.com/subject/4920389/?from=playing_poster";
        System.out.println(crawler.regexMain(url));
    }
}
