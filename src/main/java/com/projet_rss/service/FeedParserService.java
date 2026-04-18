package com.projet_rss.service;
import java.net.URL;

import org.springframework.stereotype.Service;

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
}
