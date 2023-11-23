import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

public class Sample {
    int isEven(int number) {
        int a = 1;
        int b = 2;
        boolean c = true;
        boolean d = false;
        boolean e = c && d;
        if (number == 0) {
            return a + b;
        } else {
            return a - b;
        }
    }
}
