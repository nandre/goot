package com.goot.utils;

public enum CommentType {
	 IMAGE(0), TEXT(1), SOUND(2);
	 
	 private int code;
	 
	 private CommentType(int c) {
	   code = c;
	 }
	 
	 public int getCode() {
	   return code;
	 }
	 
}