## API
!!swagger-http http://localhost:8080/voucher/api-docs!!

Create Voucher

``` mermaid
sequenceDiagram
    actor User
    User->>+VoucherController: create(idAccount)
    VoucherController->>+VoucherService: create(Voucher)
    VoucherService->>+VoucherService: configura o voucher
    VoucherService->>+VoucherRepository: grava no banco de dados
    VoucherRepository-->>-VoucherService: retorna o código
    VoucherService-->>VoucherController: parser para retornar
    VoucherController-->>User: Retorna o voucher
```
