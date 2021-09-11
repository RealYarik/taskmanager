package com.chalyk.taskmanager.config;

import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.security.JWTTokenProvider;
import com.chalyk.taskmanager.security.JwtAuthenticationToken;
import com.chalyk.taskmanager.security.SecurityConstants;
import com.chalyk.taskmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final AccountService accountService;


    @Autowired
    public WebSocketConfiguration(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountService = accountService;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        StompHeaderAccessor.wrap(message);
                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> tokenList = accessor.getNativeHeader(SecurityConstants.HEADER_STRING);
                    accessor.removeNativeHeader(SecurityConstants.HEADER_STRING);
                    String jwt = null;
                    if (tokenList != null && tokenList.size() > 0) {
                        jwt = tokenList.get(0);
                    }
                    Long accountId = jwtTokenProvider.getAccountIdFromToken(jwt.split(" ")[1]);
                    Account account = accountService.findAccountById(accountId);
                    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(account.getAuthorities(), account, jwt.split(" ")[1]);
                    accessor.setUser(jwtAuthenticationToken);
                }
                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            }
        });
    }

}
