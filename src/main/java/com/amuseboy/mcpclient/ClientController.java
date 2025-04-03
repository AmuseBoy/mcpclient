package com.amuseboy.mcpclient;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.mcp.AsyncMcpToolCallbackProvider;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * mcp例子
 */
@RestController
public class ClientController {


    @Autowired(required = false)
    private OpenAiChatModel openAiChatModel;

    @Autowired(required = false)
    private MethodToolCallbackProvider methodToolCallbackProvider;

    @Autowired(required = false)
    private AsyncMcpToolCallbackProvider asyncMcpToolCallbackProvider;

    @Autowired(required = false)
    private SyncMcpToolCallbackProvider syncMcpToolCallbackProvider;

//    @Autowired
//    private ToolCallbackProvider toolCallbackProvider;

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "北京今天天气怎么样？") String message) {
        return Map.of("generation", this.openAiChatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "北京今天天气怎么样？") String message) {
        return this.openAiChatModel.stream(new UserMessage(message));
    }

    @RequestMapping("/api/client")
    public String chat(@RequestParam(value = "message", defaultValue = "北京今天天气怎么样？") String message){
        ChatClient chatClient = null;
//            chatClient = ChatClient.builder(openAiChatModel).defaultTools("getWeatherByCity").build();
            //System.out.println(toolCallbackProvider.getToolCallbacks().length);
            chatClient = ChatClient.builder(openAiChatModel)
                    .defaultSystem("你可以使用mcp的工具来处理消息")
                    .defaultTools(syncMcpToolCallbackProvider.getToolCallbacks())
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();
        String response = chatClient.prompt().user(message).call().content();
        System.out.println("响应:" + response);
        return response;
    }


}
