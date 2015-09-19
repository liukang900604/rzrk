package com.rzrk.common.product;

public class ExtractDateStrategyNFJK implements ExtractDateStrategy{
	public java.sql.Date getExtractDate(int y,int m,int d,int n){
//		System.out.println( y+"-"+m+"-"+d);
		if(n<1){
			int _Z = (m-3);
			int Z=1;
			while(true){
				if(12*(Z-1)<=_Z && _Z < 12*Z){
					break;
				}
				Z++;
			}
			int Y = y+n+(Z-1);
			int M = m+3*(n-1)-12*(Z-1);
			int D = d;
			System.out.println( Y+"-"+M+"-"+D);
			return new java.sql.Date(Y-1900, M-1, D);
		}else{
			int _Z = m+3*(n-1);
			int Z=1;
			while(true){
				if(12*(Z-1)<=_Z && _Z < 12*Z){
					break;
				}
				Z++;
			}
			int Y = y+1+(Z-1);
			int M = m+3*(n-1)-12*(Z-1);
			int D = d;
			System.out.println( Y+"-"+M+"-"+D);
			return new java.sql.Date(Y-1900, M-1, D);
		}
	}
}
