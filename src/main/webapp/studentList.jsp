<jsp:useBean id="prof" scope="request" type="com.example.cms.models.Professor"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="initial-scale=1, width=device-width" />
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  <title>Classroom - Student list</title>
  <link rel="stylesheet" type="text/css" media="screen" href="styles/main.css">

</head>
<body>

<div class="modal fade in" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog modal-dialog-centered" role="document" >
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="addModalLabel">Add user</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" id="addStudentForm">
          <div class="form-group">
            <label  for="first-name">First name</label>
            <c:if test="${student!=null}">
              <input type="text" class="form-control" id="first-name" name="fName" aria-describedby="first-name" value="${student.fName}"  required >
            </c:if>
<c:if test="${student==null}">
            <input type="text" class="form-control" id="first-name" name="fName" aria-describedby="first-name" placeholder="Enter the first name"  required >
</c:if>
  </div>
          <div class="form-group">
            <label  for="last-name">Last name</label>
            <c:if test="${student!=null}">
              <input type="text" class="form-control" id="last-name" name="lName" aria-describedby="last-name" value="${student.lName}"  required >
            </c:if>
            <c:if test="${student==null}">
              <input type="text" class="form-control" id="last-name" name="lName" aria-describedby="last-name" placeholder="Enter the last name"  required >
            </c:if>
          </div>
          <div class="form-group">
            <label  for="age">Age</label>
            <c:if test="${student!=null}">
              <input min="18"  max="30" type="number" class="form-control" name="age"  id="age" aria-describedby="age" value="${student.age}"  required >
            </c:if>
<c:if test="${student==null}">
<input min="18"  max="30" type="number" class="form-control" name="age"  id="age" aria-describedby="age" placeholder="Enter the age"  required >
</c:if>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" form="addStudentForm">Save changes</button>
      </div>
    </div>
  </div>
</div>

<div class="app">
 <h4 class="badge badge-info float-lg-right">welcome ${prof.lName} ${prof.fName}</h4>
  <div class="tables">
  <a class=" btn btn-warning"  href="?add">add...</a>

  <table class="table  table-dark table-striped">
    <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First name</th>
      <th scope="col">Last name</th>
      <th scope="col">Age</th>
      <th scope="col">Id</th>
      <th scope="col">Action</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="students" scope="request" type="java.util.List"/>
    <c:forEach var="studentItem" items="${students}" >
      <tr>
        <jsp:useBean id="studentItem" type="com.example.cms.models.Student"/>
        <th scope="row">${students.indexOf(studentItem)}</th>
        <td>${studentItem.fName}</td>
        <td>${studentItem.lName}</td>
        <td>${studentItem.age}</td>
        <td>${studentItem.id}</td>
        <td>
          <a class="badge badge-info float-lg-right"  href="?id=${studentItem.id}">edit...</a>
          <a class="badge badge-error float-lg-right" data-toggle="modal" data-target="#addModal">delete...</a>

        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  </div>
  <a href="${pageContext.request.contextPath}/logout" class="badge badge-info float-lg-right">Logout</a>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="student.js" ></script>
<c:if test="${show!=null}">
  <script type="text/javascript">
    $('.modal').modal('show');

  </script>
</c:if>
</body>
</html>
