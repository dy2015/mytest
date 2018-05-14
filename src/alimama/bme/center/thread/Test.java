package alimama.bme.center.thread;

public class Test {

	public static void main(String[] args) {
		Thread t = new Thread(new LogSocketServer());
		t.start();
	}

}
