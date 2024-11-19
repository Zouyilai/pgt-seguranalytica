package seguranalytica.voucher;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "voucher", url = "http://voucher:8080")
public interface VoucherController {

    @PostMapping("/voucher")
    ResponseEntity<VoucherOut> create(
        @RequestHeader(value = "id-account", required = true) String idAccount,
        @RequestBody(required = true) VoucherIn in
    );

    @DeleteMapping("/voucher/{idVoucher}")
    ResponseEntity<Void> revoke(
        @RequestHeader(value = "id-account", required = true) String idAccount,
        @NonNull @PathVariable String idVoucher
    );

    @GetMapping("/voucher/{idVoucher}")
    ResponseEntity<VoucherOut> detail(
        @RequestHeader(value = "id-account", required = true) String idAccount,
        @NonNull @PathVariable String idVoucher
    );

    @GetMapping("/voucher/list")
    ResponseEntity<List<VoucherOut>> listByAccount(
        @RequestHeader(value = "id-account", required = true) String idAccount
    );

    @GetMapping("/voucher/{idVoucher}/consume")
    ResponseEntity<Void> consume(
        @RequestHeader(value = "id-account", required = false) String idAccount,
        @NonNull @PathVariable String idVoucher
    );
    

}
