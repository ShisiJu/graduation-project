package test.reflect.proxy.static0;

public class CinemaTest {

	public static void main(String[] args) {
		Movie real = new RealMovie();
		Movie movie = new Cinema(real);
		movie.play();
	}

}
