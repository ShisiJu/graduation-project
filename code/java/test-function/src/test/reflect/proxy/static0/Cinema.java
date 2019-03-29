package test.reflect.proxy.static0;

public class Cinema implements Movie{

	private Movie movie;
	
	Cinema(Movie movie){
		this.movie = movie;
	}
	
	@Override
	public void play() {
		System.out.println("cinema start");
		movie.play();
		System.out.println("cinema ads");
	}

}
