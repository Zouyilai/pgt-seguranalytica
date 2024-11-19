package seguranalytica.auth;

import java.util.Map;

import lombok.Builder;

@Builder
public record AuthenticationIn (

    String idAccount,
    Map<String, String> claims
    
) { }
