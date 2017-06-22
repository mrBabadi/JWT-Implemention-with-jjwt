# JWT-Implemention-with-jjwt for Android Application
this is a sample Android application of jwt implemention with JJWT (Java Jason Web Token) library

### Step 1 (Installation)
#### Add <a href = "https://github.com/jwtk/jjwt">JJWT</a> library to your project.

##### Note : JJWT library depends on Jackson.
### Gradle (Jackson installation)
```
compile 'com.fasterxml.jackson.core:jackson-databind:2.8.2'
```
#### If you were faced a META-INF/... error while syncing the project add this : 
```
defaultConfig {
        //...
    }

    packagingOptions {
        exclude 'META-INF/DEPENDENCIES.txt'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/dependencies.txt'
        exclude 'META-INF/LGPL2.1'
    }
```

### Step 2 (Config)
#### Generate JWT 
```
 /**
     * @param header jwt Algorithm type like HS256, you can use other algorithms like HS512, HS384
     * @param claims jwt payloads (your data as Map Object)
     * @return jwt String
     * @throws UnsupportedEncodingException
     */
String jwtCompact(Map<String, Object> header, Map<String, Object> claims) throws UnsupportedEncodingException {
//convert jwt to byte
// using HS256 Algorithm. you can use other algorithms like HS512, HS384
        byte[] data = JWT_KEY.getBytes("UTF-8");

        return Jwts.builder()
                .setHeader(header)
                .signWith(SignatureAlgorithm.HS256, data)
                .setClaims(claims)
                .compact();
    }
```
#### Parsing JWT
```
Claims jwtParser(String jwtString) {
        try {
            byte[] data = JWT_KEY.getBytes("UTF-8");
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(data).parseClaimsJws(jwtString);

            return claimsJws.getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }
```

### Step 3 (Usage)
#### put your header and payloads
```
        header.put("typ", "JWT");
        
        claims.put("username", "user_test");
        claims.put("password", "123456");
        claims.put("time", System.currentTimeMillis());

        try {
            String jwtCompactStr = jwtCompact(header, claims);
        
            return jwtCompactStr;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
```

#### retrieve your data
```
Claims body = jwtParser(jwtString);

body.get("username");;
body.get("password");
```

### proguard-rules.pro
### Note : Don't forget to add these lines for release mode 
```
-keep class org.bouncycastle.** { *; }
-keepnames class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

-keepnames class com.fasterxml.jackson.databind.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keepattributes InnerClasses

-dontwarn io.jsonwebtoken.impl.Base64Codec
```

#Author Ali Babadi
