package pigeon.crawler.bean;

public class NewsBean {
	private String newsData;
	private String websiteUrl;
	private String newsDate;
	private String newsTitle;

	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	
	public String getNewsData() {
		return newsData;
	}
	public void setNewsData(String newsData) {
		this.newsData = newsData;
	}
	
	public String getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}
}