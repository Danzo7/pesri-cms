<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aymen
  Date: 12/26/2021
  Time: 10:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <title>Classroom - Register</title>
    <link rel="stylesheet" type="text/css" media="screen" href="styles/main.css">

</head>
<body>

<div class="app">
    <h1 class="text-warning">Classroom - Register</h1>


    <div class="container w-50 p-5" style="background-color: #2C3964">
        <form method="post">
            <div class="form-group">
                <label class="text-light" for="first-name">First name</label>
                <input type="text" class="form-control" id="first-name" name="fName" aria-describedby="first-name" placeholder="Enter your first name">
            </div>
            <div class="form-group">
                <label class="text-light" for="last-name">Last name</label>
                <input type="text" class="form-control" name="lName"  id="last-name" aria-describedby="first-name" placeholder="Enter your last name">
            </div>

            <div class="form-group">
                <label class="text-light" for="emailInput">Email address</label>
                <input type="email" class="form-control" name="email"  id="emailInput" aria-describedby="emailHelp" placeholder="Enter email">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <label class="text-light" for="passwordInput" >Password</label>
                <input type="password" class="form-control " name="password"  id="passwordInput" placeholder="Password">
            </div>
            <button type="submit" class="btn btn-success">Register</button>
            <c:if test="${errorMessage!=null}">
                <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                </div>
            </c:if>
        </form>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
