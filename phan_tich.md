# Phần 1 — Phân tích logic

## 1. Vai trò của `@RestController`

`@RestController` là annotation dùng để xây dựng RESTful API trong Spring Boot.

Nó là sự kết hợp của:

```java
@Controller
@ResponseBody
```

Khi dùng `@RestController`, dữ liệu trả về từ các method sẽ được tự động chuyển thành JSON thay vì trả về giao diện HTML.

Ví dụ:

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
}
```

Điều này có nghĩa là controller này sẽ xử lý các request REST API có đường dẫn bắt đầu bằng:

```http
/api/auth
```

Trong bài này, ta cần API:

```http
POST /api/auth/register
POST /api/auth/login
```

nên `@RestController` là lựa chọn phù hợp vì client mobile hoặc SPA sẽ giao tiếp bằng JSON.

---

## 2. Vai trò của `@RequestBody`

`@RequestBody` dùng để nhận dữ liệu JSON từ body của HTTP request và ánh xạ vào object Java.

Ví dụ client gửi JSON:

```json
{
  "username": "minh",
  "password": "123456"
}
```

Spring Boot sẽ tự động convert JSON này thành object:

```java
AuthRequest request
```

Thông qua code:

```java
@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody AuthRequest request) {
}
```

Nhờ `@RequestBody`, ta không cần tự đọc JSON thủ công.

---

## 3. Vai trò của `ResponseEntity`

`ResponseEntity` dùng để tùy chỉnh cả:

* dữ liệu response trả về
* HTTP status code
* header nếu cần

Ví dụ đăng ký thành công trả về HTTP `201 Created`:

```java
return ResponseEntity.status(HttpStatus.CREATED)
        .body(new AuthResponse("Đăng ký thành công"));
```

Nếu username đã tồn tại, trả về `409 Conflict`:

```java
return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new AuthResponse("Username đã tồn tại"));
```

Trong bài này, `ResponseEntity` rất quan trọng vì đề yêu cầu trả về status code cụ thể:

| Trường hợp              | HTTP Status        |
| ----------------------- | ------------------ |
| Đăng ký thành công      | `201 Created`      |
| Username đã tồn tại     | `409 Conflict`     |
| Đăng nhập thành công    | `200 OK`           |
| Sai thông tin đăng nhập | `401 Unauthorized` |

---