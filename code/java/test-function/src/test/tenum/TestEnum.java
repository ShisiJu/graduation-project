package test.tenum;

public class TestEnum {
	
	public static void main(String[] args) {
		OrderState applied = OrderState.APPLIED;
		System.out.println(applied);
		String name = applied.name();
		int ordinal = applied.ordinal();
	}

}
