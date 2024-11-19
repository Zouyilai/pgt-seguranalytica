package seguranalytica.voucher;

import lombok.Builder;

@Builder
public record VoucherIn (

	String alias,
	String notBefore,
	String notAfter

) { }
