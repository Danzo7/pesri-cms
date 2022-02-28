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
                <h5 class="modal-title" id="addModalLabel">${delete!=null?"Delete a student":student!=null?"Edit a student":"add a student"}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <c:if test="${delete==null}">
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
                </c:if>
            <c:if test="${delete!=null}">
                <h6>Are you sure you want to delete <b class="text-warning">${student.lName} ${student.fName} ?</b></h6>
                <form id="deleteForm" method="post">
                    <label style="display: none">
                        <input name="confirm" type="checkbox"  id="confirm" aria-describedby="confirm" value="true" required disabled>
                    </label>
                </form>
            </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-${delete!=null?"danger":student!=null?"warning":"primary"}" form="${delete!=null?"deleteForm":"addStudentForm"}">${delete!=null?"Confirm":student!=null?"Save change":"Add student"}</button>
            </div>
        </div>
    </div>
</div>


<div class="app">
 <div class="header"><h4 class="text-light">welcome ${prof.fName} ${prof.lName}</h4>

 <div class="row-list">
     <a href="${pageContext.request.contextPath}/profile" class="badge badge-info float-lg-right">Profile</a>
     <a href="${pageContext.request.contextPath}/logout" class="badge badge-danger float-lg-right">Logout</a>
 </div>
 </div>

    <div class="tables">
        <div class="header">
            <div class="search-bar">
            <form class="form-inline" method="get" id="search-form" >
                <div class="form-group mx-sm-3 mb-2">
                    <label for="search" class="sr-only">search</label>
                    <input type="search" name="search" class="form-control" id="search" placeholder="Search">
                </div>
            <button type="submit" form="search-form" class="btn btn-primary mb-2">Search</button>
            </form></div>
            <c:if test="${error!=null}">
                <div class="alert alert-danger" style="flex-grow: 1" role="alert">
                    <c:out value="${error}"/>
                </div>
            </c:if>
            <a class=" btn btn-primary" style="float: right" href="?add">add...</a>
        </div>


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
        <td class="row-list">
          <a class="badge badge-info float-lg-right"  href="?id=${studentItem.id}">edit...</a>
          <a class="badge badge-danger float-lg-right" href="?id=${studentItem.id}&delete">delete...</a>

        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  </div>
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
