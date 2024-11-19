package seguranalytica.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "data", url = "http://data:8080")
public interface DataController {

    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<?> download(
        @RequestHeader(value = "id-account", required = false) String idAccount,
        @RequestHeader(value = "API-Voucher", required = true) String idVoucher
    );


}
