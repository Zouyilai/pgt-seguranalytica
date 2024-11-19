document.getElementById('register-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const password_confirm = document.getElementById('password-confirm').value;

    if (!username) {
        Swal.fire("Erro!", "O nome é obrigatório!", "error");
        return;
    }

    if (!email) {
        Swal.fire("Erro!", "O E-mail é obrigatório!", "error");
        return;
    }

    if (!password) {
        Swal.fire("Erro!", "A senha é obrigatória!", "error");
        return;
    }

    if (!password_confirm) {
        Swal.fire("Erro!", "Confirme a sua senha!", "error");
        return;
    }

    if (password == password_confirm) {
        const user = {
            name: username,
            email: email,
            password: password
        };

        try {
            let response = await fetch("http://localhost:8080/register", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
        
            if (response.ok) {
                //limpar os dados de entrada
                username.value = "";
                email.value = "";
                password.value = "";
                password_confirm.value = "";

                const result = await response.json();
                const token = result.token;
                
                console.log(token);
                
                Swal.fire("Sucesso!", "Cadastro realizado com sucesso!", "success").then(() => {
                    // Redirecionar após o usuário fechar o alerta
                    window.location.href = './index.html';
                });  
            } else {
                Swal.fire("Erro!", "Erro ao cadastrar o usuário!", "error");
                console.log(response);
                return;
            }
        } catch (ex) {
            Swal.fire("Erro!", "Erro de rede: " + ex.message, "error");
            console.log(ex);
            return;
        }        
    } else {
        Swal.fire({icon: "error", title: "Oops...", text: "As senhas digitadas não correspondem"});
        return;
    };
});