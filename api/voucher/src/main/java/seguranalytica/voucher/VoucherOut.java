package seguranalytica.voucher;

import java.util.List;

import lombok.Builder;

@Builder
public record VoucherOut (

	String id,
	String alias,
	String notBefore,
	String notAfter,
	String revoked,
	Long numberOfDownloads,
	List<VoucherLogOut> useds,
	String creation

) { }
