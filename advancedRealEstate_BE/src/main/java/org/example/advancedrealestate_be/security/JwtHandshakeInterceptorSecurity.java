//package org.example.advancedrealestate_be.security;
//
//import org.example.advancedrealestate_be.config.CustomJwtDecoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//@Component
//public class JwtHandshakeInterceptorSecurity implements HandshakeInterceptor {
//
//    @Autowired
//    private CustomJwtDecoder customJwtDecoder;
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
//        String authHeader = request.getHeaders().getFirst("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String jwtToken = authHeader.substring(7);
//            try {
//                Jwt jwt = customJwtDecoder.decode(jwtToken);
//
//                System.out.println("JWT: " + jwt);
//
//                attributes.put("user", jwt.getSubject());
//                return true;
//            } catch (JwtException e) {
//                return false;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler wsHandler, Exception exception) {
//    }
//}
