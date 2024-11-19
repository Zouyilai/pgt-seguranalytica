package seguranalytica.auth;

import lombok.Builder;

@Builder
public record ValidationIn(

    String token
    
) { }
