package webmagic.processor.example;

import webmagic.Page;
import webmagic.ResultItems;
import webmagic.Site;
import webmagic.Spider;
import webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class BaiduBaikePageProcessor implements PageProcessor {
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	String UESRAGENT_PHONE = "Mozilla/5.0 (Linux; U; Android 6.0;zh_cn; Letv X500 Build/DBXCNOP5801810092S) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/49.0.0.0 Mobile Safari/537.36 EUI Browser/5.8.018S"; 
    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
    		.setUserAgent(UESRAGENT_PHONE)
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUseGzip(true);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
    	// 部分二：定义如何抽取页面信息，并保存下来
        page.putField("name", page.getHtml().css("dd.lemmaWgt-lemmaTitle-title h1","text").toString());
        page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/text()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        Spider spider = Spider.create(new BaiduBaikePageProcessor())
        		.thread(2);
        String urlTemplate = "http://baike.baidu.com/item/%E5%A4%AA%E9%98%B3%E8%83%BD";
        ResultItems resultItems = spider.<ResultItems>get(urlTemplate);
        System.out.println(resultItems);

        spider.close();
    }
}
