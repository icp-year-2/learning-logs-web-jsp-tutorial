# Expression Language (EL)

### The Clean Way to Access Data in JSP

> *"Why write ten lines of scriptlet when `${name}` does the same thing?"*

---

## What Is Expression Language?

EL is a simple syntax introduced in JSP 2.0 for accessing data stored in request, session, and application scopes. Instead of writing Java scriptlets, you use `${}`.

### Before EL (scriptlet)

```jsp
<%
    String name = (String) request.getAttribute("name");
    out.print(name);
%>
```

### With EL

```jsp
${requestScope.name}
```

Same result, far less code.

---

## EL Syntax

Everything goes inside `${ }`:

```jsp
${scope.attributeName}
```

| Part | What It Means |
|------|--------------|
| `${ }` | Marks an EL expression |
| `scope` | Which implicit object to look in (e.g., `requestScope`, `sessionScope`) |
| `.attributeName` | The name of the attribute to retrieve |

---

## Accessing Data from Different Scopes

### Request scope

```jsp
<!-- Servlet sets it -->
<% request.setAttribute("name", "Deepak Pawar"); %>

<!-- EL reads it -->
<p>Name: ${requestScope.name}</p>
```

### Session scope

```jsp
<!-- Servlet sets it -->
<% session.setAttribute("company_name", "ABC Corp"); %>

<!-- EL reads it -->
<p>Company: ${sessionScope.company_name}</p>
```

### Form parameters

```jsp
<!-- Form submits a field named "name1" -->
<form action="output.jsp" method="get">
    <input type="text" name="name1" />
    <button type="submit">Submit</button>
</form>

<!-- On output.jsp — read the parameter -->
<p>You entered: ${param.name1}</p>
```

---

## EL Implicit Objects

| EL Object | What It Accesses | Example |
|-----------|-----------------|---------|
| `requestScope` | Attributes set on the request | `${requestScope.name}` |
| `sessionScope` | Attributes set on the session | `${sessionScope.userId}` |
| `applicationScope` | Attributes set on the application context | `${applicationScope.appName}` |
| `param` | Form/query parameters (single value) | `${param.email}` |
| `paramValues` | Form parameters (multiple values) | `${paramValues.colours[0]}` |
| `pageScope` | Attributes set on the current page | `${pageScope.localVar}` |

---

## EL vs Scriptlet — Comparison

| Task | Scriptlet | EL |
|------|-----------|-----|
| Read request attribute | `<%= request.getAttribute("name") %>` | `${requestScope.name}` |
| Read session attribute | `<%= session.getAttribute("score") %>` | `${sessionScope.score}` |
| Read form parameter | `<%= request.getParameter("email") %>` | `${param.email}` |
| Print a calculation | `<%= 10 + 25 %>` | `${10 + 25}` |

---

## Operators in EL

EL supports basic arithmetic and logical operators directly:

| Operator | Example | Result |
|----------|---------|--------|
| `+` | `${10 + 25}` | `35` |
| `-` | `${20 - 5}` | `15` |
| `*` | `${4 * 3}` | `12` |
| `/` or `div` | `${10 / 3}` | `3.333...` |
| `%` or `mod` | `${10 % 3}` | `1` |
| `==` or `eq` | `${name == 'Deepak'}` | `true` or `false` |
| `>` or `gt` | `${score > 50}` | `true` or `false` |

---

## Shorthand — Skipping the Scope

If you just write `${name}` without a scope prefix, EL searches in this order:

1. `pageScope`
2. `requestScope`
3. `sessionScope`
4. `applicationScope`

It returns the first match. Being explicit with the scope (e.g., `${sessionScope.name}`) is clearer and avoids ambiguity.

---

## Key Takeaways

- EL uses `${}` syntax to access data — much cleaner than scriptlets
- Use `${requestScope.name}` for request attributes, `${sessionScope.name}` for session attributes
- Use `${param.fieldName}` to read form/query parameters directly
- EL supports basic operators for inline calculations
- EL is for **simple expressions** — complex logic still belongs in servlets or scriptlets
- Without a scope prefix, EL searches page → request → session → application
