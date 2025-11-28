package asembly.message_service.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthFeignInterceptor implements RequestInterceptor {

    private static final ThreadLocal<String> authToken = new ThreadLocal<>();

    @Override
    public void apply(RequestTemplate template) {
        String token = authToken.get();
        if (token != null && !token.isEmpty()) {
            template.header("Authorization", "Bearer " + token);
        }
    }

    public static void setAuthToken(String token) {
        authToken.set(token);
    }

    public static void clearAuthToken() {
        authToken.remove();
    }
}