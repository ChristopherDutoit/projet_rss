package com.projet_rss.service;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projet_rss.domain.Article;
import com.projet_rss.domain.Feed;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class FeedParserService {

    public Feed parseFeed(String feedUrl) {
        try{
            URL url = new URL(feedUrl);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(url));

            return new Feed(syndFeed.getTitle(), feedUrl);
            
        } catch (Exception e) {
            throw new RuntimeException("Impossible de parser le flux RSS : " + feedUrl, e);
        }
    }
    
    public List<Article> parseArticles(String url) {
    try {
        URL feedSource = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));

        return feed.getEntries().stream()
                .map(entry -> new Article(
                        entry.getTitle(),
                        entry.getLink(),
                        null 
                ))
                .toList();
    } catch (Exception e) {
        throw new RuntimeException("Erreur de parsing des articles pour : " + url, e);
    }
}
}
