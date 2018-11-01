package com.ibm.watson.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Response<E> {
	
	private int status;
	private String errorCode;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String stacktrace;
	
	private List<String> messages;
	
	private List<E> data;
	
	private Response(Response.Builder<E> builder) {
		super();
		this.status = builder.status;
		this.errorCode = builder.errorCode;
		this.stacktrace = builder.stacktrace;
		this.messages = builder.messages;
		this.data = builder.data;
	}
	
	public static <E> Response.Builder<E> status(int status) {
		return new Response.Builder<E>(status);
	}

	public int getStatus() {
		return this.status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public List<String> getMessages() {
		return this.messages;
	}

	public List<E> getData() {
		return this.data;
	}
	
	public final static class Builder<E> {
		
		private int status;
		private String errorCode;
		private String stacktrace;
		private Exception exception;
		private List<String> messages = new ArrayList<String>();
		private List<E> data = new ArrayList<E>();
		
		private Builder() {
			super();
		}
		
		private Builder(int status) {
			this.status = status;
		}

		public Builder<E> exception(Exception exception) {
			this.exception = exception;
			return this;
		}

		public Builder<E> messages(List<String> messages) {
			this.messages = messages;
			return this;
		}
		
		public Builder<E> message(String message) {
			this.messages.add(message);
			return this;
		}

		public Builder<E> data(E data) {
			this.data.add(data);
			return this;
		}
		
		public Builder<E> data(List<E> data) {
			this.data.addAll(data);
			return this;
		}
		
		public ResponseEntity<Response<E>> build() {
			
			if ( this.status >= 400 ) {
				this.errorCode = generateErrorCode();
			}
			
			if ( this.exception != null ) {

				this.stacktrace = ExceptionUtils.getFullStackTrace( this.exception );
			}
			
			return new ResponseEntity<Response<E>>(new Response<E>(this), HttpStatus.valueOf(this.status));
		}

	}
	
	private static String generateErrorCode() {
		Random rand = new Random( System.nanoTime() );
		String errorCode = Long.toHexString( rand.nextLong() ).toUpperCase();
		
		return errorCode;
	}
}
