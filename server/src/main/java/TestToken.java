import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TestToken {

    public static void main(String[] args) {
        TestToken testToken = new TestToken();
        System.out.println(testToken.createNewToken("c2R4bWtqX21vYmlsZTQ4QTkxMzAwMTkyQzJFMTgzODc0N0NFMzk4MTREM0ZG"));
    }

    private String createNewToken(String appId){
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 7200000);
        return Jwts
                .builder()
                .setSubject(appId)
                //.claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now)
                .setIssuer("Online YAuth Builder")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "HengYuAuthv1.0.0")
                .compact();
    }
}
