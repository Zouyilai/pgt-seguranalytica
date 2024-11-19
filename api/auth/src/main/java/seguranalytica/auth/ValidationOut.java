package seguranalytica.auth;

import java.util.Map;

import lombok.Builder;

@Builder
public record ValidationOut(

    String id,
    Map<String, String> claims
    
) { }