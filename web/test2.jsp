<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.inject.Injector" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="abacus.persist.dao.AccountDAO" %>
<%@ page import="abacus.guice.JspInjector" %>
<%@ page import="abacus.domain.account.Account" %>
<%@ page import="java.util.List" %>
<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello2</title>
</head>
<body>
<%
    Injector inj = JspInjector.getInjector(pageContext);
    AccountDAO repo = inj.getInstance(AccountDAO.class);
    Account a = repo.getAccount(1000);
    String v = (a!=null) ? a.toString() : "empty";
%>
Hello2 from the new web app.
Here we go: <%= v %>

<%
    List<Account> accounts = repo.getAccounts();
    request.setAttribute("mylist", accounts);
%>
Size: <%= accounts.size()%>
<p></p>
<table>
    <c:forEach var="item" items="${mylist}" varStatus="status1">
        <tr>
            <td>has</td>
            <td><c:out value="${item}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>