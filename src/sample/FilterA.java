package sample;

import java.util.Scanner;

public  class FilterA extends Filter {
 
    public FilterA(Pipe _dataINPipe, Pipe _dataOUTPipe) {
		super();
		this._dataINPipe = _dataINPipe;
		this._dataOUTPipe = _dataOUTPipe;
	}

	Pipe _dataINPipe;
    Pipe _dataOUTPipe;
     
    public String getData(){
        return _dataINPipe.dataOUT();
    }
     
    public void sendData( String tempData){
        _dataOUTPipe.dataIN(tempData);
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		execute();
	}

	@Override
	synchronized void execute() {
		// TODO Auto-generated method stub
		_dataOUTPipe.dataIN( _dataINPipe.dataOUT());
        System.out.println("filter A : Recevoir les donnees du GUI");
	}
}
 