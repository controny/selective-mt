import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public class Sample {
    int foo(int number) {
        int a = 1;
        int b = 2;
        boolean c = true;
        boolean d = false;
        boolean e = c && d;

        try{
            //File
        } catch (IOException e){

        }

        if (number == 0) {
            return a + b;
        } else {
            return a - b;
        }
    }
}
