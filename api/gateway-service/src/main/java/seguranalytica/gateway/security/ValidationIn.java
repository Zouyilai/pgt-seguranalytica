package seguranalytica.gateway.security;

import lombok.Builder;

@Builder
public record ValidationIn(

    String token
    
) { }
