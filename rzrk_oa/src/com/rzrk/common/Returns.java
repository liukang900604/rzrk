package com.rzrk.common;

public class Returns {
	public static class Return2<R1,R2>{
		private R1 r1;
		private R2 r2;
		public Return2(R1 r1,R2 r2) {
			this.r1 = r1;
			this.r2 = r2;
		}
		public R1 getR1(){
			return r1;
		}
		public R2 getR2(){
			return r2;
		}
	}
	public static class Return3<R1,R2,R3>{
		private R1 r1;
		private R2 r2;
		private R3 r3;
		public Return3(R1 r1,R2 r2,R3 r3) {
			this.r1 = r1;
			this.r2 = r2;
			this.r3 = r3;
		}
		public R1 getR1(){
			return r1;
		}
		public R2 getR2(){
			return r2;
		}
		public R3 getR3(){
			return r3;
		}
	}

}
