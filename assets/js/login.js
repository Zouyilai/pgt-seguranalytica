document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email) {
        Swal.fire("Erro!", "O E-mail é obrigatório!", "error");
        return;
    }

    if (!password) {
        Swal.fire("Erro!", "A senha é obrigatória!", "error");
        return;
    }

    const user = {
        email: email,
        password: password
    };

    try {
        let response = await fetch("http://localhost:8080/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });
    
        if (response.ok) {
            const result = await response.json();
            const token = result.token;

            localStorage.setItem('jwtToken', token);
            localStorage.setItem('logout', 1);

            window.location.href = './dash.html';
        } else {
            Swal.fire("Erro!", "Erro ao realizar login!", "error");
            console.log(response);
            return;
        }
    } catch (ex) {
        Swal.fire("Erro!", "Erro de rede: " + ex.message, "error");
        console.log(ex);
        return;
    }        
});
