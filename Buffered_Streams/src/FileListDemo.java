import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileListDemo {

	public static void main(String[] args) throws IOException {
		
		
		File file = new File("C:\\Users\\brenin\\Documents\\Imagesdpsp");
		int fileCount = file.list().length;
		String[] files = file.list();
		
		if (fileCount > 100) {
			Arrays.stream(new File("C:\\Users\\brenin\\Documents\\Imagesdpsp").listFiles()).forEach(File::delete);
		}
		
		for(String string: files) {
			
			System.out.println(string);
		}
				
		
		
		

		
		/////////////////////////////////// FILTER finally DATE MODIFIY
		
		//findUsingNIOApi("C:\\Users\\brenin\\Documents\\Imagesdpsp");
		
	//	Path path = Paths.get("C:\\Users\\brenin\\Documents\\Imagesdpsp\\7d744a684fe03ebc7e8de545f97739dd.jpg");

	
		
	}
	
	public static Path findUsingNIOApi(String sdir) throws IOException {
	    Path dir = Paths.get(sdir);
	    if (Files.isDirectory(dir)) {
	        Optional<Path> opPath = Files.list(dir)
	          .filter(p -> !Files.isDirectory(p))
	          .sorted((p1, p2)-> Long.valueOf(p2.toFile().lastModified())
	            .compareTo(p1.toFile().lastModified()))
	          .findFirst();

	        if (opPath.isPresent()){
	        	System.out.println(opPath.get());
	            return opPath.get();
	        }
	    }

	    return null;
	}
	
	
	
	
	
	private Map<Path, ScheduledFuture> futures = new HashMap<>();

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	private static final TimeUnit UNITS = TimeUnit.SECONDS; // your time unit

	public void scheduleForDeletion(Path path, long delay) {
	    ScheduledFuture future = executor.schedule(() -> {
	        try {
	            Files.delete(path);
	        } catch (IOException e) {
	            // failed to delete
	        }
	    }, delay, UNITS);

	    futures.put(path, future);
	}

	public void onFileAccess(Path path) {
	    ScheduledFuture future = futures.get(path);
	    if (future != null) {

	        boolean result = future.cancel(false);
	        if (result) {
	            // reschedule the task
	            futures.remove(path);
	            scheduleForDeletion(path, future.getDelay(UNITS));
	        } else {
	            // too late, task was already running
	        }
	    }
	}
	
	
	
}
