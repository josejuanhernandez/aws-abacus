package software.factorial.service;

public class Factorial {
	public final int number;
	public final int factorial;

	public Factorial(int number) {
		this.number = number;
		this.factorial = calculate(number);
	}

	private static int calculate(int number) {
		int factorial = 1;
		for (int i = 1; i <= number; i++) factorial *= i;
		return factorial;
	}

}
