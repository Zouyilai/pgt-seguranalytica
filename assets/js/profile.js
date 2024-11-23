//add dados do usuário
async function getDados(jwtToken) {
  if (jwtToken) {
    try {
      let response = await fetch("http://localhost:8080/account", {
          method: 'GET',
          headers: {
              'Authorization': `Bearer ${jwtToken}`
          },
      });
  
      if (response.ok) {
          const result = await response.json();
          const name = result.name;
          const email = `E-mail: ${result.email}`;

          document.getElementById('username').innerHTML = name.toUpperCase();
          document.getElementById('email').innerHTML = email;           
      } else {
          Swal.fire("Erro!", "Erro ao localizar o usuário!", "error").then(() => {
            window.location.href = './';
          })
      }
  } catch (ex) {
      Swal.fire("Erro!", "Erro de rede: " + ex.message, "error").then(() => {
        window.location.href = './';
      })
  }        
  }
};

function copy(token) {
const copyButton = document.createElement('button');
copyButton.className = 'copy-button';
copyButton.innerHTML = '<img src="../img/copy.png" alt="Copiar Token">';
copyButton.addEventListener('click', function() {
    navigator.clipboard.writeText(token.id)
        .then(() => Swal.fire("Sucesso!", "Token copiado com sucesso!", "success"))
        .catch(err => Swal.fire("Erro!", "Erro: " + err, "error"));
});
return copyButton;
}

async function deleteToken(token, jwtToken) {
const deleteButton = document.createElement('button');
deleteButton.className = 'copy-button';
deleteButton.innerHTML = '<img src="../img/delete.png" alt="Deletar Token">';
deleteButton.addEventListener('click', function() {
  Swal.fire({
          title: "Tem certeza?",
          text: "Essa ação não poderá ser revertida!!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Sim!"
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        let response = await fetch("http://localhost:8080/voucher/"+token.id, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        if (response.ok) {
          // Swal.fire({
          //     title: "Pronto!",
          //     text: "Token deletado.",
          //     icon: "success",
          //     // timer: 5000, // mantém o alerta visível por 5 segundos
          //     // showCancelButton: false,
          //     showConfirmButton: false
          // }).then(async () => {
          //   console.log("1");
          //   return deleteButton;
          // })
          return deleteButton;
        } else {
            Swal.fire("Erro!", "Erro ao deletar token!", "error");
            console.log(response);
            // return;
        }
      } catch (ex) {
          Swal.fire("Erro!", "Erro de rede: " + ex.message, "error");
          console.log(ex);
          // return;
      }        
    }
  });
});

return deleteButton;
}

async function getData(token) {
  const downloadButton = document.createElement('button');
  downloadButton.className = 'copy-button';
  downloadButton.innerHTML = '<img src="../img/download.png" alt="Baixar Excel">';
  
  downloadButton.addEventListener('click', async function(event) {
    console.log("Iniciando download do Excel...");
    event.preventDefault();
    try {
      let response = await fetch("http://localhost:8080/data", {
          method: 'GET',
          headers: {
              'API-Voucher': token.id,
              'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
          }
      });

      if (!response.ok) {
        throw new Error('Falha na requisição: ' + response.statusText);
      }
  
      const blob = await response.blob();
      const now = new Date();
      const dateString = now.toISOString().replace(/[:]/g, '-').replace(/[T]/g, '_').slice(0, 19); 
      const fileName = `download_${dateString}.xlsx`; 
      
      const downloadUrl = URL.createObjectURL(blob);
  
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.download = fileName;
      document.body.appendChild(link);
  
      link.click();
  
      document.body.removeChild(link);
      URL.revokeObjectURL(downloadUrl);

      localStorage.removeItem('reload');
  
      console.log('Download iniciado');
    } catch (ex) {
        Swal.fire("Erro!", "Erro de rede: " + ex.message, "error");
        console.error("Erro ao realizar o download:", ex.message);
    }
  });
  
  return downloadButton;
}

async function listToken(jwtToken) {
try {
  const response = await fetch('http://localhost:8080/voucher/list', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${jwtToken}`
    },
  });

  if (!response.ok) {
    Swal.fire("Erro!", "Erro ao carregar os tokens!", "error");
    return;
  }

  const result = await response.json();
  const tokenList = document.getElementById('token-list');
  tokenList.innerHTML = '';

  result.forEach(async token => {
    if (!token.revoked) {
      const li = document.createElement('li');
      li.className = 'token-item';

      const aliasDiv = document.createElement('div');
      aliasDiv.className = 'alias';
      aliasDiv.textContent = token.alias;

      const cardDiv = document.createElement('div');
      cardDiv.className = 'token-card';

      const span = document.createElement('span');
      span.className = 'copy-content';
      span.textContent = token.id;

      const buttonGroup = document.createElement('div');
      buttonGroup.className = 'button-group';

      const copyButton = copy(token);
      const deleteButton = await deleteToken(token, jwtToken);
      const downloadButton = await getData(token);

      //buttonGroup.appendChild(downloadButton);
      buttonGroup.appendChild(copyButton);
      buttonGroup.appendChild(deleteButton);

      cardDiv.appendChild(span);
      cardDiv.appendChild(buttonGroup);

      li.appendChild(aliasDiv);
      li.appendChild(cardDiv);
      tokenList.appendChild(li);
    }
  });
} catch (error) {
  console.error('Erro:', error);
}
}

document.addEventListener('DOMContentLoaded', function () {

  let jwtToken = localStorage.getItem('jwtToken');

  if (jwtToken) {
    getDados(jwtToken); // Exibe os dados do usuário
    listToken(jwtToken); // Lista os tokens na interface
  }
});

document.getElementById('create-button').addEventListener('click', async function() {
  let descricao;

  Swal.fire({
      input: "textarea",
      title: "Descrição",
      inputPlaceholder: "Descrição do token...",
      inputAttributes: {
        "aria-label": "Type your message here"
      },
      inputValidator: (value) => {
          if (!value) {
            return "A descrição é obrigatória!";
          }
          descricao = value;
          console.log(descricao)
      },
      showCancelButton: true
  }).then(async (result) => {
      if (result.isConfirmed) {

        const token = {
          alias: descricao
        };
    
        let jwt = localStorage.getItem('jwtToken');
        
        try {
          let response = await fetch("http://localhost:8080/voucher", {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${jwt}`
              },
              body: JSON.stringify(token)
          });
    
          if (response.ok) {
            response.json().then(() => {
              // Swal.fire({
              //   title: "Sucesso!",
              //   text: "Token gerado com sucesso!",
              //   icon: "success",
              //   showConfirmButton: false, 
              //   timer: 2000 
              // });
              // return;
            }).catch(error => {
              console.error("Erro ao processar a resposta JSON:", error);
            });
            // return;
          }
        } catch (ex) {
            Swal.fire("Erro!", "Erro de rede: " + ex.message, "error");
            console.log(ex);
            // return;
        }        

      }
  });
});