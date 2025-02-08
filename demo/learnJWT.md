## **1. Understanding the Security Flow**
When a request is sent to your Spring Boot API:
1. The **JWT Authentication Filter** (`JWTAuthFilter`) checks if the request contains a **Bearer token**.
2. If a token is found, the filter extracts the **username** and validates the token.
3. If the token is valid, the user is authenticated and allowed to access secured endpoints.
4. If there’s no token, or it's invalid, the request is **rejected** or forwarded without authentication.

---

## **2. Breaking Down the Code Components**
### **(A) `SecurityConfig`**
📌 **Purpose:** Configures security settings (authentication, token filter, session management, etc.).

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())  // Disables CSRF (needed for stateless JWT authentication)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()  // Allow public access to auth endpoints
            .anyRequest().authenticated()  // All other requests require authentication
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No session storage
        .authenticationProvider(authenticationProvider)  // Set the authentication provider
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before username/password filter

    return http.build();
}
```

🛠 **Key Objects in `SecurityConfig`**:
- **`HttpSecurity`**: Configures Spring Security settings.
- **`SessionCreationPolicy.STATELESS`**: No session will be created (JWT is stateless).
- **`addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`**: Adds the JWT authentication filter before username-password authentication.

---

### **(B) `JWTAuthFilter`**
📌 **Purpose:** Intercepts every HTTP request, extracts and validates the JWT token.

```java
@Component
public class JWTAuthFilter extends OncePerRequestFilter {
```
#### **`OncePerRequestFilter`**
- Ensures that the filter is executed **only once per request**.

```java
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {
```
🛠 **Key Objects in `JWTAuthFilter`**:
- **`HttpServletRequest request`**: Represents the incoming request (can extract headers, params, etc.).
- **`HttpServletResponse response`**: Represents the response that will be sent back.
- **`FilterChain filterChain`**: Allows the request to continue in the security chain.

```java
final String authHeader = request.getHeader("Authorization");
```
- Extracts the **Authorization header** from the request.

```java
if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    filterChain.doFilter(request, response);
    return;
}
```
- If no **Authorization** header exists or doesn't start with `"Bearer "`, the request is forwarded without authentication.

```java
token = authHeader.substring(7); // Remove "Bearer " prefix
username = jwtService.extractUserName(token);
```
- Extracts the **JWT token** and gets the username from it.

```java
if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
```
- If the username exists and the user is not already authenticated, proceed with validation.

```java
UserDetails userDetails = userDetailsService.loadUserByUsername(username);
if (jwtService.validateToken(token, userDetails)) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
}
```
- Loads the user details from the database.
- If the token is **valid**, the user is authenticated and stored in **Spring Security's context**.

---

### **(C) `JWTServiceImpl`**
📌 **Purpose:** Handles **JWT token generation** and **validation**.

#### **Generating the Token**
```java
@Override
public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000))) // 10 minutes expiration
        .signWith(jwtUtil.getKey())  // Sign the token with a secret key
        .compact();
}
```
🛠 **Key Objects in `generateToken`**:
- **`Jwts.builder()`**: Creates a new JWT token.
- **`setClaims(claims)`**: (Optional) Adds extra data to the token.
- **`setSubject(username)`**: Stores the **username** inside the token.
- **`setExpiration(...)`**: Sets the expiration time.
- **`signWith(getKey())`**: Signs the token using a **secret key** for security.

#### **Extracting the Username from a Token**
```java
public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
}
```
- **Extracts the username** from the token.

#### **Extracting a Specific Claim from the Token**
```java
private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
}
```
- Extracts **any specific claim** from the token.

#### **Extracting All Claims**
```java
private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
}
```
- Parses the token and extracts **all claims**.

#### **Validating the Token**
```java
@Override
public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}
```
- **Checks if the token belongs to the user** and **has not expired**.

#### **Checking If Token Has Expired**
```java
private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}
```
- If the expiration date is **before the current date**, the token is expired.

---

## **3. Summary**
| Component               | Purpose                                                                    |
|-------------------------|----------------------------------------------------------------------------|
| `SecurityConfig`        | Configures security rules (authentication, JWT filter, stateless session). |
| `JWTAuthFilter`         | Extracts and validates JWT token from requests.                            |
| `JWTServiceImpl`        | Handles JWT token creation and validation.                                 |
| `Jwts.builder()`        | Creates a JWT token.                                                       |
| `Jwts.parserBuilder()`  | Parses a JWT token.                                                        |
| `SecurityContextHolder` | Stores authenticated user details in the current session.                  |
| `OncePerRequestFilter`  | Ensures the JWT filter runs once per request.                              |

---

### **4. Why Is My Bearer Token Not Working?**
If your JWT authentication is **not working**, check the following:
✅ **Ensure the token is included in the `Authorization` header** (format: `Bearer <token>`).  
✅ **Check if the token is expired** (try reducing expiration time for testing).  
✅ **Ensure the correct secret key is used** for signing and validating the token.  
✅ **Check for exceptions in your logs** (like `JwtException` or `ExpiredJwtException`).

---

### **5. Next Steps**
- Try logging the **JWT token** after generation to verify its structure.
- Debug `JWTAuthFilter` by printing `token` and `username` inside the filter.

Would you like me to add logging to help with debugging? 🚀