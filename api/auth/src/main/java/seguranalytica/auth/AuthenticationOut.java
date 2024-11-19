package seguranalytica.auth;

import lombok.Builder;

@Builder
public record AuthenticationOut(

    String token

) { }
