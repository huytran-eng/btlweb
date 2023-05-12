//package D20PTIT.hoidap.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//
//@Service
//public class JwtService {
//    private static final String SECRET_KEY="2B4B6250655368566D5971337436773979244226452948404D635166546A576E";
//    public String extractUseremail(String token) {
//        return null;
//    }
//    private Claims extractAllClaims(String token){
//        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    private Key getSignInKey() {
//        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
//    }
//}
