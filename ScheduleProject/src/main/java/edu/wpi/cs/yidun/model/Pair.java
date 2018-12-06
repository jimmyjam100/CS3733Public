package edu.wpi.cs.yidun.model;

public class Pair<X,Y> {
	private X x;
	private Y y;
	
	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public X getVal1() {
		return x;
	}
	public Y getVal2() {
		return y;
	}
	
	public void setVal1(X x) {
		this.x = x;
	}
	public void setVal2(Y y) {
		this.y = y;
	}
}
