package seguranalytica.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {

    private static final String AUTH_SERVICE_URL = "http://auth:8080";
    private static final String AUTH_SERVICE_TOKEN_SOLVE = AUTH_SERVICE_URL + "/auth/token/solve";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_BEARER_TOKEN_HEADER = "Bearer";

    private static final String HTTP_HEADER_ID_TOKEN = "id-token";
    private static final String HTTP_HEADER_ID_VOUCHER = "API-Voucher";
    private static final String HTTP_HEADER_ID_ACCOUNT = "id-account";

    @Autowired
    private RouterValidator routerValidator;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!routerValidator.isSecured.test(request)) {
            return chain.filter(exchange);
        }
        if (!isAuthMissing(request)) {
            final String[] parts = this.getAuthHeader(request).split(" ");
            if (parts.length != 2 || !parts[0].equals(AUTHORIZATION_BEARER_TOKEN_HEADER)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header format must be: 'Bearer {token}'");
            }
            return requestAuthTokenSolve(exchange, chain, parts[1]);
        }
        if (!isVoucherMissing(request) && request.getMethod().equals(HttpMethod.GET) && request.getURI().getPath().startsWith("/data/")) {
            return chain.filter(exchange);
        }
        if (request.getMethod().equals(HttpMethod.GET) && (
            // request.getURI().getPath().endsWith("/swagger-ui.html") ||
            // request.getURI().getPath().endsWith("/swagger-ui/index.html") ||
            request.getURI().getPath().endsWith("/api-docs")
        )) {
            return chain.filter(exchange);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization or voucher header");
    }

    private Mono<Void> requestAuthTokenSolve(ServerWebExchange exchange, GatewayFilterChain chain, String jwt) {
        final ValidationIn in = new ValidationIn(jwt);
        return webClient
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
            .post()
            .uri(AUTH_SERVICE_TOKEN_SOLVE)
            .bodyValue(in)
            .retrieve()
            .toEntity(ValidationOut.class)
            .flatMap(response -> {
                if (response != null && response.hasBody() && response.getBody() != null) {
                    final ValidationOut out = response.getBody();
                    this.updateRequest(exchange, out);
                    return chain.filter(exchange);
                } else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                }
            });
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION_HEADER).get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION_HEADER);
    }

    private boolean isVoucherMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HTTP_HEADER_ID_VOUCHER);
    }

    private void updateRequest(ServerWebExchange exchange, ValidationOut out) {
        exchange.getRequest().mutate()
                .header(HTTP_HEADER_ID_TOKEN, out.id())
                .header(HTTP_HEADER_ID_ACCOUNT, out.claims().get("id-account"))
                .build();
    }

}
