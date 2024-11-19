package seguranalytica.voucher;

import lombok.Builder;

@Builder
public record VoucherLogOut (

	String id,
	String used,
	String origin

) { }
