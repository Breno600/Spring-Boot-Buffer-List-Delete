import java.io.File;
import java.io.FileInputStream;

public class DeleteDemo {

	public static void main(String[] args) {
		
		File file = new File("C:\\Users\\brenin\\Desktop\\codigoMelhorBreno\\Buffered_Streams\\newdata.class");
		
		if (file.delete()) {
			System.out.println(file+"FILE REMOCA");
		}else {
			System.out.println("MAN TA ERRADO CARA");
		}
	}
	
}
