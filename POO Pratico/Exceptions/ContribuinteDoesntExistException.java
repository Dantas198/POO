package Exceptions;

public class ContribuinteDoesntExistException extends Exception{
	public ContribuinteDoesntExistException(String m) {
		super(m);
	}
	
	public ContribuinteDoesntExistException() {
		super();
	}
}
