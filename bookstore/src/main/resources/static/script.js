 document.addEventListener("DOMContentLoaded", function() {
     const backButton = document.getElementById("backButton");
     if (backButton) {
         backButton.addEventListener("click", function() {
             window.history.back();
         });
     }


     function goBack() {
         window.history.back();
     }


     function goHome() {
         window.location.href = "index.html";
     }
     });


//     const loginForm = document.getElementById('loginForm');
//     if (loginForm) {
//         loginForm.addEventListener('submit', function(e) {
//             e.preventDefault();
//
//             const email = document.getElementById('email').value;
//             const password = document.getElementById('password').value;
//
//             fetch('http://localhost:8080/auth/login', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json'
//                 },
//                 body: JSON.stringify({ email, password })
//             })
//             .then(response => response.json())  // Потрібно дочекатися відповіді від сервера
//             .then(data => {
//                 if (data) {
//                     localStorage.setItem('user', JSON.stringify(data));  // Зберігаємо користувача в localStorage
//                     alert(`Вітаємо, ${data.name}!`);
//                     window.location.href = 'profile.html';  // Перехід на профіль
//                 } else {
//                     alert('Невірний email або пароль.');
//                 }
//             })
//             .catch(error => console.error('Error:', error));
//         });
//     }
// });
// fetch('http://localhost:8080/auth/login', {
//     method: 'POST',
//     headers: {
//         'Content-Type': 'application/json'
//     },
//     body: JSON.stringify({ email, password })
// })
// .then(response => response.json())
// .then(data => {
//     if (data) {
//         localStorage.setItem('user', JSON.stringify(data));  // Зберігаємо користувача в localStorage
//         alert(`Вітаємо, ${data.name}!`);
//         window.location.href = 'profile.html';
//     } else {
//         alert('Невірний email або пароль.');
//     }
// })
// .catch(error => console.error('Error:', error));


