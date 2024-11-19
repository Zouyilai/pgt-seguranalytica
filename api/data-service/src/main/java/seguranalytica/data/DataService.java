package seguranalytica.data;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Value("${seguranalytica.data.file}")
    private String path;

    public Path getPath() {
        return Paths.get(path);
    }

}
