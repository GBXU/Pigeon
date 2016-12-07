package pigeon.crawler.main;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import pigeon.crawler.bean.WebsiteBean;
import pigeon.crawler.dbutil.DBController;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class WebMagicTest implements PageProcessor{

	private static List<String> mTitleList = new ArrayList<String>();
	private static List<String> mUrlList = new ArrayList<String>();
	// ����һ��ץȡ��վ��������ã��������롢ץȡ��������Դ�����
	String UESRAGENT_PHONE = "Mozilla/5.0 (Linux; U; Android 6.0;zh_cn; Letv X500 Build/DBXCNOP5801810092S) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/49.0.0.0 Mobile Safari/537.36 EUI Browser/5.8.018S"; 
	private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
    		.setUserAgent(UESRAGENT_PHONE)
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUseGzip(true);

    @Override
    // process�Ƕ��������߼��ĺ��Ľӿڣ��������д��ȡ�߼�
    public void process(Page page) {
    	// ���ֶ���������γ�ȡҳ����Ϣ������������
    	mUrlList.addAll(page.getHtml().xpath("//a").links().all());
    	mTitleList.addAll(page.getHtml().xpath("//a/allText()").all());
        
    	//page.putField("url",page.getHtml().xpath("//a").links().all());
    	//page.putField("title", page.getHtml().xpath("//a/text()").all());
//        if (page.getResultItems().get("url") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
        // ����������ҳ�淢�ֺ�����url��ַ��ץȡ
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
    public static void mytest() {
        //single download

//        while(true){
            List<String> mRssList = new ArrayList<String>();
            Connection con=(Connection) DBController.getConnection(); 
            mRssList.addAll(DBController.getDBWebsite(con));
            for(String url:mRssList){
                Spider spider = Spider.create(new WebMagicTest())
    					//.addPipeline(new JsonFilePipeline("J:\\User\\Desktop\\2\\"))
    					.thread(1);
                spider.<ResultItems>get(url);
                DBController.setDBNews(con,mUrlList,mTitleList,url);
                mTitleList.clear();
                mUrlList.clear();
                spider.close();
            }
            DBController.Close();
//            try {
//                Thread.sleep(1000*60*60*2);//2 hours
//                Timestamp mTimestamp = new Timestamp(System.currentTimeMillis()); 
//                System.out.println(mTimestamp+"see you 2 hours later");
//            } catch (InterruptedException e) {
//                e.printStackTrace(); 
//            }
            
//        }
    }

    public static void main(String[] args) {
    	mytest();
	} 	
}
