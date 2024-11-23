document.addEventListener('DOMContentLoaded', () => {
    let jwtToken = localStorage.getItem('jwtToken');
    let logout = localStorage.getItem('logout');

    if (!jwtToken) {
        if (!logout) {
            window.location.href = './logout.html';
            localStorage.setItem('logout', 1);
        } else {
            window.location.href = './';
        }
    }
});

document.getElementById('logout').addEventListener('click', function() {
    localStorage.removeItem('logout');
    localStorage.removeItem('jwtToken');
});