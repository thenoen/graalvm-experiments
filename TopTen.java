import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TopTen {

	public static void main(String[] args) {

		for(int i = 1; i<3; i++) {
			executeLoad(args[0], false);
		}
		executeLoad(args[0], true);
	}

	private static void executeLoad(String fileName, boolean showOutput) {
		Stopwatch s1 = new Stopwatch("Load file lines");
		s1.start();
		List<String> collect = Stream.of(fileName)
				.flatMap(TopTen::fileLines)
				.collect(Collectors.toList());
		s1.stop();

		Stopwatch s2 = new Stopwatch("Rest of processing");
		s2.start();

		collect.stream()
				.flatMap(line -> Arrays.stream(line.split("\\b")))
				.map(word -> word.replaceAll("[^a-zA-Z]", ""))
				.filter(word -> word.length() > 0)
				.map(word -> word.toLowerCase())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.sorted((a, b) -> -a.getValue().compareTo(b.getValue()))
				.limit(10)
				.collect(Collectors.toList());
//				.forEach(e -> System.out.format("%s = %d%n", e.getKey(), e.getValue()));
		s2.stop();

		if(showOutput) {
			System.out.println("File: " + fileName);
			s1.printTime();
			s2.printTime();
		}
	}

	private static Stream<String> fileLines(String path) {
		try {
			return Files.lines(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static class Stopwatch {

		private String name;
		private long startTime;
		private long stopTime;

		Stopwatch(String name) {
			this.name = name;
		}

		void start(){
			startTime = System.currentTimeMillis();
		}

		void stop() {
			stopTime = System.currentTimeMillis();
		}

		void printTime() {
			float duration = (stopTime - startTime)/1000f;
			System.out.println("Stopwatch: " + name + " - duration: " + duration + " s");
		}
	}

}
