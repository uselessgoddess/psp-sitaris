<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        response.sendRedirect("index.html"); // Укажите нужный URL для редиректа
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="style-manager.css">
     <style>
            /* Центрирование контейнера */
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background: linear-gradient(135deg, #4e54c8, #8f94fb);
            }

            /* Контейнер для формы */
            .login-container {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
                width: 100%;
            }

            .login-box {
                background: #fff;
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
                text-align: center;
                width: 300px;
            }

            .login-box h1 {
                margin-bottom: 1.5rem;
                font-size: 24px;
                color: #333;
            }

            /* Поля ввода */
            .form-group {
                margin-bottom: 1rem;
                text-align: left;
            }

            label {
                display: block;
                margin-bottom: 0.5rem;
                font-size: 14px;
                color: #555;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 0.5rem;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
            }

            /* Кнопка отправки */
            .btn {
                background: #4e54c8;
                color: #fff;
                padding: 0.75rem;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                width: 100%;
                font-size: 16px;
            }

            .btn:hover {
                background: #3e43a8;
            }
        </style>
</head>
<body>
    <div class="login-container">
        <div class="login-box">
            <h1>Welcome Back</h1>
            <form action="login" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn">Login</button>
            </form>
        </div>
    </div>
</body>
</html>
</html>