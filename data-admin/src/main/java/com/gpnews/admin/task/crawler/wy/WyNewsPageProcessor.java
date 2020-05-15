package com.gpnews.admin.task.crawler.wy;

import com.gpnews.admin.service.InetArticleService;
import com.gpnews.pojo.InetArticle;
import com.gpnews.utils.JsonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author HuangChongHeng
 * @date 2020/3/26
 */
@Component
public class WyNewsPageProcessor implements PageProcessor {
    private final Logger logger = LoggerFactory.getLogger(WyNewsPageProcessor.class);
    private ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

    @Autowired
    private InetArticleService service;

    @Override
    public void process(Page page) {
        String url = page.getUrl().toString();

        if (page.getUrl().toString().matches("^http://news\\.163.com/special/0001220O/news_json\\.js$")) {

            String data = page.getJson().toString();
            data = data.replaceAll("var data=", "");
            data = data.substring(0, data.length() - 1);

            HashMap<String, Object> dataMap = JsonUtil.deserialize(data, HashMap.class);
            List<List<HashMap<String, Object>>> newsListFirst = (List) dataMap.get("news");
            Set<String> hrefs = new HashSet<>();
            // 查询出最后的最后的发布时间
            Date lastDate = null;
            List<InetArticle> lastArticle = service.page(1, null, 1, 1);
            if (lastArticle.size() > 0){
                lastDate = lastArticle.get(0).getPublishTime();
            }

            for (List<HashMap<String, Object>> newsListSecond : newsListFirst) {
                for (HashMap<String, Object> news : newsListSecond) {
                    try {
                        if (lastDate != null && threadLocal.get().parse(news.get("p").toString()).compareTo(lastDate) <= 0){
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hrefs.add(news.get("l").toString());
                }
            }
            page.addTargetRequests(new ArrayList<>(hrefs));

        }else if (page.getUrl().toString().matches("^https://news\\.163\\.com/\\d.*$")){
            Document doc = Jsoup.parse(page.getHtml().getDocument().toString());
            page.putField("title", doc.select("div#epContentLeft h1").text());

            page.putField("username", doc.select("div#epContentLeft a#ne_article_source").text());
            page.putField("url", page.getUrl().toString());
            // 提取正文
            Elements content = doc.select("div#epContentLeft div#endText");
            page.putField("content", content.toString());
            // 提取发布时间
            String timeText = doc.select("div#epContentLeft div.post_time_source").text();
            Pattern timePattern = Pattern.compile("[\\d-]*\\s[\\d:]*");
            Matcher timeMatcher = timePattern.matcher(timeText);
            if (timeMatcher.find()){
                try {
                    page.putField("publishTime", this.threadLocal.get().parse(timeMatcher.group()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private Site site = Site.me()
            .setSleepTime(3000)
            .setRetryTimes(3)
            .setCharset("gb2312")
            .setCycleRetryTimes(3);

    @Override
    public Site getSite() {
        return site;
    }


}
