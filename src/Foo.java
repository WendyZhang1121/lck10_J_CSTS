import java.io.IOException;

final class Foo {

	private final ThreadLocal<Foo> perThreadInstance =new ThreadLocal<Foo>(); 
	private Helper helper = null;

	public Helper getHelper() {
		if (perThreadInstance.get() == null) {
			createHelper();
		}
		return helper;
	}
	
	private synchronized void createHelper() { 
		if (helper == null) {
			helper = new Helper(5); 
			}
		// Any non-null value can be used as an argument to set()
		perThreadInstance.set(this);
	}
	
	public void testCase(){
		Thread test = new Thread(new Runnable() {
			public void run() {
				Foo testF = new Foo();
				testF.getHelper();
				}
			});
			   test.start();
	}
	
	public void main(String[] args) throws IOException { 
		
		testCase(); // starts thread 1 
		testCase(); // starts thread 2
	}
}
