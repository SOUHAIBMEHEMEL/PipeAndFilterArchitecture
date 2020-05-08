package sample;
public  class FilterB extends Filter {
 
    Pipe _dataINPipe;
    Pipe _dataOUTPipe;
    
    public FilterB(Pipe _dataINPipe, Pipe _dataOUTPipe) {
		super();
		this._dataINPipe = _dataINPipe;
		this._dataOUTPipe = _dataOUTPipe;
	}
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
		Calcul calcul= new Calcul();
		Trace trace = new Trace(calcul);
		calcul.setTrace(trace);

		String str = _dataINPipe.dataOUT();
		String[] arrOfStr = str.split("-");
		char op = arrOfStr[0].charAt(0);
		int number1 = Integer.parseInt(arrOfStr[1]);
		int number2 = Integer.parseInt(arrOfStr[2]);
		int res;

		if (op=='s'){res= calcul.somme(number1,number2);}
		else {
			if (op=='p'){res= calcul.prod(number1,number2);}
			else {res= calcul.fact(number1);}
		}

		String strOut= ""+op+"-"+number1+"-"+number2+"-"+res;
		_dataOUTPipe.dataIN(strOut);
		System.out.println("filter B : Calcul et envoie des resultat");

	}
}
 