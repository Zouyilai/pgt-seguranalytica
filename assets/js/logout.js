document.addEventListener('DOMContentLoaded', () => {
    let jwtToken = localStorage.getItem('jwtToken');
    let logout = localStorage.getItem('logout');

    if (!jwtToken) {
        if (!logout) {
            window.location.href = './';
        } else {
            window.location.href = './logout.html';
        }
    }
});

document.getElementById('logout').addEventListener('click', function() {
    localStorage.setItem('logout', 1);
    localStorage.removeItem('jwtToken');
});

