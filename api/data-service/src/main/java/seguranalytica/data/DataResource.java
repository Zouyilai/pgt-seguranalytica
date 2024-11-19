package seguranalytica.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import seguranalytica.voucher.VoucherController;

@RestController()
public class DataResource implements DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private VoucherController voucherController;

    @Override
    public ResponseEntity<Resource> download(String idAccount, String idVoucher) {
        try {

            if (idVoucher == null || idVoucher.trim().length() == 0) {
                throw new DataException(HttpStatus.BAD_REQUEST, "id voucher is mandatory");
            }

            // verify the authorization for the resource
            voucherController.consume(idAccount, idVoucher);

            final Path path = dataService.getPath();
                    
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(contentType))
                .contentLength(Files.size(path))
                .header("Content-Disposition", "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
        } catch (FileNotFoundException e) {
            throw new DataException(HttpStatus.NOT_FOUND, e);
        } catch (IOException e) {
            throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}
