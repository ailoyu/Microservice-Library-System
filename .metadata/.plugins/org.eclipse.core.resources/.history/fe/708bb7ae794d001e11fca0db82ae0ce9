package com.twinkle.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;

@Configuration
public class AxonConfig {
	
	@Bean // config quét tất cả package com.twinkle.** trong commonservice
	public XStream xStream() {
		XStream xStream = new XStream();
		
		xStream.allowTypesByWildcard(new String [] {
				"com.twinkle.**"
		});
		return xStream;
	}
}
