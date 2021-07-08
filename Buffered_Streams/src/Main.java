import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		File file = new File("data.txt");
		System.out.println("1"+file.getAbsolutePath());
		System.out.println("3"+file.getFreeSpace());
		System.out.println("4"+file.exists());
		System.out.println("5"+file.getParent());
		System.out.println("6"+file.getPath());
		try {
			input = new BufferedInputStream(new FileInputStream(file));
			output = new BufferedOutputStream(new FileOutputStream(file+".jpg"));
			
			for (int i = input.read(); i != -1; i = input.read()) {
				output.write(i);
			}
			output.flush();
		} catch (IOException e) {
			System.out.println(e);
		}finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e2) {
				System.out.println(e2);
			}
		}
	}
	
}
