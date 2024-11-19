## API
!!swagger-http http://localhost:8080/data/api-docs!!

GET DATA

``` mermaid
sequenceDiagram
    autonumber
    actor User
    User->>+Gateway: voucher
    Gateway->>+VoucherController: verifica se voucher é válido
    break se voucher não for válido
        VoucherController-->>+Gateway: 403
        Gateway-->>+User: 403
    end
    Gateway->>+DataController: download
    DataController->>+VoucherController: consume<br/>registra o consumo do recurso
    DataController->>+DataService: getPath
    DataService-->-DataController: returna o path do arquivo
    DataController->>+DataController: recupera arquivo
    DataController-->>-User: retorna o conteúdo
```
