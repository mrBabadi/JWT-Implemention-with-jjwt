package alibabadi.com.jjwt_implemention;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    JwtGenerator jwtGenerator;
    TextView jwtTxtView;
    Button generateBtn, jwtParserBtn;
    String jwt = "";

    Map<String, Object> header = new HashMap<>();
    Map<String, Object> claims = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jwtTxtView = (TextView) findViewById(R.id.textView);
        generateBtn = (Button) findViewById(R.id.generate_jwt);
        jwtParserBtn = (Button) findViewById(R.id.jwt_parser);

        //babadi :  an instance of JwtGenerator
        jwtGenerator = new JwtGenerator();

        generateBtn.setOnClickListener(this);
        jwtParserBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.generate_jwt:
                jwt = generateJwt();
                break;
            case R.id.jwt_parser:
                showData(jwt);
                break;
        }
    }

    /**
     * Method will Generate Jwt String
     *
     * @return Jwt String
     */
    private String generateJwt() {

        header.put("typ", "JWT");
        claims.put("username", "user_test");
        claims.put("password", "123456");
        claims.put("time", System.currentTimeMillis());

        try {
            String jwtCompact = jwtGenerator.jwtCompact(header, claims);
            jwtTxtView.setText(jwtCompact);

            return jwtCompact;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * Method will show jwt claims (data)
     *
     * @param jwtString {@link String}
     */
    private void showData(String jwtString) {
        if (!jwtString.equalsIgnoreCase("")) {
            String data = jwtGenerator.jwtParser(jwtString);
            jwtTxtView.setText(data);
        } else {
            jwtTxtView.setText("Jwt not generated yet...");
        }


    }
}
