package com.amuseboy.mcpclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpclientApplication.class, args);
    }


//    @Bean
//    ChatClient chatClient(ChatModel chatModel, SyncMcpToolCallbackProvider toolCallbackProvider) {
//        return ChatClient
//                .builder(chatModel)
//                .defaultTools(toolCallbackProvider.getToolCallbacks())
//                .build();
//    }
}
