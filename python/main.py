import requests
import json

# Obter token/realizar login
uri = 'http://localhost:8080'
response = requests.post(
    uri + '/login',
    json={
        "email": "zou@test.com",
        "password": "123@321"
    }
)
content = json.loads(response.content)
token = content['token']

# Criar voucher
response = requests.post(
    uri + '/voucher',
    headers={
        'Authorization': 'Bearer ' + token
    },
    json={
        "alias": "teste - pgt"
    }
)
content = json.loads(response.content)
voucher = content['id']

# Acessando a base via voucher
response = requests.get(
    uri + '/data',
    headers={
        'API-Voucher': voucher
    }
)

# Realizar download da base em formato xlsx
if response.status_code == 200:
    with open('dados.xlsx', 'wb') as file:
        file.write(response.content)
    print("Arquivo baixado com sucesso!")
else:
    print(f"Erro ao baixar o arquivo: {response.status_code} - {response.text}")