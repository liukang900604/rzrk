package com.rzrk.common.product;

public interface ExtractDateStrategy {
	java.sql.Date getExtractDate(int y,int m,int d,int n);
}
