package br.com.alex.featuretoggle

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import nl.basjes.parse.useragent.UserAgentAnalyzer

@Configuration
class ApiConfiguration {

    @Bean
    fun agentParser(): UserAgentAnalyzer {
        return UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withField("OperatingSystemVersion")
                .withCache(10000)
                .build()
    }

}