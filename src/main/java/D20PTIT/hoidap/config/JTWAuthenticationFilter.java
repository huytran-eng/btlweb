//package D20PTIT.hoidap.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//// lớp để kiểm tra người dùng cho mỗi HTTP request
//public class JTWAuthenticationFilter extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    // kiểm tra JWT token của người dùng mỗi lần người dùng gửi request
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        // trường hợp request hiện tại không có jwt token thì tiếp tục
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // có jwt token thì lấy nó ra từ trong authheader
//        jwt= authHeader.substring(7);
//        userEmail=jwtService.extractUseremail(jwt);
//    }
//}
